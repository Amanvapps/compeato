package com.appinnovates.campeat.services.DealService;

import com.appinnovates.campeat.model.getAllDealsModel.DealModel;

import java.util.List;

/**
 * Created by reetu on 26/6/18.
 */

public interface RestaurantDealServiceInterface {
    void onRestaurantDealsSuccess(List<DealModel> dealsModelList);
    void onNoRestaurantDeals();
    void onRestaurantDealsFailure(String message);
}
