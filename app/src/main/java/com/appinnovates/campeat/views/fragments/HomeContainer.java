package com.appinnovates.campeat.views.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.BannerAdapter;
import com.appinnovates.campeat.adapter.FilterAdapter;
import com.appinnovates.campeat.adapter.HomeAdapter;
import com.appinnovates.campeat.listeners.CancelDealActionListener;
import com.appinnovates.campeat.listeners.ItemListener;
import com.appinnovates.campeat.model.Banner.Result;
import com.appinnovates.campeat.model.BookingDealModel;
import com.appinnovates.campeat.bottomSheets.BottomSheetFewDealsLeft;
import com.appinnovates.campeat.bottomSheets.BottomSheetFilter;
import com.appinnovates.campeat.bottomSheets.BottomSheetSelectionCategories;
import com.appinnovates.campeat.model.FilterData;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.services.AdService.AdService;
import com.appinnovates.campeat.services.BannerService.BannerInterface;
import com.appinnovates.campeat.services.DealService.AllDealsService;
import com.appinnovates.campeat.services.DealService.RestaurantDealsInterface;
import com.appinnovates.campeat.utils.AnalyticUtil;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.LocationDialog;
import com.appinnovates.campeat.utils.PrefManager;
import com.appinnovates.campeat.utils.UserPreferences;
import com.appinnovates.campeat.views.activities.HomePage;
import com.appinnovates.campeat.views.activities.ItemDetailActivity;
import com.appinnovates.campeat.views.activities.MapsActivity;
import com.appinnovates.campeat.views.activities.SubscibeDetailActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;

import static com.parse.Parse.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeContainer extends Fragment implements FilterAdapter.OnCategoryItemInterface, RestaurantDealsInterface, LocationDialog.LocationInterFace, BannerAdapter.OnBannerInterface
        , ItemListener, CancelDealActionListener, BottomSheetSelectionCategories.OnBottomSheetItemClicked, BottomSheetFilter.OnClickDoneInterface, BottomSheetFewDealsLeft.BottomSheetListener, BannerInterface {


    public HomeContainer() {
    }

    private RecyclerView filter_recyclerView, resturant_recyclerview;
    private LinearLayout linearLayout;
    public TextView filters;
    private Context context;
    private String reset = "";
    private ImageView imageViewLocation;
    private ImageView imageViewFilter;
    private TextView nodeals;

    private List<HomeBranchDealsModel> restaurantDealsModelList, restaurantDealsFilterList;
    private HomeAdapter homeAdapter;
    private EditText edtSearch;
    private AllDealsService allDealsService;
    private TextView bottomSheet;
    private int selectedItem = 1;
    private Map<String, Object> requestParams = new HashMap<>();
    private Map<String, Object> sortingParams = new HashMap<>();
    private Map<String, Object> geoPointParams = new HashMap<>();
    private Map<String, Object> filterItemsParams = new HashMap<>();
    private Map<String, Object> foodTypesParams = new HashMap<>();
    private Float latitude = UserPreferences.getInstance().getFloat(Constant.LATITUDE);
    private Float longitude = UserPreferences.getInstance().getFloat(Constant.LONGITUDE);
    private Map<String, Object> filterItems;
    private String checkgroupStandard = "";
    private int seekbarState = 500;
    private FilterAdapter adapter;
    private ArrayList<FilterData> foodTypes;
    private NestedScrollView nestedScrollView;
    ContentLoadingProgressBar progressBar;
    //    private Float latitude, longitude;
    private Location location;
    private LocationManager locManager;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACT = 10;
    public LocationDialog dialog;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private String dealNotFound;
    private boolean isFirstTime = false;
    private boolean isLoginPage = false;
    private ViewPager2 viewPager2;
    /*    private BannerAdapter bannerAdapter;
        private List<Result> bannerList;*/
    private TextView bannerCount;
    private int currentPage = 0;
    private Timer timer;
    private final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    private final long PERIOD_MS = 2000;
    private int NUM_PAGES = 0;
    private BannerAdapter bannerAdapter;
    private List<Result> bannerList;
    private CardView bannerLayout;
    private CardView cardview;
    private boolean isFilterSelected = false;
    private boolean isFirstLoad = true;
    private Handler handler;
    private int page = 0;
    private CardView constraintLayout;
    private Runnable runnable = new Runnable() {
        public void run() {
            if (bannerAdapter.getItemCount() == page) {
                page = 0;
            } else {
                page++;
            }
            viewPager2.setCurrentItem(page, true);
            handler.postDelayed(this, 4000);
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_container, container, false);
        isFirstLoad = true;
        if (getArguments() != null) {
            dealNotFound = getArguments().getString(Constant.NODEALS, Constant.DEALSFOUND);
/*            isFirstTime = getArguments().getBoolean(Constant.ISSPLASH);
            isLoginPage = getArguments().getBoolean(Constant.ISLOGINPAGE);*/
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(200 * 1000);
        locationRequest.setNumUpdates(1);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    longitude = (float) location.getLongitude();
                    latitude = (float) location.getLatitude();
                    UserPreferences.getInstance().save(Constant.LATITUDE, latitude);
                    UserPreferences.getInstance().save(Constant.LONGITUDE, longitude);
                    if (isFirstLoad)
                        getRestaurantDealsByLocation(latitude, longitude);
                }
            }
        };
        CommonUtils.hideKeyboard(getActivity());
