package com.appinnovates.campeat.services.SubscriptionDetailService;

import com.appinnovates.campeat.model.MenuSection;
import com.appinnovates.campeat.model.ReviewModel;
import com.appinnovates.campeat.model.getAllDeal.Deal;

import java.util.List;

/**
 * Created by reetu on 26/6/18.
 */

public interface SubscriptionDetailServiceInterface {
    void onMenusSuccess(List<MenuSection> menuSections);
    void onNoMenus();
    void onMenusFailure(String message);
    void onReviewsSuccess(List<ReviewModel> reviewModelList);
    void onNoReviews();
    void onReviewsFailure(String message);
    void onDealsSuccess(List<Deal> dealsModelList);
    void onNoDeals();
    void onDealsFailure(String message);
    void onNoBranchesAvailable(String message);
    void onBranchesFailure(String message);
}
