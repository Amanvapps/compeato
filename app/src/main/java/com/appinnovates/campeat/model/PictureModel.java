package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PictureModel implements Parcelable{
    private String url;
    public PictureModel(){}
    protected PictureModel(Parcel in) {
        url = in.readString();
    }

    public static final Creator<PictureModel> CREATOR = new Creator<PictureModel>() {
        @Override
        public PictureModel createFromParcel(Parcel in) {
            return new PictureModel(in);
        }

        @Override
        public PictureModel[] newArray(int size) {
            return new PictureModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }

    public String getUrl() {
        return url;
    }
}
