
package com.appinnovates.campeat.model.getAllDeal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.List;

@SuppressWarnings("unused")
public class RestaurantDeals implements Parcelable {



    public RestaurantDeals(){

    }
    protected RestaurantDeals(Parcel in) {
         deals= in.readParcelable(Deal.class.getClassLoader());
    }

    public static final Creator<RestaurantDeals> CREATOR = new Creator<RestaurantDeals>() {
        @Override
        public RestaurantDeals createFromParcel(Parcel in) {
            return new RestaurantDeals(in);
        }

        @Override
        public RestaurantDeals[] newArray(int size) {
            return new RestaurantDeals[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    @Expose
    private Restaurant restaurant;

    @Expose
    private List<Deal> deals;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }
}
