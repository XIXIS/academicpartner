package com.trialproject.lexis.theacademicpartnertrial.projectactivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.trialproject.lexis.theacademicpartnertrial.R;
import com.trialproject.lexis.theacademicpartnertrial.projectactivities.FinalSignUp;
import com.trialproject.lexis.theacademicpartnertrial.projectactivities.HomeActivity;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 8000;
    public TextView appName, appMotto;
    public ImageView hat;
    private boolean firstInstallation;
    private boolean fromSavedInstanceState;
    public static final String PREF_FILE_NAME="checkfirsttimeInstal";
    public static final String KEY_USER_DO_FIRST_INSTALL="user_do_first_time_installation";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splash);

        firstInstallation=Boolean.valueOf(readFromPreferences(getApplicationContext(), KEY_USER_DO_FIRST_INSTALL, "false"));
        if(savedInstanceState != null){
            fromSavedInstanceState = true;
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        TextView appName = (TextView) findViewById(R.id.appName);
        TextView appMotto = (TextView) findViewById(R.id.appMotto);
        ImageView hat = (ImageView) findViewById(R.id.hat);

        Typeface face = Typeface.createFromAsset(getAssets(),
                "Quicksand_Light.ttf");
        appMotto.setTypeface(face, Typeface.BOLD);
        appName.setTypeface(face, Typeface.BOLD);


        RelativeLayout parentLayout = (RelativeLayout)findViewById(R.id.relLayout);
        Animation alphaTweenAnim = AnimationUtils.loadAnimation(Splash.this, R.anim.alpha_tween);
        parentLayout.startAnimation(alphaTweenAnim);

        Animation translateTweenAnim = AnimationUtils.loadAnimation(Splash.this, R.anim.translate_tween);
        appName.startAnimation(translateTweenAnim);
        Animation scaleTweenAnim = AnimationUtils.loadAnimation(Splash.this, R.anim.scale_tween);
        appMotto.startAnimation(scaleTweenAnim);

        Thread thread = new Thread (){
            public void run (){
                try{
                    sleep(SPLASH_TIME_OUT);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally{
                    if (!firstInstallation && !fromSavedInstanceState){
                        firstInstallation=true;
                        saveToPreferences(getApplicationContext(), KEY_USER_DO_FIRST_INSTALL, firstInstallation+"");
                        startActivity( new Intent(getApplicationContext(),FinalSignUp.class));
                        finish();
                    }else {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }

        };
        thread.start();
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue){
        SharedPreferences sharedPreference = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreference.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }
}
