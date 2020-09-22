package com.appinnovates.campeat.services.FavoriteRestaurantService;

import com.parse.ParseObject;

public interface FavouriteRestaurantServiceDelegate {
    void restaurantIsSubscribed(ParseObject object);
    void restaurantIsNotSubscribed();
    void onIsSubscribedFailure(String message);
    void onSubscribeSuccess();
    void onSubscribeFailure(String message);
    void onUnSubscribeSuccess();
    void onUnSubscribeFailure(String message);

}
