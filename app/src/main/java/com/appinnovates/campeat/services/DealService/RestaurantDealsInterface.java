package com.appinnovates.campeat.services.DealService;

import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;

import java.util.List;

public interface RestaurantDealsInterface {
    void onRestaurantDealsSuccess(List<HomeBranchDealsModel> restaurantDeals);
    void onRestaurantDealsFailure(String message);
    void onNoDealsAvailable(String message);
    void onNoInternetAvailable();
}
