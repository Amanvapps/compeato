package com.appinnovates.campeat.services.DealService;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.Banner.BannerData;
import com.appinnovates.campeat.model.Banner.Result;
import com.appinnovates.campeat.model.BookingDealModel;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.services.BannerService.BannerInterface;
import com.appinnovates.campeat.services.CloudNetwork.ApiClient;
import com.appinnovates.campeat.services.CloudNetwork.ApiInterface;
import com.appinnovates.campeat.services.IsDealBookedService.IsDealBookedService;
import com.appinnovates.campeat.services.IsDealBookedService.IsDealBookedServiceInterface;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.PermissionsUtil;
import com.appinnovates.campeat.utils.PrefManager;
import com.appinnovates.campeat.utils.UserPreferences;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllDealsService implements IsDealBookedServiceInterface {
    private static AllDealsService allDealsService;
    private RestaurantDealsInterface delegate;
    private BannerInterface bannerDelegate;

    private Context context = null;
    private IsDealBookedService isDealBookedService;
    private List<HomeBranchDealsModel> restaurantDeals = new ArrayList<>();
    private DealStore dealStore;
    private int count = 0;

    private String lat = String.valueOf(UserPreferences.getInstance().getFloat(Constant.LATITUDE));
    private String longu = String.valueOf(UserPreferences.getInstance().getFloat(Constant.LONGITUDE));

    private AllDealsService() {
    }

    public static AllDealsService getInstance() {
        if (allDealsService == null) {
            allDealsService = new AllDealsService();
        }
        return allDealsService;
    }

    public void setDelegateAndContext(RestaurantDealsInterface delegate, Context context) {
        this.delegate = delegate;
        this.context = context;
    }

    public void setBannerDelegateAndContext(BannerInterface bannerDelegate, Context context) {
        this.bannerDelegate = bannerDelegate;
        this.context = context;
    }


    public void getAllDeals(boolean isAscending, String price, boolean isDiscounted, String discountType
            , int distance, boolean isPopular, String dealType, ArrayList<String> menuTypeId, String screenType
            , ArrayList<String> restaurantId, String isActive) {
        dealStore = new DealStore();
        count = 0;
        isDealBookedService = new IsDealBookedService();
        isDealBookedService.setDelegateAndContext(this, context);
        final Map<String, Object> map = new HashMap<>();
        map.put("restaurant_id", restaurantId);
        map.put("ftype", menuTypeId);
        map.put("radius", "5000");
        map.put("map", screenType);
        map.put("lat", lat);
        map.put("long", longu);
        map.put("status", isActive);
        if (!PermissionsUtil.isNetworkAvailable(context)) {
            delegate.onNoInternetAvailable();
            return;
        }
        ParseCloud.callFunctionInBackground("getDeals", map, (FunctionCallback<ArrayList<ParseObject>>) (objects, e) -> {
            if (e == null) {
                if (objects != null && objects.size() > 0) {
                    formatDeals(objects, "deals");
                } else {
                    delegate.onNoDealsAvailable(context.getResources().getString(R.string.no_deals));
                }
            } else {
                delegate.onRestaurantDealsFailure(e.getMessage());
            }

        });
    }


    public void getRestaurantDeals(Map<String,Object> requestParams) {
        dealStore = new DealStore();
        count = 0;
        isDealBookedService = new IsDealBookedService();
        isDealBookedService.setDelegateAndContext(this, context);
        if (!PermissionsUtil.isNetworkAvailable(context)) {
            delegate.onNoInternetAvailable();
            return;
        }
        ParseCloud.callFunctionInBackground("getDeals", requestParams, (FunctionCallback<ArrayList<ParseObject>>) (objects, e) -> {
            if (e == null) {
                if (objects != null && objects.size() > 0) {
                    formatDeals(objects, "deals");
                } else {
                    delegate.onNoDealsAvailable(context.getResources().getString(R.string.no_deals));
                }

            } else {
                delegate.onRestaurantDealsFailure(e.getMessage());
            }

        });
    }

    private void formatDeals(List<ParseObject> objects, String screenType) {
        int dealCount = 0;
        if (objects != null) {
            restaurantDeals.clear();
            isDealBookedService = new IsDealBookedService();
            isDealBookedService.setDelegateAndContext(this, context);
            if (objects.size() > 0) {
                long branchDealsCount = 0;
                for (Object object : objects) {
                    ParseObject branchParseObject = (ParseObject) object;
                    ArrayList<HashMap> branchDeals = (ArrayList<HashMap>) branchParseObject.get("deals");
                    branchDealsCount = branchDealsCount + branchDeals.size();
                }
                isDealBookedService.setBranchDealsCount(branchDealsCount);
                for (Object object : objects) {
                    ParseObject branchParseObject = (ParseObject) object;
                    ArrayList<HashMap> branchDeals = (ArrayList<HashMap>) branchParseObject.get("deals");
                    for (HashMap branchDealMap : branchDeals) {
                        dealCount++;
                        ParseObject dealBranchParseObject = new ParseObject("DealBranchRelations");
                        dealBranchParseObject.put("deal", branchDealMap.get("deal"));
                        dealBranchParseObject.put("branch", branchDealMap.get("branch"));
                        dealBranchParseObject.put("active_deal_yn", branchDealMap.get("active_deal_yn"));
                        Log.i("--deal:--", branchDealMap.get("deal").toString());
                        dealBranchParseObject.setObjectId(branchDealMap.get("objectId").toString());
                        Log.i("BranchIn:- ", String.valueOf(objects.indexOf(object)));
                        Log.i("DealIn:- ", String.valueOf(branchDeals.indexOf(branchDealMap)));
                        isDealBookedService.isDealBooked(dealBranchParseObject, objects.indexOf(object), branchDeals.indexOf(branchDealMap));
                    }
                    switch (screenType) {
                        case "deals":
                            restaurantDeals.add(dealStore.createHomeBranchDealsModel(object, context));
                            break;
                        case "myDeals":
                            restaurantDeals.add(dealStore.createMyDealsBranchDealsModel(object, context));
                            break;
                        case "mySubscriptions":
                            break;
                        default:
                            restaurantDeals.add(dealStore.createHomeBranchDealsModel(object, context));
                            break;
                    }
                }
                PrefManager.setDealsCount(dealCount);
            }
        }
    }

    private void formatMyDeals(List<ParseObject> objects, String screenType) {
        if (objects != null) {
            restaurantDeals.clear();
            isDealBookedService = new IsDealBookedService();
            isDealBookedService.setDelegateAndContext(this, context);
            Log.i("Count:- ", String.valueOf(objects.size()));
            if (objects.size() > 0) {
                long branchDealsCount = 0;
                for (Object object : objects) {
                    ParseObject branchParseObject = (ParseObject) object;
                    ArrayList<HashMap> branchDeals = (ArrayList<HashMap>) branchParseObject.get("deals");
                    branchDealsCount = branchDealsCount + branchDeals.size();
                }
                isDealBookedService.setBranchDealsCount(branchDealsCount);
                for (Object object : objects) {
                    ParseObject branchParseObject = (ParseObject) object;
                    ArrayList<ParseObject> branchDeals = (ArrayList<ParseObject>) branchParseObject.get("deals");
                    for (ParseObject branchDealParseObject : branchDeals) {
                        ParseObject dealBranchParseObject = new ParseObject("DealBranchRelations");
                        dealBranchParseObject.put("deal", branchDealParseObject.get("deal"));
                        dealBranchParseObject.put("branch", branchDealParseObject.get("branch"));
                        dealBranchParseObject.setObjectId(branchDealParseObject.getObjectId());
                        isDealBookedService.isDealBooked(dealBranchParseObject, objects.indexOf(object), branchDeals.indexOf(branchDealParseObject));
                    }
                    restaurantDeals.add(dealStore.createMyDealsBranchDealsModel(object, context));
                }
            }
        }
    }

    @Override
    public void onIsDealBooked(BookingDealModel booking, int index, int brancDealIndex) {
        setDealList(booking, Constant.CANCELDEAL, index, brancDealIndex);
    }

    @Override
    public void onIsDealNotBooked(int index, int branchDealIndex) {
        setDealList(null, context.getResources().getString(R.string.get_deal), index, branchDealIndex);
    }

    @Override
    public void onIsDealBookedFailure(String message) {
        count += 1;
    }

    private void setDealList(BookingDealModel bookingDealModel, String getDeal, int branchIndex, int brancDealIndex) {

        try {
            count += 1;
            try {
                restaurantDeals.get(branchIndex).getDeals().get(brancDealIndex).getDeal().setBookingDealModel(bookingDealModel);
                restaurantDeals.get(branchIndex).getDeals().get(brancDealIndex).getDeal().setGetDeal(getDeal);
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            if (count == isDealBookedService.getBranchDealsCount()) {
                delegate.onRestaurantDealsSuccess(restaurantDeals);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        query.findInBackground((objects, e) -> {
            if (e == null) {
                if (objects != null && objects.size() > 0) {
//                    fetchFilterDeals(objects);
                } else {
                    delegate.onNoDealsAvailable(context.getResources().getString(R.string.no_active_deals));
                }

            } else {
                delegate.onRestaurantDealsFailure(e.getMessage());
            }
        });
    }


    public void getMyAllDeals(Map<String, Object> map) {
        count = 0;
        dealStore = new DealStore();
        //My Deals Apis
        ParseCloud.callFunctionInBackground("getMyDeals", map, (FunctionCallback<ArrayList<ParseObject>>) (objects, e) -> {
            if (e == null) {
                if (objects.size() == 0) {
                    delegate.onNoDealsAvailable(context.getResources().getString(R.string.no_deals));
                } else {
                    formatMyDeals(objects, "myDeals");
                }

            } else {
                delegate.onRestaurantDealsFailure(e.getMessage());
            }
        });
    }

    public void getBannerData() {
        if (!PermissionsUtil.isNetworkAvailable(context)) {
            bannerDelegate.noInternetConnection();
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("X-Parse-Application-Id", context.getResources().getString(R.string.parse_app_id));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BannerData> call = apiInterface.getPromos(map);
        call.enqueue(new Callback<BannerData>() {
            @Override
            public void onResponse(@NonNull Call<BannerData> call, @NonNull Response<BannerData> response) {
                if (response.body() != null) {
                    List<Result> objects = response.body().getResults();
                    if (objects.size() > 0)
                        bannerDelegate.bannerDataSuccess(objects);
                    else
                        bannerDelegate.noBannerDataAvailable("no Data Found");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BannerData> call, @NonNull Throwable t) {
                bannerDelegate.bannerDatafailure(t.getMessage());
            }
        });
    }

}
