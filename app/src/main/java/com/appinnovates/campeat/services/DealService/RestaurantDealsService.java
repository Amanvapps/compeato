package com.appinnovates.campeat.services.DealService;

import android.content.Context;

import com.appinnovates.campeat.model.BookingDealModel;
import com.appinnovates.campeat.model.getAllDealsModel.DealModel;
import com.appinnovates.campeat.services.IsDealBookedService.IsDealBookedService;
import com.appinnovates.campeat.services.IsDealBookedService.IsDealBookedServiceInterface;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by reetu on 26/6/18.
 */

public class RestaurantDealsService implements IsDealBookedServiceInterface {
    private static RestaurantDealsService restaurantDealsService = null;
    private IsDealBookedService isDealBookedService;
    private RestaurantDealServiceInterface delegate = null;
    private DealStore dealStore = new DealStore();
    private Context context;
    private List<DealModel> dealsModelList = new ArrayList<>();
    private int count = 0;

    public static RestaurantDealsService getInstance(){
        if (restaurantDealsService == null){
            restaurantDealsService = new RestaurantDealsService();
        }
        return restaurantDealsService;
    }

    public void setDelegateAndContext(RestaurantDealServiceInterface delegate,Context context){
        this.delegate = delegate;
        this.context = context;
    }

    public void getRestaurantDeals(List<ParseObject> branchPointers, ParseGeoPoint currentGeoPoint
            , int distance, boolean isAscending, boolean isDiscounted, String discountType){
        count = 0;
        isDealBookedService = new IsDealBookedService();
        isDealBookedService.setDelegateAndContext(this, context);

        ParseQuery<ParseObject> subQuery =new ParseQuery<ParseObject>("BranchDealsModel");
        subQuery.whereWithinKilometers("geo_point", currentGeoPoint, distance);


        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("DealModel");
        query.include("branch_id.restaurant_id");
        if (distance < 500) {
            query.whereMatchesQuery("branch_id", subQuery);
        }
        if (isDiscounted) {
            query.whereEqualTo("type", discountType.toLowerCase());
        }

        if (isAscending){
            query.addAscendingOrder("discount_rate");
        }else{
            query.addDescendingOrder("discount_rate");
        }

        query.whereContainedIn("branch_id",branchPointers);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    if (objects.size() >0){
                        fetchRestaurantDeals(objects);
                    }else {
                        delegate.onNoRestaurantDeals();
                    }
                }else{
                    delegate.onRestaurantDealsFailure(e.getMessage());

                }
            }
        });
    }

    private void fetchRestaurantDeals(List<ParseObject> parseObjects){
        dealsModelList.clear();
        if (parseObjects != null && parseObjects.size() > 0) {
            for (ParseObject object : parseObjects) {
                //isDealBookedService.isDealBooked(object,parseObjects.indexOf(object),int dealIndex);
                dealsModelList.add(dealStore.saveDeal(object, context));
            }
        }
    }

    @Override
    public void onIsDealBooked(BookingDealModel booking, int index,int dealindex) {
        setDealList();

    }

    @Override
    public void onIsDealNotBooked(int index, int branchDealIndex) {
        setDealList();

    }

    @Override
    public void onIsDealBookedFailure(String message) {
        count +=1;
    }

    private void setDealList(){
        count +=1;
        /*dealsModelList.get(index).setBookingDealModel(bookingDealModel);
        dealsModelList.get(index).setGetDeal(getDealModel);*/

        if (count == dealsModelList.size()){
            delegate.onRestaurantDealsSuccess(dealsModelList);
        }
    }
}
