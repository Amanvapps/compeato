package com.appinnovates.campeat.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.UserPreferences;
import com.appinnovates.campeat.views.activities.Splash;

public class DeepLinkingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String referrer = intent.getStringExtra("referrer");
        if (referrer != null) {
            String refferdata[] = referrer.split(",");
            if (refferdata.length > 1) {
                String type = refferdata[0];
                String id = refferdata[1];
                if (type.equalsIgnoreCase("deal")) {
                    UserPreferences.getInstance().save(UserPreferences.TYPE, Constant.DEAL);
                    UserPreferences.getInstance().save(UserPreferences.Share_ID, id);
                } else {
                    UserPreferences.getInstance().save(UserPreferences.TYPE, Constant.Restaurant);
                    UserPreferences.getInstance().save(UserPreferences.Share_ID, id);
                }
                UserPreferences.getInstance().save(UserPreferences.OPEN_FROM_PLAYSTORE, true);
                Intent intent1 = new Intent(context, Splash.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
        }
    }
}
