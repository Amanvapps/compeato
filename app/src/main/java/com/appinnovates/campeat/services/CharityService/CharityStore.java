package com.appinnovates.campeat.services.CharityService;

import com.appinnovates.campeat.model.CharityModel;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class CharityStore {
    public List<CharityModel> saveCharity(List<ParseObject> objects) {
        List<CharityModel> charityModels = new ArrayList<>();
        if (objects != null && objects.size() > 0) {
            for (ParseObject parseObject : objects) {
                CharityModel model = new CharityModel();
                model.setObjectId(parseObject.getObjectId());
                model.setCharityName(parseObject.getString("charity_name"));
                charityModels.add(model);
            }
        }
        return charityModels;
    }
}
