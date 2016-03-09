package com.trialproject.lexis.theacademicpartnertrial.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.trialproject.lexis.theacademicpartnertrial.R;

import java.lang.reflect.Type;

public class HomeActivity extends AppCompatActivity {

    private TextView dayTxt, courseTxt, allTxt, checker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        dayTxt=(TextView) findViewById(R.id.viewByDayText);
        courseTxt=(TextView) findViewById(R.id.viewByCourseText);
        allTxt=(TextView) findViewById(R.id.viewAllText);
        checker=(TextView) findViewById(R.id.besideTimetableImage);

        Typeface face=Typeface.createFromAsset(getAssets(),
                "Quicksand_Light.ttf");
        dayTxt.setTypeface(face, Typeface.BOLD);
        courseTxt.setTypeface(face, Typeface.BOLD);
        allTxt.setTypeface(face, Typeface.BOLD);
        checker.setTypeface(face, Typeface.BOLD);

        allTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, TimetableActivity.class));
            }
        });

        dayTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TimetableByDay.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_about){
            startActivity(new Intent(getApplicationContext(), ActivityUsingTabLibrary.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
