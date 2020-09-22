
package com.appinnovates.campeat.model.WalletModels;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class GenerateToken {

    @Expose
    private Data data;
    @Expose
    private Boolean isSuccess;
    @Expose
    private String message;
    @Expose
    private Long status;
    @Expose
    private String timestamp;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
