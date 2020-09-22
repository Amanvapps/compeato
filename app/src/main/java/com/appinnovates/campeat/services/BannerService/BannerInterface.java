package com.appinnovates.campeat.services.BannerService;

import com.appinnovates.campeat.model.Banner.Result;

import java.util.List;

public interface BannerInterface {
    void bannerDataSuccess(List<Result> result);

    void bannerDatafailure(String message);

    void noBannerDataAvailable(String message);

    void noInternetConnection();
}
