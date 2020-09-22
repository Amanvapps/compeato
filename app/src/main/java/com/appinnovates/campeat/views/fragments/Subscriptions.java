package com.appinnovates.campeat.views.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.SubscribeAdapter;
import com.appinnovates.campeat.bottomSheets.BottomSheetFilter;
import com.appinnovates.campeat.listeners.ItemListener;
import com.appinnovates.campeat.model.FavoriteRestaurantModel;
import com.appinnovates.campeat.services.FavoriteRestaurantService.FavouriteRestaurantInterface;
import com.appinnovates.campeat.services.FavoriteRestaurantService.FavouriteRestaurantService;
import com.appinnovates.campeat.services.FavoriteRestaurantService.FavouriteRestaurantServiceDelegate;
import com.appinnovates.campeat.utils.AnalyticUtil;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.UserPreferences;
import com.appinnovates.campeat.views.activities.MapsActivity;
import com.appinnovates.campeat.views.activities.SubscibeDetailActivity;
import com.parse.ParseObject;
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
public class Subscriptions extends Fragment implements ItemListener, FavouriteRestaurantInterface, BottomSheetFilter.BottomSheetListener, BottomSheetFilter.OnClickDoneInterface, SubscribeAdapter.onSubscribeInterFace, FavouriteRestaurantServiceDelegate {

