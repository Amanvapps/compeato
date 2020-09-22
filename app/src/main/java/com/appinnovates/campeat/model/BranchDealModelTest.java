package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.appinnovates.campeat.model.getAllDeal.Branch;
import com.appinnovates.campeat.model.getAllDeal.Deal;
import com.appinnovates.campeat.model.getAllDeal.Restaurant;
import com.parse.ParseObject;

import java.util.List;

public class BranchDealModelTest implements Parcelable{
    private Branch branch;
    private List<Deal> deal;
    private String objectId;
    private Restaurant restaurants;
    private ParseObject branchDealPointer;
    private boolean isExpanded = false;

    public BranchDealModelTest() {
    }


    private BranchDealModelTest(Parcel in) {
        branch = in.readParcelable(Branch.class.getClassLoader());
        deal = in.createTypedArrayList(Deal.CREATOR);
        objectId = in.readString();
        restaurants = in.readParcelable(Restaurant.class.getClassLoader());
        branchDealPointer = in.readParcelable(ParseObject.class.getClassLoader());
        isExpanded = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(branch, flags);
        dest.writeTypedList(deal);
        dest.writeString(objectId);
        dest.writeParcelable(restaurants, flags);
        dest.writeParcelable(branchDealPointer, flags);
        dest.writeByte((byte) (isExpanded ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BranchDealModelTest> CREATOR = new Creator<BranchDealModelTest>() {
        @Override
        public BranchDealModelTest createFromParcel(Parcel in) {
            return new BranchDealModelTest(in);
        }

        @Override
        public BranchDealModelTest[] newArray(int size) {
            return new BranchDealModelTest[size];
        }
    };

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setDeal(List<Deal> deal) {
        this.deal = deal;
    }

    public Branch getBranch() {
        return branch;
    }

    public List<Deal> getDeal() {
        return deal;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public ParseObject getBranchDealPointer() {
        return branchDealPointer;
    }

    public void setBranchDealPointer(ParseObject branchDealPointer) {
        this.branchDealPointer = branchDealPointer;
    }

    public Restaurant getRestaurant() {
        return restaurants;
    }

    public void setRestaurants(Restaurant restaurants) {
        this.restaurants = restaurants;
    }



}
