
package com.appinnovates.campeat.model.WalletModels;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class Data {

    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
