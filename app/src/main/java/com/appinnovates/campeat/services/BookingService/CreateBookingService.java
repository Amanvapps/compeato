package com.appinnovates.campeat.services.BookingService;

import android.content.Context;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.BookingDealModel;
import com.appinnovates.campeat.services.IsDealBookedService.IsDealBookedService;
import com.appinnovates.campeat.services.IsDealBookedService.IsDealBookedServiceInterface;
import com.appinnovates.campeat.utils.DateFormatUtil;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class CreateBookingService implements IsDealBookedServiceInterface {
    private static CreateBookingService createBookingService = null;
    private CreateBookingViewInterface delegate = null;
    private Context context = null;
    private IsDealBookedService isDealBookedService = new IsDealBookedService();

    public static CreateBookingService getInsance(){
        if (createBookingService == null){
            createBookingService = new CreateBookingService();
        }
        return createBookingService;
    }

    public void setDelegateAndContext(CreateBookingViewInterface delegate, Context context){
        this.delegate = delegate;
        this.context = context;
    }

    public void requestCreateBooking(final ParseObject dealBranchPointer, String hasFullfilledYN, int noOfPeople){
        isDealBookedService.setDelegateAndContext(this,context);
        ParseObject booking = new ParseObject("BranchBooking");
        booking.put("customer_id", ParseUser.getCurrentUser());
//        ParseObject pointer = new ParseObject("DealBranchRelations");
//        pointer.put("__type", "Pointer");
////        pointer.put("className", "DealBranchRelations");
//        pointer.put("objectId", "gwEaoEPGxG");
        booking.put("deal_branch", dealBranchPointer);
        booking.put("booking_date", DateFormatUtil.getCurrentDate());
        booking.put("has_fulfilled_yn",hasFullfilledYN);
        booking.put("no_of_people",noOfPeople);
        booking.saveInBackground(e -> {
            if (e == null){
                isDealBookedService.isDealBooked(dealBranchPointer,0, 0);
            }else{
                delegate.onCreateBookingFailure(e.getMessage());
            }
        });
    }


    public void bookings(ParseObject dealBranchPointer, final BookingNumberDelegate delegate){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("BranchBooking");
        query.whereEqualTo("deal_branch",dealBranchPointer);
        query.whereEqualTo("has_fulfilled_yn","Y");
        query.findInBackground((objects, e) -> {
            if (e == null && objects != null ){
                delegate.onSuccess(objects.size());
            }else{
                delegate.message(e.getMessage());
            }
        });
    }



    @Override
    public void onIsDealBooked(BookingDealModel booking, int index,int deal) {
        delegate.onCreateBookingSuccess(booking, context.getResources().getString(R.string.cancel_deal));
    }

    @Override
    public void onIsDealNotBooked(int index, int branchDealIndex) {
        delegate.onCreateBookingFailure(context.getResources().getString(R.string.error_message));
    }

    @Override
    public void onIsDealBookedFailure(String message) {
        delegate.onCreateBookingFailure(message);
    }
}
