package com.appinnovates.campeat.services.BookingService;

import com.appinnovates.campeat.model.BookingDealModel;

public interface CreateBookingViewInterface {
    void onCreateBookingSuccess(BookingDealModel bookingDealModel,String getDeal);
    void onCreateBookingFailure(String message);
}
