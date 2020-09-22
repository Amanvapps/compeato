package com.appinnovates.campeat.services.CurrentGeoPointService;

import com.parse.ParseGeoPoint;

/**
 * Created by reetu on 26/6/18.
 */

public interface CurrentGeoPointServiceInterface {
    void onCurrentGeoPointSuccess(ParseGeoPoint currentGeoPoint);
    void onCurrentGeoPointFailure(String message);
}
