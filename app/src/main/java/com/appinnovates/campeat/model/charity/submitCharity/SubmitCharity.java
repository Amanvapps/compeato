
package com.appinnovates.campeat.model.charity.submitCharity;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class SubmitCharity {

    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
