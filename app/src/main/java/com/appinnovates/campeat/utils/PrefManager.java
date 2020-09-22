package com.appinnovates.campeat.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lincoln on 05/05/16.
 */
public class PrefManager {
    // Context
    private static int dealsCount;
    public static List<HomeBranchDealsModel> deals;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    private static boolean isFirstload;
    private static boolean isScan;
    private static boolean isFirstRequest;

    // Shared preferences file name
    private static final String PREF_NAME = "welcome_screen";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public static void setIsFirstTime(boolean isFirst){
        isFirstload=isFirst;
    }

    public static boolean isFirstTime(){
        return isFirstload;
    }

    public static void setScan(boolean isScanner){
        isScan=isScanner;
    }

    public static boolean isScan(){
        return isScan;
    }
    public static void setIsFirstrequest(boolean isFirst){
        isFirstRequest=isFirst;
    }

    public static boolean isFirstRequest(){
        return isFirstRequest;
    }

    public static void putDeals(List<HomeBranchDealsModel> dealList){
        deals=new ArrayList<>();
        deals.addAll(dealList);
    }

    public static List<HomeBranchDealsModel> getAllDeals(){
        return deals;
    }

    public static int getDealsCount() {
        return dealsCount;
    }

    public static void setDealsCount(int deals) {
        dealsCount = deals;
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
