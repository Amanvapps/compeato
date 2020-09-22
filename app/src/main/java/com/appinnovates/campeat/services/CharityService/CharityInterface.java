package com.appinnovates.campeat.services.CharityService;

import com.appinnovates.campeat.model.charity.getCharities.Result;

import java.util.List;

public interface CharityInterface {
    void onSuccess(List<Result> charityModelList);

    void onFailure(String message);
}
