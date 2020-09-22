package com.appinnovates.campeat.views.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.services.DealService.AllDealsService;
import com.appinnovates.campeat.services.DealService.RestaurantDealsInterface;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.LocationDialog;
import com.appinnovates.campeat.utils.PrefManager;
import com.appinnovates.campeat.utils.UserPreferences;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;
public class Splash extends AppCompatActivity implements RestaurantDealsInterface, LocationDialog.LocationInterFace {

    GifImageView logo;
    float latitude, longitude;
    private Map<String, Object> geoPointParams = new HashMap<>();
    private AllDealsService allDealsService;
    private LocationManager locManager;
    public int MY_PERMISSIONS_REQUEST_READ_CONTACT = 10;
    public LocationDialog dialog;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private Long startTime, endTime;
    private long runTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        startTime = System.currentTimeMillis();
        getLatLongFromName();
        PrefManager.setIsFirstTime(true);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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
                }
                getRestaurantDealsByLocation(latitude, longitude);
            }
        };

        geoPointParams.put("ftype", new ArrayList<>());
        geoPointParams.put("lat", latitude);
        geoPointParams.put("long", longitude);
        geoPointParams.put("radius", "5000");
        geoPointParams.put("map", "map");
        geoPointParams.put("status", "active");
        logo = findViewById(R.id.campeat_gif);
/*        Glide.with(this)
                .load(getDrawable(R.drawable.campeat_logo))
                .into(logo);*/
/*        if (ParseUser.getCurrentUser() != null)
            getCurrentLocation();*/
    }

    private void launch() {
        //gif timeout 5400
        int SPLASH_TIME_OUT = 5100;
        new Handler().postDelayed(() -> {
/*            if (UserPreferences.getInstance().getBoolean(UserPreferences.OPEN_FROM_PLAYSTORE)) {
                if (UserPreferences.getInstance().getStrings(UserPreferences.TYPE).equals(Constant.DEAL)) {
                    String id = UserPreferences.getInstance().getStrings(UserPreferences.Share_ID);
                    Intent i = new Intent(Splash.this, ItemDetailActivity.class);
                    i.putExtra(Constant.DealId, id);
                    startActivity(i);
                } else {
                    String id = UserPreferences.getInstance().getStrings(UserPreferences.Share_ID);
                    Intent i = new Intent(Splash.this, SubscibeDetailActivity.class);
                    i.putExtra(Constant.RestaurantID, id);
                    startActivity(i);
                }
                UserPreferences.getInstance().delete(UserPreferences.OPEN_FROM_PLAYSTORE);
                UserPreferences.getInstance().delete(UserPreferences.TYPE);
                UserPreferences.getInstance().delete(UserPreferences.Share_ID);
            } else {*/
            // This method will be executed once the timer is over

            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser == null) {
                // Start your app Sign in activity
                Intent i = new Intent(Splash.this, SignInActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            } else {

                Intent i = new Intent(Splash.this, HomePage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }

//            }
//            finish();
        }, SPLASH_TIME_OUT);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }

    private void getLatLongFromName() {
        if (Geocoder.isPresent()) {
            try {
                String city = "Seoul" + ", South Korea";
                Geocoder geocoder = new Geocoder(this);
                List<Address> address = geocoder.getFromLocationName(city, 1);
                if (address.size() > 0 && address.get(0).hasLatitude() && address.get(0).hasLongitude()) {
                    latitude = (float) address.get(0).getLatitude();
                    longitude = (float) address.get(0).getLongitude();
                    UserPreferences.getInstance().save(Constant.LATITUDE, latitude);
                    UserPreferences.getInstance().save(Constant.LONGITUDE, longitude);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            launch();
        }
    }

    private void getRestaurantDealsByLocation(float latitude, float longitude) {
        allDealsService = AllDealsService.getInstance();
        allDealsService.setDelegateAndContext(this, getApplicationContext());
        geoPointParams.put("lat", latitude);
        geoPointParams.put("long", longitude);
        geoPointParams.put("orderBy", "location");
        geoPointParams.put("orderType", "asc");
        allDealsService.getRestaurantDeals(geoPointParams);
    }

    private void getCurrentLocation() {
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        if (network_enabled) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_CONTACT);
                }
                return;
            }
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            longitude = (float) location.getLongitude();
                            latitude = (float) location.getLatitude();
                            UserPreferences.getInstance().save(Constant.LATITUDE, latitude);
                            UserPreferences.getInstance().save(Constant.LONGITUDE, longitude);
                            getRestaurantDealsByLocation(latitude, longitude);
                        } else {
                            startLocationUpdates();
                        }
                    });
        } else {

            dialog = new LocationDialog(this, this);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACT) {// If request is cancelled, the couponModelResult arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, location -> {
                            if (location != null) {
                                longitude = (float) location.getLongitude();
                                latitude = (float) location.getLatitude();
                                UserPreferences.getInstance().save(Constant.LATITUDE, latitude);
                                UserPreferences.getInstance().save(Constant.LONGITUDE, longitude);
                                getRestaurantDealsByLocation(latitude, longitude);
/*                                startActivity(new Intent(this, HomePage.class));
                                finish();*/
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
    public void onRestaurantDealsSuccess(List<HomeBranchDealsModel> restaurantDeals) {
        endTime = System.currentTimeMillis();
        long totalTime = (endTime - startTime);
        PrefManager.putDeals(restaurantDeals);
        if (totalTime < 6200) {
            runTime = 6200 - totalTime;
        }
        new Handler().postDelayed(Splash.this::goToHomePage, runTime);
    }

    void goToHomePage() {
        Intent intent = new Intent(Splash.this, HomePage.class);
        intent.putExtra(Constant.ISSPLASH, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRestaurantDealsFailure(String message) {
        Intent intent = new Intent(Splash.this, HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constant.NODEALS, Constant.NODEALS);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNoDealsAvailable(String message) {
        Intent intent = new Intent(Splash.this, HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constant.NODEALS, Constant.NODEALS);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNoInternetAvailable() {

    }

    @Override
    public void onAllowClicked() {
        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 101);
    }

    @Override
    public void onDenyClicked() {
        getRestaurantDealsByLocation(latitude, longitude);
    }

}