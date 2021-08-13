package com.app.adoptme;

import android.app.Application;
import android.content.Context;

import com.app.adoptme.utils.Preference;


public class AdoptMeApp extends Application {

    public static Preference preference;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        preference = new Preference(context);
    }
}
