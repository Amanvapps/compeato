package com.appinnovates.campeat.services.AdService;

import com.appinnovates.campeat.model.getAllDealsModel.BranchModel;
import com.parse.ParseObject;

public class TADPEntry {
    public String id;
    public int points;
    public ParseObject object;
    public Ad ad;
    public String createdAt;
    public BranchModel branchModel;
    public boolean isTad;

    public TADPEntry(String id, double points, Ad ad, String createdAt, BranchModel branchModel, boolean isTad) {
        this.id = id;
        this.points = (int) points;
        this.ad = ad;
        this.createdAt = createdAt;
        this.branchModel = branchModel;
        this.isTad = isTad;
    }
}
