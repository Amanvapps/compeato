package com.appinnovates.campeat.services.CancelDealService;

import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

public class CancelDealService {
    private static CancelDealService cancelDealService;
    private CancelDealInterface delegate;

    public static CancelDealService getInstance(){
        if (cancelDealService == null){
            cancelDealService = new CancelDealService();
        }
        return cancelDealService;
    }
    public void setDelegate(CancelDealInterface delegate){
        this.delegate = delegate;
    }

    public void cancelDeal(ParseObject bookingPointer){
        bookingPointer.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    delegate.onCancelDealSuccess("Cancelled successfully");
                } else {
                    delegate.onCancelDealFailure(e.getMessage()+"");
                }
            }
        });
    }
}
