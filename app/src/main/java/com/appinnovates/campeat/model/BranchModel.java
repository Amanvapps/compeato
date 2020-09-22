package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import org.json.JSONObject;

public class BranchModel implements Parcelable {
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
    private String id;
    private String branch_name;
    private String street_address;
    private String city;
    private String country;
    private String state;
    private String phone;
    private String zipcode;
    private String email;
    private int averageRating = 0;
    private ParseGeoPoint parseGeoPoint;
    private RestaurantModel restaurantModel;
    private ParseObject branchPointer;
    private OperationalModel operational;

    public BranchModel() {

    }

    private BranchModel(Parcel in) {
        id = in.readString();
        branch_name = in.readString();
        street_address = in.readString();
        city = in.readString();
        country = in.readString();
        state = in.readString();
        phone = in.readString();
        zipcode = in.readString();
        email = in.readString();
        averageRating = in.readInt();
        parseGeoPoint = in.readParcelable(ParseGeoPoint.class.getClassLoader());
        branchPointer = in.readParcelable(ParseObject.class.getClassLoader());
        restaurantModel = in.readParcelable(RestaurantModel.class.getClassLoader());
        operational = in.readParcelable(OperationalModel.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(branch_name);
        dest.writeString(street_address);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(state);
        dest.writeString(phone);
        dest.writeString(zipcode);
        dest.writeString(email);
        dest.writeInt(averageRating);
        dest.writeParcelable(parseGeoPoint,flags);
        dest.writeParcelable(branchPointer, flags);
        dest.writeParcelable(restaurantModel, 0);
        dest.writeParcelable(operational, flags);
    }

    public ParseObject getBranchPointer() {
        return branchPointer;
    }

    public void setBranchPointer(ParseObject branchPointer) {
        this.branchPointer = branchPointer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getStreet_address() {
        return street_address;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RestaurantModel getRestaurantModel() {
        return restaurantModel;
    }

    public void setRestaurantModel(RestaurantModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public ParseGeoPoint getParseGeoPoint() {
        return parseGeoPoint;
    }

    public void setParseGeoPoint(ParseGeoPoint parseGeoPoint) {
        this.parseGeoPoint = parseGeoPoint;
    }

    public OperationalModel getOperational() {
        return operational;
    }

    public void setOperational(OperationalModel operational) {
        this.operational = operational;
    }
}
