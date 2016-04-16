package com.trialproject.lexis.theacademicpartnertrial.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.trialproject.lexis.theacademicpartnertrial.R;
import com.trialproject.lexis.theacademicpartnertrial.adapters.CustomExpandableListAdapter;
import com.trialproject.lexis.theacademicpartnertrial.api.VolleySingleton;
import com.trialproject.lexis.theacademicpartnertrial.model.LectureTimetable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExamByDayTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExamByDayTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExamByDayTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

//    private VolleySingleton volleySingleton;
//    private RequestQueue requestQueue;
    CustomExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<LectureTimetable>> listDataChild;
    String[] days;
//    ArrayList<LectureTimetable> monLecTimetable=new ArrayList<>();
//    ArrayList<LectureTimetable> tueLecTimetable=new ArrayList<>();
//    ArrayList<LectureTimetable> wedLecTimetable=new ArrayList<>();
//    ArrayList<LectureTimetable> thurLecTimetable=new ArrayList<>();
//    ArrayList<LectureTimetable> friLecTimetable=new ArrayList<>();

    public ExamByDayTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExamByDayTab.
     */
    // TODO: Rename and change types and number of parameters
    public static ExamByDayTab newInstance(String param1, String param2) {
        ExamByDayTab fragment = new ExamByDayTab();
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
        View layout = inflater.inflate(R.layout.fragment_exam_by_day_tab, container, false);

//        volleySingleton = VolleySingleton.getInstance();
//        requestQueue = volleySingleton.getRequestQueue();

        // get the listview
        expListView = (ExpandableListView) layout.findViewById(R.id.day_list);

        // preparing list data
        prepareListData();

        listAdapter = new CustomExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                switch(listDataHeader.get(groupPosition)){
                    case "Monday":
                        break;


                }
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
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
//                switch (listDataChild.get(listDataHeader.get(groupPosition)).get(
//                        childPosition)) {
//
//                }
                return false;
            }
        });

        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

    private void prepareListData() {
        days = getResources().getStringArray(R.array.days);
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding child data
        for (String menuItem : days) {
            listDataHeader.add(menuItem);
        }


        // Adding child data
        List<LectureTimetable> monChildren = new ArrayList<>();
        monChildren.add(new LectureTimetable("CSCD422", "Human Computer Interraction", "1:30pm - 2:25pm", "Computer Science Library"));
        monChildren.add(new LectureTimetable("CSCD416", "System Programming", "2:30pm - 3:25pm", "N Block Room 2"));
        monChildren.add(new LectureTimetable("CSCD434", "Mobile Computing", "5:30pm - 6:25pm", "MATH Room 19"));

        List<LectureTimetable> tuesChildren = new ArrayList<>();
        tuesChildren.add(new LectureTimetable("CSCD418", "System Security", "7:30am - 9:25am", "Jones Quartey Building Room 14"));
        tuesChildren.add(new LectureTimetable("CSCD424", "Management Principles in Computing", "9:30am - 11:25am", "Jones Quartey Building Room 21"));

        List<LectureTimetable> wedChildren = new ArrayList<>();
        wedChildren.add(new LectureTimetable("CSCD416", "System Programming", "7:30am - 9:25am", "N Block Room 3"));
        wedChildren.add(new LectureTimetable("CSCD422", "Human Computer Interraction", "9:30pm - 11:25pm", "Jones Quartey Building Room 09"));
        wedChildren.add(new LectureTimetable("CSCD424", "Management Principles in Computing", "15:30pm - 16:25pm", "Jones Quartey Building Room 09"));

        List<LectureTimetable> thurChildren = new ArrayList<>();
        thurChildren.add(new LectureTimetable("CSCD434", "Mobile Computing", "11:30pm - 13:25pm", "Jones Quartey Building Room 14"));
        thurChildren.add(new LectureTimetable("CSCD418", "System Security", "16:30pm - 17:25pm", "Jones Quartey Building Room 23"));


        listDataChild.put(listDataHeader.get(0), monChildren); // Header, Child data
        listDataChild.put(listDataHeader.get(1), tuesChildren);
        listDataChild.put(listDataHeader.get(2), wedChildren);
        listDataChild.put(listDataHeader.get(3), thurChildren);

    }
}
