
package com.appinnovates.campeat.model.HomeBranchDeals;

import android.os.Parcel;
import android.os.Parcelable;

import com.appinnovates.campeat.model.getAllDealsModel.Operational;
import com.appinnovates.campeat.model.getAllDealsModel.RestaurantIdModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.List;

@SuppressWarnings("unused")
public class HomeBranchDealsModel implements Parcelable {

    @SerializedName("__type")
    private String _Type;
    @SerializedName("branch_name")
    private String branchName;
    @Expose
    private String city;
    @Expose
    private String className;
    @Expose
    private List<String> closed;
    @Expose
    private String country;
    @Expose
    private String createdAt;
    @Expose
    private List<BranchDealModel> deals;
    @Expose
    private Double distance;
    @SerializedName("geo_point")
    private ParseGeoPoint geoPoint;
    @Expose
    private String objectId;
    @Expose
    private Operational operational;
    @Expose
    private String phone;

    @SerializedName("street_address")
    private String streetAddress;
    @Expose
    private String updatedAt;

    @SerializedName("restaurant_id")
    private RestaurantIdModel mRestaurantIdModel;

    @Expose
    private int rating;


    public HomeBranchDealsModel(Parcel in) {
        _Type = in.readString();
        branchName = in.readString();
        city = in.readString();
        className = in.readString();
        closed = in.createStringArrayList();
        country = in.readString();
        createdAt = in.readString();
        deals = in.createTypedArrayList(BranchDealModel.CREATOR);
        if (in.readByte() == 0) {
            distance = null;
        } else {
            distance = in.readDouble();
        }
        geoPoint = in.readParcelable(ParseGeoPoint.class.getClassLoader());
        objectId = in.readString();
        operational = in.readParcelable(Operational.class.getClassLoader());
        phone = in.readString();
        streetAddress = in.readString();
        updatedAt = in.readString();
        mRestaurantIdModel = in.readParcelable(RestaurantIdModel.class.getClassLoader());
        rating = in.readInt();
        branchPointer = in.readParcelable(ParseObject.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_Type);
        dest.writeString(branchName);
        dest.writeString(city);
        dest.writeString(className);
        dest.writeStringList(closed);
        dest.writeString(country);
        dest.writeString(createdAt);
        dest.writeTypedList(deals);
        if (distance == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(distance);
        }
        dest.writeParcelable(geoPoint, flags);
        dest.writeString(objectId);
        dest.writeParcelable(operational, flags);
        dest.writeString(phone);
        dest.writeString(streetAddress);
        dest.writeString(updatedAt);
        dest.writeParcelable(mRestaurantIdModel, flags);
        dest.writeInt(rating);
        dest.writeParcelable(branchPointer, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HomeBranchDealsModel> CREATOR = new Creator<HomeBranchDealsModel>() {
        @Override
        public HomeBranchDealsModel createFromParcel(Parcel in) {
            return new HomeBranchDealsModel(in);
        }

        @Override
        public HomeBranchDealsModel[] newArray(int size) {
            return new HomeBranchDealsModel[size];
        }
    };

    public ParseObject getBranchPointer() {
        return branchPointer;
    }

    public void setBranchPointer(ParseObject branchPointer) {
        this.branchPointer = branchPointer;
    }

    @Expose
    ParseObject branchPointer;

    public HomeBranchDealsModel() {
    }




    public List<BranchDealModel> getDeals() {
        return deals;
    }

    public void setDeals(List<BranchDealModel> deals) {
        this.deals = deals;
    }

    public RestaurantIdModel getmRestaurantIdModel() {
        return mRestaurantIdModel;
    }

    public void setmRestaurantIdModel(RestaurantIdModel mRestaurantIdModel) {
        this.mRestaurantIdModel = mRestaurantIdModel;
    }


    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getClosed() {
        return closed;
    }

    public void setClosed(List<String> closed) {
        this.closed = closed;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<BranchDealModel> getBranchDealModel() {
        return deals;
    }

    public void setBranchDealModel(List<BranchDealModel> deals) {
        this.deals = deals;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public ParseGeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(ParseGeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Operational getOperational() {
        return operational;
    }

    public void setOperational(Operational operational) {
        this.operational = operational;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }





}
