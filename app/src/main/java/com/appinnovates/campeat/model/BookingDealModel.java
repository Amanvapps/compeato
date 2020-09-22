package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel;
import com.parse.ParseObject;

public class BookingDealModel implements Parcelable {

    private String id;
    private String booking_date;
    private String has_fulfilled_yn;
    private String fulfillment_date;
    private String no_of_people;
    private UserModel userModel;
    private BranchDealModel branchDealModel;
    private ParseObject bookingPointer;

    public BookingDealModel() {

    }

    protected BookingDealModel(Parcel in) {
        id = in.readString();
        booking_date = in.readString();
        has_fulfilled_yn = in.readString();
        fulfillment_date = in.readString();
        no_of_people = in.readString();
        userModel = in.readParcelable(UserModel.class.getClassLoader());
        branchDealModel = in.readParcelable(BranchDealModel.class.getClassLoader());
        bookingPointer = in.readParcelable(ParseObject.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(booking_date);
        dest.writeString(has_fulfilled_yn);
        dest.writeString(fulfillment_date);
        dest.writeString(no_of_people);
        dest.writeParcelable(userModel, flags);
        dest.writeParcelable(branchDealModel, flags);
        dest.writeParcelable(bookingPointer, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookingDealModel> CREATOR = new Creator<BookingDealModel>() {
        @Override
        public BookingDealModel createFromParcel(Parcel in) {
            return new BookingDealModel(in);
        }

        @Override
        public BookingDealModel[] newArray(int size) {
            return new BookingDealModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getHas_fulfilled_yn() {
        return has_fulfilled_yn;
    }

    public void setHas_fulfilled_yn(String has_fulfilled_yn) {
        this.has_fulfilled_yn = has_fulfilled_yn;
    }

    public String getFulfillment_date() {
        return fulfillment_date;
    }

    public void setFulfillment_date(String fulfillment_date) {
        this.fulfillment_date = fulfillment_date;
    }

    public String getNo_of_people() {
        return no_of_people;
    }

    public void setNo_of_people(String no_of_people) {
        this.no_of_people = no_of_people;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public BranchDealModel getBranchDealModel() {
        return branchDealModel;
    }

    public void setBranchDealModel(BranchDealModel branchDealModel) {
        this.branchDealModel = branchDealModel;
    }

    public ParseObject getBookingPointer() {
        return bookingPointer;
    }

    public void setBookingPointer(ParseObject bookingPointer) {
        this.bookingPointer = bookingPointer;
    }

}
