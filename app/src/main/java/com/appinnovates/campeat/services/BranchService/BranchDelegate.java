package com.appinnovates.campeat.services.BranchService;

import com.appinnovates.campeat.model.BranchModel;
import com.parse.ParseObject;

public interface BranchDelegate {
    void onSuccess(ParseObject model);
    void onFailure(String message);
}
