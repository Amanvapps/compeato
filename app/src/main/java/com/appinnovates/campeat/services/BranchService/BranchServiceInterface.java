package com.appinnovates.campeat.services.BranchService;

import com.parse.ParseObject;

import java.util.List;

public interface BranchServiceInterface {
    void onBranchesSuccess(List<ParseObject> branches);
    void onNoBranchAvailable(String message);
    void onBranchFailure(String message);
}
