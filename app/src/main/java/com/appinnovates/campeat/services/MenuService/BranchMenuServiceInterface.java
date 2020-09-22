package com.appinnovates.campeat.services.MenuService;

import com.appinnovates.campeat.model.BranchMenuModel;
import com.appinnovates.campeat.model.BranchMenuType;
import com.appinnovates.campeat.model.MenuTypeModel;

import java.util.List;

/**
 * Created by reetu on 26/6/18.
 */

public interface BranchMenuServiceInterface {
    void onBranchMenuSuccess(List<BranchMenuModel> branchMenuList, List<BranchMenuType> branchMenuTypeList);
    void onMenuTypeSuccess(List<MenuTypeModel> menuTypeModels);
    void onNoBranchMenu();
    void onBranchMenuFailure(String message);
}
