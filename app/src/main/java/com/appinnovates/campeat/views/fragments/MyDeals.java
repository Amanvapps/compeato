package com.appinnovates.campeat.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.MyDealsAdapter;
import com.appinnovates.campeat.listeners.ItemListener;
import com.appinnovates.campeat.model.BookingDealModel;
import com.appinnovates.campeat.bottomSheets.BottomSheetFilter;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.model.ReviewModel;
import com.appinnovates.campeat.services.DealService.AllDealsService;
import com.appinnovates.campeat.services.DealService.RestaurantDealsInterface;
import com.appinnovates.campeat.services.IsDealBookedService.IsDealBookedServiceInterface;
import com.appinnovates.campeat.services.ReviewService.ReviewServiceInterface;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.UserPreferences;
import com.appinnovates.campeat.views.activities.ItemDetailActivity;
import com.appinnovates.campeat.views.activities.MapsActivity;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.parse.Parse.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyDeals extends Fragment implements BottomSheetFilter.OnClickDoneInterface, ItemListener
        , ReviewServiceInterface, RestaurantDealsInterface, IsDealBookedServiceInterface, BottomSheetFilter.BottomSheetListener {


    public MyDeals() {
        // Required empty public constructor
    }

    private RecyclerView resturant_recyclerview;
    private ImageView filters;
    private Context context;
    private List<HomeBranchDealsModel> bookingDealModelsList = new ArrayList<>();
    private List<HomeBranchDealsModel> bookingFilterList = new ArrayList<>();
    private MyDealsAdapter myDealsAdapter;
    private TextView nodeals;
    private AllDealsService allDealsService;
    private Map<String, Object> requestParams;
    private Map<String, Object> filterItems;
    private Map<String, Object> geoPointParams;
    private String reset = "";
    private String checkgroupStandard = "0";
    private int seekbarState = 500;
    private ContentLoadingProgressBar progressBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_deals, container, false);

        ImageView location = view.findViewById(R.id.image_view_location_mydeals);
        progressBar = view.findViewById(R.id.progressBar2);
        nodeals = view.findViewById(R.id.no_deals);
        progressBar.show();
        progressBar.setVisibility(View.VISIBLE);
        filters = view.findViewById(R.id.image_view_filter_mydeals);
        setUp();
        requestParams = new HashMap<>();
        filterItems = new HashMap<>();
        geoPointParams = new HashMap<>();
        String lat = String.valueOf(UserPreferences.getInstance().getFloat(Constant.LATITUDE));
        String longi = String.valueOf(UserPreferences.getInstance().getFloat(Constant.LONGITUDE));

        initField();

        requestParams.put(Constant.USERID, String.valueOf(ParseUser.getCurrentUser().getObjectId()));
        requestParams.put(Constant.FTYPE, new ArrayList<>());
        requestParams.put(Constant.LAT, lat);
        requestParams.put(Constant.LONG, longi);
        requestParams.put(Constant.RADIUS, Constant.RADIUS_NUM);
        requestParams.put(Constant.ORDERBY, Constant.ORDERBYLOCATION);
        requestParams.put(Constant.MAP, Constant.MAP);
        requestParams.put(Constant.STATUS_ID, Constant.ACTIVE);

        filters.setOnClickListener(view1 -> {
            BottomSheetFilter bottomSheet = new BottomSheetFilter(context, this, reset, checkgroupStandard, seekbarState);
            assert getFragmentManager() != null;
            bottomSheet.show(getFragmentManager(), "bottomSheetFilter");
        });
        location.setOnClickListener(view1 -> startActivity(new Intent(getApplicationContext(), MapsActivity.class)
                .putExtra(Constant.FROM_SCREEN, Constant.MYDEALS)));

        resturantRecyclerView(view);
        setAdapter();
        setTextWatcher(view);
        return view;
    }

    void setData(Map<String, Object> map) {
        allDealsService.getMyAllDeals(map);
    }


    private void resturantRecyclerView(View view) {
        resturant_recyclerview = view.findViewById(R.id.mydeals_recyclerview);
        resturant_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        resturant_recyclerview.setHasFixedSize(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        geoPointParams.clear();
        geoPointParams.putAll(requestParams);
        setData(geoPointParams);
    }

    private void initField() {
        bookingDealModelsList = new ArrayList<>();
        bookingFilterList = new ArrayList<>();
    }

    private void setAdapter() {
        Fragment fragment= Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.fragment_container);
        myDealsAdapter = new MyDealsAdapter(getContext(), bookingFilterList, this,fragment);
        resturant_recyclerview.setAdapter(myDealsAdapter);
    }

    private void setTextWatcher(View view) {
        EditText edtSearch = view.findViewById(R.id.edtSearch_mydeals);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    bookingFilterList.clear();
                    for (HomeBranchDealsModel booking : bookingDealModelsList) {
                        if (booking.getmRestaurantIdModel()
                                .getRestaurantName().toLowerCase()
                                .contains(s.toString().toLowerCase())) {
                            bookingFilterList.add(booking);
                        }
                    }
                    myDealsAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                CommonUtils.hideKeyboard(getActivity());
                return true;
            }
            return false;
        });
    }

    private void setUp() {
        allDealsService = AllDealsService.getInstance();
        allDealsService.setDelegateAndContext(this, getApplicationContext());
    }


    @Override
    public void onReviewsSuccess(List<ReviewModel> reviewList) {
        if (reviewList.size() > 0) {                                             //deal used and reviewed
            Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
            boolean isActiveDeal = true;
            intent.putExtra(Constant.isActive, isActiveDeal);
            intent.putExtra(Constant.GetDealAgain, true);
            startActivity(intent);
        }
    }

    @Override
    public void onNoReviews() {
    }

    @Override
    public void onReviewsFailure(String message) {
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(getActivity(), LocaleManager.getLanguage(getActivity()));
    }


    @Override
    public void onRestaurantDealsSuccess(List<HomeBranchDealsModel> restaurantDeals) {
        bookingDealModelsList.clear();
        bookingFilterList.clear();
        nodeals.setVisibility(View.GONE);
        bookingDealModelsList.addAll(restaurantDeals);
        bookingFilterList.addAll(restaurantDeals);
        progressBar.hide();
        myDealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRestaurantDealsFailure(String message) {
        progressBar.hide();
        progressBar.setVisibility(View.GONE);
        nodeals.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNoDealsAvailable(String message) {
        progressBar.hide();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNoInternetAvailable() {
        progressBar.hide();

    }

    @Override
    public void onIsDealBooked(BookingDealModel booking, int index, int deal) {

    }

    @Override
    public void onIsDealNotBooked(int index, int branchDealIndex) {

    }

    @Override
    public void onIsDealBookedFailure(String message) {

    }

    @Override
    public void onFilter(String text) {

    }

    @Override
    public void onClickDoneListener(boolean isDiscounted, String discountType, int distance) {
        bookingDealModelsList.clear();
        bookingFilterList.clear();
        geoPointParams.clear();
        geoPointParams.putAll(requestParams);
        progressBar.show();
        if (!discountType.equalsIgnoreCase("0")) {
            filterItems.put("discountType", discountType);
            checkgroupStandard = discountType;
            filters.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon_orange));
        }
        if (distance >= 1) {
            filterItems.put(Constant.RADIUS, String.valueOf(distance));
            seekbarState = distance;
            filters.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon_orange));
        }

        if (filterItems.size() != 0) {
            geoPointParams.put("filterItems", filterItems);
        }

        setData(geoPointParams);
        myDealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
        HomeBranchDealsModel restaurant = bookingFilterList.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("restaurants", restaurant);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void filterReset() {
        bookingFilterList.clear();
        reset = Constant.HOME;
        checkgroupStandard = "";
        seekbarState = 500;
        geoPointParams.clear();
        geoPointParams.putAll(requestParams);
        setData(geoPointParams);
        filters.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon));
    }
}
