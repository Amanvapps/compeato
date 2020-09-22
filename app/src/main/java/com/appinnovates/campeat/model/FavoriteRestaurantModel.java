package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.appinnovates.campeat.model.getAllDealsModel.RestaurantIdModel;
import com.parse.ParseObject;

public class FavoriteRestaurantModel implements Parcelable {

    private String id;
    private ParseObject parseObject;
    private String favorited_date;
    private UserModel userModel;
    private RestaurantIdModel restaurantModel;


    public FavoriteRestaurantModel() {

    }


    protected FavoriteRestaurantModel(Parcel in) {
        id = in.readString();
        parseObject = in.readParcelable(ParseObject.class.getClassLoader());
        favorited_date = in.readString();
        userModel = in.readParcelable(UserModel.class.getClassLoader());
        restaurantModel = in.readParcelable(RestaurantIdModel.class.getClassLoader());
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

    public static final Creator<FavoriteRestaurantModel> CREATOR = new Creator<FavoriteRestaurantModel>() {
        @Override
        public FavoriteRestaurantModel createFromParcel(Parcel in) {
            return new FavoriteRestaurantModel(in);
        }

        @Override
        public FavoriteRestaurantModel[] newArray(int size) {
            return new FavoriteRestaurantModel[size];
        }
    };

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

    public RestaurantIdModel getRestaurantModel() {
        return restaurantModel;
    }

    public void setRestaurantModel(RestaurantIdModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }
}
