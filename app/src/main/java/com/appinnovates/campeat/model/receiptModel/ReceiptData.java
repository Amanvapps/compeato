
package com.appinnovates.campeat.model.receiptModel;

import java.util.List;
import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class ReceiptData {

    @Expose
    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}
