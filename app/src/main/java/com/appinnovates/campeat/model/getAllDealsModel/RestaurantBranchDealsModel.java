
package com.appinnovates.campeat.model.getAllDealsModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.appinnovates.campeat.model.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.parse.ParseObject;

import java.util.List;

@SuppressWarnings("unused")
public class RestaurantBranchDealsModel implements Parcelable {

    @SerializedName("__type")
    private String _Type;
    @Expose
    private List<BranchDealsModel> branchDealModels;
    @Expose
    private String className;
    @Expose
    private String country;
    @Expose
    private String createdAt;
    @Expose
    private String distance;
    @SerializedName("founded_date")
    private String foundedDate;
    @SerializedName("manager_name")
    private String managerName;
    @Expose
    private String objectId;
    @SerializedName("restaurant_name")
    private String restaurantName;
    @Expose
    private Long totalReviews;
    @Expose
    private String updatedAt;
    @SerializedName("user_id")
    private UserId userId;
    @Expose
    private String zipcode;
    @Expose
    private Long averageRating;
    @Expose
    private String city;
    @Expose
    private
    String description;
    @Expose
    private
    RestaurantBranchDealsModel restaurantModel;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ParseObject getParseObject() {
        return parseObject;
    }

    public void setParseObject(ParseObject parseObject) {
        this.parseObject = parseObject;
    }

    public String getFavorited_date() {
        return favorited_date;
    }

    public void setFavorited_date(String favorited_date) {
        this.favorited_date = favorited_date;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    private String id;
    private ParseObject parseObject;
    private String favorited_date;
    private UserModel userModel;

    protected RestaurantBranchDealsModel(Parcel in) {
        _Type = in.readString();
        branchDealModels = in.createTypedArrayList(BranchDealsModel.CREATOR);
        className = in.readString();
        country = in.readString();
        createdAt = in.readString();
        distance = in.readString();
        foundedDate = in.readString();
        managerName = in.readString();
        objectId = in.readString();
        restaurantName = in.readString();
        if (in.readByte() == 0) {
            totalReviews = null;
        } else {
            totalReviews = in.readLong();
        }
        updatedAt = in.readString();
        userId = in.readParcelable(UserId.class.getClassLoader());
        zipcode = in.readString();
        if (in.readByte() == 0) {
            averageRating = null;
        } else {
            averageRating = in.readLong();
        }
        city = in.readString();
        description = in.readString();
        pointer = in.readParcelable(ParseObject.class.getClassLoader());
        state = in.readString();
        streetAddress = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_Type);
        dest.writeTypedList(branchDealModels);
        dest.writeString(className);
        dest.writeString(country);
        dest.writeString(createdAt);
        dest.writeString(distance);
        dest.writeString(foundedDate);
        dest.writeString(managerName);
        dest.writeString(objectId);
        dest.writeString(restaurantName);
        if (totalReviews == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(totalReviews);
        }
        dest.writeString(updatedAt);
        dest.writeParcelable(userId, flags);
        dest.writeString(zipcode);
        if (averageRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(averageRating);
        }
        dest.writeString(city);
        dest.writeString(description);
        dest.writeParcelable(pointer, flags);
        dest.writeString(state);
        dest.writeString(streetAddress);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RestaurantBranchDealsModel> CREATOR = new Creator<RestaurantBranchDealsModel>() {
        @Override
        public RestaurantBranchDealsModel createFromParcel(Parcel in) {
            return new RestaurantBranchDealsModel(in);
        }

        @Override
        public RestaurantBranchDealsModel[] newArray(int size) {
            return new RestaurantBranchDealsModel[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ParseObject getRestaurantPointer() {
        return pointer;
    }

    public void setRestaurantPointer(ParseObject pointer) {
        this.pointer = pointer;
    }

    @Expose
    private ParseObject pointer;

    public Long getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Long averageRating) {
        this.averageRating = averageRating;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public static Creator<RestaurantBranchDealsModel> getCREATOR() {
        return CREATOR;
    }

    @Expose
    private String state;
    @SerializedName("street_address")
    private String streetAddress;

    public RestaurantBranchDealsModel() {
    }


    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }

    public List<BranchDealsModel> getBranchDealModels() {
        return branchDealModels;
    }

    public void setBranchDealModels(List<BranchDealsModel> branchDealModels) {
        this.branchDealModels = branchDealModels;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getFoundedDate() {
        return foundedDate;
    }

    public void setFoundedDate(String foundedDate) {
        this.foundedDate = foundedDate;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public RestaurantBranchDealsModel getRestaurantModel() {
        return restaurantModel;
    }

    public void setRestaurantModel(RestaurantBranchDealsModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }

}
