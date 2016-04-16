package com.trialproject.lexis.theacademicpartnertrial.projectactivities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.trialproject.lexis.theacademicpartnertrial.R;
import com.trialproject.lexis.theacademicpartnertrial.adapters.CustomExpandableListAdapter;
import com.trialproject.lexis.theacademicpartnertrial.api.CustomArrayRequest;
import com.trialproject.lexis.theacademicpartnertrial.api.VolleySingleton;
import com.trialproject.lexis.theacademicpartnertrial.fragments.ExamByDayTab;
import com.trialproject.lexis.theacademicpartnertrial.fragments.LectureByDayTab;
import com.trialproject.lexis.theacademicpartnertrial.fragments.MyFragment;
import com.trialproject.lexis.theacademicpartnertrial.fragments.Tab1Fragment;
import com.trialproject.lexis.theacademicpartnertrial.fragments.Tab2Fragment;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class TimetableByDay extends AppCompatActivity implements MaterialTabListener
        /*LectureByDayTab.OnFragmentInteractionListener, ExamByDayTab.OnFragmentInteractionListener*/ {

    private ViewPager mPager;
    private MaterialTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_tab_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.splashBackgroundColor));
        toolbar.setTitle("Timetable");
        setSupportActionBar(toolbar);

        mPager=(ViewPager) findViewById(R.id.pager);
        tabHost=(MaterialTabHost) findViewById(R.id.materialTabHost);

        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }
        });

        for (int i=0; i < adapter.getCount(); i++){
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }

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

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTabSelected(MaterialTab tab) {
        mPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment=null;
            switch(position){
                case 0:
                    fragment=new LectureByDayTab().newInstance("","");
                    break;
                case 1:
                    fragment=new ExamByDayTab().newInstance("","");
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
