package com.appinnovates.campeat.utils;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticUtil {

//    public static void setAnalyticEvent(Context context, String id, String eventName,String event) {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, id);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, eventName);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, eventName);
//        bundle.putBoolean(Constant.IS_FROM_SEARCH,false);
////        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//        mFirebaseAnalytics.logEvent(event,bundle);
//    }
//    public static void setLoginAnalyticEvent(Context context, String id, String eventName) {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, id);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, eventName);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, eventName);
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
//
//    }
//    public static void setSignUpAnalyticEvent(Context context, String id, String eventName) {
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, id);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, eventName);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, eventName);
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
//    }

    public static void setSaleEvent(Context context, String value,String restaurant_id){
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("value", value);
        params.putString("restaurant_id",restaurant_id);
        mFirebaseAnalytics.logEvent("sale", params);
    }
    public static void setDealViewEvent(Context context,String restaurant_id){
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("restaurant_id",restaurant_id);
        mFirebaseAnalytics.logEvent("deal_views", params);
    }
    public static void setCommentsEvent(Context context,String restaurant_id){
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("restaurant_id",restaurant_id);
        mFirebaseAnalytics.logEvent("comments", params);
    }
    public static void setSharesEvent(Context context,String restaurant_id){
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("restaurant_id",restaurant_id);
        mFirebaseAnalytics.logEvent("shares", params);
    }
    public static void setUsersVistedEvent(Context context,String restaurant_id){
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("restaurant_id",restaurant_id);
        mFirebaseAnalytics.logEvent("user_visted", params);
    }
    public static void setTopKeywordEvent(Context context,String restaurant_id,String word){
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString("word",word);
        params.putString("restaurant_id",restaurant_id);
        mFirebaseAnalytics.logEvent("keywords", params);
    }

}
