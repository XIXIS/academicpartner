package com.trialproject.lexis.theacademicpartnertrial.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.trialproject.lexis.theacademicpartnertrial.R;

public class TimetableByDay extends AppCompatActivity {

    Button monButton, tueButton, wedButton, thurButton, friButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_by_day);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        monButton=(Button) findViewById(R.id.monday_button);
        tueButton=(Button) findViewById(R.id.tuesday_button);
        wedButton=(Button) findViewById(R.id.wednesday_button);
        thurButton=(Button) findViewById(R.id.thursday_button);
        friButton=(Button) findViewById(R.id.friday_button);

        Typeface face=Typeface.createFromAsset(getAssets(),
                "Quicksand_Light.ttf");
        monButton.setTypeface(face, Typeface.BOLD);
        tueButton.setTypeface(face, Typeface.BOLD);
        wedButton.setTypeface(face, Typeface.BOLD);
        thurButton.setTypeface(face, Typeface.BOLD);
        friButton.setTypeface(face, Typeface.BOLD);
    }

}
