
package com.appinnovates.campeat.model.RedeemCoupon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class StartDate implements Parcelable {

    @SerializedName("__type")
    private String _Type;
    @Expose
    private String iso;

    protected StartDate(Parcel in) {
        _Type = in.readString();
        iso = in.readString();
    }

    public StartDate() {
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

    public static final Creator<StartDate> CREATOR = new Creator<StartDate>() {
        @Override
        public StartDate createFromParcel(Parcel in) {
            return new StartDate(in);
        }

        @Override
        public StartDate[] newArray(int size) {
            return new StartDate[size];
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
