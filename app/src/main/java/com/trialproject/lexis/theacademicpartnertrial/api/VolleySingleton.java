package com.trialproject.lexis.theacademicpartnertrial.api;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.trialproject.lexis.theacademicpartnertrial.other.MyApplication;

/**
 * Created by Lexis on 14/01/2016.
 */
public class VolleySingleton {

    private static VolleySingleton sInstance=null;
    private RequestQueue mRequestQueue;

    private VolleySingleton(){
        mRequestQueue= Volley.newRequestQueue(MyApplication.getAppContext());
    }
    public static VolleySingleton getInstance(){
        if (sInstance == null){
            sInstance=new VolleySingleton();
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}
