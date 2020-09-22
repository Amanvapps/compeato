package com.appinnovates.campeat.services.ReviewService;

import android.content.Context;

import com.appinnovates.campeat.model.ReviewModel;
import com.appinnovates.campeat.model.UserModel;
import com.appinnovates.campeat.model.getAllDealsModel.BranchModel;
import com.appinnovates.campeat.model.getAllDealsModel.Operational;
import com.appinnovates.campeat.services.DealService.DealStore;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.DateFormatUtil;
import com.google.gson.Gson;
import com.parse.ParseObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class ReviewStore {
    List<ReviewModel> saveData(List<ParseObject> objects, Context context, String string) {
        DealStore dealStore = new DealStore();
        List<ReviewModel> reviewModelList = new ArrayList<>();
        try {
            if (objects != null && objects.size() > 0) {
                for (ParseObject parseObject : objects) {
                    ReviewModel reviewModel = new ReviewModel();
                    reviewModel.setId(parseObject.getObjectId());
                    ParseObject branchId = (ParseObject) parseObject.get("branch_id");
                    reviewModel.setBranch_id(String.valueOf(branchId));
                    ParseObject customerId = (ParseObject) parseObject.get("customer_id");
                    reviewModel.setCustomer_id(String.valueOf(customerId));
                    ParseObject dealId = (ParseObject) parseObject.get("deal_id");
                    reviewModel.setDeal_id(String.valueOf(dealId));
                    reviewModel.setReview((String) parseObject.get("review"));
                    ParseObject userParseObject = parseObject.getParseObject("customer_id");
                    UserModel userModel = saveUserData(userParseObject);
                    reviewModel.setUserModel(userModel);
                    if (!string.equalsIgnoreCase(Constant.Restaurant)) {
                        ParseObject branchParseObject = parseObject.getParseObject("branch_id");
                        reviewModel.setBranch_Model(saveBranchData(branchParseObject));
                    }
/*                    ParseObject dealParseObject = parseObject.getParseObject("deal_id");
                    reviewModel.setDeal_Model(dealStore.saveDeal(dealParseObject, context));*/

                    try {
                        int reviewPoint = (int) parseObject.get("review_point");
                        reviewModel.setReview_point(reviewPoint);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Date reviewDate = (Date) parseObject.get("review_date");
                    if (reviewDate != null) {
                        reviewModel.setReview_date(DateFormatUtil.getDateInString(reviewDate));
                    }
                    reviewModelList.add(reviewModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviewModelList;
    }

    List<ReviewModel> setReviews(List<ParseObject> objects, Context context) {
        DealStore dealStore = new DealStore();
        List<ReviewModel> reviewModelList = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            for (ParseObject parseObject : objects) {
                ReviewModel reviewModel = new ReviewModel();
                reviewModel.setId(parseObject.getObjectId());
                ParseObject branchId = (ParseObject) parseObject.get("branch_id");
                reviewModel.setBranch_id(String.valueOf(branchId));
                ParseObject customerId = (ParseObject) parseObject.get("customer_id");
                reviewModel.setCustomer_id(String.valueOf(customerId));
                ParseObject dealId = (ParseObject) parseObject.get("deal_id");
                reviewModel.setDeal_id(String.valueOf(dealId));
                reviewModel.setReview((String) parseObject.get("review"));
                ParseObject userParseObject = parseObject.getParseObject("customer_id");
                UserModel userModel = saveUserData(userParseObject);
                reviewModel.setUserModel(userModel);
                ParseObject branchParseObject = parseObject.getParseObject("branch_id");
                reviewModel.setBranch_Model(saveBranchData(branchParseObject));
                ParseObject dealParseObject = parseObject.getParseObject("deal_id");
                reviewModel.setDeal_Model(dealStore.saveDeal(dealParseObject, context));

                try {
                    int reviewPoint = (int) parseObject.get("review_point");
                    reviewModel.setReview_point(reviewPoint);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Date reviewDate = (Date) parseObject.get("review_date");
                if (reviewDate != null) {
                    reviewModel.setReview_date(DateFormatUtil.getDateInString(reviewDate));
                }
                reviewModelList.add(reviewModel);
            }
        }
        return reviewModelList;
    }


    private UserModel saveUserData(ParseObject userParseObject) {
        UserModel userModel = null;
        try {
            if (userParseObject != null) {
                userModel = new UserModel();
                userModel.setId(userParseObject.getObjectId());
                userModel.setName(userParseObject.getString("name") != null ? userParseObject.getString("name") : "Not Specified");
                userModel.setUserName(userParseObject.get("username") != null ? userParseObject.get("username").toString() : "Not Specified");
                userModel.setEmail(userParseObject.get("email") != null ? userParseObject.get("email").toString() : "Not Specified");
                userModel.setArea(userParseObject.getString("area"));
                userModel.setCity(userParseObject.getString("city"));
                userModel.setSurname(userParseObject.getString("surname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userModel;
    }

    private BranchModel saveBranchData(ParseObject parseObject) {
        BranchModel branchModel = null;
        if (parseObject != null) {
            branchModel = new BranchModel();
            branchModel.setBranchPointer(parseObject);
            branchModel.setObjectId(parseObject.getObjectId());
            branchModel.setBranchName((String) parseObject.get("branch_name"));
            branchModel.setStreetAddress((String) parseObject.get("street_address"));
            branchModel.setCity((String) parseObject.get("city"));
            branchModel.setCountry((String) parseObject.get("country"));
            branchModel.setBranchPointer(parseObject);
/*            branchModel.setState((String) parseObject.get("state"));
            branchModel.setZipcode((String) parseObject.get("zipcode"));*/
            int rating = 0;
            try {
                rating = (int) parseObject.get("averageRating");
            } catch (Exception e) {
                e.printStackTrace();
            }
            branchModel.setAverageRating((long) rating);
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
        }
        return branchModel;
    }
}
