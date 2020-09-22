package com.appinnovates.campeat.Testing;

import android.content.Context;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.BookingDealModel;
import com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel;
import com.appinnovates.campeat.services.DealService.AllDealsServiceInterface;
import com.appinnovates.campeat.services.DealService.DealStore;
import com.appinnovates.campeat.services.IsDealBookedService.IsDealBookedService;
import com.appinnovates.campeat.services.IsDealBookedService.IsDealBookedServiceInterface;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.PermissionsUtil;
import com.appinnovates.campeat.utils.UserPreferences;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllDealsServiceTest implements IsDealBookedServiceInterface {
    private static AllDealsServiceTest allDealsService;
    private AllDealsServiceInterface delegate;
    private Context context = null;
    private IsDealBookedService isDealBookedService;
    private List<BranchDealModel> branchDealModelList = new ArrayList<>();
    private DealStore dealStore;
    private int count = 0;
    private boolean isDiscounted;

    public static AllDealsServiceTest getInstance() {
        if (allDealsService == null) {
            allDealsService = new AllDealsServiceTest();
        }
        return allDealsService;
    }

    public void setDelegateAndContext(AllDealsServiceInterface delegate, Context context) {
        this.delegate = delegate;
        this.context = context;
    }

    public void getAllDeals(boolean isAscending, String price, boolean isDiscounted, String discountType
            , int distance, boolean isPopular, String dealType, ArrayList<String> menuTypeId, String screenType
            , ArrayList<String> restaurantId, String isActive) {
        dealStore = new DealStore();
        count = 0;
        isDealBookedService = new IsDealBookedService();
        isDealBookedService.setDelegateAndContext(this, context);
        String prices = "0";
        String type = "0";
        String filter = "0";
        if (isAscending) {
            prices = price;
        }
        if (isDiscounted) {
            type = discountType.toLowerCase();
        }
        if (isPopular) {
            if (dealType.equalsIgnoreCase(Constant.POPULAR)) {
                filter = "popular";
            } else {
                filter = "new";
            }
        }
        String lat = String.valueOf(UserPreferences.getInstance().getFloat(Constant.LATITUDE));
        String longu = String.valueOf(UserPreferences.getInstance().getFloat(Constant.LONGITUDE));
        final Map<String, Object> map = new HashMap<>();



        map.put("filter", filter);
        map.put("ftype", menuTypeId);
        map.put("price", prices);
        map.put("restaurant_id", restaurantId);
        map.put("type", type);
        map.put("radius", String.valueOf(distance));
        map.put("map", screenType);
        map.put("lat", lat);
        map.put("long", longu);
        map.put("status", isActive);
        if (!PermissionsUtil.isNetworkAvailable(context)) {
            delegate.onNoInternetAvailable();
            return;
        }
        ParseCloud.callFunctionInBackground("getFilterredDeal", map, (FunctionCallback<ArrayList<ParseObject>>) (objects, e) -> {
            if (e == null) {
                if (objects != null && objects.size() > 0) {
                    fetchFilterDeals(objects);
                } else {
                    delegate.onNoDealsAvailable(context.getResources().getString(R.string.no_deals));
                }

            } else {
                delegate.onAllDealsFailure(e.getMessage());
            }

        });
    }

    private void fetchFilterDeals(List<ParseObject> objects) {
        branchDealModelList.clear();
        if (objects != null && objects.size() > 0) {
            for (ParseObject parseObject : objects) {
               // isDealBookedService.isDealBooked(parseObject, objects.indexOf(parseObject));
                branchDealModelList.add(dealStore.saveBranchDeal(parseObject, context));
            }
        }
    }

    @Override
    public void onIsDealBooked(BookingDealModel booking, int index,int DealIndex) {
        setDealList(booking, context.getResources().getString(R.string.cancel_deal), index);
    }

    @Override
    public void onIsDealNotBooked(int index, int branchDealIndex) {
        setDealList(null, context.getResources().getString(R.string.get_deal), index);
    }

    @Override
    public void onIsDealBookedFailure(String message) {
        count += 1;
    }

    private void setDealList(BookingDealModel bookingDealModel, String getDeal, int index) {
        count += 1;
/*        branchDealModelList.get(index).getDealModel().setBookingDealModel(bookingDealModel);
        branchDealModelList.get(index).getDealModel().setGetDeal(getDealModel);*/

        if (count == branchDealModelList.size()) {
            delegate.onAllDealsSuccess(branchDealModelList, isDiscounted);
        }
    }


    public void getSingleDeal(String id) {
        dealStore = new DealStore();
        count = 0;
        isDealBookedService = new IsDealBookedService();
        isDealBookedService.setDelegateAndContext(this, context);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("DealBranchRelations");
        query.include("branch.restaurant_id");
        query.include("deal");
        query.include("branch");
        query.whereEqualTo("objectId", id);
        if (!PermissionsUtil.isNetworkAvailable(context)) {
            delegate.onNoInternetAvailable();
            return;
        }
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects != null && objects.size() > 0) {
                        fetchFilterDeals(objects);
                    } else {
                        delegate.onNoDealsAvailable(context.getResources().getString(R.string.no_active_deals));
                    }

                } else {
                    delegate.onAllDealsFailure(e.getMessage());
                }
            }
        });
    }

}
