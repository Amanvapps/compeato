
package com.appinnovates.campeat.model.receiptModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BranchId implements Parcelable {

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
    private String country;
    @Expose
    private String createdAt;
    @Expose
    private String objectId;
    @Expose
    private String phone;
    @SerializedName("restaurant_id")
    private RestaurantId restaurantId;
    @SerializedName("street_address")
    private String streetAddress;
    @Expose
    private String updatedAt;

    protected BranchId(Parcel in) {
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
        country = in.readString();
        createdAt = in.readString();
        objectId = in.readString();
        phone = in.readString();
        streetAddress = in.readString();
        updatedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_Type);
        if (averageRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(averageRating);
        }
        dest.writeString(branchName);
        dest.writeString(city);
        dest.writeString(className);
        dest.writeStringList(closed);
        dest.writeString(country);
        dest.writeString(createdAt);
        dest.writeString(objectId);
        dest.writeString(phone);
        dest.writeString(streetAddress);
        dest.writeString(updatedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BranchId> CREATOR = new Creator<BranchId>() {
        @Override
        public BranchId createFromParcel(Parcel in) {
            return new BranchId(in);
        }

        @Override
        public BranchId[] newArray(int size) {
            return new BranchId[size];
        }
    };

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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(RestaurantId restaurantId) {
        this.restaurantId = restaurantId;
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
