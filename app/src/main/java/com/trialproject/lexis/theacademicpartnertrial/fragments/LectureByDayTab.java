package com.trialproject.lexis.theacademicpartnertrial.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.trialproject.lexis.theacademicpartnertrial.R;
import com.trialproject.lexis.theacademicpartnertrial.adapters.CustomExpandableListAdapter;
import com.trialproject.lexis.theacademicpartnertrial.api.CustomArrayRequest;
import com.trialproject.lexis.theacademicpartnertrial.api.VolleySingleton;
import com.trialproject.lexis.theacademicpartnertrial.model.LectureTimetable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_BASE_URL;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_CLOSING_TIME;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_COURSE_CODE;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_COURSE_TITLE;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_LECTURE_TIMETABLE_URL;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_REGISTER_URL;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_STARTING_TIME;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_VENUE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //LectureByDayTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LectureByDayTab#newInstance} factory method to
 * create an instance of this fragment.
 */

public class LectureByDayTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    CustomExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<LectureTimetable>> listDataChild;
    String[] days;
    ArrayList<LectureTimetable> monLecTimetable=new ArrayList<>();
    ArrayList<LectureTimetable> tueLecTimetable=new ArrayList<>();
    ArrayList<LectureTimetable> wedLecTimetable=new ArrayList<>();
    ArrayList<LectureTimetable> thurLecTimetable=new ArrayList<>();
    ArrayList<LectureTimetable> friLecTimetable=new ArrayList<>();

    public LectureByDayTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LectureByDayTab.
     */
    // TODO: Rename and change types and number of parameters
    public static LectureByDayTab newInstance(String param1, String param2) {
        LectureByDayTab fragment = new LectureByDayTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_lecture_by_day_tab, container, false);

        volleySingleton = VolleySingleton.getInstance();
        requestQueue=volleySingleton.getRequestQueue();

        retrieveTimetable("Monday");
        retrieveTimetable("Tuesday");
        retrieveTimetable("Wednesday");
        retrieveTimetable("Thursday");
        retrieveTimetable("Friday");

        expListView = (ExpandableListView) layout.findViewById(R.id.day_list);

        listAdapter = new CustomExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);


        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                switch (listDataHeader.get(groupPosition)){
                    case "Monday":
                        break;
                    case "Tuesday":
                        break;
                    case "Wednesday":
                        break;
                    case "Thursday":
                        break;
                    case "Friday":
                        break;


                }
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
//                listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition)
//                switch (listDataChild.get(childPosition)) {
//
//                }
                return false;
            }
        });

        return layout;
    }

    //query to obtain all timetable data
    private void retrieveTimetable(String day) {
        sendJsonRequest(day);
    }

    private void sendJsonRequest(final String day) {

        Map<String, String> params = new HashMap<>();
        params.put("what_to_retrieve", "lecture_timetable_by_day");
        params.put("dept", "CSCD");
        params.put("day", day);
        params.put("level", "400");


        CustomArrayRequest jsObjRequest = new CustomArrayRequest(Request.Method.POST,
                KEY_BASE_URL+KEY_LECTURE_TIMETABLE_URL, params, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                parseJSONResponse(response, day);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        requestQueue.add(jsObjRequest);
    }

    private void parseJSONResponse(JSONArray response, String day) {
        if (response == null || response.length() == 0) {
            Log.d(day, String.valueOf(response.length()));
            switch (day) {
                case "Monday":
                    monLecTimetable.add(new LectureTimetable("", "", "", "" ));
                    break;
                case "Tuesday":
                    tueLecTimetable.add(new LectureTimetable("", "", "", "" ));
                    break;
                case "Wednesday":
                    wedLecTimetable.add(new LectureTimetable("", "", "", "" ));
                    break;
                case "Thursady":
                    thurLecTimetable.add(new LectureTimetable("", "", "", "" ));
                    break;
                case "Friday":
                    friLecTimetable.add(new LectureTimetable("Not Available", "Not Available", "Not Available", "Not Available" ));
                    break;
            }
        } else {
            try {
                Log.d("Json String", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    JSONObject obj = response.getJSONObject(i);
                    String courseCode = obj.getString(KEY_COURSE_CODE);
                    String courseTitle = obj.getString(KEY_COURSE_TITLE);
                    String startingTime = obj.getString(KEY_STARTING_TIME);
                    String closingTime = obj.getString(KEY_CLOSING_TIME);
                    String venue = obj.getString(KEY_VENUE);

                    LectureTimetable lectTimetableEntry = new LectureTimetable(courseCode, courseTitle, startingTime + " - " + closingTime, venue);
                    switch (day){
                        case "Monday":
                            monLecTimetable.add(lectTimetableEntry);
                            break;
                        case "Tuesday":
                            tueLecTimetable.add(lectTimetableEntry);
                            break;
                        case "Wednesday":
                            wedLecTimetable.add(lectTimetableEntry);
                            break;
                        case "Thursady":
                            thurLecTimetable.add(lectTimetableEntry);
                            break;
                        case "Friday":
                            friLecTimetable.add(lectTimetableEntry);
                            break;

                    }

                    if(day.equalsIgnoreCase("Friday"))
                        prepareListData();

                }
            } catch (JSONException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }



    private void prepareListData() {
        days = getResources().getStringArray(R.array.days);
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding group data
        for (String menuItem : days) {
            listDataHeader.add(menuItem);
        }
        Log.d("days", listDataHeader.size()+"");

        ArrayList[] allDaysTimetableEntries={monLecTimetable, tueLecTimetable,
                wedLecTimetable, thurLecTimetable, friLecTimetable};

        for (ArrayList s: allDaysTimetableEntries){
            if (s.size() == 0 ) {
                return;
            } else{
                String entry=listDataHeader.get(Arrays.asList(allDaysTimetableEntries).indexOf(s));
                listDataChild.put(entry,s);
                Log.d("info", entry + " - " +  s.size()  +" children");
            }
        }

    }




}
