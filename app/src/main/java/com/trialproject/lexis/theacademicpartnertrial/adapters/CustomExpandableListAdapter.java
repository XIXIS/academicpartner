package com.trialproject.lexis.theacademicpartnertrial.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trialproject.lexis.theacademicpartnertrial.R;
import com.trialproject.lexis.theacademicpartnertrial.model.LectureTimetable;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Lexis on 3/31/2016.
 */
public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<LectureTimetable>> _listDataChild;

    public CustomExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<LectureTimetable>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final LectureTimetable lectureTimetable = (LectureTimetable) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.lecture_by_day_list_item, null);
        }

        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView courseCode = (TextView) convertView.findViewById(R.id.course_code);
        TextView courseTitle = (TextView) convertView.findViewById(R.id.course_title);
        TextView venue = (TextView) convertView.findViewById(R.id.venue);

        time.setText(lectureTimetable.getTime());
        courseCode.setText(lectureTimetable.getCourseCode());
        courseTitle.setText(lectureTimetable.getCourseTitle());
        venue.setText(lectureTimetable.getVenue());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }


    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.dashboard_list, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.list_item);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
