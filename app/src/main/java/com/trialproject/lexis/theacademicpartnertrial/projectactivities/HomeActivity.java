package com.trialproject.lexis.theacademicpartnertrial.projectactivities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.renderscript.Type;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trialproject.lexis.theacademicpartnertrial.R;
import com.trialproject.lexis.theacademicpartnertrial.activities.ActivityUsingTabLibrary;

public class HomeActivity extends AppCompatActivity {

    private TextView checker, subText, one, two, three, four, five, six, seven;
    private LinearLayout timetableByDay, timetableByCourse,
            allTimetable, findVenue;


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
//        });\

        checker=(TextView) findViewById(R.id.besideTimetableImage);
        subText=(TextView) findViewById(R.id.subText);
        one=(TextView) findViewById(R.id.one);
        two=(TextView) findViewById(R.id.two);
        three=(TextView) findViewById(R.id.three);
        four=(TextView) findViewById(R.id.four);
        five=(TextView) findViewById(R.id.five);
        six=(TextView) findViewById(R.id.six);
        seven=(TextView) findViewById(R.id.seven);
        timetableByDay=(LinearLayout) findViewById(R.id.timetableByDay);
        timetableByCourse=(LinearLayout) findViewById(R.id.timetableByCourse);
        allTimetable=(LinearLayout) findViewById(R.id.AllTmetable);
        findVenue=(LinearLayout) findViewById(R.id.findVenue);

        Typeface face = Typeface.createFromAsset(getAssets(), "Quicksand_Light.ttf");
        checker.setTypeface(face, Typeface.BOLD);
        subText.setTypeface(face);
        one.setTypeface(face, Typeface.BOLD);
        two.setTypeface(face);
        three.setTypeface(face, Typeface.BOLD);
        four.setTypeface(face);
        five.setTypeface(face, Typeface.BOLD);
        six.setTypeface(face, Typeface.BOLD);
        seven.setTypeface(face);

        timetableByDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, DayActivity.class));
            }
        });

        timetableByCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        allTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
