
package com.appinnovates.campeat.model.MenusModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class MenuItem {

    @SerializedName("__type")
    private String _Type;
    @Expose
    private List<Category> categories;
    @Expose
    private String className;
    @Expose
    private String createdAt;
    @SerializedName("is_available_yn")
    private String isAvailableYn;
    @SerializedName("menu_extra_details")
    private String menuExtraDetails;
    @SerializedName("menu_name")
    private String menuName;
    @Expose
    private String objectId;
    @Expose
    private Picture picture;
    @Expose
    private Long price;
    @Expose
    private String updatedAt;

    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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

    public String getIsAvailableYn() {
        return isAvailableYn;
    }

    public void setIsAvailableYn(String isAvailableYn) {
        this.isAvailableYn = isAvailableYn;
    }

    public String getMenuExtraDetails() {
        return menuExtraDetails;
    }

    public void setMenuExtraDetails(String menuExtraDetails) {
        this.menuExtraDetails = menuExtraDetails;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