    private RecyclerView recyclerView;
    private ImageView imageViewFilter;
    private TextView textViewDefault;
    private List<FavoriteRestaurantModel> favRestaurantModelsList, favFilterList;
    private SubscribeAdapter subscribeAdapter;
    private EditText edtSearch;
    private int mDistance = 10000;
    private Context context;
    ImageView location;
    private String reset = "";
    ContentLoadingProgressBar progressBar;
    ArrayList<String> restaurantIds;
    private Map<String, Object> requestParams = new HashMap<>();
    private Map<String, Object> geoPointParams = new HashMap<>();
    private Map<String, Object> filterItemsParams = new HashMap<>();
    private Map<String, Object> filterItems = new HashMap<>();
    private FavouriteRestaurantService favouriteRestaurantService;
    private String checkgroupStandard = "";
    private int seekbarState = 500;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            favouriteRestaurantService.getMyFavorites(requestParams);
        }
    };

    public Subscriptions() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        getMyFavRestaurant(geoPointParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subscriptions, container, false);
        initField();
        geoPointParams.put("user_id", ParseUser.getCurrentUser().getObjectId());
        geoPointParams.put("type", "0");
        geoPointParams.put("ftype", new ArrayList<>());
        geoPointParams.put("lat", String.valueOf(UserPreferences.getInstance().getFloat(Constant.LATITUDE)));
        geoPointParams.put("long", String.valueOf(UserPreferences.getInstance().getFloat(Constant.LONGITUDE)));
        geoPointParams.put("radius", mDistance);
        boolean mRbSubDate = false;
        geoPointParams.put("subdate", mRbSubDate);
        boolean mRbDealUpdate = false;
        geoPointParams.put("dupdate", mRbDealUpdate);
        geoPointParams.put("orderBy", "location");
        geoPointParams.put("orderType", "desc");
        requestParams.clear();
        requestParams.putAll(geoPointParams);
        initView(view);
        setDeligates();
        setAdapter();
        setListener();
        setTextWatcher(view);

        return view;
    }

    public void setDeligates() {
        favouriteRestaurantService = FavouriteRestaurantService.getInstance();
        favouriteRestaurantService.setDelegate(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).unregisterReceiver(mReceiver);
    }


    private void initField() {
        favRestaurantModelsList = new ArrayList<>();
        favFilterList = new ArrayList<>();
        restaurantIds = new ArrayList<>();
        filterItems = new HashMap<>();
    }

    private void setListener() {

        location.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), MapsActivity.class);
            intent.putExtra(Constant.FROM_SCREEN, Constant.SUBSCRIPTION);
            intent.putStringArrayListExtra(Constant.RestaurantID, restaurantIds);
            startActivity(intent);
        });
        imageViewFilter.setOnClickListener(view -> {
            BottomSheetFilter bottomSheet = new BottomSheetFilter(context, this, reset, checkgroupStandard, seekbarState);
            assert getFragmentManager() != null;
            bottomSheet.show(getFragmentManager(), "bottomSheetFilter");

        });
    }

    private void setAdapter() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        subscribeAdapter = new SubscribeAdapter(getActivity(), favFilterList, R.layout.my_subscription_list_item, this, this);
        recyclerView.setAdapter(subscribeAdapter);
    }

    private void initView(View view) {
        progressBar = view.findViewById(R.id.progressBar2);
        recyclerView = view.findViewById(R.id.recycler_view);
        location = view.findViewById(R.id.image_location_icon);
        textViewDefault = view.findViewById(R.id.text_view_default);
        imageViewFilter = view.findViewById(R.id.image_view_filter_icon);
    }

    private void setTextWatcher(View view) {
        edtSearch = view.findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                favFilterList.clear();
                for (FavoriteRestaurantModel restaurantModel : favRestaurantModelsList) {
                    if (restaurantModel.getRestaurantModel().getRestaurantName().toLowerCase().contains(s.toString().toLowerCase())) {
                        favFilterList.add(restaurantModel);
                    }
                }
                subscribeAdapter.notifyDataSetChanged();
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

    private void getMyFavRestaurant(Map<String,Object> map) {
        progressBar.show();
        favRestaurantModelsList.clear();
        favFilterList.clear();
        subscribeAdapter.notifyDataSetChanged();
        favouriteRestaurantService = FavouriteRestaurantService.getInstance();
        favouriteRestaurantService.setFavoriteRestaurantInterface(this, getActivity());
        favouriteRestaurantService.getMyFavorites(map);
    }


    @Override
    public void onFavouriteRestaurantSuccess(List<FavoriteRestaurantModel> favRestaurantModels) {
        progressBar.hide();
        recyclerView.setVisibility(View.VISIBLE);
        textViewDefault.setVisibility(View.GONE);
        favRestaurantModelsList.clear();
        favFilterList.clear();
        favRestaurantModelsList.addAll(favRestaurantModels);
        favFilterList.addAll(favRestaurantModelsList);
        subscribeAdapter.notifyDataSetChanged();

        for (FavoriteRestaurantModel restaurantModel : favRestaurantModelsList) {
            restaurantIds.add(restaurantModel.getRestaurantModel().getObjectId());
        }
    }

    @Override
    public void onFavouriteRestaurantFailure(String message) {
        progressBar.hide();
        recyclerView.setVisibility(View.GONE);
        textViewDefault.setVisibility(View.VISIBLE);
        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoInternetAvailable() {
        progressBar.hide();
        Toast.makeText(context, "" + getString(R.string.no_internet_available), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(getActivity(), LocaleManager.getLanguage(getActivity()));
    }

    @Override
    public void onItemClicked(int position) {
        String keyword = edtSearch.getText().toString();
        if (keyword.length() > 0) {
            AnalyticUtil.setTopKeywordEvent(getApplicationContext(), favRestaurantModelsList.get(position).getRestaurantModel().getObjectId(), keyword);
        }
        AnalyticUtil.setUsersVistedEvent(getApplicationContext(), favRestaurantModelsList.get(position).getRestaurantModel().getObjectId());
        Intent intent = new Intent(getActivity(), SubscibeDetailActivity.class);
        intent.putExtra(Constant.SUBSCRIBE, true);
        intent.putExtra(Constant.RestaurantModel, favRestaurantModelsList.get(position).getRestaurantModel());
        startActivity(intent);
    }

    @Override
    public void onSubscribeClick(int position, FavoriteRestaurantModel item) {
        favouriteRestaurantService.requestUnSubscribe(item.getRestaurantModel().getPointer());
        favFilterList.remove(position);
        subscribeAdapter.notifyItemRemoved(position);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                subscribeAdapter.notifyDataSetChanged();
            }
        }, 2000);

        Log.i("position--", position + "");
    }

    @Override
    public void restaurantIsSubscribed(ParseObject object) {

    }

    @Override
    public void restaurantIsNotSubscribed() {

    }

    @Override
    public void onIsSubscribedFailure(String message) {

    }

    @Override
    public void onSubscribeSuccess() {

    }

    @Override
    public void onSubscribeFailure(String message) {

    }

    @Override
    public void onUnSubscribeSuccess() {
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).sendBroadcast(new Intent(Constant.SUBSCRIBE));
        Toast.makeText(context, "Restaurant Unsubscribed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUnSubscribeFailure(String message) {
        CommonUtils.showToast(getActivity(), message);
    }

    @Override
    public void onFilter(String text) {

    }

    @Override
    public void onClickDoneListener(boolean isDiscounted, String discountType, int distance) {
        progressBar.show();
        requestParams.clear();
        requestParams.putAll(geoPointParams);
        favRestaurantModelsList.clear();
        if (!discountType.equalsIgnoreCase("0")) {
            filterItems.put("type", discountType);
            checkgroupStandard = discountType;
            imageViewFilter.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon_orange));
        }
        if (distance > 0) {
            filterItems.put("distance", String.valueOf(distance));
            seekbarState = distance;
            imageViewFilter.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon_orange));
        }
        requestParams.putAll(filterItems);
        getMyFavRestaurant(requestParams);
    }

    @Override
    public void filterReset() {
        favRestaurantModelsList.clear();
        reset = Constant.HOME;
        checkgroupStandard = "";
        seekbarState = 500;
        imageViewFilter.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon));
        getMyFavRestaurant(geoPointParams);
    }
}

