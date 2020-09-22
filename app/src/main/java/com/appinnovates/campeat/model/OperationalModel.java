package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class OperationalModel implements Parcelable{
    private List<String> SUN;
    private List<String> MON;
    private List<String> TUE;
    private List<String> WED;
    private List<String> THU;
    private List<String> FRI;
    private List<String> SAT;

    public List<String> getSUN() {
        return SUN;
    }

    public void setSUN(List<String> SUN) {
        this.SUN = SUN;
    }

    public List<String> getMON() {
        return MON;
    }

    public void setMON(List<String> MON) {
        this.MON = MON;
    }

    public List<String> getTUE() {
        return TUE;
    }

    public void setTUE(List<String> TUE) {
        this.TUE = TUE;
    }

    public List<String> getWED() {
        return WED;
    }

    public void setWED(List<String> WED) {
        this.WED = WED;
    }

    public List<String> getTHU() {
        return THU;
    }

    public void setTHU(List<String> THU) {
        this.THU = THU;
    }

    public List<String> getFRI() {
        return FRI;
    }

    public void setFRI(List<String> FRI) {
        this.FRI = FRI;
    }

    public List<String> getSAT() {
        return SAT;
    }

    public void setSAT(List<String> SAT) {
        this.SAT = SAT;
    }

    private OperationalModel(Parcel in) {
        SUN = in.createStringArrayList();
        MON = in.createStringArrayList();
        TUE = in.createStringArrayList();
        WED = in.createStringArrayList();
        THU = in.createStringArrayList();
        FRI = in.createStringArrayList();
        SAT = in.createStringArrayList();
    }

    public static final Creator<OperationalModel> CREATOR = new Creator<OperationalModel>() {
        @Override
        public OperationalModel createFromParcel(Parcel in) {
            return new OperationalModel(in);
        }

        @Override
        public OperationalModel[] newArray(int size) {
            return new OperationalModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(SUN);
        dest.writeStringList(MON);
        dest.writeStringList(TUE);
        dest.writeStringList(WED);
        dest.writeStringList(THU);
        dest.writeStringList(FRI);
        dest.writeStringList(SAT);
    }
}
