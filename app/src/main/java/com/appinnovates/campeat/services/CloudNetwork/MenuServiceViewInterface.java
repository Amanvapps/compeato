package com.appinnovates.campeat.services.CloudNetwork;

import com.appinnovates.campeat.model.MenuResult;

public interface MenuServiceViewInterface {
    void onMenusSuccess(MenuResult menuResult);
    void onMenusFailure(String message);
}
