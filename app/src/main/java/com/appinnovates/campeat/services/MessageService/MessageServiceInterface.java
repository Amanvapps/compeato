package com.appinnovates.campeat.services.MessageService;

import com.appinnovates.campeat.model.MessageModel;

import java.util.List;

public interface MessageServiceInterface {
    void onMessagesSuccess(List<MessageModel> messageModelList);
    void onNoMessageAvailable(String message);
    void onMessagesFailure(String message);
    void onMessageSentSuccess();
    void onMessageSentFailure(String message);
}
