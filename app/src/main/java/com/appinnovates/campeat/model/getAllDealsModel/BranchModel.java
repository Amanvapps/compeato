
package com.appinnovates.campeat.model.getAllDealsModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.List;

@SuppressWarnings("unused")
public class BranchModel implements Parcelable {

    @SerializedName("branch_name")
    private String mBranchName;
    @SerializedName("city")
    private String mCity;
    @SerializedName("className")
    private String mClassName;
    @SerializedName("closed")
    private List<String> mClosed;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("distance")
    private Double mDistance;
    @SerializedName("geo_point")
    private ParseGeoPoint mGeoPoint;
    @SerializedName("objectId")
    private String mObjectId;
    @SerializedName("operational")
    private Operational mOperational;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("restaurant_id")
    private RestaurantIdModel mRestaurantIdModel;
    @SerializedName("street_address")
    private String mStreetAddress;
    @SerializedName("updatedAt")
    private String mUpdatedAt;
    @SerializedName("__type")
    private String m_Type;

    protected BranchModel(Parcel in) {
        mBranchName = in.readString();
        mCity = in.readString();
        mClassName = in.readString();
        mClosed = in.createStringArrayList();
        mCountry = in.readString();
        mCreatedAt = in.readString();
        if (in.readByte() == 0) {
            mDistance = null;
        } else {
            mDistance = in.readDouble();
        }
        mGeoPoint = in.readParcelable(ParseGeoPoint.class.getClassLoader());
        mObjectId = in.readString();
        mOperational = in.readParcelable(Operational.class.getClassLoader());
        mPhone = in.readString();
        mRestaurantIdModel = in.readParcelable(RestaurantIdModel.class.getClassLoader());
        mStreetAddress = in.readString();
        mUpdatedAt = in.readString();
        m_Type = in.readString();
        if (in.readByte() == 0) {
            averageRating = null;
        } else {
            averageRating = in.readLong();
        }
        branchPointer = in.readParcelable(ParseObject.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBranchName);
        dest.writeString(mCity);
        dest.writeString(mClassName);
        dest.writeStringList(mClosed);
        dest.writeString(mCountry);
        dest.writeString(mCreatedAt);
        if (mDistance == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mDistance);
        }
        dest.writeParcelable(mGeoPoint, flags);
        dest.writeString(mObjectId);
        dest.writeParcelable(mOperational, flags);
        dest.writeString(mPhone);
        dest.writeParcelable(mRestaurantIdModel, flags);
        dest.writeString(mStreetAddress);
        dest.writeString(mUpdatedAt);
        dest.writeString(m_Type);
        if (averageRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(averageRating);
        }
        dest.writeParcelable(branchPointer, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BranchModel> CREATOR = new Creator<BranchModel>() {
        @Override
        public BranchModel createFromParcel(Parcel in) {
            return new BranchModel(in);
        }

        @Override
        public BranchModel[] newArray(int size) {
            return new BranchModel[size];
        }
    };

    public Long getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Long averageRating) {
        this.averageRating = averageRating;
    }

    @Expose
    private Long averageRating;

    public ParseObject getBranchPointer() {
        return branchPointer;
    }

    public void setBranchPointer(ParseObject branchPointer) {
        this.branchPointer = branchPointer;
    }

    @Expose
    ParseObject branchPointer;

    public BranchModel() {
    }


    public String getBranchName() {
        return mBranchName;
    }

    public void setBranchName(String branchName) {
        mBranchName = branchName;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        mClassName = className;
    }

    public List<String> getClosed() {
        return mClosed;
    }

    public void setClosed(List<String> closed) {
        mClosed = closed;
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

    public ParseGeoPoint getGeoPoint() {
        return mGeoPoint;
    }

    public void setGeoPoint(ParseGeoPoint geoPoint) {
        mGeoPoint = geoPoint;
    }

    public String getObjectId() {
        return mObjectId;
    }

    public void setObjectId(String objectId) {
        mObjectId = objectId;
    }

    public Operational getOperational() {
        return mOperational;
    }

    public void setOperational(Operational operational) {
        mOperational = operational;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public RestaurantIdModel getRestaurantId() {
        return mRestaurantIdModel;
    }

    public void setRestaurantId(RestaurantIdModel restaurantIdModel) {
        mRestaurantIdModel = restaurantIdModel;
    }

    public String getStreetAddress() {
        return mStreetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        mStreetAddress = streetAddress;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String get_Type() {
        return m_Type;
    }

    public void set_Type(String _Type) {
        m_Type = _Type;
    }

}
