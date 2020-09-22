package com.appinnovates.campeat.services.AdService;

import com.appinnovates.campeat.model.getAllDealsModel.BranchModel;
import com.appinnovates.campeat.services.DealService.DealStore;
import com.appinnovates.campeat.utils.DateFormatUtil;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PointService {


    public void points(final PointsDelegate delegate){
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Ads");
        parseQuery.include("type_id");
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("TADPEntries");
        query.whereEqualTo("user_id",ParseUser.getCurrentUser());
        query.include("ad.type_id");
        query.include("ad.status");
        query.include("branch.restaurant_id");
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                try {
                    if (objects != null && objects.size() > 0) {
                        ArrayList<TADPEntry> tadpEntries = new ArrayList<>();
                        ArrayList<TADPEntry> tadEntries = new ArrayList<>();
                        int totalTADPPoints = 0,totalTADPoints = 0;
                        for (int i = 0 ; i<objects.size() ; i++) {
                            TADPEntry entry = fetchEntry(objects.get(i));

                            if (entry.isTad){
                                tadEntries.add(entry);
                                totalTADPoints += entry.points;
                            }else{
                                totalTADPPoints += entry.points;
                                tadpEntries.add(entry);
                            }
                        }
                        delegate.onSuccess(tadpEntries,tadEntries,totalTADPPoints,totalTADPoints);
                    } else
                        delegate.onFailure(e.getMessage());
                }catch (Exception exception){
                     delegate.onFailure("");
                }

            }
        });
    }



    public void addPoints(final double points, final ParseObject client,ParseObject branchPointer, final ParseObject ad,boolean isTad , final AddPointsDelegate delegate){
        ParseObject object = new ParseObject("TADPEntries");
        object.put("user_id",ParseUser.getCurrentUser());
        object.put("points",points);
        object.put("isTAD",isTad);
        if (branchPointer != null){
            object.put("branch",branchPointer);
        }
        if (ad != null){
            object.put("ad",ad);
        }
        object.saveInBackground(e -> {
            if (e == null){
                if (ad != null){
                    addUserAdSubmission(ad,points);
                    addResponses(ad);
                }
                delegate.success();
            }else{
                delegate.failure(e.getMessage());
            }
        });
    }


    private void addUserAdSubmission(ParseObject ad,double points){
        ParseObject object = new ParseObject("UserAdSubmissions");
        object.put("ad_id",ad);
        object.put("user_id",ParseUser.getCurrentUser());
        object.put("points",points);
        object.saveInBackground();
    }

    private void addResponses(ParseObject ad){
        int responses = ad.getInt("user_responses");
        ad.put("user_responses",responses+1);
        ad.saveInBackground();
    }


    private TADPEntry fetchEntry(ParseObject object){
        ParseObject obj = object.getParseObject("ad");
        ParseObject branch = object.getParseObject("branch");
        Ad ad = null;
        if (obj != null){
            ad = AdService.instance.fetchAd(obj);
        }
        BranchModel branchModel = new DealStore().saveBranchData(branch);
        return new TADPEntry(object.getObjectId()
                ,object.getDouble("points")
                ,ad
                ,DateFormatUtil.getDateString(object.getCreatedAt())
                ,branchModel
                ,object.getBoolean("isTAD"));
    }

    private Settings fetchSetting(ParseObject object){
        return new Settings(object.getString("key"),object.getString("value"));
    }

}
