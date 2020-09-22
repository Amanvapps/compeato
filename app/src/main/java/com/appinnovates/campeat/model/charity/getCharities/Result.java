
package com.appinnovates.campeat.model.charity.getCharities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Result implements Parcelable {

    @Expose
    private String address;
    @SerializedName("charity_earning")
    private Long charityEarning;
    @SerializedName("charity_name")
    private String charityName;
    @Expose
    private String city;
    @Expose
    private String createdAt;
    @Expose
    private String description;
    @Expose
    private List<String> donators;
    @Expose
    private String link;
    @Expose
    private Logo logo;
    @Expose
    private String objectId;
    @SerializedName("total_no_donations")
    private Long totalNoDonations;
    @SerializedName("total_no_of_donators")
    private Long totalNoOfDonators;
    @Expose
    private String updatedAt;

    public Result(){}

    protected Result(Parcel in) {
        address = in.readString();
        if (in.readByte() == 0) {
            charityEarning = null;
        } else {
            charityEarning = in.readLong();
        }
        charityName = in.readString();
        city = in.readString();
        createdAt = in.readString();
        description = in.readString();
        donators = in.createStringArrayList();
        link = in.readString();
        objectId = in.readString();
        if (in.readByte() == 0) {
            totalNoDonations = null;
        } else {
            totalNoDonations = in.readLong();
        }
        if (in.readByte() == 0) {
            totalNoOfDonators = null;
        } else {
            totalNoOfDonators = in.readLong();
        }
        updatedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        if (charityEarning == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(charityEarning);
        }
        dest.writeString(charityName);
        dest.writeString(city);
        dest.writeString(createdAt);
        dest.writeString(description);
        dest.writeStringList(donators);
        dest.writeString(link);
        dest.writeString(objectId);
        if (totalNoDonations == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(totalNoDonations);
        }
        if (totalNoOfDonators == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(totalNoOfDonators);
        }
        dest.writeString(updatedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCharityEarning() {
        return charityEarning;
    }

    public void setCharityEarning(Long charityEarning) {
        this.charityEarning = charityEarning;
    }

    public String getCharityName() {
        return charityName;
    }

    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDonators() {
        return donators;
    }

    public void setDonators(List<String> donators) {
        this.donators = donators;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Long getTotalNoDonations() {
        return totalNoDonations;
    }

    public void setTotalNoDonations(Long totalNoDonations) {
        this.totalNoDonations = totalNoDonations;
    }

    public Long getTotalNoOfDonators() {
        return totalNoOfDonators;
    }

    public void setTotalNoOfDonators(Long totalNoOfDonators) {
        this.totalNoOfDonators = totalNoOfDonators;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
