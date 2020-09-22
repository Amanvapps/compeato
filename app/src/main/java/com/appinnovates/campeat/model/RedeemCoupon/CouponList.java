
package com.appinnovates.campeat.model.RedeemCoupon;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class CouponList implements Parcelable {

    @Expose
    private List<Result> result;

    protected CouponList(Parcel in) {
    }

    public CouponList() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CouponList> CREATOR = new Creator<CouponList>() {
        @Override
        public CouponList createFromParcel(Parcel in) {
            return new CouponList(in);
        }

        @Override
        public CouponList[] newArray(int size) {
            return new CouponList[size];
        }
    };

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}
