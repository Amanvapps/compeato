
package com.appinnovates.campeat.model.receiptModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Deal {

    @SerializedName("__type")
    private String _Type;
    @Expose
    private String className;
    @Expose
    private String createdAt;
    @SerializedName("deal_name")
    private String dealName;
    @SerializedName("deals_left")
    private Long dealsLeft;
    @SerializedName("discount_rate")
    private Long discountRate;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("group_discount_rate")
    private Long groupDiscountRate;
    @SerializedName("max_index")
    private Long maxIndex;
    @SerializedName("min_person_required")
    private Long minPersonRequired;
    @Expose
    private String objectId;
    @SerializedName("repeat_type")
    private String repeatType;
    @SerializedName("start_time")
    private String startTime;
    @Expose
    private String type;
    @Expose
    private String updatedAt;

    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
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

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public Long getDealsLeft() {
        return dealsLeft;
    }

    public void setDealsLeft(Long dealsLeft) {
        this.dealsLeft = dealsLeft;
    }

    public Long getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Long discountRate) {
        this.discountRate = discountRate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getGroupDiscountRate() {
        return groupDiscountRate;
    }

    public void setGroupDiscountRate(Long groupDiscountRate) {
        this.groupDiscountRate = groupDiscountRate;
    }

    public Long getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(Long maxIndex) {
        this.maxIndex = maxIndex;
    }

    public Long getMinPersonRequired() {
        return minPersonRequired;
    }

    public void setMinPersonRequired(Long minPersonRequired) {
        this.minPersonRequired = minPersonRequired;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
