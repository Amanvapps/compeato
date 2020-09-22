package com.appinnovates.campeat.model.getAllDealsModel;

import android.os.Parcel;
import android.os.Parcelable;
import com.parse.ParseObject;

public class BranchDealModel implements Parcelable{
    private BranchModel branch;
    private DealModel deal;
    private String objectId;
    private ParseObject branchDealPointer;
    public boolean isExpanded = false;

    public BranchDealModel() {
    }


    protected BranchDealModel(Parcel in) {
        branch = in.readParcelable(BranchModel.class.getClassLoader());
        deal = in.readParcelable(DealModel.class.getClassLoader());
        objectId = in.readString();
        branchDealPointer = in.readParcelable(ParseObject.class.getClassLoader());
        isExpanded = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(branch, flags);
        dest.writeParcelable(deal, flags);
        dest.writeString(objectId);
        dest.writeParcelable(branchDealPointer, flags);
        dest.writeByte((byte) (isExpanded ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BranchDealModel> CREATOR = new Creator<BranchDealModel>() {
        @Override
        public BranchDealModel createFromParcel(Parcel in) {
            return new BranchDealModel(in);
        }

        @Override
        public BranchDealModel[] newArray(int size) {
            return new BranchDealModel[size];
        }
    };

    public void setBranch(BranchModel branch) {
        this.branch = branch;
    }

    public void setDeal(DealModel deal) {
        this.deal = deal;
    }

    public BranchModel getBranch() {
        return branch;
    }

    public DealModel getDeal() {
        return deal;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public ParseObject getBranchDealPointer() {
        return branchDealPointer;
    }

    public void setBranchDealPointer(ParseObject branchDealPointer) {
        this.branchDealPointer = branchDealPointer;
    }


}