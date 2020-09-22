
package com.appinnovates.campeat.model.getAllDealsModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class BranchDealsModel implements Parcelable {

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
    private List<com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel> deals;
    @Expose
    private Double distance;
    @SerializedName("geo_point")
    private GeoPoint geoPoint;
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

    public BranchDealsModel() {
    }


    protected BranchDealsModel(Parcel in) {
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
        objectId = in.readString();
        operational = in.readParcelable(Operational.class.getClassLoader());
        phone = in.readString();
        streetAddress = in.readString();
        updatedAt = in.readString();
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
        dest.writeString(objectId);
        dest.writeParcelable(operational, flags);
        dest.writeString(phone);
        dest.writeString(streetAddress);
        dest.writeString(updatedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BranchDealsModel> CREATOR = new Creator<BranchDealsModel>() {
        @Override
        public BranchDealsModel createFromParcel(Parcel in) {
            return new BranchDealsModel(in);
        }

        @Override
        public BranchDealsModel[] newArray(int size) {
            return new BranchDealsModel[size];
        }
    };

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

    public List<com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel> getBranchDealModel() {
        return deals;
    }

    public void setBranchDealModel(List<com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel> deals) {
        this.deals = deals;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
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

}
