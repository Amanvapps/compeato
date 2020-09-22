package com.appinnovates.campeat.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class MenuSection implements Parcelable {
    private String catName;
    private List<MenuItem> menuItem;
    private boolean expanded;

    public MenuSection() {
    }

    private MenuSection(Parcel in) {
        catName = in.readString();
        menuItem = in.createTypedArrayList(MenuItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(catName);
        dest.writeTypedList(menuItem);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MenuSection> CREATOR = new Creator<MenuSection>() {
        @Override
        public MenuSection createFromParcel(Parcel in) {
            return new MenuSection(in);
        }

        @Override
        public MenuSection[] newArray(int size) {
            return new MenuSection[size];
        }
    };

    public String getCatName() {
        return catName;
    }

    public List<MenuItem> getMenuItem() {
        return menuItem;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

}
