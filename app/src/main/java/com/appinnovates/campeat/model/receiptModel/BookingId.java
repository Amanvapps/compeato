
package com.appinnovates.campeat.model.receiptModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BookingId {

    @SerializedName("__type")
    private String _Type;
    @SerializedName("booking_date")
    private BookingDate bookingDate;
    @Expose
    private String className;
    @Expose
    private String createdAt;
    @SerializedName("deal_branch")
    private DealBranch dealBranch;
    @SerializedName("has_fulfilled_yn")
    private String hasFulfilledYn;
    @SerializedName("no_of_people")
    private Long noOfPeople;
    @Expose
    private String objectId;
    @Expose
    private String updatedAt;

    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }

    public BookingDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(BookingDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public DealBranch getDealBranch() {
        return dealBranch;
    }

    public void setDealBranch(DealBranch dealBranch) {
        this.dealBranch = dealBranch;
    }

    public String getHasFulfilledYn() {
        return hasFulfilledYn;
    }

    public void setHasFulfilledYn(String hasFulfilledYn) {
        this.hasFulfilledYn = hasFulfilledYn;
    }

    public Long getNoOfPeople() {
        return noOfPeople;
    }

    public void setNoOfPeople(Long noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
