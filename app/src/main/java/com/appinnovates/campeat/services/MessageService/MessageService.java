package com.appinnovates.campeat.services.MessageService;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class MessageService {
    private static MessageService instance;
    private MessageServiceInterface delegate;

    public static MessageService getInstance() {
        if (instance == null){
            instance = new MessageService();
        }
        return instance;
    }
    public void setDelegate(MessageServiceInterface delegate){
        this.delegate = delegate;
    }
    public void getMessages(final ParseObject dealPointer, ParseObject restaurantPointer){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Message");
        query.whereEqualTo("customer_id", ParseUser.getCurrentUser());
        query.whereEqualTo("deal_id",dealPointer);
        query.addAscendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    if (objects.size()>0){
                        delegate.onMessagesSuccess(MessagesParser.parseMessages(objects));
                    }else {
                        delegate.onNoMessageAvailable("No messages available");
                    }
                }else{
                    delegate.onMessagesFailure(e.getMessage());
                }
            }
        });
    }
    public void sendMessage(String message,ParseObject dealPointer,ParseObject restaurantPointer){
        ParseObject parseObject = new ParseObject("Message");
        parseObject.put("message",message);
        parseObject.put("deal_id",dealPointer);
        parseObject.put("customer_id",ParseUser.getCurrentUser());
        parseObject.put("is_from_customer",true);
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    delegate.onMessageSentSuccess();
                }else{
                    delegate.onMessageSentFailure(e.getMessage());
                }
            }
        });
    }
}
