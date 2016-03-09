package com.trialproject.lexis.theacademicpartnertrial.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.trialproject.lexis.theacademicpartnertrial.fragments.MyFragment;
import com.trialproject.lexis.theacademicpartnertrial.R;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


/**
 * Created by Lexis on 12/01/2016.
 */
public class ActivityUsingTabLibrary extends AppCompatActivity implements MaterialTabListener{

    TextView next;
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

    private class ViewPagerAdapter extends FragmentStatePagerAdapter{

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }

        @Override
        public Fragment getItem(int position) {

            return MyFragment.getInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


}
