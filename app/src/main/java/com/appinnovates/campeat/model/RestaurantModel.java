package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseFile;
import com.parse.ParseObject;

public class RestaurantModel implements Parcelable {
    public static final Creator<RestaurantModel> CREATOR = new Creator<RestaurantModel>() {
        @Override
        public RestaurantModel createFromParcel(Parcel in) {
            return new RestaurantModel(in);
        }

        @Override
        public RestaurantModel[] newArray(int size) {
            return new RestaurantModel[size];
        }
    };
    private String id;
    private ParseObject restaurantPointer;
    private String restaurant_name;
    private String manager_name;
    private String manager_surname;
    private String street_address;
    private String city;
    private String country;
    private String state;
    private String phone;
    private String zipcode;
    private String email;
    private String restaurant_type;
    private String founded_date;
    private String description;
    private ParseFile imageFile;
    private int averageRating = 0;

    public RestaurantModel() {

    }

    private RestaurantModel(Parcel in) {
        id = in.readString();
        restaurantPointer = in.readParcelable(ParseObject.class.getClassLoader());
        restaurant_name = in.readString();
        manager_name = in.readString();
        manager_surname = in.readString();
        street_address = in.readString();
        city = in.readString();
        country = in.readString();
        state = in.readString();
        phone = in.readString();
        zipcode = in.readString();
        email = in.readString();
        restaurant_type = in.readString();
        founded_date = in.readString();
        description = in.readString();
        averageRating = in.readInt();
        imageFile = in.readParcelable(ParseFile.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(restaurantPointer, flags);
        dest.writeString(restaurant_name);
        dest.writeString(manager_name);
        dest.writeString(manager_surname);
        dest.writeString(street_address);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeString(state);
        dest.writeString(phone);
        dest.writeString(zipcode);
        dest.writeString(email);
        dest.writeString(restaurant_type);
        dest.writeString(founded_date);
        dest.writeString(description);
        dest.writeInt(averageRating);
        dest.writeParcelable(imageFile,flags);
    }

    public ParseObject getRestaurantPointer() {
        return restaurantPointer;
    }

    public void setRestaurantPointer(ParseObject restaurantPointer) {
        this.restaurantPointer = restaurantPointer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public String getManager_surname() {
        return manager_surname;
    }

    public void setManager_surname(String manager_surname) {
        this.manager_surname = manager_surname;
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

    public String getRestaurant_type() {
        return restaurant_type;
    }

    public void setRestaurant_type(String restaurant_type) {
        this.restaurant_type = restaurant_type;
    }

    public String getFounded_date() {
        return founded_date;
    }

    public void setFounded_date(String founded_date) {
        this.founded_date = founded_date;
    }

    public ParseFile getImageFile() {
        return imageFile;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public void setImageFile(ParseFile imageFile) {
        this.imageFile = imageFile;
    }
}
