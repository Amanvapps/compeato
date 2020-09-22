package com.appinnovates.campeat.views.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.HomeAdapter;
import com.appinnovates.campeat.listeners.ItemListener;
import com.appinnovates.campeat.bottomSheets.BottomSheetFilter;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.model.getAllDealsModel.RestaurantIdModel;
import com.appinnovates.campeat.services.BranchService.BranchServiceInterface;
import com.appinnovates.campeat.services.BranchService.BranchStore;
import com.appinnovates.campeat.services.DealService.AllDealsService;
import com.appinnovates.campeat.services.DealService.RestaurantDealsInterface;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.IconGenerator;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.PermissionsUtil;
import com.appinnovates.campeat.utils.UserPreferences;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.gms.tasks.OnSuccessListener;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, RestaurantDealsInterface, BottomSheetFilter.BottomSheetListener
        , LocationListener, GoogleApiClient.OnConnectionFailedListener
        , GoogleApiClient.ConnectionCallbacks, GoogleMap.OnMyLocationChangeListener, ItemListener, BranchServiceInterface, BottomSheetFilter.OnClickDoneInterface {

    private GoogleMap mMap;
    private RecyclerView recyclerView;
    private List<HomeBranchDealsModel> branchDealsFilterList, branchDealModelList;
    private HomeAdapter mapItemAdapter;
    private AllDealsService allDealsService;
    private EditText edtSearch;
    private ImageView control;
    private Location mLastLocation;
    private boolean mLocationPermissionGranted;
    private GoogleApiClient mGoogleApiClient;
    private ArrayList<Marker> markers = new ArrayList();
    private String fromScreen;
    private ArrayList<String> restaurantIdList;
    private List<HomeBranchDealsModel> branchesList;
    private List<String> branchIds;
    private Map<String, Object> geoPointParams;
    private Map<String, Object> filterItems;
    private Map<String, Object> requestParams;
    private List<String> restaurantIds;
    private String reset = "";
    private String checkgroupStandard = "";
    private int seekbarState = 500;
    private ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_maps);
        restaurantIds = new ArrayList<>();
        fromScreen = getIntent().getStringExtra(Constant.FROM_SCREEN);
        String lat = String.valueOf(UserPreferences.getInstance().getFloat(Constant.LATITUDE));
        String longi = String.valueOf(UserPreferences.getInstance().getFloat(Constant.LONGITUDE));
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        geoPointParams = new HashMap<>();
        filterItems = new HashMap<>();
        requestParams = new HashMap<>();
        geoPointParams.put("ftype", new ArrayList<>());
        geoPointParams.put("lat", lat);
        geoPointParams.put("long", longi);
        geoPointParams.put("radius", "5000");
        geoPointParams.put("orderBy", "discount");
        geoPointParams.put("orderType", "desc");
        geoPointParams.put("map", "map");
        geoPointParams.put("status", "active");
        if (fromScreen.equalsIgnoreCase(Constant.MYDEALS)) {
            geoPointParams.put("user_id", String.valueOf(ParseUser.getCurrentUser().getObjectId()));
        }
        requestParams.putAll(geoPointParams);
        ImageView backButton = findViewById(R.id.imageView10);
        backButton.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        backButton.setImageDrawable(getDrawable(R.drawable.arrow_back_black));
        backButton.setOnClickListener(view -> onBackPressed());
        initFields();
        initMAp();
        initViews();
        setListeners();
        setUpRecycler();
        setUpServices(getIntent());
        checkLocationPermission();
        setDelegateAndContext();

    }

    private void checkLocationPermission() {
        if (PermissionsUtil.arePermissionsGranted(MapsActivity.this, PermissionsUtil.permissions)) {
            mLocationPermissionGranted = true;
        } else {
            PermissionsUtil.requestPermissions(MapsActivity.this, PermissionsUtil.permissions);
        }
    }

    private void initMAp() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buildGoogleApiClient();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        branchDealsFilterList.clear();
        branchDealModelList.clear();
        initMAp();
        setListeners();
        setUpServices(intent);
        checkLocationPermission();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        control = findViewById(R.id.control);
        edtSearch = findViewById(R.id.edt_search);
        progressBar = findViewById(R.id.progressBar2);
    }

    private void initFields() {
        branchDealsFilterList = new ArrayList<>();
        branchDealModelList = new ArrayList<>();
        restaurantIdList = new ArrayList<>();
        branchesList = new ArrayList<>();
        branchIds = new ArrayList<>();

    }

    private void setListeners() {
        findViewById(R.id.location_fab).setOnClickListener(view -> {
            if (mLastLocation != null) {
                moveCameraToLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            } else {
                CommonUtils.showToast(getApplicationContext(), getApplicationContext().getResources().getString(R.string.please_enable_location));
            }
        });

        control.setOnClickListener(view -> {
            BottomSheetFilter bottomSheet = new BottomSheetFilter(this, this, reset, checkgroupStandard, seekbarState);
            bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                branchDealsFilterList.clear();
                for (HomeBranchDealsModel booking : branchDealModelList) {
                    if (booking.getmRestaurantIdModel()
                            .getRestaurantName().toLowerCase()
                            .contains(charSequence.toString().toLowerCase())) {
                        branchDealsFilterList.add(booking);
                    }
                }
                dropMarkers();
                mapItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                CommonUtils.hideKeyboard(MapsActivity.this);
                return true;
            }
            return false;
        });
    }

    private void getFilteredDeals(String value) {
        if (value.equals(Constant.SUBSCRIPTION) || value.equals(Constant.DEAL_DETAIL)) {
            requestParams.put("restaurant_id", restaurantIds);
        }
        progressBar.setVisibility(View.VISIBLE);
        allDealsService.getRestaurantDeals(requestParams);
        mapItemAdapter.notifyDataSetChanged();
    }

    private void getMyDeals(Map<String, Object> map) {
        progressBar.setVisibility(View.VISIBLE);
        allDealsService.getMyAllDeals(map);
    }


    private void setUpRecycler() {
        recyclerView.setHasFixedSize(true);
        mapItemAdapter = new HomeAdapter(this, branchDealsFilterList, this, this,Constant.MapsActivity);
        recyclerView.setAdapter(mapItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.MapActivity) {
            setDelegateAndContext();
            getFilteredDeals("");
        }
    }

    private void setDelegateAndContext() {
        allDealsService = AllDealsService.getInstance();
        allDealsService.setDelegateAndContext(this, getApplicationContext());

    }

    private void setUpServices(Intent intent) {
        //screenType = "";
        restaurantIdList.clear();

        switch (fromScreen) {
            case Constant.DEAL_DETAIL:
                HomeBranchDealsModel homeBranchDealsModel = getIntent().getParcelableExtra(Constant.DEAL);
                branchDealsFilterList.clear();
                branchDealModelList.clear();
                branchDealModelList.add(homeBranchDealsModel);
                branchDealsFilterList.addAll(branchDealModelList);


                mapItemAdapter.notifyDataSetChanged();
                if (branchDealsFilterList.size() > 0) {
                    dropMarkers();
                    zoomCamera();
                }

                break;
            case Constant.SUBSCRIPTION_DETAIL:
                allDealsService = AllDealsService.getInstance();
                allDealsService.setDelegateAndContext(this, getApplicationContext());
                branchDealsFilterList.clear();
                branchDealModelList.clear();

                List<HomeBranchDealsModel> homeBranchDealsModels = intent.getParcelableArrayListExtra(Constant.SUBSCRIPTION);
                branchDealModelList.addAll(homeBranchDealsModels);
                branchDealsFilterList.addAll(branchDealModelList);
                mapItemAdapter.notifyDataSetChanged();
                dropMarkers();
                zoomCamera();
                break;
            case Constant.SUBSCRIPTION:
                SupportMapFragment mapFragment3 = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment3.getMapAsync(this);
                allDealsService = AllDealsService.getInstance();
                allDealsService.setDelegateAndContext(this, getApplicationContext());
                restaurantIds = getIntent().getStringArrayListExtra(Constant.RestaurantID);

                getFilteredDeals(Constant.SUBSCRIPTION);
                break;
            case Constant.HOME:
                allDealsService = AllDealsService.getInstance();
                allDealsService.setDelegateAndContext(this, getApplicationContext());
                getFilteredDeals(Constant.HOME);
                break;
            case Constant.MYDEALS:
                allDealsService = AllDealsService.getInstance();
                allDealsService.setDelegateAndContext(this, getApplicationContext());
                requestParams.clear();
                requestParams.putAll(geoPointParams);
                if (requestParams.size() != 0) {
                    getMyDeals(requestParams);
                }
                break;
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                getDeviceLocation();
            } else {
                showGPSDisabledAlertToUser();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        if (mLocationPermissionGranted) {
            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    mLastLocation = location;
                    Log.e("GPS: ", "mLastLocation " + mLastLocation);

                }
            });
        }
        // Set the map's camera position to the current location of the device.
        if (mLastLocation != null) {
            Log.e("GPS: ", "moveCamera ");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(),
                    mLastLocation.getLongitude()), 11));
        }
    }

    private void moveCameraToLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
