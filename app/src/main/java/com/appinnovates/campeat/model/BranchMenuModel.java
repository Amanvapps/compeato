package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.appinnovates.campeat.model.getAllDeal.Branch;

import java.util.List;

public class BranchMenuModel implements Parcelable{
    private String id;
    private String menu_name;
    private String price;
    private List<MenuTypeModel> menu_type;
    private String is_available_yn;
    private String menu_extra_details;
    private Branch branchModel;

    public BranchMenuModel() {}


    protected BranchMenuModel(Parcel in) {
        id = in.readString();
        menu_name = in.readString();
        price = in.readString();
        menu_type = in.createTypedArrayList(MenuTypeModel.CREATOR);
        is_available_yn = in.readString();
        menu_extra_details = in.readString();
        branchModel = in.readParcelable(BranchModel.class.getClassLoader());
    }

    public static final Creator<BranchMenuModel> CREATOR = new Creator<BranchMenuModel>() {
        @Override
        public BranchMenuModel createFromParcel(Parcel in) {
            return new BranchMenuModel(in);
        }

        @Override
        public BranchMenuModel[] newArray(int size) {
            return new BranchMenuModel[size];
        }
    };

    public List<MenuTypeModel> getMenu_type() {
        return menu_type;
    }

    public void setMenu_type(List<MenuTypeModel> menu_type) {
        this.menu_type = menu_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getIs_available_yn() {
        return is_available_yn;
    }

    public void setIs_available_yn(String is_available_yn) {
        this.is_available_yn = is_available_yn;
    }

    public String getMenu_extra_details() {
        return menu_extra_details;
    }

    public void setMenu_extra_details(String menu_extra_details) {
        this.menu_extra_details = menu_extra_details;
    }

    public Branch getBranchModel() {
        return branchModel;
    }

    public void setBranchModel(Branch branchModel) {
        this.branchModel = branchModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(menu_name);
        dest.writeString(price);
        dest.writeTypedList(menu_type);
        dest.writeString(is_available_yn);
        dest.writeString(menu_extra_details);
        dest.writeParcelable(branchModel, flags);
    }
}
