package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import com.parse.ParseFile;
import com.parse.ParseObject;


import java.util.Date;
import java.util.List;

public class DealsModel implements Parcelable {

    private String id;
    private String deal_name;
    private int discount_rate;
    private int group_discount_rate;
    private int dealsLeft = 0;
    private String min_person_required;
    private String max_person_required;
    private Date start_date;
    private String start_time;
    private Date end_date;
    private String end_time;
    private String repeat_type;
    private String active_deal_yn;
    private String createdAt;
    private String timeLeft;
    private String getDeal;
    private String type;
    private int timeGone = 0;
    private ParseFile imageFile;
    private BranchModel branchModel;
    private BookingDealModel bookingDealModel;
    private ParseObject dealPointer;
    private int freeCouponDiscount;
    private int[] indexes,usedIndexes;

    public DealsModel() {
    }

    public int[] getIndexes() {
        return indexes;
    }

    public void setIndexes(int[] indexes) {
        this.indexes = indexes;
    }

    protected DealsModel(Parcel in) {
        bookingDealModel = in.readParcelable(BookingDealModel.class.getClassLoader());
        id = in.readString();
        deal_name = in.readString();
        dealsLeft = in.readInt();
        discount_rate = in.readInt();
        group_discount_rate = in.readInt();
        min_person_required = in.readString();
        max_person_required = in.readString();
        start_time = in.readString();
        end_time = in.readString();
        repeat_type = in.readString();
        active_deal_yn = in.readString();
        createdAt = in.readString();
        timeLeft = in.readString();
        getDeal = in.readString();
        type = in.readString();
        timeGone = in.readInt();
        freeCouponDiscount = in.readInt();
        dealPointer = in.readParcelable(ParseObject.class.getClassLoader());
        imageFile = in.readParcelable(ParseFile.class.getClassLoader());
        branchModel = in.readParcelable(BranchModel.class.getClassLoader());
        indexes = in.createIntArray();
    }

    public static final Creator<DealsModel> CREATOR = new Creator<DealsModel>() {
        @Override
        public DealsModel createFromParcel(Parcel in) {
            return new DealsModel(in);
        }

        @Override
        public DealsModel[] newArray(int size) {
            return new DealsModel[size];
        }
    };

    public BranchModel getBranchModel() {
        return branchModel;
    }

    public void setBranchModel(BranchModel branchModel) {
        this.branchModel = branchModel;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeal_name() {
        return deal_name;
    }

    public void setDeal_name(String deal_name) {
        this.deal_name = deal_name;
    }

    public int getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(int discount_rate) {
        this.discount_rate = discount_rate;
    }

    public String getMin_person_required() {
        return min_person_required;
    }

    public void setMin_person_required(String min_person_required) {
        this.min_person_required = min_person_required;
    }

    public String getMax_person_required() {
        return max_person_required;
    }

    public void setMax_person_required(String max_person_required) {
        this.max_person_required = max_person_required;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getRepeat_type() {
        return repeat_type;
    }

    public void setRepeat_type(String repeat_type) {
        this.repeat_type = repeat_type;
    }

    public String getActive_deal_yn() {
        return active_deal_yn;
    }

    public void setActive_deal_yn(String active_deal_yn) {
        this.active_deal_yn = active_deal_yn;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getTimeGone() {
        return timeGone;
    }

    public void setTimeGone(int timeGone) {
        this.timeGone = timeGone;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public ParseFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(ParseFile imageFile) {
        this.imageFile = imageFile;
    }

    public ParseObject getDealPointer() {
        return dealPointer;
    }

    public void setDealPointer(ParseObject dealPointer) {
        this.dealPointer = dealPointer;
    }

    public int getDealsLeft() {
        return dealsLeft;
    }

    public void setDealsLeft(int dealsLeft) {
        this.dealsLeft = dealsLeft;
    }

    public BookingDealModel getBookingDealModel() {
        return bookingDealModel;
    }

    public void setBookingDealModel(BookingDealModel bookingDealModel) {
        this.bookingDealModel = bookingDealModel;
    }

    public String getGetDeal() {
        return getDeal;
    }

    public void setGetDeal(String getDeal) {
        this.getDeal = getDeal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGroup_discount_rate() {
        return group_discount_rate;
    }

    public void setGroup_discount_rate(int group_discount_rate) {
        this.group_discount_rate = group_discount_rate;
    }

    public int getFreeCouponDiscount() {
        return freeCouponDiscount;
    }

    public void setFreeCouponDiscount(int freeCouponDiscount) {
        this.freeCouponDiscount = freeCouponDiscount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(bookingDealModel, flags);
        dest.writeString(id);
        dest.writeString(deal_name);
        dest.writeInt(dealsLeft);
        dest.writeInt(discount_rate);
        dest.writeInt(group_discount_rate);
        dest.writeString(min_person_required);
        dest.writeString(max_person_required);
        dest.writeString(start_time);
        dest.writeString(end_time);
        dest.writeString(repeat_type);
        dest.writeString(active_deal_yn);
        dest.writeString(createdAt);
        dest.writeString(timeLeft);
        dest.writeString(getDeal);
        dest.writeString(type);
        dest.writeInt(timeGone);
        dest.writeInt(freeCouponDiscount);
        dest.writeParcelable(dealPointer, flags);
        dest.writeParcelable(imageFile, flags);
        dest.writeParcelable(branchModel, flags);
        dest.writeIntArray(indexes);
    }

    public int[] getUsedIndexes() {
        return usedIndexes;
    }

    public void setUsedIndexes(int[] usedIndexes) {
        this.usedIndexes = usedIndexes;
    }
}
