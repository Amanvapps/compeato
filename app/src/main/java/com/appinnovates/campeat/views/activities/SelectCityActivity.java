package com.appinnovates.campeat.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.services.CurrentGeoPointService.CurrentGeoPointService;
import com.appinnovates.campeat.services.CurrentGeoPointService.CurrentGeoPointServiceInterface;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.UserPreferences;
import com.parse.ParseGeoPoint;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neha on 5/14/18.
 */

public class SelectCityActivity extends Activity implements View.OnClickListener, CurrentGeoPointServiceInterface {

    List<String> cityList;
    List<String> list;
    private CardView cardViewPopup;
    private TextView textViewCitySelection, txtArea;
    private RecyclerView recyclerView;
    private CurrentGeoPointService currentGeoPointService;
    private Geocoder geocoder;
    private List<Address> addresses;
    private Address address;
    private String mSelectedItem;
    private Spinner mSpinnerCity;
    private Spinner mSpinnerArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcity);
        initView();
        setField();
        setListener();
        setUpServices();
        setAreaSpinnerAdapter();
        setCitySpinnerAdapter();

        findViewById(R.id.find_restaurant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomePage.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();

            }
        });


    }

    private void setCitySpinnerAdapter() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_city_header, getCities());
        mSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String city = cityList.get(position);
                getLatLongFromName(city);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setSpinnerHeight(mSpinnerCity);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerCity.setAdapter(dataAdapter);
    }

    private void getLatLongFromName(String city) {
        if (Geocoder.isPresent()) {
            try {
                city = "Seoul" + ", South Korea";
                Geocoder geocoder = new Geocoder(this);
                List<Address> address = geocoder.getFromLocationName(city, 1);
                if (address.size() > 0 && address.get(0).hasLatitude() && address.get(0).hasLongitude()) {
                    UserPreferences.getInstance().save(Constant.LATITUDE, (float) address.get(0).getLatitude());
                    UserPreferences.getInstance().save(Constant.LONGITUDE, (float) address.get(0).getLongitude());
//                    List<Address> addr = geocoder.getFromLocation(address.get(0).getLatitude(), address.get(0).getLongitude(), 1);
//                    currentGeoPointService.saveDataToUser(addr.get(0).getLocality(), addr.get(0).getAdminArea());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setAreaSpinnerAdapter() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_area_header, getArea());
        mSpinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        setSpinnerHeight(mSpinnerArea);
        mSpinnerArea.setAdapter(dataAdapter);
    }

    private void setSpinnerHeight(Spinner mSpinner) {
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(mSpinner);

            // Set popupWindow height to 300px
            popupWindow.setHeight(300);
        }
        catch (Exception ex){
            // Failed...
        }
    }

    private void setUpServices() {
        currentGeoPointService = CurrentGeoPointService.getInstance();
        currentGeoPointService.setDelegate(this);
//        currentGeoPointService.getCurrentGeoPoint();
    }

    private void setField() {
        list = new ArrayList<>();
        cityList = new ArrayList<>();
        float lat = (float) 37.5665;
        float longg = (float) 126.9780;
        UserPreferences.getInstance().save(Constant.LATITUDE, lat);
        UserPreferences.getInstance().save(Constant.LONGITUDE, longg);
    }

//    private void setAdapter(List<String> list) {
//        recyclerView.setHasFixedSize(true);
//        CityAdapter cityAdapter = new CityAdapter(list);
//        cityAdapter.setActionListener(new CityAdapter.ActionListener() {
//            @Override
//            public void onActionListener(String value) {
//                if (mSelectedItem.equals("city")) {
//                    textViewCitySelection.setText(value);
//                } else {
//                    txtArea.setText(value);
//                }
//                cardViewPopup.setVisibility(View.GONE);
//            }
//        });
//        recyclerView.setAdapter(cityAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//    }

    private List<String> getCities() {
        cityList.clear();
        cityList.add("Seoul");
        cityList.add("Busan");
        cityList.add("Daejeon");
        cityList.add("Goyang-si");
        cityList.add("Gwangju");
        cityList.add("Incheon");
        cityList.add("Seongnam-si");
        cityList.add("Suwon");
        cityList.add("Ulsan");
        return cityList;
    }

    private List<String> getArea() {
        list.clear();
        list.add("All");
        return list;
    }

    private void setListener() {
        textViewCitySelection.setOnClickListener(this);
        txtArea.setOnClickListener(this);
    }

    private void initView() {
        textViewCitySelection = findViewById(R.id.text_view_city_selection);
        txtArea = findViewById(R.id.txt_area);
        cardViewPopup = findViewById(R.id.card_view_popup);
        recyclerView = findViewById(R.id.recycler_view);
        mSpinnerArea = findViewById(R.id.spinner_area);
        mSpinnerCity = findViewById(R.id.spinner_city);
    }

    @Override
    public void onClick(View v) {
//        boolean shouldShow = cardViewPopup.getVisibility() == View.GONE;
//        if (v.getId() == textViewCitySelection.getId()) {
//            if (shouldShow) {
//                mSelectedItem =  "city";
//                List<String> cityList = getCities();
//                setAdapter(cityList);
//                int height = textViewCitySelection.getMeasuredHeight();
//                height = height + 10;
//                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) cardViewPopup.getLayoutParams();
//                lp.setMargins(0, height, 0, 0);
//                cardViewPopup.setLayoutParams(lp);
//                textViewCitySelection.setBackground(getResources().getDrawable(R.drawable.rounded_border_orange));
//                txtArea.setBackground(getResources().getDrawable(R.drawable.rounded_border_grey));
//                cardViewPopup.setVisibility(View.VISIBLE);
//            } else {
//                cardViewPopup.setVisibility(View.GONE);
//            }
//        }
//        if (v.getId() == txtArea.getId()) {
//            if (shouldShow) {
//                List<String> areaList = getArea();
//                mSelectedItem =  "area";
//                setAdapter(areaList);
//                int height = txtArea.getMeasuredHeight();
//                height = height + 10;
//                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) cardViewPopup.getLayoutParams();
//                lp.setMargins(0, height, 0, 0);
//                cardViewPopup.setLayoutParams(lp);
//                textViewCitySelection.setBackground(getResources().getDrawable(R.drawable.rounded_border_grey));
//                txtArea.setBackground(getResources().getDrawable(R.drawable.rounded_border_orange));
//                cardViewPopup.setVisibility(View.VISIBLE);
//            } else {
//                cardViewPopup.setVisibility(View.GONE);
//            }
//        }
    }

    @Override
    public void onCurrentGeoPointSuccess(ParseGeoPoint currentGeoPoint) {
//        UserPreferences.getInstance().save(Constant.LATITUDE, (float) currentGeoPoint.getLatitude());
//        UserPreferences.getInstance().save(Constant.LONGITUDE, (float) currentGeoPoint.getLongitude());
//
//        geocoder = new Geocoder(this);
//        try {
//            addresses = geocoder.getFromLocation(currentGeoPoint.getLatitude(), currentGeoPoint.getLongitude(), 1);
//            address = addresses.get(0);
//            currentGeoPointService.saveDataToUser(address.getLocality(), address.getSubAdminArea());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onCurrentGeoPointFailure(String message) {
        CommonUtils.showToast(getApplicationContext(),message);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }
}