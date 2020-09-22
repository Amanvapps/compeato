package com.appinnovates.campeat.services.AdService;

public class AdGlobal {
    private static final AdGlobal ourInstance = new AdGlobal();

    public static AdGlobal instance() {
        return ourInstance;
    }

    private AdGlobal() {}

    private Ad ad;

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }
}
