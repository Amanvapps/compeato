package com.appinnovates.campeat.utils;

import android.app.Activity;
import android.content.Intent;

import com.appinnovates.campeat.views.activities.TrackingActivity;
import com.github.zawadz88.activitychooser.MaterialActivityChooserBuilder;



public class ShareUtil {

    public static void shareDeal(Activity activity, String restaurantName, String discount
            , String dealId, String restaurantId) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = restaurantName + " offer "+ discount + " %" +
                " discount ";
        shareBody = shareBody + "\n\nJoin Campeat\n\n";
        shareBody = shareBody + "https://play.google.com/store/apps/details?id=com.appinnovates.campeat&referrer=deal,"+dealId;
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        UserPreferences.getInstance().save(UserPreferences.RestaurantId, restaurantId);

        new MaterialActivityChooserBuilder(activity)
                .withIntent(sharingIntent)
                .withActivity(TrackingActivity.class)
                .show();
    }

    public static void shareRestaurantDetail(Activity activity, String restaurantName, String restaurantId) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = restaurantName + " offers ";
        shareBody = shareBody + "\n\nJoin Campeat\n\n";
        shareBody = shareBody + "https://play.google.com/store/apps/details?id=com.appinnovates.campeat&referrer=restaurant,"+restaurantId;
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        UserPreferences.getInstance().save(UserPreferences.RestaurantId, restaurantId);

        new MaterialActivityChooserBuilder(activity)
                .withIntent(sharingIntent)
                .withActivity(TrackingActivity.class)
                .show();

    }

    public static void contribution(Activity activity) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Check it out, Campeat donates for every meal we eat\n\n";
        shareBody = shareBody + "https://play.google.com/store/apps/details?id=com.appinnovates.campeat";
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        activity.startActivity(Intent.createChooser(sharingIntent, "Share using"));

    }

    public static void shareApplication(Activity activity) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Hey mate, join me on Campeat, Korea's best food deals! \n\n";
        shareBody = shareBody + "https://play.google.com/store/apps/details?id=com.appinnovates.campeat";
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        activity.startActivity(Intent.createChooser(sharingIntent, "Share using"));

    }
}
