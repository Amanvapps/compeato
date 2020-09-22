package com.appinnovates.campeat.services.MessageService;

import com.appinnovates.campeat.model.MessageModel;
import com.appinnovates.campeat.utils.DateFormatUtil;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class MessagesParser {

    public static List<MessageModel> parseMessages(List<ParseObject> parseObjects){
        List<MessageModel> messageModelList = new ArrayList<>();
        for (ParseObject object:parseObjects) {
            messageModelList.add(new MessageModel(object.getObjectId(),object.getString("message"), DateFormatUtil.getDateInString(object.getCreatedAt()),object.getBoolean("is_from_customer"),object.getParseObject("deal_id").getObjectId()));
        }
        return messageModelList;
    }

}
