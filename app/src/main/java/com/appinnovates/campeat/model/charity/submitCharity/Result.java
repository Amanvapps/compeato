
package com.appinnovates.campeat.model.charity.submitCharity;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class Result {

    @Expose
    private String message;
    @Expose
    private Boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