//        getCurrentLocation();
        initView(view);
        filterRecyclerview(view);
        itemCategories();
        resturantRecyclerView(view);

        bottomSheet = view.findViewById(R.id.filter_textview);
        linearLayout = view.findViewById(R.id.linearLayout);
        progressBar = view.findViewById(R.id.progressBar2);
        nodeals = view.findViewById(R.id.no_deals);
        viewPager2 = view.findViewById(R.id.banner_pager);
        bannerCount = view.findViewById(R.id.banner_count);
        bannerLayout = view.findViewById(R.id.banner_layout);
        cardview = view.findViewById(R.id.cardview_id);
        cardview.setVisibility(View.GONE);
        if (dealNotFound != null && dealNotFound.equalsIgnoreCase(Constant.NODEALS))
            nodeals.setVisibility(View.VISIBLE);
        linearLayout.setOnClickListener(view1 -> {
            BottomSheetSelectionCategories bottomSheet = new BottomSheetSelectionCategories(getActivity(), this, selectedItem);
            bottomSheet.show(getFragmentManager(), "BottomSheet");
        });

        setListener();
        //loadFilterFragment();
        AdService.instance.getSettings(settings -> {
        });
        geoPointParams.put(Constant.FTYPE, new ArrayList<>());
        geoPointParams.put(Constant.LAT, latitude);
        geoPointParams.put(Constant.LONG, longitude);
        geoPointParams.put(Constant.RADIUS, Constant.RADIUS_NUM);
        requestParams.put(Constant.MAP, Constant.MAP);
        geoPointParams.put(Constant.STATUS_ID, Constant.ACTIVE);
/*        restaurantDealsFilterList = new Gson().fromJson(prefManager.getAllDeals(), new TypeToken<ArrayList<HomeBranchDealsModel>>() {
        }.getType());*/
/*        try {
            restaurantDealsFilterList = getArguments() != null ? getArguments().getParcelableArrayList("data") : null;
        } catch (Exception e) {
            Log.i("Exception ", e.getMessage());
        }*/
        imageViewFilter.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon));
        nodeals.setVisibility(View.GONE);
        setUpServices();
        setRecyclerView();
