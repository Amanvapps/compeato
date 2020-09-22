package com.appinnovates.campeat.model.getAllDealsModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;

public class RestaurantandBranches implements Parcelable {
    public RestaurantandBranches(BranchDealsModel branch, HomeBranchDealsModel restaurant) {
        this.branch = branch;
        this.restaurant = restaurant;
    }

    private BranchDealsModel branch;

    private HomeBranchDealsModel restaurant;
    public RestaurantandBranches(){

    }

    protected RestaurantandBranches(Parcel in) {
        branch = in.readParcelable(BranchDealsModel.class.getClassLoader());
        restaurant = in.readParcelable(RestaurantBranchDealsModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(branch, flags);
        dest.writeParcelable(restaurant, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RestaurantandBranches> CREATOR = new Creator<RestaurantandBranches>() {
        @Override
        public RestaurantandBranches createFromParcel(Parcel in) {
            return new RestaurantandBranches(in);
        }

        @Override
        public RestaurantandBranches[] newArray(int size) {
            return new RestaurantandBranches[size];
        }
    };

    public BranchDealsModel getBranch() {
        return branch;
    }

    public void setBranch(BranchDealsModel branch) {
        this.branch = branch;
    }

    public HomeBranchDealsModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(HomeBranchDealsModel restaurant) {
        this.restaurant = restaurant;
    }
}
