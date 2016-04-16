package com.trialproject.lexis.theacademicpartnertrial.projectactivities;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.trialproject.lexis.theacademicpartnertrial.R;

import java.util.ArrayList;

public class DayActivity extends AppCompatActivity {

    GridView gridDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridDays = (GridView) findViewById(R.id.grid);
        gridDays.setAdapter(new BAdapter(this));

    }



}

class Day{

    int imageID;
    String day;

    public Day(int imageID, String day){
        this.imageID = imageID;
        this.day = day;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}

class BAdapter extends BaseAdapter{

    ArrayList<Day> days;
    Context context;

    public BAdapter(Context context){
        this.context = context;
        days = new ArrayList<>();
        Resources res=context.getResources();
        String[] lectureDays = res.getStringArray(R.array.lecture_days);
        int[] images = {R.drawable.day_icon, R.drawable.day_icon, R.drawable.day_icon,
                R.drawable.day_icon, R.drawable.day_icon};

        for(int i=0; i<lectureDays.length; i++){
            Day day = new Day(images[i], lectureDays[i]);
            days.add(day);
        }

    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        ImageView image;
        TextView text;
        ViewHolder(View view){
            image = (ImageView) view.findViewById(R.id.image);
            text = (TextView) view.findViewById(R.id.day_name);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;
        if(convertView == null){
            LayoutInflater layout=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layout.inflate(R.layout.dashboard_list, parent, false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        Day d = days.get(position);
        holder.text.setText(d.getDay());


        return convertView;
    }
}