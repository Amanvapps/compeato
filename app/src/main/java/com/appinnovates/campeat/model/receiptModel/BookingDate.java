
package com.appinnovates.campeat.model.receiptModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BookingDate implements Parcelable {

    @SerializedName("__type")
    private String _Type;
    @Expose
    private String iso;

    public BookingDate() {
    }

    protected BookingDate(Parcel in) {
        _Type = in.readString();
        iso = in.readString();
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

    public static final Creator<BookingDate> CREATOR = new Creator<BookingDate>() {
        @Override
        public BookingDate createFromParcel(Parcel in) {
            return new BookingDate(in);
        }

        @Override
        public BookingDate[] newArray(int size) {
            return new BookingDate[size];
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
