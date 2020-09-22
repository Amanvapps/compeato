
package com.appinnovates.campeat.model.getAllDealsModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.parse.ParseObject;

@SuppressWarnings("unused")
public class RestaurantIdModel implements Parcelable {

    @SerializedName("className")
    private String mClassName;
    @SerializedName("country")
    private String mCountry;
    @Expose
    private
    String description;

    protected RestaurantIdModel(Parcel in) {
        mClassName = in.readString();
        mCountry = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            averageRating = null;
        } else {
            averageRating = in.readLong();
        }
        streetAddress = in.readString();
        city = in.readString();
        phone = in.readString();
        mCreatedAt = in.readString();
        if (in.readByte() == 0) {
            mDistance = null;
        } else {
            mDistance = in.readDouble();
        }
        mObjectId = in.readString();
        mRestaurantName = in.readString();
        if (in.readByte() == 0) {
            mTotalReviews = null;
        } else {
            mTotalReviews = in.readLong();
        }
        mUpdatedAt = in.readString();
        mUserId = in.readParcelable(UserId.class.getClassLoader());
        m_Type = in.readString();
        pointer = in.readParcelable(ParseObject.class.getClassLoader());
        logo = in.readParcelable(Logo.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mClassName);
        dest.writeString(mCountry);
        dest.writeString(description);
        if (averageRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(averageRating);
        }
        dest.writeString(streetAddress);
        dest.writeString(city);
        dest.writeString(phone);
        dest.writeString(mCreatedAt);
        if (mDistance == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mDistance);
        }
        dest.writeString(mObjectId);
        dest.writeString(mRestaurantName);
        if (mTotalReviews == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mTotalReviews);
        }
        dest.writeString(mUpdatedAt);
        dest.writeParcelable(mUserId, flags);
        dest.writeString(m_Type);
        dest.writeParcelable(pointer, flags);
        dest.writeParcelable(logo, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RestaurantIdModel> CREATOR = new Creator<RestaurantIdModel>() {
        @Override
        public RestaurantIdModel createFromParcel(Parcel in) {
            return new RestaurantIdModel(in);
        }

        @Override
        public RestaurantIdModel[] newArray(int size) {
            return new RestaurantIdModel[size];
        }
    };

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    @Expose
    private Logo logo;

    @Expose
    private Long averageRating;



    public Long getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Long averageRating) {
        this.averageRating = averageRating;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @SerializedName("street_address")
    private String streetAddress;
    @Expose
    private String city;
    @Expose
    private String phone;


    public ParseObject getPointer() {
        return pointer;
    }

    public void setPointer(ParseObject pointer) {
        this.pointer = pointer;
    }

    public Object getObjectPointer() {
        return pointer2;
    }

    public void setObjectPointer(Object pointer2) {
        this.pointer2 = pointer;
    }



    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("distance")
    private Double mDistance;
    @SerializedName("objectId")
    private String mObjectId;
    @SerializedName("restaurant_name")
    private String mRestaurantName;
    @SerializedName("totalReviews")
    private Long mTotalReviews;
    @SerializedName("updatedAt")
    private String mUpdatedAt;
    @SerializedName("user_id")
    private UserId mUserId;
    @SerializedName("__type")
    private String m_Type;
    @Expose
    private ParseObject pointer;

    @Expose
    private Object pointer2;


    public RestaurantIdModel(){}


    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        mClassName = className;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public Double getDistance() {
        return mDistance;
    }

    public void setDistance(Double distance) {
        mDistance = distance;
    }

    public String getObjectId() {
        return mObjectId;
    }

    public void setObjectId(String objectId) {
        mObjectId = objectId;
    }

    public String getRestaurantName() {
        return mRestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        mRestaurantName = restaurantName;
    }

    public Long getTotalReviews() {
        return mTotalReviews;
    }

    public void setTotalReviews(Long totalReviews) {
        mTotalReviews = totalReviews;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public UserId getUserId() {
        return mUserId;
    }

    public void setUserId(UserId userId) {
        mUserId = userId;
    }

    public String get_Type() {
        return m_Type;
    }

    public void set_Type(String _Type) {
        m_Type = _Type;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
