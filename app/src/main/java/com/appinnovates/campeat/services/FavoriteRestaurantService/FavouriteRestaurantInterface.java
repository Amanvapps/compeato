package com.appinnovates.campeat.services.FavoriteRestaurantService;

import com.appinnovates.campeat.model.FavoriteRestaurantModel;

import java.util.List;

public interface FavouriteRestaurantInterface {
    void onFavouriteRestaurantSuccess(List<FavoriteRestaurantModel> branchDealModels);
    void onFavouriteRestaurantFailure(String message);
    void onNoInternetAvailable();
}
