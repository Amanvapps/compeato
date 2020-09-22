
package com.appinnovates.campeat.model.getAllDealsModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.appinnovates.campeat.model.BookingDealModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.parse.ParseObject;

@SuppressWarnings("unused")
public class DealModel implements Parcelable {

    @SerializedName("__type")
    private String _Type;
    @SerializedName("active_deal_yn")
    private String activeDealYn;
    @Expose
    private BranchDealsModel branchDealsModel;
    @Expose
    private String className;
    @Expose
    private String createdAt;
    @Expose
    private DealModel dealModel;
    @SerializedName("deal_name")
    private String dealName;
    @SerializedName("deal_url")
    private DealUrl dealUrl;
    @SerializedName("deals_left")
    private Long dealsLeft;
    @SerializedName("discount_rate")
    private Long discountRate;
    @SerializedName("end_date")
    private EndDate endDate;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("group_discount_rate")
    private Long groupDiscountRate;
    @Expose
    private String objectId;
    @SerializedName("repeat_type")
    private String repeatType;
    @SerializedName("start_date")
    private StartDate startDate;
    @SerializedName("start_time")
    private String startTime;

    protected DealModel(Parcel in) {
        _Type = in.readString();
        activeDealYn = in.readString();
        branchDealsModel = in.readParcelable(BranchDealsModel.class.getClassLoader());
        className = in.readString();
        createdAt = in.readString();
        dealModel = in.readParcelable(DealModel.class.getClassLoader());
        dealName = in.readString();
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
        endDate = in.readParcelable(EndDate.class.getClassLoader());
        endTime = in.readString();
        if (in.readByte() == 0) {
            groupDiscountRate = null;
        } else {
            groupDiscountRate = in.readLong();
        }
        objectId = in.readString();
        repeatType = in.readString();
        startDate = in.readParcelable(StartDate.class.getClassLoader());
        startTime = in.readString();
        dealDesc = in.readString();
        if (in.readByte() == 0) {
            leftTime = null;
        } else {
            leftTime = in.readLong();
        }
        timeToStart = in.readString();
        timeLeft = in.readString();
        dealExpire = in.readString();
        bookingDealModel = in.readParcelable(BookingDealModel.class.getClassLoader());
        indexes = in.createIntArray();
        getDeal = in.readString();
        minPersonRequired = in.readString();
        if (in.readByte() == 0) {
            freeCouponDiscount = null;
        } else {
            freeCouponDiscount = in.readLong();
        }
        type = in.readString();
        updatedAt = in.readString();
        isFixed = in.readByte() != 0;
        dealPointer = in.readParcelable(ParseObject.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_Type);
        dest.writeString(activeDealYn);
        dest.writeParcelable(branchDealsModel, flags);
        dest.writeString(className);
        dest.writeString(createdAt);
        dest.writeParcelable(dealModel, flags);
        dest.writeString(dealName);
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
        dest.writeParcelable(endDate, flags);
        dest.writeString(endTime);
        if (groupDiscountRate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(groupDiscountRate);
        }
        dest.writeString(objectId);
        dest.writeString(repeatType);
        dest.writeParcelable(startDate, flags);
        dest.writeString(startTime);
        dest.writeString(dealDesc);
        if (leftTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(leftTime);
        }
        dest.writeString(timeToStart);
        dest.writeString(timeLeft);
        dest.writeString(dealExpire);
        dest.writeParcelable(bookingDealModel, flags);
        dest.writeIntArray(indexes);
        dest.writeString(getDeal);
        dest.writeString(minPersonRequired);
        if (freeCouponDiscount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(freeCouponDiscount);
        }
        dest.writeString(type);
        dest.writeString(updatedAt);
        dest.writeByte((byte) (isFixed ? 1 : 0));
        dest.writeParcelable(dealPointer, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DealModel> CREATOR = new Creator<DealModel>() {
        @Override
        public DealModel createFromParcel(Parcel in) {
            return new DealModel(in);
        }

        @Override
        public DealModel[] newArray(int size) {
            return new DealModel[size];
        }
    };

    public String getDealDesc() {
        return dealDesc;
    }

    public void setDealDesc(String dealDesc) {
        this.dealDesc = dealDesc;
    }

    @SerializedName("deal_description")
    private String dealDesc;

    private Long leftTime;

    @Expose
    private String timeToStart;

    @Expose
    private
    String timeLeft;

    @Expose
    private String dealExpire;

    private
    BookingDealModel bookingDealModel;

    public BookingDealModel getBookingDealModel() {
        return bookingDealModel;
    }

    public void setBookingDealModel(BookingDealModel bookingDealModel) {
        this.bookingDealModel = bookingDealModel;
    }

    public int[] getIndexes() {
        return indexes;
    }

    public void setIndexes(int[] indexes) {
        this.indexes = indexes;
    }

    public String getGetDeal() {
        return getDeal;
    }

    public void setGetDeal(String getDeal) {
        this.getDeal = getDeal;
    }

    @Expose
    private int[] indexes;
    @Expose
    private String getDeal;

    public String getMinPersonRequired() {
        return minPersonRequired;
    }

    public void setMinPersonRequired(String minPersonRequired) {
        this.minPersonRequired = minPersonRequired;
    }

    public Long getFreeCouponDiscount() {
        return freeCouponDiscount;
    }

    public void setFreeCouponDiscount(Long freeCouponDiscount) {
        this.freeCouponDiscount = freeCouponDiscount;
    }

    @SerializedName("min_person_required")
    private String minPersonRequired;
    @Expose
    private Long freeCouponDiscount;
    @Expose
    private String type;
    @Expose
    private String updatedAt;
    @Expose
    private boolean isFixed;

    public ParseObject getDealPointer() {
        return dealPointer;
    }

    public void setDealPointer(ParseObject dealPointer) {
        this.dealPointer = dealPointer;
    }

    @Expose
    private ParseObject dealPointer;

    public DealModel()
    {

    }


    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }

    public String getActiveDealYn() {
        return activeDealYn;
    }

    public void setActiveDealYn(String activeDealYn) {
        this.activeDealYn = activeDealYn;
    }

    public BranchDealsModel getBranchDealsModel() {
        return branchDealsModel;
    }

    public void setBranchDealsModel(BranchDealsModel branchDealsModel) {
        this.branchDealsModel = branchDealsModel;
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

    public DealModel getDealModel() {
        return dealModel;
    }

    public void setDealModel(DealModel dealModel) {
        this.dealModel = dealModel;
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

    public EndDate getEndDate() {
        return endDate;
    }

    public void setEndDate(EndDate endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getGroupDiscountRate() {
        return groupDiscountRate;
    }

    public void setGroupDiscountRate(Long groupDiscountRate) {
        this.groupDiscountRate = groupDiscountRate;
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

    public StartDate getStartDate() {
        return startDate;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public String getTimeToStart() {
        return timeToStart;
    }

    public void setTimeToStart(String timeToStart) {
        this.timeToStart = timeToStart;
    }

    public String getDealExpire() {
        return dealExpire;
    }

    public void setDealExpire(String dealExpire) {
        this.dealExpire = dealExpire;
    }

    public boolean getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(boolean fixed) {
        isFixed = fixed;
    }

    public Long getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(Long leftTime) {
        this.leftTime = leftTime;
    }
}
