package com.appinnovates.campeat.services.BranchService;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class BranchService {
    private static BranchService instance = null;
    private BranchServiceInterface delegate;

    public static BranchService getInstance() {
        if (instance == null){
            instance = new BranchService();
        }
        return instance;
    }

    public void setDelegate(BranchServiceInterface delegate){
        this.delegate = delegate;
    }

    public void requestBranches(List<ParseObject> restaurantPointers){
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("BranchDealsModel");
        parseQuery.include("restaurant_id");
        parseQuery.whereContainedIn("restaurant_id",restaurantPointers);
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    if(objects.size()>0){
                        delegate.onBranchesSuccess(objects);
                    }else{
                        delegate.onNoBranchAvailable("No branches available");
                    }
                }else{
                    delegate.onBranchFailure(e.getMessage());
                }
            }
        });

    }

    public void branches(String branchId, final BranchDelegate delegate){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("BranchDealsModel");
        query.whereEqualTo("objectId",branchId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    if (objects.size() > 0){
                        delegate.onSuccess(objects.get(0));
                    }else{
                        delegate.onFailure("No branches available");
                    }
                }else{
                    delegate.onFailure(e.getMessage());
                }

            }
        });
    }

}
