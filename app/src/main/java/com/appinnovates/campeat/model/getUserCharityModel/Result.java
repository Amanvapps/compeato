
package com.appinnovates.campeat.model.getUserCharityModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Result {

    @SerializedName("charity_name")
    private String mCharityName;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("link")
    private String mLink;
    @SerializedName("logo")
    private Logo mLogo;
    @SerializedName("objectId")
    private String mObjectId;
    @SerializedName("subscribed")
    private Boolean mSubscribed;
    @SerializedName("subscribers")
    private Subscribers mSubscribers;
    @SerializedName("updatedAt")
    private String mUpdatedAt;

    public String getCharityName() {
        return mCharityName;
    }

    public void setCharityName(String charityName) {
        mCharityName = charityName;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public Logo getLogo() {
        return mLogo;
    }

    public void setLogo(Logo logo) {
        mLogo = logo;
    }

    public String getObjectId() {
        return mObjectId;
    }

    public void setObjectId(String objectId) {
        mObjectId = objectId;
    }

    public Boolean getSubscribed() {
        return mSubscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        mSubscribed = subscribed;
    }

    public Subscribers getSubscribers() {
        return mSubscribers;
    }

    public void setSubscribers(Subscribers subscribers) {
        mSubscribers = subscribers;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
