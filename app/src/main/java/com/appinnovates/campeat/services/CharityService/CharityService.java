package com.appinnovates.campeat.services.CharityService;


import android.content.Context;

import androidx.annotation.NonNull;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.listeners.ActionListener;
import com.appinnovates.campeat.model.CharityModelData;
import com.appinnovates.campeat.model.charity.getCharities.Charity;
import com.appinnovates.campeat.model.charity.getCharities.Result;
import com.appinnovates.campeat.services.CloudNetwork.ApiClient;
import com.appinnovates.campeat.services.CloudNetwork.ApiInterface;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharityService {

    private CharityInterface delegate;

    public void setDelegateAndContext(CharityInterface delegate) {
        this.delegate = delegate;
        CharityStore charityStore = new CharityStore();
    }

    public void getCharityList(Context context) {
        Map<String, String> map = new HashMap<>();
        map.put("X-Parse-Application-Id", context.getResources().getString(R.string.parse_app_id));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        String userId = String.valueOf(ParseUser.getCurrentUser().getObjectId());
        Call<Charity> call = apiInterface.getCharity(map);
        call.enqueue(new Callback<Charity>() {
            @Override
            public void onResponse(@NonNull Call<Charity> call, @NonNull Response<Charity> response) {
                if (response.body() != null) {
                    List<Result> submitResult =response.body().getResults();
                    delegate.onSuccess(submitResult);
                } else {
                    delegate.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Charity> call, @NonNull Throwable t) {
                delegate.onFailure(t.getMessage());
            }
        });
    }

    public void saveMyCharity(Context context, ArrayList<String> selectedCharityList, final ActionListener actionListener) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("X-Parse-Application-Id", context.getResources().getString(R.string.parse_app_id));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        String userId = String.valueOf(ParseUser.getCurrentUser().getObjectId());
        if (selectedCharityList.size() == 1) {
            selectedCharityList.add(" ");
        }
        Call<CharityModelData> call = apiInterface.saveCharity(userId, selectedCharityList, headerMap);
        call.enqueue(new Callback<CharityModelData>() {
            @Override
            public void onResponse(@NonNull Call<CharityModelData> call, @NonNull Response<CharityModelData> response) {
                if (response.body() != null) {
                    actionListener.onSuccess();
                } else {
                    actionListener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CharityModelData> call, @NonNull Throwable t) {
                actionListener.onFailure(t.getMessage());

            }
        });
    }
}
