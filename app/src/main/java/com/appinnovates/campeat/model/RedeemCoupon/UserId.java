
package com.appinnovates.campeat.model.RedeemCoupon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UserId implements Parcelable {

    @SerializedName("__type")
    private String _Type;
    @Expose
    private String className;
    @Expose
    private String objectId;

    protected UserId(Parcel in) {
        _Type = in.readString();
        className = in.readString();
        objectId = in.readString();
    }

    public UserId() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_Type);
        dest.writeString(className);
        dest.writeString(objectId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserId> CREATOR = new Creator<UserId>() {
        @Override
        public UserId createFromParcel(Parcel in) {
            return new UserId(in);
        }

        @Override
        public UserId[] newArray(int size) {
            return new UserId[size];
        }
    };

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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

}
