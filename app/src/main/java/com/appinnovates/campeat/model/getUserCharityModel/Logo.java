
package com.appinnovates.campeat.model.getUserCharityModel;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Logo {

    @SerializedName("name")
    private String mName;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("__type")
    private String m_Type;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String get_Type() {
        return m_Type;
    }

    public void set_Type(String _Type) {
        m_Type = _Type;
    }

}
