
package com.appinnovates.campeat.model.getAllDeal;

import android.os.Parcel;
import android.os.Parcelable;

import com.appinnovates.campeat.model.getAllDealsModel.Logo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.parse.ParseObject;

@SuppressWarnings("unused")
public class Restaurant implements Parcelable {

    public Restaurant(){

    }

    @SerializedName("__type")
    private String _Type;
    @Expose
    private Long averageRating;
    @Expose
    private ParseObject restaurantPointer;
    @Expose
    private Branch branch;
    @Expose
    private String city;
    @Expose
    private String className;
    @Expose
    private String distance;
    @Expose
    private String country;
    @Expose
    private String createdAt;
    @Expose
    private String description;
    @SerializedName("geo_point")
    private GeoPoint geoPoint;
    @Expose
    private Double lat;
    @Expose
    private Logo logo;
    @Expose
    private Double longu;
    @Expose
    private String objectId;
    @Expose
    private String phone;
    @SerializedName("restaurant_name")
    private String restaurantName;
    @SerializedName("street_address")
    private String streetAddress;
    @Expose
    private Long totalReviews;
    @Expose
    private String updatedAt;
    @SerializedName("user_id")
    private UserId userId;

    protected Restaurant(Parcel in) {
        _Type = in.readString();
        if (in.readByte() == 0) {
            averageRating = null;
        } else {
            averageRating = in.readLong();
        }
        restaurantPointer = in.readParcelable(ParseObject.class.getClassLoader());
        city = in.readString();
        className = in.readString();
        distance = in.readString();
        country = in.readString();
        createdAt = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            lat = null;
        } else {
            lat = in.readDouble();
        }
        if (in.readByte() == 0) {
            longu = null;
        } else {
            longu = in.readDouble();
        }
        objectId = in.readString();
        phone = in.readString();
        restaurantName = in.readString();
        streetAddress = in.readString();
        if (in.readByte() == 0) {
            totalReviews = null;
        } else {
            totalReviews = in.readLong();
        }
        updatedAt = in.readString();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public ParseObject getRestaurantPointer() {
        return restaurantPointer;
    }

    public void setRestaurantPointer(ParseObject restaurantPointer) {
        this.restaurantPointer = restaurantPointer;
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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public Double getLong() {
        return longu;
    }

    public void setLong(Double longu) {
        this.longu = longu;
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

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public Long getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Long totalReviews) {
        this.totalReviews = totalReviews;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
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
        parcel.writeParcelable(restaurantPointer, i);
        parcel.writeString(city);
        parcel.writeString(className);
        parcel.writeString(distance);
        parcel.writeString(country);
        parcel.writeString(createdAt);
        parcel.writeString(description);
        if (lat == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(lat);
        }
        if (longu == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(longu);
        }
        parcel.writeString(objectId);
        parcel.writeString(phone);
        parcel.writeString(restaurantName);
        parcel.writeString(streetAddress);
        if (totalReviews == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(totalReviews);
        }
        parcel.writeString(updatedAt);
    }
}
