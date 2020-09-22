
package com.appinnovates.campeat.model.RedeemCoupon;
import android.os.Parcel;
import android.os.Parcelable;

import com.appinnovates.campeat.model.getAllDealsModel.BranchModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.parse.ParseObject;

@SuppressWarnings("unused")
public class Result implements Parcelable {

    @SerializedName("__type")
    private String _Type;
    @Expose
    private BranchModel branch;
    @Expose
    private String className;
    @Expose
    private Codes codes;
    @Expose
    private String createdAt;

    protected Result(Parcel in) {
        _Type = in.readString();
        branch = in.readParcelable(BranchModel.class.getClassLoader());
        className = in.readString();
        codes = in.readParcelable(Codes.class.getClassLoader());
        createdAt = in.readString();
        couponPointer = in.readParcelable(ParseObject.class.getClassLoader());
        description = in.readString();
        endDate = in.readParcelable(EndDate.class.getClassLoader());
        couponName = in.readString();
        menu = in.readParcelable(Menu.class.getClassLoader());
        if (in.readByte() == 0) {
            noOfCoupon = null;
        } else {
            noOfCoupon = in.readLong();
        }
        objectId = in.readString();
        picture = in.readParcelable(Picture.class.getClassLoader());
        startDate = in.readParcelable(StartDate.class.getClassLoader());
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
        if (in.readByte() == 0) {
            type = null;
        } else {
            type = in.readLong();
        }
        updatedAt = in.readString();
        users = in.readParcelable(Users.class.getClassLoader());
        if (in.readByte() == 0) {
            value = null;
        } else {
            value = in.readLong();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_Type);
        dest.writeParcelable(branch, flags);
        dest.writeString(className);
        dest.writeParcelable(codes, flags);
        dest.writeString(createdAt);
        dest.writeParcelable(couponPointer, flags);
        dest.writeString(description);
        dest.writeParcelable(endDate, flags);
        dest.writeString(couponName);
        dest.writeParcelable(menu, flags);
        if (noOfCoupon == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(noOfCoupon);
        }
        dest.writeString(objectId);
        dest.writeParcelable(picture, flags);
        dest.writeParcelable(startDate, flags);
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
        if (type == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(type);
        }
        dest.writeString(updatedAt);
        dest.writeParcelable(users, flags);
        if (value == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(value);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public ParseObject getCouponPointer() {
        return couponPointer;
    }

    public void setCouponPointer(ParseObject couponPointer) {
        this.couponPointer = couponPointer;
    }

    @Expose
    private ParseObject couponPointer;
    @Expose
    private String description;
    @SerializedName("end_date")
    private EndDate endDate;

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }


    @SerializedName("coupon_name")
    private String couponName;
    @Expose
    private Menu menu;
    @SerializedName("no_of_coupon")
    private Long noOfCoupon;
    @Expose
    private String objectId;
    @Expose
    private Picture picture;
    @SerializedName("start_date")
    private StartDate startDate;
    @Expose
    private Boolean status;
    @Expose
    private Long type;
    @Expose
    private String updatedAt;
    @Expose
    private Users users;
    @Expose
    private Long value;

    public Result() {
    }



    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }

    public BranchModel getBranch() {
        return branch;
    }

    public void setBranch(BranchModel branch) {
        this.branch = branch;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Codes getCodes() {
        return codes;
    }

    public void setCodes(Codes codes) {
        this.codes = codes;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EndDate getEndDate() {
        return endDate;
    }

    public void setEndDate(EndDate endDate) {
        this.endDate = endDate;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Long getNoOfCoupon() {
        return noOfCoupon;
    }

    public void setNoOfCoupon(Long noOfCoupon) {
        this.noOfCoupon = noOfCoupon;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

}
