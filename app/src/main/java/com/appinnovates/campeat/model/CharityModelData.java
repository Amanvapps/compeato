package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CharityModelData  implements Parcelable {
    private List<CharityModelResult> result;

    public CharityModelData(){}
    private CharityModelData(Parcel in) {
        result = in.createTypedArrayList(CharityModelResult.CREATOR);
    }

    public static final Creator<CharityModelData> CREATOR = new Creator<CharityModelData>() {
        @Override
        public CharityModelData createFromParcel(Parcel in) {
            return new CharityModelData(in);
        }

        @Override
        public CharityModelData[] newArray(int size) {
            return new CharityModelData[size];
        }
    };

    public List<CharityModelResult> getResult() {
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(result);
    }
}