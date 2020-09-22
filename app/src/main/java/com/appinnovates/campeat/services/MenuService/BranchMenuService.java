package com.appinnovates.campeat.services.MenuService;

import android.view.View;

import com.appinnovates.campeat.model.BranchMenuModel;
import com.appinnovates.campeat.model.BranchMenuType;
import com.appinnovates.campeat.model.MenuTypeModel;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.views.activities.ItemDetailActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by reetu on 26/6/18.
 */

public class BranchMenuService {
    private static BranchMenuService branchMenuService = null;
    private BranchMenuServiceInterface delegate = null;
    public static BranchMenuService getInstance() {
        if (branchMenuService == null) {
            branchMenuService = new BranchMenuService();
        }
        return branchMenuService;
    }

    public void setDelegate(BranchMenuServiceInterface delegate) {
        this.delegate = delegate;
    }

/*    public void getMenuType() {
        final MenuStore menuStore = new MenuStore();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MenuType");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        List<MenuTypeModel> menuTypeModelList = menuStore.saveMenuData(objects);
                        delegate.onMenuTypeSuccess(menuTypeModelList);
                    } else {
                        delegate.onNoBranchMenu();
                    }
                }
            }
        });
    }*/
}
