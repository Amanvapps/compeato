package com.appinnovates.campeat.services.FavoriteRestaurantService;

import com.appinnovates.campeat.model.FavoriteRestaurantModel;
import com.appinnovates.campeat.model.UserModel;
import com.appinnovates.campeat.model.getAllDealsModel.RestaurantIdModel;
import com.appinnovates.campeat.services.DealService.DealStore;
import com.appinnovates.campeat.utils.DateFormatUtil;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

class FavoriteRestaurantStore {

    List<FavoriteRestaurantModel> saveData(List<Map> objects) {
        List<FavoriteRestaurantModel> restaurantBranchDealsModelList = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            for (Map parseObject : objects) {
                FavoriteRestaurantModel restaurantBranchDealsModel = new FavoriteRestaurantModel();
                restaurantBranchDealsModel.setId(parseObject.get("objectId").toString());
//                restaurantBranchDealsModel.setParseObject(parseObject);
                ParseObject userParseObject = (ParseObject)parseObject.get("customer_id");
                UserModel userModel = saveUserData(userParseObject);
                restaurantBranchDealsModel.setUserModel(userModel);
                ParseObject restaurantParseObject = (ParseObject)parseObject.get("restaurant_id");
                DealStore dealStore = new DealStore();
                RestaurantIdModel restaurantMeodel = dealStore.createBranchRestaurantModel(restaurantParseObject);
                restaurantBranchDealsModel.setRestaurantModel(restaurantMeodel);

                Date favoritedDate = (Date) parseObject.get("favorited_date");
                if (favoritedDate != null) {
                    restaurantBranchDealsModel.setFavorited_date(DateFormatUtil.getDateInString(favoritedDate));
                }
                restaurantBranchDealsModelList.add(restaurantBranchDealsModel);
            }
        }
        return restaurantBranchDealsModelList;
    }

    private UserModel saveUserData(ParseObject userParseObject) {
        UserModel userModel = null;
        if (userParseObject != null) {
            userModel = new UserModel();
            userModel.setId(userParseObject.getObjectId());
            userModel.setUserName(userParseObject.getString("username"));
            userModel.setEmail(userParseObject.getString("email"));
        }
        return userModel;
    }
}
