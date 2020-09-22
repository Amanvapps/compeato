package com.appinnovates.campeat.services.DealService;

import com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel;

import java.util.List;

public interface AllDealsServiceInterface {
    void onAllDealsSuccess(List<BranchDealModel> branchDealModels, boolean isDiscounted);
    void onAllDealsFailure(String message);
    void onNoDealsAvailable(String message);
    void onNoInternetAvailable();
}