/*        if (mLastLocation.hasBearing()) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)             // Sets the center of the map to current location
                    .zoom(15)                   // Sets the zoom
                    .bearing(mLastLocation.getBearing()) // Sets the orientation of the camera to east
                    .tilt(0)                   // Sets the tilt of the camera to 0 degrees
                    .build();                   // Creates a CameraPosition from the builder

        }*/
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    private void zoomCamera() {
        try {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 10);
            mMap.animateCamera(cu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
//                setMarker(mLastLocation);
                mMap.setOnMyLocationChangeListener(this);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastLocation = null;
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
        mMap.setOnCameraIdleListener(() -> {
            float zoomLevel = mMap.getCameraPosition().zoom;
            branchDealsFilterList.clear();
            try {
                VisibleRegion visibleRegion = mMap.getProjection().getVisibleRegion();
                for (Marker marker : markers) {
                    String title = marker.getTitle();
                    title=title.replaceAll("[a-zA-Z]+","");
                    String[] address = title.split(",");
                    String restName = address[0] + "," + address[1];
                    View markerLayout = getLayoutInflater().inflate(R.layout.custom_marker, null);
                    if (zoomLevel >= 5 && zoomLevel < 9) {
                        Bitmap bitmap = IconGenerator.createMarker(markerLayout, "");
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
                    } else if (zoomLevel >= 10 && zoomLevel <= 14) {
                        Bitmap bitmap = IconGenerator.createMarker(markerLayout, "");
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
                    } else if (zoomLevel > 14 && zoomLevel <= 18) {
                        Bitmap bitmap = IconGenerator.createMarker(markerLayout, restName);
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
                    } else if (zoomLevel > 18) {
                        Bitmap bitmap = IconGenerator.createMarker(markerLayout, title);
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
                    }
                    if (visibleRegion.latLngBounds.contains(marker.getPosition())) {
                        for (HomeBranchDealsModel branchDealModel : branchDealModelList) {
                            if (marker.getSnippet().equals(branchDealModel.getObjectId())) {
                                branchDealsFilterList.add(branchDealModel);
                            }
                        }
                    }
                }
                mapItemAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.i("Exception", "Error :- " + e.getMessage());
            }

        });

        mMap.setOnMarkerClickListener(marker -> {
            if (fromScreen.equals(Constant.SUBSCRIPTION_DETAIL)) {
                for (HomeBranchDealsModel branchModel : branchesList) {
                    if (marker.getSnippet().equals(branchModel.getObjectId())) {
                        openRestaurant(branchModel.getmRestaurantIdModel());
                        return true;
                    }
                }
            } else {
                for (HomeBranchDealsModel branchDealModel : branchDealModelList) {
                    if (marker.getSnippet().equals(branchDealModel.getObjectId())) {
                        openRestaurant(branchDealModel.getmRestaurantIdModel());
                        return true;
                    }
                }
            }
            return true;
        });
        mMap.setOnCameraMoveListener(() -> {

        });
        mMap.setOnMapLoadedCallback(() -> {
            if (fromScreen.equalsIgnoreCase(Constant.DEAL_DETAIL)) {
                setUpServices(getIntent());

            }
            if (fromScreen.equalsIgnoreCase(Constant.SUBSCRIPTION_DETAIL)) {
                setUpServices(getIntent());
            }
        });

        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void openRestaurant(RestaurantIdModel restaurantModel) {
        Intent intent = new Intent(getApplicationContext(), SubscibeDetailActivity.class);
        intent.putExtra(Constant.RestaurantModel, restaurantModel);
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        Log.e("GPS: ", "onLocationChanged");

        //Place current location markersetMarker(location);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (mLocationPermissionGranted) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            showGPSDisabledAlertToUser();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMyLocationChange(Location location) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fromScreen = getIntent().getStringExtra(Constant.FROM_SCREEN);
        //setUpServices(getIntent());

        //restaurantIds = new ArrayList<>();
        //getSingleDeal = new ArrayList<>();
    }

    //AllDeals Response

    @Override
    public void onRestaurantDealsSuccess(List<HomeBranchDealsModel> restaurantDeals) {
        progressBar.setVisibility(View.GONE);
        if (fromScreen.equalsIgnoreCase(Constant.DEAL_DETAIL))
            return;
        branchDealsFilterList.clear();
        branchDealModelList.clear();
        branchDealModelList.addAll(restaurantDeals);
        branchDealsFilterList.addAll(branchDealModelList);
        mapItemAdapter.notifyDataSetChanged();
        dropMarkers();
        zoomCamera();
    }

    @Override
    public void onRestaurantDealsFailure(String message) {
        progressBar.setVisibility(View.GONE);
        CommonUtils.hideProgress();
        if (fromScreen.equals(Constant.DEAL_DETAIL))
            return;
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onNoDealsAvailable(String message) {
        progressBar.setVisibility(View.GONE);
        CommonUtils.hideProgress();
        if (fromScreen.equals(Constant.DEAL_DETAIL))
            return;
        branchDealsFilterList.clear();
        branchDealModelList.clear();
        mapItemAdapter.notifyDataSetChanged();

        dropMarkers();
        CommonUtils.showToast(this, message);
    }

    private void dropMarkers() {
        Log.i("dropMarkers", "dropMarkersrun");
        if (mMap != null) {
            mMap.clear();
            markers.clear();
            branchIds.clear();
            ParseGeoPoint parseGeoPoint;
            /*if (fromScreen.equals(Constant.SUBSCRIPTION_DETAIL)) {
                for (HomeBranchDealsModel branchModel : branchesList) {
                    if (branchModel != null && branchModel.getGeoPoint() != null) {
                        parseGeoPoint = branchModel.getGeoPoint();
                        if (parseGeoPoint != null)
                            addMarker(new LatLng(parseGeoPoint.getLatitude(), parseGeoPoint.getLongitude())
                                    , branchModel.getmRestaurantIdModel().getRestaurantName()
                                    , R.drawable.marker
                                    , branchModel.getObjectId());
                    }
                }
            } else {*/
            for (HomeBranchDealsModel branchDealModel : branchDealsFilterList) {
                if (branchDealModel.getBranchDealModel() != null && branchDealModel.getGeoPoint() != null) {
                    if (!branchIds.contains(branchDealModel.getObjectId())) {
                        String restAddress = branchDealModel.getmRestaurantIdModel().getRestaurantName() + "," + branchDealModel.getBranchName() + "," + branchDealModel.getStreetAddress();
                        branchIds.add(branchDealModel.getObjectId());
                        parseGeoPoint = branchDealModel.getGeoPoint();
                        if (parseGeoPoint != null)
                            addMarker(new LatLng(parseGeoPoint.getLatitude(), parseGeoPoint.getLongitude())
                                    , restAddress,
                                    branchDealModel.getObjectId());
                    }
                }

            }
        }
    }

    private void addMarker(LatLng latLng, String restaurantName, String branchId) {
        if (latLng != null) {
            View markerLayout = getLayoutInflater().inflate(R.layout.custom_marker, null);
            Bitmap bitmap = IconGenerator.createMarker(markerLayout, "");
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(restaurantName);

            markerOptions.visible(true);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
            Marker marker = mMap.addMarker(markerOptions);
//        marker.showInfoWindow();
            marker.setSnippet(branchId);
            markers.add(marker);
        }
    }

    @Override
    public void onItemClicked(int position) {
        moveCameraToLocation(branchDealsFilterList.get(position).getGeoPoint().getLatitude()
                , branchDealsFilterList.get(position).getGeoPoint().getLongitude());
        Intent intent = new Intent(MapsActivity.this, ItemDetailActivity.class);
        HomeBranchDealsModel restaurant = branchDealsFilterList.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("restaurants", restaurant);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onNoInternetAvailable() {
        progressBar.setVisibility(View.GONE);
        CommonUtils.hideProgress();
        CommonUtils.showToast(this, getString(R.string.no_internet_available));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }

    @Override
    public void onBranchesSuccess(List<ParseObject> branches) {
        branchesList.clear();
        BranchStore branchStore = new BranchStore();
        branchesList.addAll(branchStore.saveBranchData(branches));
        dropMarkers();
    }

    @Override
    public void onNoBranchAvailable(String message) {

    }

    @Override
    public void onBranchFailure(String message) {

    }


    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getApplicationContext().getResources().getString(R.string.gps_message))
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton(getString(R.string.cancel),
                (dialog, id) -> dialog.cancel());
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onFilter(String text) {

    }

    @Override
    public void onClickDoneListener(boolean isDiscounted, String discountType, int distance) {
        branchDealsFilterList.clear();
        branchDealModelList.clear();


        if (!discountType.equalsIgnoreCase("0")) {
            filterItems.put("discountType", discountType);
            checkgroupStandard = discountType;
            control.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon_orange));
        }
        if (distance < 5000) {
            filterItems.put("radius", String.valueOf(distance));
            seekbarState = distance;
            control.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon_orange));
        }
        requestParams.clear();
        requestParams.putAll(geoPointParams);
        if (!filterItems.isEmpty()) {
            requestParams.put("filterItems", filterItems);
        }
        if (fromScreen.equalsIgnoreCase(Constant.HOME))
            getFilteredDeals(Constant.HOME);
        else
            getMyDeals(requestParams);

        mapItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void filterReset() {
        branchDealsFilterList.clear();
        reset = Constant.HOME;
        checkgroupStandard = "";
        seekbarState = 500;
        requestParams.clear();
        requestParams.putAll(geoPointParams);
        if (fromScreen.equalsIgnoreCase(Constant.HOME))
            getFilteredDeals(Constant.HOME);
        else
            getMyDeals(requestParams);
        control.setImageDrawable(getResources().getDrawable(R.drawable.filter_icon));
    }
}
