package com.appinnovates.campeat.utils;

import com.appinnovates.campeat.model.BookingDealModel;
import com.appinnovates.campeat.model.DealsModel;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;

import java.util.ArrayList;
import java.util.List;

public class FilterUtil {
    private List<DealsModel> list = new ArrayList<>();
    private List<BookingDealModel> modelList = new ArrayList<>();
    private List<HomeBranchDealsModel> branchDealModelList  = new ArrayList<>();
    private static  FilterUtil filterUtil;

    public static FilterUtil getInstance(){
        if (filterUtil == null){
            filterUtil = new FilterUtil();
        }
        return filterUtil;
    }

    public List<HomeBranchDealsModel> getSearchedDeals(List<HomeBranchDealsModel> dealsModelList, CharSequence s) {
       /* branchDealModelList.clear();
        for(BranchDealModel branchDealModel : dealsModelList) {
            if (branchDealModel.getBranchDealModels().getRestaurantModel() != null) {
                if (branchDealModel.getBranchDealModels().getRestaurantModel().getRestaurantName().toLowerCase()
                        .contains(s.toString().toLowerCase())) {
                    branchDealModelList.add(branchDealModel);
                }
            }
        }*/
        return branchDealModelList;
    }

    public  List<HomeBranchDealsModel>  getFilterBranchDeal(boolean isDisCounted, String disCountType, List<HomeBranchDealsModel> branchDealModels) {
        branchDealModelList.clear();
        /*DealModel dealsModel = branchDealModels.get(0).getBranchDealModel();
        if (dealsModel != null) {
            if (isDisCounted) {
                if (dealsModel.getType() != null && dealsModel.getType().equalsIgnoreCase(disCountType)) {
                    branchDealModelList.add(branchDealModels.get(0));
                }
            } else {
                branchDealModelList.add(branchDealModels.get(0));
            }
        }*/
        return branchDealModelList;
    }
}
