package com.appinnovates.campeat.services.IsDealBookedService;

import com.appinnovates.campeat.model.BookingDealModel;

public interface IsDealBookedServiceInterface {
    void onIsDealBooked(BookingDealModel booking, int index, int branchDealIndex);
    void onIsDealNotBooked(int index, int branchDealIndex);
    void onIsDealBookedFailure(String message);
}
