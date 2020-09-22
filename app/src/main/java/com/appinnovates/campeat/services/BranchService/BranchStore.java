package com.appinnovates.campeat.services.BranchService;

import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.model.getAllDealsModel.Operational;
import com.appinnovates.campeat.model.getAllDealsModel.RestaurantIdModel;
import com.google.gson.Gson;
import com.parse.ParseObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BranchStore {

    public List<HomeBranchDealsModel> saveBranchData(List<ParseObject> parseObjectList) {
        List<HomeBranchDealsModel> branchModelList = new ArrayList<>();
        HomeBranchDealsModel branchModel = null;
        if (parseObjectList != null && parseObjectList.size() > 0) {
            for (ParseObject parseObject : parseObjectList) {
                branchModel = new HomeBranchDealsModel();
                branchModel.setBranchPointer(parseObject);
                branchModel.setObjectId(parseObject.getObjectId());
                branchModel.setBranchName((String) parseObject.get("branch_name"));
                branchModel.setStreetAddress((String) parseObject.get("street_address"));
                branchModel.setCity((String) parseObject.get("city"));
                branchModel.setCountry((String) parseObject.get("country"));
                /*branchModel.set((String) parseObject.get("state"));
                branchModel.setZipcode((String) parseObject.get("zipcode"));*/
                int rating = 0;
                try {
                    rating = (int) parseObject.get("averageRating");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                branchModel.setRating(rating);
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
                RestaurantIdModel restaurantModel = saveRestaurantData(restaurantParseObject);
                branchModel.setmRestaurantIdModel(restaurantModel);
                branchModelList.add(branchModel);
            }
        }
        return branchModelList;
    }

    private RestaurantIdModel saveRestaurantData(ParseObject parseObject) {
        RestaurantIdModel restaurantModel = null;
        if (parseObject != null) {
            restaurantModel = new RestaurantIdModel();
            restaurantModel.setPointer(parseObject);
            restaurantModel.setObjectId(parseObject.getObjectId());
            restaurantModel.setRestaurantName((String) parseObject.get("restaurant_name"));
/*            restaurantModel.setManager_name((String) parseObject.get("manager_name"));
            restaurantModel.setManager_surname((String) parseObject.get("manager_surname"));*/
            restaurantModel.setStreetAddress((String) parseObject.get("street_address"));
            restaurantModel.setCity((String) parseObject.get("city"));
            restaurantModel.setCountry((String) parseObject.get("country"));
/*            restaurantModel.setState((String) parseObject.get("state"));
            restaurantModel.setZipcode((String) parseObject.get("zipcode"));
            restaurantModel.setLogo( parseObject.getParseFile("logo"));*/
            restaurantModel.setDescription((String) parseObject.get("description"));
            restaurantModel.setAverageRating((long)parseObject.getInt("averageRating"));
            try {
                String phoneNo = (String) parseObject.get("phone");
                restaurantModel.setPhone(phoneNo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //restaurantModel.setEmail((String) parseObject.get("email"));
            restaurantModel.set_Type((String) parseObject.get("restaurant_type"));
            String foundedDate = (String) parseObject.get("founded_date");
            if (foundedDate != null) {
                //restaurantModel.setFounded_date(foundedDate);
            }
        }
        return restaurantModel;
    }
}
