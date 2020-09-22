package com.appinnovates.campeat.views.activities;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.utils.AuthType;
import com.appinnovates.campeat.utils.KakaoSDKAdapter;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.UserPreferences;
import com.kakao.auth.KakaoSDK;
import com.parse.Parse;
import com.parse.ParseUser;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public  class App extends Application {
    private static App mInstance;
    private static volatile Activity currentActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        KakaoSDK.init(new KakaoSDKAdapter());



        LocaleManager.setLocale(getApplicationContext(),LocaleManager.getLanguage(getApplicationContext()));

/*        TypefaceUtil.setDefaultFont(getApplicationContext(), "DEFAULT", "fonts/avenir_next_regular.ttf");
        TypefaceUtil.setDefaultFont(this, "MONOSPACE", "fonts/avenir_next_regular.ttf");
        TypefaceUtil.setDefaultFont(this, "SERIF", "fonts/avenir_next_bold.ttf");
        TypefaceUtil.setDefaultFont(this, "SANS_SERIF", "fonts/avenir_next_regular.ttf");*/
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);
        Parse.initialize(new Parse.Configuration.Builder(this)
//                .clientBuilder(builder)
                .applicationId(getString(R.string.parse_app_id))
                .server(getString(R.string.parse_server))
                .build());
        ParseUser.registerAuthenticationCallback("google",new AuthType());
        ParseUser.registerAuthenticationCallback("kakao",new AuthType());
        UserPreferences userPreferences = new UserPreferences(this);
    }



    public static Activity getCurrentActivity() {
        Log.d("TAG", "++ currentActivity : " + (currentActivity != null ? currentActivity.getClass().getSimpleName() : ""));
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        App.currentActivity = currentActivity;
    }

    /**
     * singleton
     * @return singleton
     */
    public static App getGlobalApplicationContext() {
        if(mInstance == null)
            throw new IllegalStateException("this application does not inherit GlobalApplication");
        return mInstance;
    }

}
