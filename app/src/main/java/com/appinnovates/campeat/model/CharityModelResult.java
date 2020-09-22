package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CharityModelResult implements Parcelable {
    private CharityModel charity;
    private Boolean selected;

    public CharityModel getCharity() {
        return charity;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setCharity(CharityModel charity) {
        this.charity = charity;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public  CharityModelResult(){}

    private CharityModelResult(Parcel in) {
        charity = in.readParcelable(CharityModel.class.getClassLoader());
        byte tmpSelected = in.readByte();
        selected = tmpSelected == 0 ? null : tmpSelected == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(charity, flags);
        dest.writeByte((byte) (selected == null ? 0 : selected ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CharityModelResult> CREATOR = new Creator<CharityModelResult>() {
        @Override
        public CharityModelResult createFromParcel(Parcel in) {
            return new CharityModelResult(in);
        }

        @Override
        public CharityModelResult[] newArray(int size) {
            return new CharityModelResult[size];
        }
    };
}
