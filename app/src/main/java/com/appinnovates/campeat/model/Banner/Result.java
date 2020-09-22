
package com.appinnovates.campeat.model.Banner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Result {

    @Expose
    private Banner banner;
    @Expose
    private String createdAt;
    @SerializedName("end_date")
    private EndDate endDate;
    @SerializedName("external_link")
    private String externalLink;
    @Expose
    private String name;
    @Expose
    private String objectId;
    @SerializedName("restaurant_link")
    private RestaurantLink restaurantLink;
    @SerializedName("start_date")
    private StartDate startDate;
    @Expose
    private Boolean status;
    @Expose
    private Long type;
    @Expose
    private String updatedAt;

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public EndDate getEndDate() {
        return endDate;
    }

    public void setEndDate(EndDate endDate) {
        this.endDate = endDate;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public RestaurantLink getRestaurantLink() {
        return restaurantLink;
    }

    public void setRestaurantLink(RestaurantLink restaurantLink) {
        this.restaurantLink = restaurantLink;
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
