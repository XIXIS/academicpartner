package com.trialproject.lexis.theacademicpartnertrial.api;

import com.trialproject.lexis.theacademicpartnertrial.model.gitmodel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Lexis on 13/01/2016.
 */
public interface gitapi {

    //here is the other url part.best way is to start using /
    @GET("/users/{user}")

    //string user is for passing values from edittext for eg: user=basil2style,google
    public void getFeed(@Path("user") String user, Callback<gitmodel> response);

     //response is the response from the server which is now in the POJO

}

