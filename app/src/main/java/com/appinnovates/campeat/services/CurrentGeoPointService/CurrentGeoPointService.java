package com.appinnovates.campeat.services.CurrentGeoPointService;

import android.location.Geocoder;

import com.parse.LocationCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

/**
 * Created by reetu on 26/6/18.
 */

public class CurrentGeoPointService {
    private static CurrentGeoPointService currentGeoPointService = null;
    private CurrentGeoPointServiceInterface delegate;
    private ParseUser parseUser;
    private Geocoder geocoder;

    public static CurrentGeoPointService getInstance(){
        if (currentGeoPointService == null){
            currentGeoPointService = new CurrentGeoPointService();
        }
        return currentGeoPointService;
    }

    public void setDelegate(CurrentGeoPointServiceInterface delegate){this.delegate = delegate;}

    public void getCurrentGeoPoint(){

        ParseGeoPoint.getCurrentLocationInBackground(1000, new LocationCallback() {
            @Override
            public void done(ParseGeoPoint geoPoint, ParseException e) {
                if (e == null){
                    if (geoPoint!=null){
                        delegate.onCurrentGeoPointSuccess(geoPoint);
                    }else {
                        delegate.onCurrentGeoPointFailure("No GeoPoint available");
                    }
                }else{
                    delegate.onCurrentGeoPointFailure(e.getMessage());
                }
            }
        });
    }

    public void saveDataToUser(String city,String area){
        parseUser = ParseUser.getCurrentUser();
        if (city != null) {
            parseUser.put("city",city);
        }
        if (area != null) {
            parseUser.put("area",area);
        }
        parseUser.saveInBackground();
    }
}
