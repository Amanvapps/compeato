package com.appinnovates.campeat.model;

import java.io.Serializable;
import java.util.Date;

public class MessageModel implements Serializable {
    private String msg_id;
    private String message;
    private String createdAt;
    private boolean isFromCustomer;
    private String deal_id;

    public MessageModel(String msg_id, String message, String createdAt, boolean isFromCustomer, String deal_id) {
        this.msg_id = msg_id;
        this.message = message;
        this.createdAt = createdAt;
        this.isFromCustomer = isFromCustomer;
        this.deal_id = deal_id;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public String getMessage() {
        return message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean isFromCustomer() {
        return isFromCustomer;
    }

    public String getDeal_id() {
        return deal_id;
    }

}
