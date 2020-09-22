
package com.appinnovates.campeat.model.receiptModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@SuppressWarnings("unused")
public class DealBranch {

    @SerializedName("__type")
    private String _Type;
    @SerializedName("active_deal_yn")
    private String activeDealYn;
    @Expose
    private Branch branch;
    @Expose
    private String className;
    @Expose
    private String createdAt;
    @Expose
    private Deal deal;
    @Expose
    private String objectId;
    @Expose
    private String updatedAt;

    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }

    public String getActiveDealYn() {
        return activeDealYn;
    }

    public void setActiveDealYn(String activeDealYn) {
        this.activeDealYn = activeDealYn;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
