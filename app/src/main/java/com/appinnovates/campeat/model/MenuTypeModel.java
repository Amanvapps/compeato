package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuTypeModel implements Parcelable {

    private String id;
    private String menu_type_name;
    private boolean isSelected;

    public MenuTypeModel() {

    }

    private MenuTypeModel(Parcel in) {
        id = in.readString();
        menu_type_name = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<MenuTypeModel> CREATOR = new Creator<MenuTypeModel>() {
        @Override
        public MenuTypeModel createFromParcel(Parcel in) {
            return new MenuTypeModel(in);
        }

        @Override
        public MenuTypeModel[] newArray(int size) {
            return new MenuTypeModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenu_type_name() {
        return menu_type_name;
    }

    public void setMenu_type_name(String menu_type_name) {
        this.menu_type_name = menu_type_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(menu_type_name);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }
}
