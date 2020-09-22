
package com.appinnovates.campeat.model.getUserCharityModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Subscribers {

    @SerializedName("className")
    private String mClassName;
    @SerializedName("__type")
    private String m_Type;

    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        mClassName = className;
    }

    public String get_Type() {
        return m_Type;
    }

    public void set_Type(String _Type) {
        m_Type = _Type;
    }

}
