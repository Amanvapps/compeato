package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.appinnovates.campeat.model.getAllDealsModel.BranchModel;
import com.appinnovates.campeat.model.getAllDealsModel.DealModel;

public class ReviewModel implements Parcelable {
    public static final Creator<ReviewModel> CREATOR = new Creator<ReviewModel>() {
        @Override
        public ReviewModel createFromParcel(Parcel in) {
            return new ReviewModel(in);
        }

        @Override
        public ReviewModel[] newArray(int size) {
            return new ReviewModel[size];
        }
    };
    private String id;
    private String customer_id;
    private String branch_id;
    private String deal_id;
    private BranchModel branch_Model;
    private DealModel deal_Model;
    private String review;
    private String review_date;
    private int review_point;
    private UserModel userModel;

    private ReviewModel(Parcel in) {
        id = in.readString();
        customer_id = in.readString();
        branch_id = in.readString();
        deal_id = in.readString();
        branch_Model = in.readParcelable(BranchModel.class.getClassLoader());
        deal_Model = in.readParcelable(DealsModel.class.getClassLoader());
        review = in.readString();
        review_date = in.readString();
        review_point = in.readInt();
        userModel = in.readParcelable(UserModel.class.getClassLoader());
    }

    public ReviewModel() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(customer_id);
        dest.writeString(branch_id);
        dest.writeString(deal_id);
        dest.writeParcelable(branch_Model, flags);
        dest.writeParcelable(deal_Model, flags);
        dest.writeString(review);
        dest.writeString(review_date);
        dest.writeInt(review_point);
        dest.writeParcelable(userModel,0);
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(String deal_id) {
        this.deal_id = deal_id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }

    public int getReview_point() {
        return review_point;
    }

    public void setReview_point(int review_point) {
        this.review_point = review_point;
    }

    public BranchModel getBranch_Model() {
        return branch_Model;
    }

    public void setBranch_Model(BranchModel branch_Model) {
        this.branch_Model = branch_Model;
    }

    public DealModel getDeal_Model() {
        return deal_Model;
    }

    public void setDeal_Model(DealModel deal_Model) {
        this.deal_Model = deal_Model;
    }
}
