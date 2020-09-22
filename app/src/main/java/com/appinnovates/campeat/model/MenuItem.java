package com.appinnovates.campeat.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuItem implements Parcelable{
    private int price;
    private String menu_name;
    private String menu_extra_details;
    private String is_available_yn;
    private PictureModel picture;

    private boolean isExpanded;
    public MenuItem() {

    }

    protected MenuItem(Parcel in) {
        price = in.readInt();
        menu_name = in.readString();
        menu_extra_details = in.readString();
        is_available_yn = in.readString();
        picture = in.readParcelable(PictureModel.class.getClassLoader());
        isExpanded = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(price);
        dest.writeString(menu_name);
        dest.writeString(menu_extra_details);
        dest.writeString(is_available_yn);
        dest.writeParcelable(picture, flags);
        dest.writeByte((byte) (isExpanded ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

    public int getPrice() {
        return price;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public String getMenu_extra_details() {
        return menu_extra_details;
    }

    public String getIs_available_yn() {
        return is_available_yn;
    }

    public PictureModel getPicture() {
        return picture;
    }
}
