package com.trialproject.lexis.theacademicpartnertrial.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.trialproject.lexis.theacademicpartnertrial.R;

public class SignUpSuccess extends AppCompatActivity {

    TextView appName, appMotto, congratTV, settingUpTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_success);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        appName = (TextView) findViewById(R.id.appName);
        appMotto = (TextView) findViewById(R.id.appMotto);
        congratTV = (TextView) findViewById(R.id.congratTextView);
        settingUpTV = (TextView) findViewById(R.id.settingUpTextView);

        Typeface face = Typeface.createFromAsset(getAssets(), "Quicksand_Light.ttf");
        appName.setTypeface(face, Typeface.BOLD);
        appMotto.setTypeface(face, Typeface.BOLD);
        congratTV.setTypeface(face, Typeface.BOLD_ITALIC);
        settingUpTV.setTypeface(face, Typeface.BOLD);

        Thread thread = new Thread (){
            public void run (){
                try{
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(getApplicationContext(), SignIn.class);
                    startActivity(intent);
                    finish();
                }

            }

        };
        thread.start();
    }

}
