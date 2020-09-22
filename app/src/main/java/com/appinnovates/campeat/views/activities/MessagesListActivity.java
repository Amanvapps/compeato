package com.appinnovates.campeat.views.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeBounds;
import androidx.transition.TransitionManager;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.MessageListAdapter;
import com.appinnovates.campeat.listeners.ItemListener;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.model.getAllDealsModel.RestaurantBranchDealsModel;
import com.appinnovates.campeat.services.DealService.AllDealsService;
import com.appinnovates.campeat.services.DealService.RestaurantDealsInterface;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.FilterUtil;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.views.fragments.MainFilterFragment;

import java.util.ArrayList;
import java.util.List;

public class MessagesListActivity extends AppCompatActivity implements View.OnClickListener,ItemListener
        , RestaurantDealsInterface {

    private ImageView imageViewBack,control;
    private RecyclerView recyclerView;
    private List<HomeBranchDealsModel> branchDealsFilterList, branchDealModelList;
    private List<RestaurantBranchDealsModel> restaurantBranchDealsModels;
    private MessageListAdapter messageListAdapter;
    private FrameLayout filterContainer;
    private EditText edtSearch;
    private MainFilterFragment mainFilterFragment;
    private AllDealsService allDealsService;
    private ArrayList<String> restaurantIdList;
    private String mDealType = "0";
    private boolean mIsPopular = false;
    private boolean isDisCounted = false;
    private String disCountType = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        initView();
        initRecyclerView();
        initListener();
        loadFilterFragment();

        restaurantBranchDealsModels = getIntent().getParcelableArrayListExtra(Constant.SUBSCRIPTION_LIST);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpServices();
    }

    private void initView() {
        control = findViewById(R.id.control);
        imageViewBack = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recycler_view);
        filterContainer = findViewById(R.id.frame_layout_filter);
        edtSearch = findViewById(R.id.edt_search);

        branchDealsFilterList = new ArrayList<>();
        branchDealModelList = new ArrayList<>();
        restaurantIdList = new ArrayList<>();

    }
    private void loadFilterFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
         mainFilterFragment = new MainFilterFragment();
         mainFilterFragment.setActionListener(new MainFilterFragment.ActionListener() {
            @Override
            public void onActionListener(boolean isAscending, String price, boolean isDiscounted, String discountType
                    , int distance, String dealType) {
                mDealType = dealType;
                isDisCounted = isDiscounted;
                disCountType = discountType;

                filterContainer.setVisibility(View.GONE);
                getFilteredDeals();
            }

             @Override
             public void onCloseListener() {
                 filterContainer.setVisibility(View.GONE);
             }
         });
        ChangeBounds transition = new ChangeBounds();
        transition.setDuration(125);
        TransitionManager.beginDelayedTransition(filterContainer, transition);
        fragmentManager.beginTransaction().add(R.id.frame_layout_filter, mainFilterFragment).commit();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        messageListAdapter = new MessageListAdapter(this, R.layout.adapter_message_item,
                branchDealsFilterList,this);
        recyclerView.setAdapter(messageListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initListener() {
        imageViewBack.setOnClickListener(this);


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                branchDealsFilterList.clear();
                branchDealsFilterList.addAll(FilterUtil.getInstance().getSearchedDeals(branchDealModelList,s));
                messageListAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                CommonUtils.hideKeyboard(MessagesListActivity.this);
                return true;
            }
            return false;
        });

        control.setOnClickListener(v -> {
            filterContainer.setVisibility(View.VISIBLE);
            mainFilterFragment.priceLayout.setVisibility(View.GONE);
            mainFilterFragment.distanceLayout.setVisibility(View.GONE);
            mainFilterFragment.foodTypeLayout.setVisibility(View.GONE);
            mainFilterFragment.dealTypeLayout.setVisibility(View.VISIBLE);
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
    private void setUpServices(){
        for (RestaurantBranchDealsModel restaurantBranchDealsModel : restaurantBranchDealsModels){
            restaurantIdList.add(restaurantBranchDealsModel.getRestaurantModel().getObjectId());
        }
        allDealsService = AllDealsService.getInstance();
        getFilteredDeals();
    }

    private void getFilteredDeals() {
        CommonUtils.showProgress(this);
        allDealsService.getAllDeals(false, "", isDisCounted, disCountType, 500
                , mIsPopular, mDealType, new ArrayList<String>(), "message", restaurantIdList,"");
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.DEAL, branchDealModelList.get(position));
        intent.putExtra(Constant.BUNDLE,bundle);
        startActivity(intent);
    }

    @Override
    public void onNoDealsAvailable(String message) {
        CommonUtils.hideProgress();
        this.branchDealsFilterList.clear();
        this.branchDealModelList.clear();
        messageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoInternetAvailable() {
        CommonUtils.hideProgress();
        CommonUtils.showToast(this, getString(R.string.no_internet_available));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }


    @Override
    public void onRestaurantDealsFailure(String message) {
        CommonUtils.hideProgress();
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onRestaurantDealsSuccess(List<HomeBranchDealsModel> restaurantDeals) {
        CommonUtils.hideProgress();
        this.branchDealsFilterList.clear();
        this.branchDealModelList.clear();
        this.branchDealModelList.addAll(restaurantDeals);
        this.branchDealsFilterList.addAll(branchDealModelList);
        messageListAdapter.notifyDataSetChanged();
    }
}
