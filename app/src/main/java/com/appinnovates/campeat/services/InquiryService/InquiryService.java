package com.appinnovates.campeat.services.InquiryService;

import android.content.Context;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.utils.Constant;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class InquiryService {
    private static InquiryService inquiryService;
    private InquiryServiceInterface delegate;
    private Context context;

    public static InquiryService getInstance(){
        if (inquiryService == null){
            inquiryService = new InquiryService();
        }
        return inquiryService;
    }

    public void setDelegateAndContext(InquiryServiceInterface delegate,Context context){
        this.delegate = delegate;
        this.context = context;
    }

    public void submitInquiry(String fullName, String email, final String contact, String message){
        ParseObject inquiry = new ParseObject("CustomerInquiry");
//        inquiry.put("full_name",fullName);
        inquiry.put("email",email);
        inquiry.put("contact",contact);
        inquiry.put("message",message);
        inquiry.put("customer_id", ParseUser.getCurrentUser());
        inquiry.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    delegate.onInquirySuccess(context.getResources().getString(R.string.inquiry_message));
                }else {
                    delegate.onInquiryFailure(e.getMessage());
                }
            }
        });
    }

}
