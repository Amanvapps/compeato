package com.appinnovates.campeat.services.FavoriteRestaurantService;

import android.content.Context;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.FavoriteRestaurantModel;
import com.appinnovates.campeat.services.DealService.DealStore;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.DateFormatUtil;
import com.appinnovates.campeat.utils.PermissionsUtil;
import com.appinnovates.campeat.utils.UserPreferences;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouriteRestaurantService {
    private static FavouriteRestaurantService favouriteRestaurantService = null;
    private FavouriteRestaurantServiceDelegate delegate;
    private FavouriteRestaurantInterface restaurantInterface;
    private Context context;
    private List<FavoriteRestaurantModel> restaurantDeals;

    private DealStore dealStore;

    public static FavouriteRestaurantService getInstance(){
         if (favouriteRestaurantService == null){
             favouriteRestaurantService = new FavouriteRestaurantService();
         }
         return favouriteRestaurantService;
     }

     public void setDelegate(FavouriteRestaurantServiceDelegate delegate){
         this.delegate = delegate;
     }

     public void setFavoriteRestaurantInterface(FavouriteRestaurantInterface restaurantInterface, Context context) {
         this.restaurantInterface = restaurantInterface;
         this.context = context;
     }

    public void getFavoriteRestaurant(boolean isAscending, String price, boolean isDiscounted
            , String discountType, int distance, ArrayList<String> menuTypeId
            , boolean subdate, boolean dupdate) {
        if (!PermissionsUtil.isNetworkAvailable(context)) {
            restaurantInterface.onNoInternetAvailable();
            return;
        }
        final FavoriteRestaurantStore favoriteRestaurantStore = new FavoriteRestaurantStore();
        String lat = String.valueOf(UserPreferences.getInstance().getFloat(Constant.LATITUDE));
        String longu = String.valueOf(UserPreferences.getInstance().getFloat(Constant.LONGITUDE));
        String prices = "0";
        String type = "0";

        if (isAscending) {
            prices = price;
        }

        if (isDiscounted) {
            type = discountType.toLowerCase();
        }
        final Map<String, Object> map = new HashMap<>();
        map.put("user_id", String.valueOf(ParseUser.getCurrentUser().getObjectId()));
        map.put("price", prices);
        map.put("type", type);
        map.put("radius", String.valueOf(distance));
        map.put("lat", lat);
        map.put("long", longu);
        map.put("ftype", menuTypeId);
        map.put("subdate", subdate);
        map.put("dupdate", dupdate);

         ParseCloud.callFunctionInBackground("getMySub", map
                 , (FunctionCallback<ArrayList<Map>>) (objects, e) -> {
                     if (e == null) {
                         if (objects != null && objects.size() > 0) {
                             restaurantInterface.onFavouriteRestaurantSuccess(favoriteRestaurantStore.saveData(objects));
                         } else {
                             restaurantInterface.onFavouriteRestaurantFailure
                                     (context.getResources().getString(R.string.no_fav_restaurant));
                         }

                     } else {
                         restaurantInterface.onFavouriteRestaurantFailure(e.getMessage());
                     }

                 });
     }

    public void getMyFavorites(Map<String,Object> requestParams) {
        if (!PermissionsUtil.isNetworkAvailable(context)) {
            restaurantInterface.onNoInternetAvailable();
            return;
        }
        final FavoriteRestaurantStore favoriteRestaurantStore = new FavoriteRestaurantStore();
        ParseCloud.callFunctionInBackground("getMyFavorites", requestParams
                , (FunctionCallback<ArrayList<Map>>) (objects, e) -> {
                    if (e == null) {
                        if (objects != null && objects.size() > 0) {
                            restaurantInterface.onFavouriteRestaurantSuccess(favoriteRestaurantStore.saveData(objects));
                        } else {
                            restaurantInterface.onFavouriteRestaurantFailure
                                    (context.getResources().getString(R.string.no_fav_restaurant));
                        }

                    } else {
                        restaurantInterface.onFavouriteRestaurantFailure(e.getMessage());
                    }

                });
    }

    public void isRestaurantSubscribed(ParseObject restaurantPointer){
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("FavoriteRestaurant");
        parseQuery.include("restaurant_id");
        parseQuery.whereEqualTo("restaurant_id", restaurantPointer);
        parseQuery.whereEqualTo("customer_id", ParseUser.getCurrentUser());
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects != null && objects.size() > 0) {
                        delegate.restaurantIsSubscribed(objects.get(0));
                    } else {
                        delegate.restaurantIsNotSubscribed();
                    }
                } else {
                    delegate.onIsSubscribedFailure(e.getMessage());
                }
            }
        });
    }

    public void requestUnSubscribe(ParseObject restaurantPointer){
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("FavoriteRestaurant");
        parseQuery.whereEqualTo("restaurant_id", restaurantPointer);
        parseQuery.whereEqualTo("customer_id", ParseUser.getCurrentUser());
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects != null && objects.size() > 0) {
                       objects.get(0).deleteInBackground(new DeleteCallback() {
                           @Override
                           public void done(ParseException e) {
                               if (e == null) {
                                   delegate.onUnSubscribeSuccess();
                               }else{
                                   delegate.onUnSubscribeFailure(e.getMessage());
                               }
                           }
                       });

                    } else {
                        delegate.restaurantIsNotSubscribed();
                    }
                } else {
                    delegate.onIsSubscribedFailure(e.getMessage());
                }
            }
        });

    }

    public void requestSubscribe(ParseObject restaurantPointer){
        final ParseObject parseObject = new ParseObject("FavoriteRestaurant");
        parseObject.put("customer_id", ParseUser.getCurrentUser());
        parseObject.put("restaurant_id", restaurantPointer);
        parseObject.put("favorited_date", DateFormatUtil.getCurrentDate());
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    delegate.onSubscribeSuccess();
                }else{
                    delegate.onSubscribeFailure(e.getMessage());
                }
            }
        });
    }
}
