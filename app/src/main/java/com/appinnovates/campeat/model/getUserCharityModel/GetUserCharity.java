
package com.appinnovates.campeat.model.getUserCharityModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GetUserCharity {

    @SerializedName("couponModelResult")
    private List<Result> mResult;

    public List<Result> getResult() {
        return mResult;
    }

    public void setResult(List<Result> result) {
        mResult = result;
    }

}
