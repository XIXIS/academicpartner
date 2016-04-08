package com.trialproject.lexis.theacademicpartnertrial.other;

import android.app.Application;
import android.content.Context;

/**
 * Created by Lexis on 14/01/2016.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }

    public MyApplication getsInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
