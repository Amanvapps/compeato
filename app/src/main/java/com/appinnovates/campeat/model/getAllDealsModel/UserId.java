
package com.appinnovates.campeat.model.getAllDealsModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UserId implements Parcelable {

    @SerializedName("className")
    private String mClassName;
    @SerializedName("objectId")
    private String mObjectId;
    @SerializedName("__type")
    private String m_Type;

    protected UserId(Parcel in) {
        mClassName = in.readString();
        mObjectId = in.readString();
        m_Type = in.readString();
    }

    public static final Creator<UserId> CREATOR = new Creator<UserId>() {
        @Override
        public UserId createFromParcel(Parcel in) {
            return new UserId(in);
        }

        @Override
        public UserId[] newArray(int size) {
            return new UserId[size];
        }
    };

    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        mClassName = className;
    }

    public String getObjectId() {
        return mObjectId;
    }

    public void setObjectId(String objectId) {
        mObjectId = objectId;
    }

    public String get_Type() {
        return m_Type;
    }

    public void set_Type(String _Type) {
        m_Type = _Type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mClassName);
        parcel.writeString(mObjectId);
        parcel.writeString(m_Type);
    }
}
