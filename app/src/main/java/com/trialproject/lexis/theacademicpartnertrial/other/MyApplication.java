package com.trialproject.lexis.theacademicpartnertrial.other;

import android.app.Application;
import android.content.Context;

import com.trialproject.lexis.theacademicpartnertrial.databasehelper.StudentDatabase;

/**
 * Created by Lexis on 14/01/2016.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;

    private static StudentDatabase studentDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
        studentDatabase=new StudentDatabase(this);
    }

    public synchronized static StudentDatabase getWritableDatabase(){
        if(studentDatabase == null)
            studentDatabase=new StudentDatabase(getAppContext());
        return studentDatabase;
    }

    public MyApplication getsInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
