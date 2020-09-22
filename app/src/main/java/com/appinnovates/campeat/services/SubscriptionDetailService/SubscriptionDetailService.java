package com.appinnovates.campeat.services.SubscriptionDetailService;

import android.content.Context;

import com.appinnovates.campeat.model.MenuResult;
import com.appinnovates.campeat.model.ReviewModel;
import com.appinnovates.campeat.services.BranchService.BranchService;
import com.appinnovates.campeat.services.BranchService.BranchServiceInterface;
import com.appinnovates.campeat.services.CloudNetwork.MenuService;
import com.appinnovates.campeat.services.CloudNetwork.MenuServiceViewInterface;
import com.appinnovates.campeat.services.ReviewService.ReviewService;
import com.appinnovates.campeat.services.ReviewService.ReviewServiceInterface;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;

/**
 * Created by reetu on 26/6/18.
 */

public class SubscriptionDetailService implements ReviewServiceInterface, MenuServiceViewInterface, BranchServiceInterface {
    private static SubscriptionDetailService subscriptionDetailService = null;
    private SubscriptionDetailServiceInterface delegate;
    private ReviewService reviewService;
    private BranchService branchService;
    private Context context;
    private List<ParseObject> objects = new ArrayList<>();
    private MenuService menuService;


    public static SubscriptionDetailService getInstance(){
        if (subscriptionDetailService == null){
            subscriptionDetailService = new SubscriptionDetailService();
        }
        return subscriptionDetailService;
    }

    public void setDelegateAndContext(SubscriptionDetailServiceInterface delegate,Context context){
        this.delegate = delegate;
        this.context = context;
    }

    public void requestRestaurantData(ParseObject restaurantPointer){
        objects.clear();
        objects.add(restaurantPointer);
        reviewService = ReviewService.getInstance();
        reviewService.setDelegate(this,context);

        menuService = MenuService.getInstance();
        menuService.setDelegateAndContext(this, getApplicationContext());

        branchService = BranchService.getInstance();
        branchService.setDelegate(this);
        branchService.requestBranches(objects);

    }

    //Review response
    @Override
    public void onReviewsSuccess(List<ReviewModel> reviewList) {
        delegate.onReviewsSuccess(reviewList);
    }

    @Override
    public void onNoReviews() {
        delegate.onNoReviews();
    }

    @Override
    public void onReviewsFailure(String message) {
        delegate.onReviewsFailure(message);
    }

    @Override
    public void onBranchesSuccess(List<ParseObject> branches) {
        reviewService.getReviews(context, branches);
//        menuService.requestMenus(branches.get(0).getObjectId(), "all");
    }

    @Override
    public void onNoBranchAvailable(String message) {
        delegate.onNoBranchesAvailable(message);
    }

    @Override
    public void onBranchFailure(String message) {
        delegate.onBranchesFailure(message);
    }

    @Override
    public void onMenusSuccess(MenuResult menuResult) {
        delegate.onMenusSuccess(menuResult.getResult());
    }

    @Override
    public void onMenusFailure(String message) {

    }
}
