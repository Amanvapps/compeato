
package com.appinnovates.campeat.model.RedeemCoupon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class EndDate implements Parcelable {

    @SerializedName("__type")
    private String _Type;
    @Expose
    private String iso;

    protected EndDate(Parcel in) {
        _Type = in.readString();
        iso = in.readString();
    }

    public EndDate() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_Type);
        dest.writeString(iso);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EndDate> CREATOR = new Creator<EndDate>() {
        @Override
        public EndDate createFromParcel(Parcel in) {
            return new EndDate(in);
        }

        @Override
        public EndDate[] newArray(int size) {
            return new EndDate[size];
        }
    };

    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

}