//        getRestaurantDealsByDiscount();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showBanner();
        getCurrentLocation();

        setTextWatcher();
        handler.postDelayed(runnable, 5000);
    }

    private void filterRecyclerview(View view) {
        filterItems = new HashMap<>();
        filter_recyclerView = view.findViewById(R.id.filter_recyclerview);

        filter_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        filter_recyclerView.setHasFixedSize(true);
        filter_recyclerView.setVisibility(View.GONE);

    }

    private void itemCategories() {
        foodTypes = new ArrayList<>();
        foodTypes.add(new FilterData(R.drawable.kitchens, getResources().getString(R.string.korean_category)));
        foodTypes.add(new FilterData(R.drawable.fast_food, getResources().getString(R.string.japanese)));
        foodTypes.add(new FilterData(R.drawable.japanese, getResources().getString(R.string.chinese)));
        foodTypes.add(new FilterData(R.drawable.coffee_2, getResources().getString(R.string.fast_food)));
        foodTypes.add(new FilterData(R.drawable.bar_2, getResources().getString(R.string.chicken)));
        foodTypes.add(new FilterData(R.drawable.restaurants_2, getResources().getString(R.string.pizza)));
        foodTypes.add(new FilterData(R.drawable.grocery_2, getResources().getString(R.string.desert)));
        foodTypes.add(new FilterData(R.drawable.others_2, getResources().getString(R.string.global)));
        adapter = new FilterAdapter(foodTypes, getActivity(), this);
        filter_recyclerView.setAdapter(adapter);
    }

    private void resturantRecyclerView(View view) {
        resturant_recyclerview = view.findViewById(R.id.resturants_recyclerview);
        resturant_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        resturant_recyclerview.setHasFixedSize(true);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        viewPager2.unregisterOnPageChangeCallback(onPageChangeCallback);
    }

    private void setRecyclerView() {
        Fragment fragment = Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.fragment_container);
        restaurantDealsModelList.addAll(restaurantDealsFilterList);
        homeAdapter = new HomeAdapter(getContext(), restaurantDealsFilterList, this, fragment);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        resturant_recyclerview.setLayoutManager(mLayoutManager);
        resturant_recyclerview.setAdapter(homeAdapter);
        if (isFirstTime)
            restaurantDealsFilterList.clear();
        resturant_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    Log.i("recyclerview_scroll", dy + "");
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                        }
                    }
                }
                if (dx < 0) {

                }
            }
        });
    }

    private void setTextWatcher() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                restaurantDealsFilterList.clear();
                try {
                    for (HomeBranchDealsModel booking : restaurantDealsModelList) {
                        if (booking.getmRestaurantIdModel()
                                .getRestaurantName().toLowerCase()
                                .contains(s.toString().toLowerCase())) {
                            restaurantDealsFilterList.add(booking);
                        }
                    }
                    if (homeAdapter != null)
                        homeAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.i("Exception", "Error :- " + e.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                CommonUtils.hideKeyboard(Objects.requireNonNull(getActivity()));
                return true;
            }
            return false;
        });
    }

    private void initView(View view) {
        handler = new Handler();
        imageViewLocation = view.findViewById(R.id.image_view_location);
        edtSearch = view.findViewById(R.id.edtSearch);
        imageViewFilter = view.findViewById(R.id.image_view_filter);
        constraintLayout = view.findViewById(R.id.constraintLayout3);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);

    }

    private void setListener() {
        imageViewLocation.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MapsActivity.class)
                .putExtra(Constant.FROM_SCREEN, Constant.HOME)));


        imageViewFilter.setOnClickListener(view -> {
            BottomSheetFilter bottomSheet = new BottomSheetFilter(context, this, reset, checkgroupStandard, seekbarState);
            assert getFragmentManager() != null;
            bottomSheet.show(getFragmentManager(), "bottomSheetFilter");
        });


        nestedScrollView.setSmoothScrollingEnabled(true);
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            Log.i("scrolls", "" + scrollX);
            if (scrollY > 450) {
                constraintLayout.setElevation(context.getResources().getDimension(R.dimen._8sdp));
            } else {
                constraintLayout.setElevation(0);
            }
        });
    }


    private void setUpServices() {
        bannerList = new ArrayList<>();
        restaurantDealsModelList = new ArrayList<>();
        restaurantDealsFilterList = new ArrayList<>();
        allDealsService = AllDealsService.getInstance();
        allDealsService.setDelegateAndContext(this, getApplicationContext());
        allDealsService.setBannerDelegateAndContext(this, getActivity());
    }

    private void getRestaurantDealsByLocation(float latitude, float longitude) {
        isFirstLoad = false;
        try {
            geoPointParams.put(Constant.LAT, latitude);
            geoPointParams.put(Constant.LONG, longitude);
            restaurantDealsFilterList.clear();
            progressBar.setVisibility(View.VISIBLE);
            imageViewFilter.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon));
            requestParams.clear();
            requestParams.putAll(geoPointParams);
            sortingParams.clear();
            sortingParams.put(Constant.ORDERBY, Constant.ORDERBYLOCATION);
            sortingParams.put(Constant.ORDERTYPE, Constant.ASC);
            requestParams.putAll(sortingParams);
            allDealsService.getRestaurantDeals(requestParams);
            itemCategories();
        } catch (Exception e) {
            Log.i("Exception :- ", "Error :- " + e.getMessage());
        }

    }

    private void getRestaurantDealsByFoodType(ArrayList<String> foodTypes) {
        restaurantDealsFilterList.clear();
        if (foodTypes.size() == 0) {
            requestParams.clear();
            requestParams.putAll(geoPointParams);
            requestParams.putAll(sortingParams);
            requestParams.putAll(filterItemsParams);
            progressBar.show();
            allDealsService.getRestaurantDeals(requestParams);
        } else {
            requestParams.clear();
            requestParams.putAll(geoPointParams);
            requestParams.putAll(sortingParams);
            requestParams.putAll(filterItemsParams);
            if (foodTypes.size() != 0) {
                foodTypesParams.put(Constant.FoodItems, foodTypes);
            }
            if (!foodTypesParams.isEmpty()) {
                requestParams.putAll(foodTypesParams);
            }
            progressBar.show();
            allDealsService.getRestaurantDeals(requestParams);
        }
    }

    private void getRestaurantDealsByDiscount() {
        restaurantDealsFilterList.clear();
        filterItems.clear();
        imageViewFilter.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon));
        requestParams.clear();
        requestParams.putAll(geoPointParams);
        sortingParams.clear();
        sortingParams.put(Constant.ORDERBY, Constant.ORDERBYDISCOUNT);
        sortingParams.put(Constant.ORDERTYPE, Constant.DESC);
        requestParams.putAll(sortingParams);
        progressBar.setVisibility(View.VISIBLE);
        allDealsService.getRestaurantDeals(requestParams);
        itemCategories();
    }

    private void getRestaurantDealsByRating() {
        restaurantDealsFilterList.clear();
        filterItems.clear();
        imageViewFilter.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon));
        requestParams.clear();
        requestParams.putAll(geoPointParams);
        sortingParams.clear();
        sortingParams.put(Constant.ORDERBY, Constant.ORDERBYRATING);
        sortingParams.put(Constant.ORDERTYPE, Constant.DESC);
        requestParams.putAll(sortingParams);
        progressBar.setVisibility(View.VISIBLE);
        allDealsService.getRestaurantDeals(requestParams);
        itemCategories();
    }

    private void fewDealLeftSheet() {
        PrefManager.setIsFirstTime(false);
        isFirstTime = false;
        isLoginPage = false;
        BottomSheetFewDealsLeft bottomSheetFewDealsLeft = new BottomSheetFewDealsLeft(getActivity(), "");
        if (getFragmentManager() != null) {
            bottomSheetFewDealsLeft.show(getFragmentManager(), "fewDealLeft");
        }
    }

    //AllDeals Response
    @Override
    public void onRestaurantDealsFailure(String message) {
        progressBar.hide();
        nodeals.setVisibility(View.VISIBLE);
        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
        restaurantDealsFilterList.clear();
        restaurantDealsModelList.clear();
        if (homeAdapter != null) {
            homeAdapter.notifyDataSetChanged();
        }
        if (!isFilterSelected)
            if (PrefManager.isFirstTime())
                fewDealLeftSheet();
    }

    @Override
    public void onNoDealsAvailable(String message) {
        progressBar.hide();
        nodeals.setVisibility(View.VISIBLE);
        restaurantDealsFilterList.clear();
        restaurantDealsModelList.clear();
        homeAdapter.notifyDataSetChanged();
        if (!isFilterSelected)
            if (PrefManager.isFirstTime())
                fewDealLeftSheet();
/*
        textViewDefault.setVisibility(View.VISIBLE);
*/
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
        if (restaurantDealsFilterList.get(position) != null) {
            if (keyword.length() > 0) {
                AnalyticUtil.setTopKeywordEvent(getApplicationContext(), restaurantDealsFilterList.get(position).getObjectId(), keyword);
            }
            AnalyticUtil.setDealViewEvent(getApplicationContext(), restaurantDealsFilterList.get(position).getObjectId());
        }
        Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
        HomeBranchDealsModel restaurant = restaurantDealsFilterList.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("restaurants", restaurant);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void actionListener(int position) {

    }

    @Override
    public void onLocationClicked() {
        getRestaurantDealsByLocation(latitude, longitude);
        bottomSheet.setText(getResources().getString(R.string.location));
        selectedItem = 1;
    }

    @Override
    public void onRatingClicked() {
        getRestaurantDealsByRating();
        bottomSheet.setText(getResources().getString(R.string.best_rating));
        selectedItem = 3;
    }

    @Override
    public void onDiscountClicked() {
        getRestaurantDealsByDiscount();
        bottomSheet.setText(getResources().getString(R.string.highest_discount));
        selectedItem = 2;
    }

    @Override
    public void onClickDoneListener(boolean isDiscounted, String discountType, int distance) {
//        requestParams.put("foodItems", "");
        isFilterSelected = true;
        progressBar.show();
        restaurantDealsFilterList.clear();
        if (!discountType.equalsIgnoreCase("0")) {
            filterItems.put("discountType", discountType);
            checkgroupStandard = discountType;
            imageViewFilter.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon_orange));
        }
        if (distance > 0) {
            filterItems.put("radius", String.valueOf(distance));
            seekbarState = distance;
            imageViewFilter.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon_orange));
        }
        requestParams.clear();
        requestParams.putAll(geoPointParams);
        requestParams.putAll(sortingParams);
        if (!filterItems.isEmpty()) {
            filterItemsParams.put("filterItems", filterItems);
        }
        if (!filterItemsParams.isEmpty()) {
            requestParams.putAll(filterItemsParams);
        }
        allDealsService.getRestaurantDeals(requestParams);
