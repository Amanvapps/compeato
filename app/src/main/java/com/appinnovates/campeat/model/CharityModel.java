package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CharityModel implements Parcelable{
    private String charity_name;
    private String objectId;

    public  CharityModel() {

    }

    private CharityModel(Parcel in) {
        charity_name = in.readString();
        objectId = in.readString();
    }

    public static final Creator<CharityModel> CREATOR = new Creator<CharityModel>() {
        @Override
        public CharityModel createFromParcel(Parcel in) {
            return new CharityModel(in);
        }

        @Override
        public CharityModel[] newArray(int size) {
            return new CharityModel[size];
        }
    };

    public String getCharityName() {
        return charity_name;
    }

    public void setCharityName(String charityName) {
        this.charity_name = charityName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(charity_name);
        dest.writeString(objectId);
    }
}
