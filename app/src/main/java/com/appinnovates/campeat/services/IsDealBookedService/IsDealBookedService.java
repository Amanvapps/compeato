package com.appinnovates.campeat.services.IsDealBookedService;

import android.content.Context;
import android.util.Log;

import com.appinnovates.campeat.services.BookingService.BookingDealStore;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class IsDealBookedService {
    private IsDealBookedServiceInterface delegate;
    private Context context;
    private long branchDealsCounter;
    private long dealsCounter;

    public void setDelegateAndContext(IsDealBookedServiceInterface delegate, Context context){
        this.delegate = delegate;
        this.context = context;
    }

    public void isDealBooked(ParseObject dealBranchParseObject, final int branchIndex, int dealBranchIndex){
        ParseQuery<ParseObject> parseQuery = new ParseQuery<>("BranchBooking");
        parseQuery.whereEqualTo("deal_branch", dealBranchParseObject);
        parseQuery.whereEqualTo("customer_id", ParseUser.getCurrentUser());
        parseQuery.include("deal_branch.branch.restaurant_id");
        parseQuery.include("deal_branch.deal");
        parseQuery.findInBackground((objects, e) -> {
            if (e == null) {
                if (objects != null && objects.size() > 0) {
                    delegate.onIsDealBooked(BookingDealStore.fetchBooking(objects.get(0), context), branchIndex, dealBranchIndex);
                } else {
                    delegate.onIsDealNotBooked(branchIndex, dealBranchIndex);
                }
            } else {
                Log.i("Failure", e.getMessage());
                delegate.onIsDealBookedFailure(e.getMessage());
            }
        });
    }

    public void setBranchDealsCount(long branchDealsCounter) {
        this.branchDealsCounter = branchDealsCounter;
    }

    public long getBranchDealsCount() {
        return branchDealsCounter;
    }

    public void setDealsCount(long dealsCounter) {
        this.dealsCounter = dealsCounter;
    }

    public long getDealsCount() {
        return dealsCounter;
    }
}
