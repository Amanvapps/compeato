
package com.appinnovates.campeat.model.RedeemCoupon;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Menu implements Parcelable {

    @SerializedName("__type")
    private String _Type;
    @Expose
    private String className;

    protected Menu(Parcel in) {
        _Type = in.readString();
        className = in.readString();
    }

    public Menu() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_Type);
        dest.writeString(className);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
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

}
