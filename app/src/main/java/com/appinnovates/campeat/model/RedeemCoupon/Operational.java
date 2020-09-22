
package com.appinnovates.campeat.model.RedeemCoupon;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Operational implements Parcelable {

    @SerializedName("FRI")
    private List<String> fRI;
    @SerializedName("MON")
    private List<String> mON;
    @SerializedName("SAT")
    private List<String> sAT;
    @SerializedName("SUN")
    private List<String> sUN;
    @SerializedName("THU")
    private List<String> tHU;
    @SerializedName("TUE")
    private List<String> tUE;
    @SerializedName("WED")
    private List<String> wED;

    protected Operational(Parcel in) {
        fRI = in.createStringArrayList();
        mON = in.createStringArrayList();
        sAT = in.createStringArrayList();
        sUN = in.createStringArrayList();
        tHU = in.createStringArrayList();
        tUE = in.createStringArrayList();
        wED = in.createStringArrayList();
    }

    public Operational() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(fRI);
        dest.writeStringList(mON);
        dest.writeStringList(sAT);
        dest.writeStringList(sUN);
        dest.writeStringList(tHU);
        dest.writeStringList(tUE);
        dest.writeStringList(wED);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Operational> CREATOR = new Creator<Operational>() {
        @Override
        public Operational createFromParcel(Parcel in) {
            return new Operational(in);
        }

        @Override
        public Operational[] newArray(int size) {
            return new Operational[size];
        }
    };

    public List<String> getFRI() {
        return fRI;
    }

    public void setFRI(List<String> fRI) {
        this.fRI = fRI;
    }

    public List<String> getMON() {
        return mON;
    }

    public void setMON(List<String> mON) {
        this.mON = mON;
    }

    public List<String> getSAT() {
        return sAT;
    }

    public void setSAT(List<String> sAT) {
        this.sAT = sAT;
    }

    public List<String> getSUN() {
        return sUN;
    }

    public void setSUN(List<String> sUN) {
        this.sUN = sUN;
    }

    public List<String> getTHU() {
        return tHU;
    }

    public void setTHU(List<String> tHU) {
        this.tHU = tHU;
    }

    public List<String> getTUE() {
        return tUE;
    }

    public void setTUE(List<String> tUE) {
        this.tUE = tUE;
    }

    public List<String> getWED() {
        return wED;
    }

    public void setWED(List<String> wED) {
        this.wED = wED;
    }

}
