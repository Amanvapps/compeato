
package com.appinnovates.campeat.model.getAllDealsModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DealUrl implements Parcelable {

    @SerializedName("__type")
    private String _Type;
    @Expose
    private String name;
    @Expose
    private String url;

    public DealUrl(){}

    private DealUrl(Parcel in) {
        _Type = in.readString();
        name = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_Type);
        dest.writeString(name);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DealUrl> CREATOR = new Creator<DealUrl>() {
        @Override
        public DealUrl createFromParcel(Parcel in) {
            return new DealUrl(in);
        }

        @Override
        public DealUrl[] newArray(int size) {
            return new DealUrl[size];
        }
    };

    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}