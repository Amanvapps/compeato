package com.appinnovates.campeat.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by reetu on 26/6/18.
 */

public class UserPreferences {
    private static UserPreferences instance;
    public static final String RestaurantId = "RestaurantID";
    private  SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;

    public static final String OPEN_FROM_PLAYSTORE = "openFromPlayStore";
    public static final String Share_ID = "dealID";
    public static final String TYPE = "shareType";

    public static UserPreferences getInstance() {
        return instance;
    }

    public UserPreferences(Context context){
        instance = this;
        sharedPreferences = context.getSharedPreferences( context.getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void save(String key, Object value) {
        delete(key);

        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }  else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Enum) {
            editor.putString(key, value.toString());
        } else if (value != null) {
            throw new RuntimeException("Attempting to save non-primitive preference");
        }

        editor.commit();
    }

    public boolean getBoolean(String key){
       return sharedPreferences.getBoolean(key, false);
    }
    public int getInt(String key){
        return sharedPreferences.getInt(key, 0);
    }
    public float getFloat(String key){
        return sharedPreferences.getFloat(key, 0);
    }
    public long getLong(String key){
        return sharedPreferences.getLong(key, 0);
    }
    public String getStrings(String key){
        return sharedPreferences.getString(key, "");
    }


    public void delete(String key) {
        if (sharedPreferences.contains(key)) {
            editor.remove(key).commit();
        }
    }
    public void destroy() {
        if (editor != null) {
            editor.clear().commit();
        }
    }
}
