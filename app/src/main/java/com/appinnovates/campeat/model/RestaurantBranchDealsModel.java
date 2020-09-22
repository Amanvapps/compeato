package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseObject;

public class RestaurantBranchDealsModel implements Parcelable {

    public static final Creator<RestaurantBranchDealsModel> CREATOR = new Creator<RestaurantBranchDealsModel>() {
        @Override
        public RestaurantBranchDealsModel createFromParcel(Parcel in) {
            return new RestaurantBranchDealsModel(in);
        }

        @Override
        public RestaurantBranchDealsModel[] newArray(int size) {
            return new RestaurantBranchDealsModel[size];
        }
    };
    private String id;
    private ParseObject parseObject;
    private String favorited_date;
    private UserModel userModel;
    private com.appinnovates.campeat.model.getAllDealsModel.RestaurantBranchDealsModel restaurantModel;


    public RestaurantBranchDealsModel() {

    }

    private RestaurantBranchDealsModel(Parcel in) {
        id = in.readString();
        parseObject = in.readParcelable(ParseObject.class.getClassLoader());
        favorited_date = in.readString();
        userModel = in.readParcelable(UserModel.class.getClassLoader());
        restaurantModel = in.readParcelable(RestaurantModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(parseObject, flags);
        dest.writeString(favorited_date);
        dest.writeParcelable(userModel, flags);
        dest.writeParcelable(restaurantModel, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ParseObject getParseObject() {
        return parseObject;
    }

    public void setParseObject(ParseObject parseObject) {
        this.parseObject = parseObject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFavorited_date() {
        return favorited_date;
    }

    public void setFavorited_date(String favorited_date) {
        this.favorited_date = favorited_date;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public com.appinnovates.campeat.model.getAllDealsModel.RestaurantBranchDealsModel getRestaurantModel() {
        return restaurantModel;
    }

    public void setRestaurantModel(com.appinnovates.campeat.model.getAllDealsModel.RestaurantBranchDealsModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }
}
