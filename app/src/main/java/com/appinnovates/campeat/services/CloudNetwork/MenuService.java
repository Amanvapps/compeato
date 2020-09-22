package com.appinnovates.campeat.services.CloudNetwork;

import android.content.Context;

import androidx.annotation.NonNull;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.MenuResult;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuService {
    private static MenuService instance;
    private MenuServiceViewInterface delegate;
    private Context context;

    public static MenuService getInstance() {
        if (instance == null) {
            instance = new MenuService();
        }
        return instance;
    }

    public void setDelegateAndContext(MenuServiceViewInterface delegate, Context context) {
        this.delegate = delegate;
        this.context = context;
    }

    public void requestMenus(String id,String branchId, String type) {
        Map<String, String> map = new HashMap<>();
        map.put("X-Parse-Application-Id", context.getResources().getString(R.string.parse_app_id));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MenuResult> call = apiInterface.getMenus(id,branchId, map);
        call.enqueue(new Callback<MenuResult>() {
            @Override
            public void onResponse(@NonNull Call<MenuResult> call, @NonNull Response<MenuResult> response) {
                MenuResult menuResult = response.body();
                if (menuResult != null) {
                    delegate.onMenusSuccess(menuResult);
                } else {
                    delegate.onMenusFailure(context.getResources().getString(R.string.error_menu_message));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MenuResult> call, @NonNull Throwable t) {
                delegate.onMenusFailure(t.getMessage());
            }
        });
    }

    public void requestMenusofCoupons(String couponId) {
        Map<String, String> map = new HashMap<>();
        map.put("X-Parse-Application-Id", context.getResources().getString(R.string.parse_app_id));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MenuResult> call = apiInterface.getCouponsMenus(couponId, map);
        call.enqueue(new Callback<MenuResult>() {
            @Override
            public void onResponse(@NonNull Call<MenuResult> call, @NonNull Response<MenuResult> response) {
                MenuResult menuResult = response.body();
                if (menuResult != null) {
                    delegate.onMenusSuccess(menuResult);
                } else {
                    delegate.onMenusFailure(context.getResources().getString(R.string.error_menu_message));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MenuResult> call, @NonNull Throwable t) {
                delegate.onMenusFailure(t.getMessage());
            }
        });
    }

/*    public void requestMenusByBranch(String branchId, String type) {
        Map<String, String> map = new HashMap<>();
        map.put("X-Parse-Application-Id", context.getResources().getString(R.string.parse_app_id));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MenuResult> call = apiInterface.getMenus(branchId, map);
        call.enqueue(new Callback<MenuResult>() {
            @Override
            public void onResponse(@NonNull Call<MenuResult> call, @NonNull Response<MenuResult> response) {
                MenuResult menuResult = response.body();
                if (menuResult != null) {
                    delegate.onMenusSuccess(menuResult);
                } else {
                    delegate.onMenusFailure(context.getResources().getString(R.string.error_menu_message));
                }
            }

            @Override
            public void onFailure(Call<MenuResult> call, Throwable t) {
                delegate.onMenusFailure(t.getMessage());
            }
        });
    }*/
}
