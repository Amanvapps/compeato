package com.appinnovates.campeat.services.DealService;

import android.content.Context;
import android.util.Log;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.model.getAllDealsModel.Logo;
import com.appinnovates.campeat.model.getAllDealsModel.BranchDealsModel;
import com.appinnovates.campeat.model.getAllDealsModel.BranchModel;
import com.appinnovates.campeat.model.getAllDealsModel.DealModel;
import com.appinnovates.campeat.model.getAllDealsModel.DealUrl;
import com.appinnovates.campeat.model.getAllDealsModel.EndDate;
import com.appinnovates.campeat.model.getAllDealsModel.Operational;
import com.appinnovates.campeat.model.getAllDealsModel.RestaurantBranchDealsModel;
import com.appinnovates.campeat.model.getAllDealsModel.RestaurantIdModel;
import com.appinnovates.campeat.model.getAllDealsModel.StartDate;
import com.appinnovates.campeat.services.ImageService.ImageService;
import com.appinnovates.campeat.utils.DateFormatUtil;
import com.google.gson.Gson;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class DealStore {

    List<DealModel> dealsModelList = new ArrayList<>();
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

    public BranchModel saveBranchData(ParseObject parseObject) {
        BranchModel branchModel = null;
        if (parseObject != null) {
            branchModel = new BranchModel();
            //branchModel.setBranchPointer(parseObject);
            branchModel.setObjectId(parseObject.getObjectId());
            branchModel.setBranchName((String) parseObject.get("branch_name"));
            String address = parseObject.get("street_address").toString();
            address = address.replaceAll("[a-zA-Z]+", "");
            address = address.replaceAll("[,()]", "");
            String koreanAddress = "";
            String[] streetAddress = address.split(" ");
            for (String place : streetAddress) {
                if (place.length()>0) {
                    if (!Character.isDigit(place.charAt(0)) || !Character.isDigit(place.charAt(place.length() - 1))) {
                        place = place.replaceAll("[-]", "");
                    }
                    koreanAddress = koreanAddress.concat(place + " ");
                }
            }
            branchModel.setStreetAddress(koreanAddress);
            branchModel.setCity((String) parseObject.get("city"));
            branchModel.setCountry((String) parseObject.get("country"));
            branchModel.setPhone(parseObject.get("phone").toString());
            branchModel.setBranchPointer(parseObject);
            branchModel.setRestaurantId(createBranchRestaurantModel(parseObject.getParseObject("restaurant_id")));
//            branchModel.setState((String) parseObject.get("state"));
            //branchModel.setZipcode((String) parseObject.get("zipcode"));
            int rating = 0;
            try {
                rating = (int) parseObject.get("averageRating");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //branchModel.setAverageRating(rating);
            branchModel.setGeoPoint(parseObject.getParseGeoPoint("geo_point"));
            try {
                String phoneNo = (String) parseObject.get("phone");
                branchModel.setPhone(phoneNo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //branchModel.setEmail((String) parseObject.get("email"));
            try {
                JSONObject jsonObject = parseObject.getJSONObject("operational");
                Gson gson = new Gson();
                Operational model = gson.fromJson(String.valueOf(jsonObject), Operational.class);
                branchModel.setOperational(model);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ParseObject restaurantParseObject = parseObject.getParseObject("restaurant_id");
            RestaurantBranchDealsModel restaurantModel = saveRestaurantData(restaurantParseObject);
            //branchModel.setRestaurantModel(restaurantModel);
        }
        return branchModel;
    }

    public RestaurantBranchDealsModel saveRestaurantData(ParseObject parseObject) {
        RestaurantBranchDealsModel restaurantModel = null;
        if (parseObject != null) {
            restaurantModel = new RestaurantBranchDealsModel();
            //restaurantModel.setRestaurantPointer(parseObject);
            restaurantModel.setObjectId(parseObject.getObjectId());
            restaurantModel.setRestaurantName((String) parseObject.get("restaurant_name"));
            restaurantModel.setManagerName((String) parseObject.get("manager_name"));
            restaurantModel.setManagerName((String) parseObject.get("manager_surname"));
            String address = parseObject.get("street_address").toString();
            address = address.replaceAll("[a-zA-Z]+", "");
            address = address.replaceAll("[,()]", "");
            String koreanAddress = "";
            String[] streetAddress = address.split(" ");
            for (String place : streetAddress) {
                if (place.length()>0) {
                    if (!Character.isDigit(place.charAt(0)) || !Character.isDigit(place.charAt(place.length() - 1))) {
                        place = place.replaceAll("[-]", "");
                    }
                    koreanAddress = koreanAddress.concat(place + " ");
                }
            }
            restaurantModel.setStreetAddress(koreanAddress);
            restaurantModel.setCity((String) parseObject.get("city"));
            restaurantModel.setCountry((String) parseObject.get("country"));
            restaurantModel.setState((String) parseObject.get("state"));
            restaurantModel.setZipcode((String) parseObject.get("zipcode"));
            //restaurantModel.setImageFile(parseObject.getParseFile("logo"));
            //restaurantModel.setDescription((String) parseObject.get("description"));
            restaurantModel.setAverageRating((long) parseObject.getInt("averageRating"));
            try {
                String phoneNo = (String) parseObject.get("phone");
                //restaurantModel.setPhone(phoneNo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //restaurantModel.setEmail((String) parseObject.get("email"));
            restaurantModel.set_Type((String) parseObject.get("restaurant_type"));
            String foundedDate = (String) parseObject.get("founded_date");
            if (foundedDate != null) {
                restaurantModel.setFoundedDate(foundedDate);
            }
        }
        return restaurantModel;
    }

    public DealModel saveDeal(ParseObject parseObject, Context context) {
        DealModel dealsModel = null;
        this.context = context;
        try {
            if (parseObject != null) {
                dealsModel = new DealModel();
                dealsModel.setObjectId(parseObject.getObjectId());
                dealsModel.setDealDesc(parseObject.getString("deal_description"));
                dealsModel.setDealName((String) parseObject.get("deal_name"));
                dealsModel.setActiveDealYn((String) parseObject.get("active_deal_yn"));
                dealsModel.setRepeatType((String) parseObject.get("repeat_type"));
                //dealsModel.setImageFile(parseObject.getParseFile("deal_url"));
                dealsModel.setDealPointer(parseObject);
                dealsModel.setDealsLeft((long) parseObject.getInt("deals_left"));
                dealsModel.setType(parseObject.getString("type"));
                String minPerson = (String) parseObject.get("min_person_required");
                String maxPerson = (String) parseObject.get("max_person_required");

                //dealsModel.setMinPersonRequired(String.valueOf(minPerson));
                //dealsModel.setMax_person_required(String.valueOf(maxPerson));

                long discountRate = parseObject.getLong("discount_rate");
                dealsModel.setDiscountRate(discountRate);
                long groupDiscountRate = parseObject.getLong("group_discount_rate");
                dealsModel.setGroupDiscountRate(groupDiscountRate);
                long freeCouponDiscount = Long.parseLong(parseObject.get("free_coupon_discount") != null ? parseObject.getString("free_coupon_discount") : "0");
                dealsModel.setFreeCouponDiscount(freeCouponDiscount);
                try {
                    JSONArray indexes = parseObject.getJSONArray("indexes");
                    int[] ints = new int[indexes.length()];
                    for (int i = 0; i < indexes.length() - 1; i++) {
                        ints[i] = indexes.getInt(i);
                    }
                    //dealsModel.setIndexes(ints);

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
                if (startTime != null && endTime != null) {
                    //dealsModel.setTimeLeft(DateFormatUtil.getTimeLeft(startTime, endTime));
                } else {
                    //dealsModel.setTimeLeft("0 min");
                }
                if (startDate != null) {
                    // dealsModel.setStartDate(startDate);
                    //dealsModel.setTimeGone((int) DateFormatUtil.getNumberOfDays(dealsModel.getStartDate()));
//                dealsModel.setStart_time(DateFormatUtil.getTimeFromDate(startDate));

                }
                if (endDate != null) {
                    //dealsModel.setEndDate(endDate);
//                dealsModel.setEnd_time(DateFormatUtil.getTimeFromDate(endDate));
//                if (startDate != null) {
//                    dealsModel.setTimeLeft(DateFormatUtil.getTimeLeft(startDate, endDate));
//                }
                }
                if (createAt != null) {
                    dealsModel.setCreatedAt(DateFormatUtil.getDateInString(createAt));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dealsModel;
    }


    public BranchDealModel saveBranchDeal(ParseObject parseObject, Context context) {
        BranchDealModel branchDealModel = new BranchDealModel();
        this.context = context;
/*        if (parseObject != null) {
            branchDealModel.setObjectId(parseObject.getObjectId());
            branchDealModel.setBranchDealPointer(parseObject);
            branchDealModel.setDeal(createDealModel((ParseObject)parseObject.get("deal")));
            branchDealModel.setBranch(createBranchModel((ParseObject)parseObject.get("branch")));
            //BranchDealsModel branchModel = saveBranchData(branchParseObject);
            //branchDealModel.setBranchDealModels(branchModel);

            //ParseObject dealParseObject = parseObject.getParseObject("deal");
            //branchDealModel.setDealModel(saveDeal(dealParseObject, context));
        }*/
        if (parseObject != null) {
            branchDealModel.setObjectId(parseObject.getObjectId());
            branchDealModel.setBranchDealPointer(parseObject);
            ParseObject branchParseObject = parseObject.getParseObject("branch");
            BranchModel branchModel = saveBranchData(branchParseObject);
            branchDealModel.setBranch(branchModel);

            ParseObject dealParseObject = parseObject.getParseObject("deal");
            branchDealModel.setDeal(createDealModel(dealParseObject, context));
        }
        return branchDealModel;
    }

    public BranchDealModel saveBranchDeal1(String objectId, Context context) {
        BranchDealModel branchDealModel = new BranchDealModel();
        this.context = context;
        branchDealModel.setObjectId(objectId);
        ParseObject pointer = new ParseObject("DealBranchRelations");
        pointer.setObjectId(objectId);
        branchDealModel.setBranchDealPointer(pointer);
        return branchDealModel;
    }

    List<BranchDealsModel> createBranchDealsModel(List<ParseObject> branchesDeals) {
        List<BranchDealsModel> branchDealsModels = new ArrayList<>();
        for (ParseObject branchDeals : branchesDeals) {
            BranchDealsModel branchDealsModel = new BranchDealsModel();
            branchDealsModel.setBranchName(branchDeals.get("branch_name").toString());
            List<HashMap> deals = (List<HashMap>) branchDeals.get("deals");
            branchDealsModel.setBranchDealModel(createDealsBranchDealModel(deals));
            branchDealsModels.add(branchDealsModel);
        }
        return branchDealsModels;
    }

    List<BranchDealModel> createMyDealsBranchDealModel(List<ParseObject> branchDeals) {
        List<BranchDealModel> branchDealModels = new ArrayList<>();
        for (ParseObject branchDealParseObject : branchDeals) {
            BranchDealModel branchDealModel = new BranchDealModel();
            branchDealModel.setDeal(createDealModel((ParseObject) branchDealParseObject.get("deal"), context));
            branchDealModel.setBranch(createBranchModel((ParseObject) branchDealParseObject.get("branch")));
            ParseObject branchParseObject = new ParseObject("DealBranchRelations");
            branchParseObject.put("deal", branchDealParseObject.get("deal"));
            branchParseObject.put("branch", branchDealParseObject.get("branch"));
            branchParseObject.setObjectId(branchDealParseObject.getObjectId());
            branchParseObject.put("active_deal_yn", "Y");
/*            branchDealModel.getDeal().setBookingDealModel(BookingDealStore.fetchBooking((ParseObject)branchDealParseObject.get("deal"), context));
            branchDealModel.getDeal().setGetDeal(context.getResources().getString(R.string.cancel_deal));*/
            branchDealModel.setBranchDealPointer(branchParseObject);
            branchDealModels.add(branchDealModel);
        }
        return branchDealModels;
    }

    List<BranchDealModel> createDealsBranchDealModel(List<HashMap> branchDeals) {
        List<BranchDealModel> branchDealModels = new ArrayList<>();
        for (HashMap branchDealMap : branchDeals) {
            BranchDealModel branchDealModel = new BranchDealModel();
            branchDealModel.setDeal(createDealModel((ParseObject) branchDealMap.get("deal"), context));
            branchDealModel.setBranch(createBranchModel((ParseObject) branchDealMap.get("branch")));
            ParseObject branchParseObject = new ParseObject("DealBranchRelations");
            branchParseObject.put("deal", branchDealMap.get("deal"));
            branchParseObject.put("branch", branchDealMap.get("branch"));
            branchParseObject.setObjectId(branchDealMap.get("objectId").toString());
            branchParseObject.put("active_deal_yn", "Y");
//            branchDealModel.getDeal().setBookingDealModel(BookingDealStore.fetchBooking((ParseObject)branchDealMap.get("deal"), context));
/*            branchParseObject.put("__type", "Pointer");
            branchParseObject.put("objectId", ParseUser.getCurrentUser());*/
            branchDealModel.setBranchDealPointer(branchParseObject);
            branchDealModels.add(branchDealModel);
        }
        return branchDealModels;
    }

    private BranchModel createBranchModel(ParseObject branchParseObject) {
        BranchModel branchModel = new BranchModel();
        branchModel.setBranchName(branchParseObject.get("branch_name").toString());
        String address = branchParseObject.get("street_address").toString();
        address = address.replaceAll("[a-zA-Z]+", "");
        address = address.replaceAll("[,()]", "");
        String koreanAddress = "";
        String[] streetAddress = address.split(" ");
        for (String place : streetAddress) {
            if (place.length()>0) {
                if (!Character.isDigit(place.charAt(0)) || !Character.isDigit(place.charAt(place.length() - 1))) {
                    place = place.replaceAll("[-]", "");
                }
                koreanAddress = koreanAddress.concat(place + " ");
            }
        }
        branchModel.setStreetAddress(koreanAddress);
        branchModel.setCity(branchParseObject.get("city").toString());
        branchModel.setCountry(branchParseObject.get("country").toString());
        branchModel.setPhone(branchParseObject.get("phone").toString());
        branchModel.setRestaurantId(createBranchRestaurantModel(branchParseObject.getParseObject("restaurant_id")));
        branchModel.setObjectId(branchParseObject.getObjectId());
        try {
            JSONObject jsonObject = branchParseObject.getJSONObject("operational");
            Gson gson = new Gson();
            Operational model = gson.fromJson(String.valueOf(jsonObject), Operational.class);
            branchModel.setOperational(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        branchModel.setBranchPointer(branchParseObject);
        return branchModel;
    }

/*    private RestaurantIdModel createRestaurantIdModel(ParseObject restaurantIdParseObject) {
        RestaurantIdModel restaurantIdModel = new RestaurantIdModel();
        //restaurantIdModel.setRestaurantName(restaurantIdParseObject.get("restaurant_name").toString());
        restaurantIdModel.setPointer(restaurantIdParseObject);
        return restaurantIdModel;
    }*/

    private DealModel createDealModel(ParseObject dealParseObject, Context context) {
        DealModel dealModel = new DealModel();
        dealModel.setDealPointer(dealParseObject);
        dealModel.setDealName(dealParseObject.getString("deal_name"));
        dealModel.setDealDesc(dealParseObject.getString("deal_description"));
        dealModel.setObjectId(dealParseObject.getObjectId());
        int discountRate = dealParseObject.getInt("discount_rate");
        dealModel.setDiscountRate(Long.parseLong(String.valueOf(discountRate)));
        int groupDiscountRate = dealParseObject.getInt("group_discount_rate");
        dealModel.setGroupDiscountRate(Long.parseLong(String.valueOf(groupDiscountRate)));
        int freeCouponDiscount = dealParseObject.getInt("free_coupon_discount");
        dealModel.setFreeCouponDiscount((long) freeCouponDiscount);
        dealModel.setDealsLeft((long) dealParseObject.getInt("deals_left") != 0 ? (long) dealParseObject.getInt("deals_left") : 0);
        dealModel.setType(dealParseObject.getString("type"));
        dealModel.setMinPersonRequired(dealParseObject.get("min_person_required") != null ? dealParseObject.get("min_person_required").toString() : "0");
        dealModel.setGroupDiscountRate(dealParseObject.getLong("group_discount_rate"));

        try {
            JSONArray indexes = dealParseObject.getJSONArray("indexes");
            int[] ints = new int[indexes.length()];
            for (int i = 0; i < indexes.length() - 1; i++) {
                ints[i] = indexes.getInt(i);
            }
            dealModel.setIndexes(ints);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd");
        Date startDate = (Date) dealParseObject.get("start_date");
        Date endDate = (Date) dealParseObject.get("end_date");
//        Date formattedStartDate = null;
//        Date formattedEndDate = null;
//        try {
//            formattedStartDate = simpleDateFormat.parse(simpleDateFormat.format(startDate));
//            formattedEndDate = simpleDateFormat.parse(simpleDateFormat.format(endDate));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        long startTimeStamp = DateFormatUtil.calculateTime(dealParseObject.get("start_time") != null && !"Not specified".equals(dealParseObject.get("start_time"))? dealParseObject.get("start_time").toString() : "0");
//        long endTimeStamp = DateFormatUtil.calculateTime(dealParseObject.get("end_time") != null && !"Not specified".equals(dealParseObject.get("end_time")) ? dealParseObject.get("end_time").toString() : "0");
//
//        long totalStartTimeStamp = formattedStartDate.getTime() + startTimeStamp;
//        long totalEndTimeStamp = formattedEndDate.getTime() + endTimeStamp;
//
//        calendar.setTimeInMillis(totalStartTimeStamp);
//        Date startDateByDefaultTimeZone = calendar.getTime();
//
//        calendar.setTimeInMillis(totalEndTimeStamp);
//        Date endDateByDefaultTimeZone = calendar.getTime();
//
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        Date currentDateByDefaultTimeZone = calendar.getTime();

        dealModel.setLeftTime(endDate.getTime());

//        if(startDateByDefaultTimeZone.getTime() < currentDateByDefaultTimeZone.getTime() && currentDateByDefaultTimeZone.getTime() < endDateByDefaultTimeZone.getTime()) {
//            dealModel.setTimeLeft(String.valueOf(endDateByDefaultTimeZone.getTime() - currentDateByDefaultTimeZone.getTime()));
//        } else {
//            Log.i("Else---", "Else");
//        }


//        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd");
//        Date todayDate = new Date();
//        calendar.setTime(todayDate);
//        Date todayUTCDate = calendar.getTime();
//        Date startDate = (Date) dealParseObject.get("start_date");
//        calendar.setTime(startDate);
//        Date startUTCDate = calendar.getTime();
//        Date endDate = (Date) dealParseObject.get("end_date");
//        calendar.setTime(endDate);
//        Date endUTCDate = calendar.getTime();
//        Calendar startDateCalendar = new GregorianCalendar(1900+startUTCDate.getYear(), startUTCDate.getMonth(), startUTCDate.getDate());
//        Calendar endDateCalendar = new GregorianCalendar(1900+endUTCDate.getYear(), endUTCDate.getMonth(), endUTCDate.getDate());
//        Date formattedStartDate = startDateCalendar.getTime();//new Date(simpleDateFormat.format(startDate));
//        Date formattedEndDate = endDateCalendar.getTime();//new Date(simpleDateFormat.format(endDate));
//        Log.i("date--================", "================");
//        Log.i("date---start", formattedStartDate.toString());
//        Log.i("date---end", formattedEndDate.toString());
//        Log.i("date---today", todayDate.toString());
//        Log.i("date------------------", "----------------");
////        long startTime = Long.parseLong(dealParseObject.get("start_time")!=null ? dealParseObject.get("start_time").toString() : "0");
////        long endTime = Long.parseLong(dealParseObTadject.getString("end_time")!=null ? dealParseObject.getString("start_time") : "0");
//        long startTime = DateFormatUtil.calculateTime(dealParseObject.get("start_time") != null && !"Not specified".equals(dealParseObject.get("start_time"))? dealParseObject.get("start_time").toString() : "0");
//        long endTime = DateFormatUtil.calculateTime(dealParseObject.get("end_time") != null && !"Not specified".equals(dealParseObject.get("end_time")) ? dealParseObject.get("end_time").toString() : "0");
//        Log.i("time--================", "================");
//        Log.i("time--start", startTime + "");
//        Log.i("time--end", endTime + "");
//        Log.i("time------------------", "----------------");
//        long totalStartTime = startDate.getTime() + startTime;
//        long totalEndTime = endDate.getTime() + endTime;
//        long currentTime = todayDate.getTime();
//        Calendar todayDateCalendar = new GregorianCalendar(1900+todayDate.getYear(), todayDate.getMonth(), todayDate.getDate() + 1);
//        long todayEndTime = todayDateCalendar.getTime().getTime()-1000;
//        Log.i("--ToDate--", calendar.getTime().toString());
//        Log.i("time1--================", "================");
//        Log.i("time1--start", totalStartTime + "");
//        Log.i("time1--end", totalEndTime + "");
//        Log.i("time1--current", currentTime + "");
//        Log.i("--todayEndTime--", todayEndTime + "");
//        Log.i("time1------------------", "----------------");
//        if (totalStartTime > currentTime && currentTime < totalEndTime  && totalEndTime < todayEndTime) {
//            Log.i("status-About", "to come");
//            dealModel.setIsFixed(true);
//            dealModel.setTimeToStart(dealParseObject.get("start_time").toString());
//        } else if (currentTime > totalStartTime && currentTime < totalEndTime && totalEndTime < todayEndTime) {
//            Log.i("status-Running", "Running");
//            dealModel.setIsFixed(false);
//            dealModel.setTimeLeft(String.valueOf(totalEndTime));
//        } else if(totalEndTime < currentTime) {
//            Log.i("time1--================", "================");
//            Log.i("e-time1--start", totalStartTime + "");
//            Log.i("e-time1--end", totalEndTime + "");
//            Log.i("e-time1--current", currentTime + "");
//            Log.i("e-todayEndTime--", todayEndTime + "");
//            Log.i("time1------------------", "----------------");
//            Log.i("status-Expired", "Expired");
//            dealModel.setIsFixed(true);
//            dealModel.setTimeToStart("00 : 00 : 00");
//        }
//        dealModel.setTimeLeft(String.valueOf(endDate.getTime()-startDate.getTime()));
//        dealModel.setEndDate(setEndDateDealModel(dealParseObject.getParseObject("end_date")));
//        dealModel.setStartDate(setStartDateDealModel(dealParseObject.getParseObject("start_date")));
        DealUrl dealUrl = new DealUrl();
        ParseFile dealUrlFile = dealParseObject.getParseFile("deal_url");
        String url = dealUrlFile.getUrl();
        dealUrl.setUrl(url);
        dealModel.setDealUrl(dealUrl);
        dealModel.setDealPointer(dealParseObject);
        return dealModel;
    }

    private EndDate setEndDateDealModel(ParseObject endDateParseObject) {
        EndDate endDate = new EndDate();
        endDate.setIso(endDateParseObject.get("iso").toString());
        endDate.set_Type(endDateParseObject.get("__type").toString());
        return endDate;
    }

    private StartDate setStartDateDealModel(ParseObject endDateParseObject) {
        StartDate startDate = new StartDate();
        startDate.setIso(endDateParseObject.get("iso").toString());
        startDate.set_Type(endDateParseObject.get("__type").toString());
        return startDate;
    }

    HomeBranchDealsModel createHomeBranchDealsModel(Object object, Context context) {
        this.context = context;
        HomeBranchDealsModel homeBranchDealsModel = new HomeBranchDealsModel();
        ParseObject branchParseObject = (ParseObject) object;
        homeBranchDealsModel.setBranchName(branchParseObject.get("branch_name").toString());
        String address = branchParseObject.get("street_address").toString();
        address = address.replaceAll("[a-zA-Z]+", "");
        address = address.replaceAll("[,()]", "");
        String koreanAddress = "";
        String[] streetAddress = address.split(" ");
        for (String place : streetAddress) {
            if (place.length()>0) {
                if (!Character.isDigit(place.charAt(0)) || !Character.isDigit(place.charAt(place.length() - 1))) {
                    place = place.replaceAll("[-]", "");
                }
                    koreanAddress = koreanAddress.concat(place + " ");
            }
        }
        homeBranchDealsModel.setStreetAddress(koreanAddress);
        homeBranchDealsModel.setCity(branchParseObject.get("city").toString());
        homeBranchDealsModel.setPhone(branchParseObject.get("phone").toString());
        homeBranchDealsModel.setDistance(branchParseObject.getDouble("distance"));
        homeBranchDealsModel.setGeoPoint(branchParseObject.getParseGeoPoint("geo_point"));
        homeBranchDealsModel.setCountry(branchParseObject.get("country").toString());
        homeBranchDealsModel.setRating((branchParseObject.get("averageRating") != null) ? Integer.parseInt(branchParseObject.get("averageRating") + "") : 0);
        homeBranchDealsModel.setmRestaurantIdModel(createBranchRestaurantModel((ParseObject) branchParseObject.get("restaurant_id")));
        List<HashMap> deals = (List<HashMap>) branchParseObject.get("deals");
        homeBranchDealsModel.setObjectId(branchParseObject.getObjectId());
        homeBranchDealsModel.setBranchDealModel(createDealsBranchDealModel(deals));
        try {
            JSONObject jsonObject = branchParseObject.getJSONObject("operational");
            Gson gson = new Gson();
            Operational model = gson.fromJson(String.valueOf(jsonObject), Operational.class);
            homeBranchDealsModel.setOperational(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return homeBranchDealsModel;
    }

    public HomeBranchDealsModel createMyDealsBranchDealsModel(Object object, Context context) {
        HomeBranchDealsModel homeBranchDealsModel = new HomeBranchDealsModel();
        try {
            this.context = context;
            ParseObject branchParseObject = (ParseObject) object;
            homeBranchDealsModel.setBranchName(branchParseObject.get("branch_name").toString());
            String address = branchParseObject.get("street_address").toString();
            address = address.replaceAll("[a-zA-Z]+", "");
            address = address.replaceAll("[,()]", "");
            String koreanAddress = "";
            String[] streetAddress = address.split(" ");
            for (String place : streetAddress) {
                if (place.length()>0) {
                    if (!Character.isDigit(place.charAt(0)) || !Character.isDigit(place.charAt(place.length() - 1))) {
                        place = place.replaceAll("[-]", "");
                    }
                    koreanAddress = koreanAddress.concat(place + " ");
                }
            }
            homeBranchDealsModel.setStreetAddress(koreanAddress);
            homeBranchDealsModel.setCity(branchParseObject.get("city").toString());
            homeBranchDealsModel.setGeoPoint(branchParseObject.getParseGeoPoint("geo_point"));
            homeBranchDealsModel.setDistance(branchParseObject.getDouble("distance"));
            homeBranchDealsModel.setCountry(branchParseObject.get("country").toString());
            homeBranchDealsModel.setPhone(branchParseObject.get("phone").toString());
            homeBranchDealsModel.setmRestaurantIdModel(createBranchRestaurantModel((ParseObject) branchParseObject.get("restaurant_id")));
            List<ParseObject> deals = (List<ParseObject>) branchParseObject.get("deals");
            homeBranchDealsModel.setBranchDealModel(createMyDealsBranchDealModel(deals));
            homeBranchDealsModel.setObjectId(branchParseObject.getObjectId());

            JSONObject jsonObject = branchParseObject.getJSONObject("operational");
            Gson gson = new Gson();
            Operational model = gson.fromJson(String.valueOf(jsonObject), Operational.class);
            homeBranchDealsModel.setOperational(model);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Exception", "Error :- " + e.getMessage());
        }

        return homeBranchDealsModel;
    }

    public RestaurantIdModel createBranchRestaurantModel(ParseObject restaurantParseObject) {
        RestaurantIdModel restaurantIdModel = new RestaurantIdModel();
        restaurantIdModel.setRestaurantName(restaurantParseObject.get("restaurant_name").toString());
        restaurantIdModel.setObjectId(restaurantParseObject.getObjectId());
        try {
            restaurantIdModel.setDistance(restaurantParseObject.getDouble("distance"));
            Logo logo = new Logo();
            ParseFile dealUrlFile = restaurantParseObject.getParseFile("logo");
            if (null != dealUrlFile) {
                String url = dealUrlFile.getUrl();
                logo.setUrl(url);
                restaurantIdModel.setLogo(logo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        restaurantIdModel.setDescription((String) restaurantParseObject.get("description"));
        String address = (restaurantParseObject.get("street_address") != null) ? restaurantParseObject.get("street_address") + "" : "Not Specified";
        address = address.replaceAll("[a-zA-Z]+", "");
        address = address.replaceAll("[,()]", "");
        String koreanAddress = "";
        String[] streetAddress = address.split(" ");
        for (String place : streetAddress) {
            if (place.length()>0) {
                if (!Character.isDigit(place.charAt(0)) || !Character.isDigit(place.charAt(place.length() - 1))) {
                    place = place.replaceAll("[-]", "");
                }
                koreanAddress = koreanAddress.concat(place + " ");
            }
        }
        restaurantIdModel.setStreetAddress(koreanAddress);
        restaurantIdModel.setCity((restaurantParseObject.get("city") != null) ? restaurantParseObject.get("city") + "" : "Not Specified");
        Log.i("street---", restaurantParseObject.get("street_address") + "");
        restaurantIdModel.setAverageRating((restaurantParseObject.get("averageRating") != null) ? Long.parseLong(restaurantParseObject.get("averageRating") + "") : 0);
        restaurantIdModel.setCountry(restaurantParseObject.get("country").toString());
        restaurantIdModel.setAverageRating((long) restaurantParseObject.getInt("averageRating"));
        restaurantIdModel.setPointer(restaurantParseObject);
        return restaurantIdModel;
    }


    public RestaurantBranchDealsModel createRestaurantDeals(Object object, Context context) {
        this.context = context;
        RestaurantBranchDealsModel restaurantBranchDealsModel = null;
        if (object != null) {
            BranchDealModel branchDealModel = new BranchDealModel();
            restaurantBranchDealsModel = new RestaurantBranchDealsModel();
            //ParseObject tempObj=(ParseObject) object;
            //tempObj.get("restaurant_name");
            ParseObject restaurantObject = (ParseObject) object;
            restaurantBranchDealsModel.setRestaurantName(restaurantObject.get("restaurant_name").toString());
            //restaurant.setRestaurantPointer(restaurantObject);
            restaurantBranchDealsModel.setObjectId(restaurantObject.getObjectId());
            restaurantBranchDealsModel.setDistance(restaurantObject.get("distance").toString());
            long ss = (restaurantObject.get("averageRating") != null) ? Long.parseLong(restaurantObject.get("averageRating") + "") : 0;
            restaurantBranchDealsModel.setStreetAddress((restaurantObject.get("street_address") != null) ? restaurantObject.get("street_address") + "" : "Not Specified");
            restaurantBranchDealsModel.setCity((restaurantObject.get("city") != null) ? restaurantObject.get("city") + "" : "Not Specified");
            Log.i("street---", restaurantObject.get("street_address") + "");
            restaurantBranchDealsModel.setAverageRating((restaurantObject.get("averageRating") != null) ? Long.parseLong(restaurantObject.get("averageRating") + "") : 0);
            restaurantBranchDealsModel.setCountry(restaurantObject.get("country").toString());
            restaurantBranchDealsModel.setAverageRating((long) restaurantObject.getInt("averageRating"));
            List<ParseObject> branchDeals = (List<ParseObject>) restaurantObject.get("branches");
            restaurantBranchDealsModel.setBranchDealModels(createBranchDealsModel(branchDeals));

//            List<BranchModel> branchList = new ArrayList<>();
//            assert branchDeals != null;
//            for (ParseObject branchObj : branchDeals) {
//                BranchModel branch = new BranchModel();
//                branch.setObjectId(branchObj.getObjectId());
//                branch.setBranchName(branchObj.get("branch_name").toString());
//                branch.setStreetAddress(branchObj.get("street_address").toString());
//                branch.setCity(branchObj.get("city").toString());
//                branch.setCountry(branchObj.get("country").toString());
//                branchDealModel.setBranch(branch);
//
//                try {
//                    JSONObject jsonObject = branchObj.getJSONObject("operational");
//                    Gson gson = new Gson();
//                    Operational model = gson.fromJson(String.valueOf(jsonObject), Operational.class);
//                    branch.setOperational(model);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                List<HashMap> deals = (List<HashMap>) branchObj.get("deals");
//                List<DealModel> dealList = new ArrayList<>();
//
////                ParseObject Dealsobject=(ParseObject) deals;
//                for (HashMap object1 : deals) {
////                    Object ooo = (Object)dealObject;
////                    ParseObject pointer = new ParseObject(object1.toString());
//                    ParseObject deal1 = (ParseObject) object1.get("deal");
//                    ParseObject branch1 = (ParseObject) object1.get("branch");
//                    ParseObject pointer = new ParseObject("DealBranchRelations");
//                    pointer.put("deal", deal1);
//                    pointer.put("branch", branch1);
//                    pointer.put("objectId", object1.get("objectId"));
//                    DealModel deal = new DealModel();
//                    deal.setDealPointer(deal1);
//                    //Log.i("deal name:- ", dealsObject.get("deal_name").toString());
//                    // Log.i("groupDiscount",deal1.get("group_discount_rate").toString()+"a");
//                    deal.setDealName(deal1.getString("deal_name"));
//                    int discountRate = deal1.getInt("discount_rate");
//                    deal.setDiscountRate(Long.parseLong(String.valueOf(discountRate)));
//                    int groupDiscountRate = deal1.getInt("group_discount_rate");
//                    deal.setGroupDiscountRate(Long.parseLong(String.valueOf(groupDiscountRate)));
//                    int freeCouponDiscount = deal1.getInt("free_coupon_discount");
//                    deal.setFreeCouponDiscount((long) freeCouponDiscount);
//                    deal.setDealsLeft(Long.parseLong(String.valueOf(deal1.getInt("deals_left"))));
//                    deal.setType(deal1.getString("type"));
///*                deal.setStartDate(new Date(deal1.get("start_date").toString()));
//            deal.setEndDate(new Date(deal1.get("end_date").toString()));*/
//                    Log.i("Mini-----", Objects.requireNonNull(deal1.getString("min_person_required") != null ? deal1.getString("min_person_required") : "0"));
//                    deal.setMinPersonRequired(deal1.getString("min_person_required") != null ? deal1.getString("min_person_required") : "0");
//                    deal.setGroupDiscountRate(deal1.getLong("group_discount_rate"));
//                    DealUrl dealUrl = new DealUrl();
//                    ParseFile dealUrlFile = deal1.getParseFile("deal_url");
//                    Log.i("--DealUrl--", dealUrlFile.toString());
//                    Log.i("--Url--", dealUrlFile.getUrl());
//                    String url = dealUrlFile.getUrl();
//                    String[] urls = url.split(":3000");
//                    dealUrl.setUrl("http://35.162.72.179" + urls[1]);
//                    deal.setDealUrl(dealUrl);
//                    dealList.add(deal);
//                    branchDealModel.setDeal(deal);
//                    branchDealModel.setBranch(branch);
//                    branchDealModel.setObjectId(branchObj.getObjectId());
//                    branchDealModel.setBranchDealPointer(pointer);
////                    saveBranchDeal(new ParseObject(dealObject.toString()), context);
//
//
//                    /*JSONArray indexes = deal1.getJSONArray("indexes")*/
//           /* int[] ints = new int[indexes.length()];
//            for(int i = 0; i < indexes.length() - 1 ; i++){
//                try {
//                    ints[i] = indexes.getInt(i);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            deal.setIndexes(ints);*/
//
//                }
//                branch.setBranchDealModel(dealList);
//
//                branchList.add(branch);
//            }
//            restaurantBranchDealsModel.setBranches(branchList);


            // branchDealModel.setRestaurants(restaurant);;
            //restaurant.setBranchDealModel(dealList);
            //restaurantDeals.setRestaurant(restaurant);

        }
        return restaurantBranchDealsModel;

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
