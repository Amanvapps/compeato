
package com.appinnovates.campeat.model.getAllDeal;

import android.os.Parcel;
import android.os.Parcelable;

import com.appinnovates.campeat.model.BookingDealModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.parse.ParseObject;

import java.util.Date;

@SuppressWarnings("unused")
public class Deal implements Parcelable {

    public Deal(){

    }
    @SerializedName("__type")
    private String _Type;
    @Expose
    private String className;
    @Expose
    private ParseObject dealPointer;
    @Expose
    private String createdAt;
    @SerializedName("deal_name")
    private String dealName;
    @SerializedName("min_person_required")
    private String person;
    @SerializedName("deal_url")
    private DealUrl dealUrl;
    @SerializedName("deals_left")
    private Long dealsLeft;
    @SerializedName("discount_rate")
    private Long discountRate;
    @SerializedName("end_date")
    private Date endDate;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("free_coupon_discount")
    private Long freeCouponDiscount;
    @SerializedName("group_discount_rate")
    private Long groupDiscountRate;
    @Expose
    private String objectId;
    @SerializedName("repeat_type")
    private String repeatType;
    @SerializedName("start_date")
    private Date startDate;
    @SerializedName("start_time")
    private String startTime;
    @Expose
    private String type;
    @Expose
    private String updatedAt;
    @Expose
    private String getDeal;

    private int[] indexes;
    private BookingDealModel bookingDealModel;
    @Expose
    String timeLeft;


    protected Deal(Parcel in) {
        _Type = in.readString();
        className = in.readString();
        dealPointer = in.readParcelable(ParseObject.class.getClassLoader());
        createdAt = in.readString();
        dealName = in.readString();
        person = in.readString();
        dealUrl = in.readParcelable(DealUrl.class.getClassLoader());
        if (in.readByte() == 0) {
            dealsLeft = null;
        } else {
            dealsLeft = in.readLong();
        }
        if (in.readByte() == 0) {
            discountRate = null;
        } else {
            discountRate = in.readLong();
        }
        endTime = in.readString();
        if (in.readByte() == 0) {
            freeCouponDiscount = null;
        } else {
            freeCouponDiscount = in.readLong();
        }
        if (in.readByte() == 0) {
            groupDiscountRate = null;
        } else {
            groupDiscountRate = in.readLong();
        }
        objectId = in.readString();
        repeatType = in.readString();
        startTime = in.readString();
        type = in.readString();
        updatedAt = in.readString();
        getDeal = in.readString();
        indexes = in.createIntArray();
        bookingDealModel = in.readParcelable(BookingDealModel.class.getClassLoader());
        timeLeft = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_Type);
        dest.writeString(className);
        dest.writeParcelable(dealPointer, flags);
        dest.writeString(createdAt);
        dest.writeString(dealName);
        dest.writeString(person);
        dest.writeParcelable(dealUrl, flags);
        if (dealsLeft == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(dealsLeft);
        }
        if (discountRate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(discountRate);
        }
        dest.writeString(endTime);
        if (freeCouponDiscount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(freeCouponDiscount);
        }
        if (groupDiscountRate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(groupDiscountRate);
        }
        dest.writeString(objectId);
        dest.writeString(repeatType);
        dest.writeString(startTime);
        dest.writeString(type);
        dest.writeString(updatedAt);
        dest.writeString(getDeal);
        dest.writeIntArray(indexes);
        dest.writeParcelable(bookingDealModel, flags);
        dest.writeString(timeLeft);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Deal> CREATOR = new Creator<Deal>() {
        @Override
        public Deal createFromParcel(Parcel in) {
            return new Deal(in);
        }

        @Override
        public Deal[] newArray(int size) {
            return new Deal[size];
        }
    };

    public int[] getIndexes() {
        return indexes;
    }

    public void setIndexes(int[] indexes) {
        this.indexes = indexes;
    }

    public ParseObject getDealPointer() {
        return dealPointer;
    }

    public void setDealPointer(ParseObject dealPointer) {
        this.dealPointer = dealPointer;
    }

    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
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

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public DealUrl getDealUrl() {
        return dealUrl;
    }

    public void setDealUrl(DealUrl dealUrl) {
        this.dealUrl = dealUrl;
    }

    public Long getDealsLeft() {
        return dealsLeft;
    }

    public void setDealsLeft(Long dealsLeft) {
        this.dealsLeft = dealsLeft;
    }

    public Long getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Long discountRate) {
        this.discountRate = discountRate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getFreeCouponDiscount() {
        return freeCouponDiscount;
    }

    public void setFreeCouponDiscount(Long freeCouponDiscount) {
        this.freeCouponDiscount = freeCouponDiscount;
    }

    public Long getGroupDiscountRate() {
        return groupDiscountRate;
    }

    public void setGroupDiscountRate(Long groupDiscountRate) {
        this.groupDiscountRate = groupDiscountRate;
    }

    public BookingDealModel getBookingDealModel() {
        return bookingDealModel;
    }

    public void setBookingDealModel(BookingDealModel bookingDealModel) {
        this.bookingDealModel = bookingDealModel;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getMinPersonRequired() {
        return person;
    }

    public void setMinPersonRequired(String person) {
        this.person = person;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getGetDeal() {
        return getDeal;
    }

    public void setGetDeal(String getDeal) {
        this.getDeal = getDeal;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }


}
