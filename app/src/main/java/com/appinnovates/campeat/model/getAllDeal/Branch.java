
package com.appinnovates.campeat.model.getAllDeal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.List;
@SuppressWarnings("unused")
public class Branch implements Parcelable {

    public Branch(){

    }

    @SerializedName("__type")
    private String _Type;
    @Expose
    private Long averageRating;
    @SerializedName("branch_name")
    private String branchName;
    @Expose
    private String city;
    @Expose
    private String className;
    @Expose
    private List<String> closed;
    @Expose
    private ParseObject branchPointer;
    @Expose
    private String country;
    @Expose
    private String createdAt;
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

    Restaurant restaurantModel;

    protected Branch(Parcel in) {
        _Type = in.readString();
        if (in.readByte() == 0) {
            averageRating = null;
        } else {
            averageRating = in.readLong();
        }
        branchName = in.readString();
        city = in.readString();
        className = in.readString();
        closed = in.createStringArrayList();
        branchPointer = in.readParcelable(ParseObject.class.getClassLoader());
        country = in.readString();
        createdAt = in.readString();
        geoPoint = in.readParcelable(ParseGeoPoint.class.getClassLoader());
        objectId = in.readString();
        phone = in.readString();
        streetAddress = in.readString();
        updatedAt = in.readString();
        restaurantModel = in.readParcelable(Restaurant.class.getClassLoader());
    }

    public static final Creator<Branch> CREATOR = new Creator<Branch>() {
        @Override
        public Branch createFromParcel(Parcel in) {
            return new Branch(in);
        }

        @Override
        public Branch[] newArray(int size) {
            return new Branch[size];
        }
    };

    public Restaurant getRestaurantModel() {
        return restaurantModel;
    }

    public void setRestaurantModel(Restaurant restaurantModel) {
        this.restaurantModel = restaurantModel;
    }


    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }

    public Long getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Long averageRating) {
        this.averageRating = averageRating;
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

    public ParseObject getBranchPointer() {
        return branchPointer;
    }

    public void setBranchPointer(ParseObject branchPointer) {
        this.branchPointer = branchPointer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_Type);
        if (averageRating == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(averageRating);
        }
        parcel.writeString(branchName);
        parcel.writeString(city);
        parcel.writeString(className);
        parcel.writeStringList(closed);
        parcel.writeParcelable(branchPointer, i);
        parcel.writeString(country);
        parcel.writeString(createdAt);
        parcel.writeParcelable(geoPoint, i);
        parcel.writeString(objectId);
        parcel.writeString(phone);
        parcel.writeString(streetAddress);
        parcel.writeString(updatedAt);
        parcel.writeParcelable(restaurantModel, i);
    }
}
