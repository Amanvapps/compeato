package com.appinnovates.campeat.views.activities;

import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.appinnovates.campeat.utils.UserPreferences;
import com.github.zawadz88.activitychooser.MaterialActivityChooserActivity;
import com.parse.ParseCloud;

import java.util.HashMap;
import java.util.Map;

public class TrackingActivity extends MaterialActivityChooserActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityClicked(ResolveInfo activity) {
        String restaurantId = UserPreferences.getInstance().getStrings(UserPreferences.RestaurantId);
        String service = "others";
        String packageName = activity.activityInfo.name;
        if (packageName.contains("android.email") || packageName.contains("android.gm")) {
            service = "mail";
        } else if (packageName.contains("twitter")) {
            service = "twitter";
        } else if (packageName.contains("messenger")) {
            service = "facebook";
        } else if (packageName.contains("skype")) {
            service = "skype";
        } else if (packageName.contains("mms")) {
            service = "sms";
        } else if (packageName.contains("whatsapp")) {
            service = "whatsapp";
        } else if (packageName.contains("facebook")) {
            service = "facebook";
        } else if (packageName.contains("Slack")) {
            service = "slack";
        } else if (packageName.contains("instagram")) {
            service = "instagram";
//                } else if (packageName.contains("snapchat")) {
//                    service = "snapchat";
        } else if (packageName.contains("kakao.talk")) {
                    service = "kakaotalk";
        } else if (packageName.contains("com.viber")) {
            service = "viber";
        } else if (packageName.contains("wechat")) {
            service = "wechat";
        } else if (packageName.contains("dropbox")) {
            service = "dropbox";
        } else if (packageName.contains("linkedin")) {
            service = "linkedin";
        } else if (packageName.contains("pinterest")) {
            service = "pinterest";
        } else if (packageName.contains("tumblr")) {
            service = "tumblr";
        } else if (packageName.contains("vine")) {
            service = "vine";
        } else if (packageName.contains("yahoo")) {
            service = "yahoo";
        } else if (packageName.contains("youtube")) {
            service = "youtube";
        }
        final Map<String, Object> map = new HashMap<>();
        map.put("restaurant_id", restaurantId);
        map.put("service", service);
        ParseCloud.callFunctionInBackground("share", map);
        super.onActivityClicked(activity);
    }
}
