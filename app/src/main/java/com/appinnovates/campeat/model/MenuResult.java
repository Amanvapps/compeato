package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class MenuResult implements Parcelable{
    private List<MenuSection> result;


    protected MenuResult() {
    }

    private MenuResult(Parcel in) {
        result = in.createTypedArrayList(MenuSection.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(result);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MenuResult> CREATOR = new Creator<MenuResult>() {
        @Override
        public MenuResult createFromParcel(Parcel in) {
            return new MenuResult(in);
        }

        @Override
        public MenuResult[] newArray(int size) {
            return new MenuResult[size];
        }
    };

    public List<MenuSection> getResult() {
        return result;
    }

}