//        allDealsService.getRestaurantDeals( mDistance, mMenuTypeIDs, "", "active", new ArrayList<String>(),disCountType,"","");
    }

    @Override
    public void onRestaurantDealsSuccess(List<HomeBranchDealsModel> restaurantDeals) {
        progressBar.hide();
        nodeals.setVisibility(View.GONE);
        restaurantDealsFilterList.clear();
        restaurantDealsModelList.clear();
        restaurantDealsModelList.addAll(restaurantDeals);
        restaurantDealsFilterList.addAll(restaurantDeals);
        homeAdapter.notifyDataSetChanged();
        int getDealCount = restaurantDealsFilterList.size();
        if (!isFilterSelected)
            if ((getDealCount < 5) && (PrefManager.isFirstTime())) {
                fewDealLeftSheet();
            }
    }

    @Override
    public void filterReset() {
        isFilterSelected = false;
        restaurantDealsFilterList.clear();
        reset = Constant.HOME;
        checkgroupStandard = "";
        seekbarState = 500;
        String title = bottomSheet.getText().toString();
        if (title.equalsIgnoreCase(getString(R.string.location))) {
            getRestaurantDealsByLocation(latitude, longitude);
        } else if (title.equalsIgnoreCase(getString(R.string.best_rating))) {
            getRestaurantDealsByRating();
        } else {
            getRestaurantDealsByDiscount();
        }
        imageViewFilter.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon));
    }

    @Override
    public void onCategoryItemClick(int text, Boolean isClicked, ArrayList<String> items) {
        if (items.size() > 0)
            isFilterSelected = true;
        HomeContainer.this.getRestaurantDealsByFoodType(items);
    }

    private void getCurrentLocation() {
        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        if (network_enabled) {

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_CONTACT);
                return;
            }
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), location -> {
                        if (location != null) {
                            longitude = (float) location.getLongitude();
                            latitude = (float) location.getLatitude();
                            UserPreferences.getInstance().save(Constant.LATITUDE, latitude);
                            UserPreferences.getInstance().save(Constant.LONGITUDE, longitude);
                            if (isFirstLoad)
                                getRestaurantDealsByLocation(latitude, longitude);
                        } else {
                            startLocationUpdates();
                        }
                    });
        } else {

            dialog = new LocationDialog(getActivity(), this);
            dialog.setCancelable(false);
            dialog.show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            startActivity(new Intent(getActivity(), HomePage.class));
            getActivity().finish();
        }
        if (requestCode == 123 && data != null) {
            int branchIndex = 0, dealIndex = 0;
            boolean isbooked, isCancel;
            BookingDealModel bookingDealModel = data.getParcelableExtra("booking_model");
            isbooked = data.getBooleanExtra("isBooked", false);
            isCancel = data.getBooleanExtra("isCancelled", false);
            branchIndex = data.getIntExtra("branch_index", -1);
            dealIndex = data.getIntExtra("deal_index", -1);
            if (isbooked) {
                restaurantDealsFilterList.get(branchIndex).getDeals().get(dealIndex).getDeal().setGetDeal(Constant.CANCELDEAL);
                restaurantDealsFilterList.get(branchIndex).getDeals().get(dealIndex).getDeal().setBookingDealModel(bookingDealModel);
            } else if (isCancel)
                restaurantDealsFilterList.get(branchIndex).getDeals().get(dealIndex).getDeal().setGetDeal(Constant.GETDEAL);
            homeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACT) {// If request is cancelled, the couponModelResult arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), location -> {
                            if (location != null) {
                                longitude = (float) location.getLongitude();
                                latitude = (float) location.getLatitude();
                                UserPreferences.getInstance().save(Constant.LATITUDE, latitude);
                                UserPreferences.getInstance().save(Constant.LONGITUDE, longitude);
                                startActivity(new Intent(getActivity(), HomePage.class));
                                getActivity().finish();
                            } else {
                                startLocationUpdates();
                            }
                        });

            }
        }
    }

    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback, null);
    }

    @Override
    public void onAllowClicked() {
        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 101);
    }

    @Override
    public void onDenyClicked() {
        getRestaurantDealsByLocation(latitude, longitude);
    }

    @Override
    public void onButtonCancelDeal(String text) {

    }

    private void showBanner() {
        allDealsService.getBannerData();
        bannerAdapter = new BannerAdapter(context, bannerList, this);
        viewPager2.setAdapter(bannerAdapter);
        viewPager2.setNestedScrollingEnabled(false);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.registerOnPageChangeCallback(onPageChangeCallback);
    }

    private ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            page = position;
            if (bannerList.size() != 0)
                bannerCount.setText(((position + 1) % bannerList.size())+1 + " / " + bannerList.size());
        }
    };


    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void bannerDataSuccess(List<Result> result) {
        bannerList.clear();
        bannerList.addAll(result);
        bannerCount.setText("1 / " + bannerList.size());
        cardview.setVisibility(View.VISIBLE);
//        bannerSlider();
        bannerAdapter.notifyDataSetChanged();
        filter_recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void bannerDatafailure(String message) {
        bannerLayout.setVisibility(View.GONE);
        filter_recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void noBannerDataAvailable(String message) {
        bannerLayout.setVisibility(View.GONE);
        filter_recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void noInternetConnection() {
        bannerLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBannerClicked(Result result, int position) {
        if (result.getType() == 1) {
            Uri uri = Uri.parse(result.getExternalLink());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), SubscibeDetailActivity.class);
            intent.putExtra(Constant.Banner, Constant.Banner);
            intent.putExtra(Constant.RestaurantID, result.getRestaurantLink().getObjectId());
            startActivity(intent);
        }

    }
}
