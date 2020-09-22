package com.appinnovates.campeat.Testing;

import android.content.Context;
import android.util.Log;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.BranchDealModelTest;
import com.appinnovates.campeat.model.DealsModel;
import com.appinnovates.campeat.model.getAllDeal.Branch;
import com.appinnovates.campeat.model.getAllDeal.Deal;
import com.appinnovates.campeat.model.getAllDeal.DealUrl;
import com.appinnovates.campeat.model.getAllDeal.Operational;
import com.appinnovates.campeat.model.getAllDeal.Restaurant;
import com.appinnovates.campeat.model.getAllDeal.RestaurantDeals;
import com.appinnovates.campeat.services.ImageService.ImageService;
import com.appinnovates.campeat.utils.DateFormatUtil;
import com.google.gson.Gson;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DealStoreTest {

    List<DealsModel> dealsModelList = new ArrayList<>();
    private ImageService imageService;
    private int days = 0;
    private int hours = 0;
    private int mins = 0;
    private Context context;


    private String getTimeLeft(Date date) {
        days = (int) DateFormatUtil.getNumberOfDays(date);
        hours = (int) DateFormatUtil.getNumberOfHours(date);
        mins = (int) DateFormatUtil.getNumberOfMins(date);

        if (days > 0) {
            switch (days) {
                case 1:
                    return "" + days + " " + context.getResources().getString(R.string.day);
                default:
                    return "" + days + " " + context.getResources().getString(R.string.days);
            }
        } else if (hours > 0) {
            switch (hours) {
                case 1:
                    return "" + hours + " " + context.getResources().getString(R.string.hour);
                default:
                    return "" + hours + " " + context.getResources().getString(R.string.hours);
            }
        } else if (mins > 0) {
            switch (mins) {
                case 1:
                    return "" + mins + " " + context.getResources().getString(R.string.min);
                default:
                    return "" + mins + " " + context.getResources().getString(R.string.mins);
            }
        } else {
            return "" + 0 + " " + context.getResources().getString(R.string.mins);
        }
    }


    public Branch saveBranchData(ParseObject parseObject) {
        RestaurantDeals restaurantDeals=new RestaurantDeals();
        Branch branchModel = null;
        if (parseObject != null) {
            branchModel = new Branch();
            branchModel.set_Type(parseObject.toString());
            branchModel.setObjectId(parseObject.getObjectId());
            branchModel.setBranchName((String) parseObject.get("branch_name"));
            branchModel.setStreetAddress((String) parseObject.get("street_address"));
            branchModel.setCity((String) parseObject.get("city"));
            branchModel.setCountry((String) parseObject.get("country"));
/*            branchModel.set((String) parseObject.get("state"));
            branchModel.set((String) parseObject.get("zipcode"));*/
            int rating = 0;
            try {
               rating = (int) parseObject.get("averageRating");
            } catch (Exception e) {
                e.printStackTrace();
            }
            branchModel.setAverageRating(Long.parseLong(String.valueOf(rating)));
            branchModel.setGeoPoint(parseObject.getParseGeoPoint("geo_point"));
            try {
                String phoneNo = (String) parseObject.get("phone");
                branchModel.setPhone(phoneNo);
            } catch (Exception e) {
                e.printStackTrace();
            }
/*
            branchModel.set((String) parseObject.get("email"));
*/
            try {
                JSONObject jsonObject = parseObject.getJSONObject("operational");
                Gson gson = new Gson();
                Operational model = gson.fromJson(String.valueOf(jsonObject), Operational.class);
                branchModel.setOperational(model);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ParseObject restaurantParseObject = parseObject.getParseObject("restaurant_id");
            Restaurant restaurantModel = saveRestaurantData(restaurantParseObject);
            restaurantDeals.setRestaurant(restaurantModel);
        }
        return branchModel;
    }

    public Restaurant saveRestaurantData(ParseObject parseObject) {
        Restaurant restaurantModel = null;
        if (parseObject != null) {
            restaurantModel = new Restaurant();
            restaurantModel.set_Type(parseObject.toString());
            restaurantModel.setObjectId(parseObject.getObjectId());
            restaurantModel.setRestaurantName((String) parseObject.get("restaurant_name"));
/*            restaurantModel.set((String) parseObject.get("manager_name"));
            restaurantModel.setManager_surname((String) parseObject.get("manager_surname"));*/
            restaurantModel.setStreetAddress((String) parseObject.get("street_address"));
            restaurantModel.setCity((String) parseObject.get("city"));
            restaurantModel.setCountry((String) parseObject.get("country"));
/*            restaurantModel.set((String) parseObject.get("state"));
            restaurantModel.setZipcode((String) parseObject.get("zipcode"));*/
           // restaurantModel.getLogo().setUrl(parseObject.getParseFile("logo").toString());
            restaurantModel.setDescription((String) parseObject.get("description"));
            restaurantModel.setAverageRating(Long.parseLong(String.valueOf(parseObject.getInt("averageRating"))));
            try {
                String phoneNo = (String) parseObject.get("phone");
                restaurantModel.setPhone(phoneNo);
            } catch (Exception e) {
                e.printStackTrace();
            }
/*
            restaurantModel.setEmail((String) parseObject.get("email"));
*/
            restaurantModel.set_Type((String) parseObject.get("restaurant_type"));
/*            String foundedDate = (String) parseObject.get("founded_date");
            if (foundedDate != null) {
                restaurantModel.setFounded_date(foundedDate);
            }*/
        }
        return restaurantModel;
    }

    public Deal saveDeal(ParseObject parseObject, Context context) {
        Deal dealsModel = null;
        this.context = context;
        if (parseObject != null) {
            dealsModel = new Deal();
            dealsModel.setObjectId(parseObject.getObjectId());
            dealsModel.setDealName((String) parseObject.get("deal_name"));
/*
            dealsModel.setActive_deal_yn((String) parseObject.get("active_deal_yn"));
*/
/*
            dealsModel.setRepeat_type((String) parseObject.get("repeat_type"));
*/
            dealsModel.getDealUrl().setUrl(parseObject.getParseFile("deal_url").toString());
/*
            dealsModel.setDealPointer(parseObject);
*/
            dealsModel.setDealsLeft(Long.parseLong(String.valueOf(parseObject.getInt("deals_left"))));
            dealsModel.setType(parseObject.getString("type"));
            String minPerson = (String) parseObject.get("min_person_required");
            String maxPerson = (String) parseObject.get("max_person_required");

/*            dealsModel.setMin_person_required(String.valueOf(minPerson));
            dealsModel.setMax_person_required(String.valueOf(maxPerson));*/
            try {
                int discountRate = (int) parseObject.get("discount_rate");
                dealsModel.setDiscountRate(Long.parseLong(String.valueOf(discountRate)));
                int groupDiscountRate = (int) parseObject.get("group_discount_rate");
                dealsModel.setGroupDiscountRate(Long.parseLong(String.valueOf(groupDiscountRate)));
                int freeCouponDiscount = (int) parseObject.getDouble("free_coupon_discount");
                dealsModel.setFreeCouponDiscount(Long.parseLong(String.valueOf(freeCouponDiscount)));
/*                JSONArray indexes = parseObject.getJSONArray("indexes");
                int[] ints = new int[indexes.length()];
                for(int i = 0; i < indexes.length() - 1 ; i++){
                    ints[i] = indexes.getInt(i);
                }
                dealsModel.setIndexes(ints);*/

            } catch (Exception e) {
                e.printStackTrace();
            }

            Date createAt = parseObject.getCreatedAt();
            Date startDate = (Date) parseObject.get("start_date");
            Date endDate = (Date) parseObject.get("end_date");
            String startTime = (String) parseObject.get("start_time");
            String endTime = (String) parseObject.get("end_time");
            if (startTime != null && !startTime.equals("null")) {
                dealsModel.setStartTime(DateFormatUtil.getTimeFromDate(DateFormatUtil.setTime(startTime)));
            }
            if (endTime != null && !endTime.equals("null")) {
                dealsModel.setEndTime(DateFormatUtil.getTimeFromDate(DateFormatUtil.setTime(endTime)));
            }
/*            if (startTime != null && endTime != null) {
                dealsModel.setTimeLeft(DateFormatUtil.getTimeLeft(startTime, endTime));
            } else {
                dealsModel.setTimeLeft("0 min");
            }*/
            if (startDate != null) {
                dealsModel.setStartDate(startDate);
/*
                dealsModel.setTimeGone((int) DateFormatUtil.getNumberOfDays(dealsModel.getStart_date()));
*/
//                dealsModel.setStart_time(DateFormatUtil.getTimeFromDate(startDate));

            }
            if (endDate != null) {
                dealsModel.setEndDate(endDate);
//                dealsModel.setEnd_time(DateFormatUtil.getTimeFromDate(endDate));
//                if (startDate != null) {
//                    dealsModel.setTimeLeft(DateFormatUtil.getTimeLeft(startDate, endDate));
//                }
            }
            if (createAt != null) {
                dealsModel.setCreatedAt(DateFormatUtil.getDateInString(createAt));
            }
        }
        return dealsModel;
    }


    public BranchDealModelTest saveBranchDeal(ParseObject parseObject, Context context) {
        BranchDealModelTest branchDealModel = new BranchDealModelTest();
        this.context = context;
        if (parseObject != null) {
            branchDealModel.setObjectId(parseObject.getObjectId());
            branchDealModel.setBranchDealPointer(parseObject);
        }
        return branchDealModel;
    }

    public RestaurantDeals createRestaurantDeals(Object object, Context context) {
        this.context = context;
        RestaurantDeals restaurantDeals = null;
        if (object != null) {
            restaurantDeals = new RestaurantDeals();
            Restaurant restaurant = new Restaurant();
            Map map = (HashMap)object;
            ParseObject restaurantObject = (ParseObject)map.get("restaurant");


            BranchDealModelTest branchDealModel = new BranchDealModelTest();
            branchDealModel.setObjectId(restaurantObject.getObjectId());
            branchDealModel.setBranchDealPointer(restaurantObject);

            restaurant.setRestaurantName(restaurantObject.get("restaurant_name").toString());
            restaurant.setRestaurantPointer(restaurantObject);
            restaurant.setObjectId(restaurantObject.getObjectId());
            restaurant.setDistance(restaurantObject.get("distance").toString());
            long ss= (restaurantObject.get("averageRating") != null)?Long.parseLong(restaurantObject.get("averageRating")+""):0;
            restaurant.setStreetAddress((restaurantObject.get("street_address") != null)?restaurantObject.get("street_address")+"":"Not Specified");
            restaurant.setCity((restaurantObject.get("city") != null)?restaurantObject.get("city")+"":"Not Specified");
            Log.i("street---",restaurantObject.get("street_address")+"");
            restaurant.setAverageRating((restaurantObject.get("averageRating") != null)?Long.parseLong(restaurantObject.get("averageRating")+""):0);
            restaurant.setCountry(restaurantObject.get("country").toString());
//            restaurant.setAverageRating((Long)restaurantObject.get("averageRating"));
            Log.i("----BranchDealsModel----", restaurantObject.get("branch").toString());
            ParseObject branchObject = (ParseObject)restaurantObject.get("branch");
            if(branchObject != null) {
                Branch branch = new Branch();
                Log.i("---BranchDealsModel Name:-", branchObject.get("branch_name").toString());
                branch.setObjectId(branchObject.getObjectId());
                branch.setBranchName(branchObject.get("branch_name").toString());
                branch.setStreetAddress(branchObject.get("street_address").toString());
                branch.setCity(branchObject.get("city").toString());
                branch.setCountry(branchObject.get("country").toString());
                branch.setBranchPointer(branchObject);
                restaurant.setBranch(branch);
/*                Restaurant restaurantModel = saveRestaurantData(restaurantObject);
                Log.i("restaurantsmodel",restaurantModel.toString());*/
                branch.setRestaurantModel(restaurant);
                branchDealModel.setBranch(branch);

                try {
                    JSONObject jsonObject = branchObject.getJSONObject("operational");
                    Gson gson = new Gson();
                    Operational model = gson.fromJson(String.valueOf(jsonObject), Operational.class);
                    branch.setOperational(model);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            List<ParseObject> deals = (List<ParseObject>)map.get("deals");
            List<Deal> dealList = new ArrayList<Deal>();
            for (ParseObject dealObject : deals) {
                Deal deal = new Deal();
                deal.setDealName(dealObject.get("deal_name").toString());
                deal.setDealPointer(dealObject);
                Log.i("deal name:- ", dealObject.get("deal_name").toString());
                int discountRate = (int) dealObject.get("discount_rate");
                deal.setDiscountRate(Long.parseLong(String.valueOf(discountRate)));
/*
                int groupDiscountRate = (int) dealObject.get("group_discount_rate");
*//*
                deal.setGroupDiscountRate(Long.parseLong(String.valueOf(groupDiscountRate)));
                int freeCouponDiscount = (int) dealObject.getDouble("free_coupon_discount");
                deal.setFreeCouponDiscount(Long.parseLong(String.valueOf(freeCouponDiscount)));*/
                deal.setDealsLeft(Long.parseLong(String.valueOf(dealObject.getInt("deals_left"))));
                deal.setType(dealObject.getString("type"));
                deal.setStartDate(new Date(dealObject.get("start_date").toString()));
                deal.setEndDate(new Date(dealObject.get("end_date").toString()));
                Log.i("Mini-----", dealObject.getString("min_person_required") != null ? dealObject.getString("min_person_required"):"0");
                deal.setMinPersonRequired(dealObject.getString("min_person_required") != null ? dealObject.getString("min_person_required"):"0");
                deal.setGroupDiscountRate(dealObject.getLong("group_discount_rate"));
                deal.setFreeCouponDiscount(dealObject.getLong("free_coupon_discount"));
                DealUrl dealUrl = new DealUrl();
                ParseFile dealUrlFile = dealObject.getParseFile("deal_url");
                Log.i("--DealUrl--", dealUrlFile.toString());
                Log.i("--Url--", dealUrlFile.getUrl());
                String url = dealUrlFile.getUrl();
                String[] urls = url.split(":3000");
                dealUrl.setUrl("http://35.162.72.179:3000"+urls[1]);
                deal.setDealUrl(dealUrl);
                dealList.add(deal);

                JSONArray indexes = dealObject.getJSONArray("indexes");
                int[] ints = new int[indexes.length()];
                for(int i = 0; i < indexes.length() - 1 ; i++){
                    try {
                        ints[i] = indexes.getInt(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                deal.setIndexes(ints);


            }
            branchDealModel.setDeal(dealList);
            restaurantDeals.setDeals(dealList);
            restaurantDeals.setRestaurant(restaurant);

        }
        return restaurantDeals;

//        RestaurantDeals restaurantDeals = new RestaurantDeals();
//        this.context = context;
//        if (parseObject != null) {
//            Restaurant restaurant= restaurantDeals.getRestaurant();
//            restaurant.setObjectId(parseObject.getObjectId());
//            restaurant.getUserId().set_Type(parseObject.toString());
//            ParseObject branchParseObject = parseObject.getParseObject("branch");
//            BranchDealsModel branchModel = saveBranchData(branchParseObject);
//            restaurant.setBranchDealModels(branchModel);
//
//            ParseObject dealParseObject = parseObject.getParseObject("deal");
//            restaurantDeals.setBranchDealModel(saveDeal(dealParseObject, context));
//        }
//        return restaurantDeals;
    }
}
