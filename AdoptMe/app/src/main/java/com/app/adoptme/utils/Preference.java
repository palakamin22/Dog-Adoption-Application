package com.app.adoptme.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Preference {

    private static final String TAG = Preference.class.getName();

    public static String USER = "user";
    public static String IS_LOGIN = "is_login";

    private SharedPreferences sharedPreferences;

    public Preference(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public String getString(String key) {
        Log.e("putString:"+key,sharedPreferences.getString(key, ""));
        return sharedPreferences.getString(key, "");
    }

    public void putString(String key, String value) {
        Log.e("putString:"+key,value);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public void ClearPref() {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        edit.apply();
    }

}
