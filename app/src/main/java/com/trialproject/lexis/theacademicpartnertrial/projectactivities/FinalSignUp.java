package com.trialproject.lexis.theacademicpartnertrial.projectactivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.trialproject.lexis.theacademicpartnertrial.R;
import com.trialproject.lexis.theacademicpartnertrial.helperclasses.Student;
import com.trialproject.lexis.theacademicpartnertrial.api.CustomArrayRequest;
import com.trialproject.lexis.theacademicpartnertrial.api.CustomRequest;
import com.trialproject.lexis.theacademicpartnertrial.api.VolleySingleton;
import com.trialproject.lexis.theacademicpartnertrial.other.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.ID_COLLEGE_BASIC_AND_APPLIED_SCIENCES;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.ID_COLLEGE_EDUCATION;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.ID_COLLEGE_HEALTH_SCIENCES;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.ID_COLLEGE_HUMANITIES;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_BASE_URL;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_REGISTER_FINAL_URL;


public class FinalSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    //Declare variables and views
    TextView appName, colTV, schTV, deptTV, courseTV, finish, lvl;
    Spinner col, sch, level;
    Button dept, course;
    boolean[] mSelection = null;
    private static String[] COLLEGES, SCHOOLS, DEPTS, COURSES;
    private ArrayList<String> selectedDepts=new ArrayList<>();
    private List<String> selectedCourses=new ArrayList<>();
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    Typeface face;
    private ArrayList<String> colleges = new ArrayList<>();
    private Map<String, String> schools = new HashMap<>();
    private Map<String, String> depts =  new HashMap<>();
    private Map<String, String> courses =  new HashMap<>();
    private String selectedCollegeID, selectedSchoolID, selectedDeptIDs;
    private static String[] LEVELS;
    private ArrayList<String> levels = new ArrayList<>();
    private Student student;
    private String selCourses;
    private String selDepts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_sign_up);

        //Initialize variables and views
        appName = (TextView) findViewById(R.id.appName);
        colTV = (TextView) findViewById(R.id.collegeTextView);
        schTV = (TextView) findViewById(R.id.schoolTextView);
        deptTV = (TextView) findViewById(R.id.deptTextView);
        courseTV = (TextView) findViewById(R.id.coursesTextView);
        lvl=(TextView) findViewById(R.id.levelTextView);
        finish = (TextView) findViewById(R.id.finish);
        level=(Spinner) findViewById(R.id.level);
        col = (Spinner) findViewById(R.id.college);
        sch = (Spinner) findViewById(R.id.school);
        dept = (Button) findViewById(R.id.dept);
        course = (Button) findViewById(R.id.courses);

        //Set the font for textviews
        face = Typeface.createFromAsset(getAssets(), "Quicksand_Light.ttf");
        lvl.setTypeface(face, Typeface.BOLD);
        appName.setTypeface(face, Typeface.BOLD);
        colTV.setTypeface(face, Typeface.BOLD);
        schTV.setTypeface(face, Typeface.BOLD);
        deptTV.setTypeface(face, Typeface.BOLD);
        courseTV.setTypeface(face, Typeface.BOLD);
        finish.setTypeface(face, Typeface.BOLD);

        //Initialize the volleySingleton to make requests
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        //Make appropriate requests to get necessary data
        sendLevelsRequest();
        sendCollegesRequest();
        col.setOnItemSelectedListener(this);
        sch.setOnItemSelectedListener(this);

        dept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FinalSignUp.this);
                builder.setTitle("Select your Departments").setMultiChoiceItems(DEPTS, mSelection, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        selectedDepts.add(DEPTS[which]);
                    }
                });
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedDepts.clear();
                        dept.setText(selectedDepts.toString());
                        selectedDeptIDs="";
                        for (int i=0;i<selectedDepts.size();i++){
                            if (selectedDepts.size()-1 == i) {
                                selectedDeptIDs+=depts.get(selectedDepts.get(i));
                                break;
                            }
                            selectedDeptIDs+=(depts.get(selectedDepts.get(i))+",");
                        }

                        sendCoursesRequest();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Please select a dept", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FinalSignUp.this);
                builder.setTitle("Select your Courses").setMultiChoiceItems(COURSES, mSelection, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        selectedCourses.add(COURSES[which]);
                    }
                });
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        course.setText(selectedCourses.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Please select a course", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] selectDepts=new String[selectedDepts.size()];
                String [] selectCourses=new String[selectedCourses.size()];

                student=new Student(level.getSelectedItem().toString(),
                                    selectedCourses.toArray(selectCourses),
                                    selectedDepts.toArray(selectDepts) ,
                                    col.getSelectedItem().toString(), sch.getSelectedItem().toString());
                MyApplication.getWritableDatabase().overrideStudentSettings(student, true);
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.college:
                //Log.d("selected college", (String) parent.getItemAtPosition(position));
                switch ((String) parent.getItemAtPosition(position)){
                    case "Basic and Applied Sciences":
                        selectedCollegeID = ID_COLLEGE_BASIC_AND_APPLIED_SCIENCES;
                        sendSchoolsRequest();
                        break;
                    case "Education":
                        selectedCollegeID = ID_COLLEGE_EDUCATION;
                        sendSchoolsRequest();
                        break;
                    case "Health Sciences":
                        selectedCollegeID = ID_COLLEGE_HEALTH_SCIENCES;
                        sendSchoolsRequest();
                        break;
                    case "Humanities":
                        selectedCollegeID = ID_COLLEGE_HUMANITIES;
                        sendSchoolsRequest();
                        break;
                }
                break;

            case R.id.school:
//                Log.d("selected school", (String) parent.getItemAtPosition(position));
//                Log.d("school_id", schools.get( parent.getItemAtPosition(position)));
                switch ((String) parent.getItemAtPosition(position)){
                    case "Agriculture":
                        selectedSchoolID = schools.get("Agriculture");
                        sendDeptsRequest();
                        break;
                    case "Biotechnology Research Centre":
                        selectedSchoolID = schools.get("Biotechnology Research Centre");
                        Log.d("selectedSchoolID", selectedSchoolID);
                        sendDeptsRequest();
                        break;
                    case "Biological Sciences":
                        selectedSchoolID = schools.get("Biological Sciences");
                        Log.d("selectedSchoolID", selectedSchoolID);
                        sendDeptsRequest();
                        break;
                    case "Engineering Sciences":
                        selectedSchoolID = schools.get("Engineering Sciences");
                        Log.d("selectedSchoolID", selectedSchoolID);
                        sendDeptsRequest();
                        break;
                    case "Institute of Applied Science and Technology":
                        selectedSchoolID=schools.get("Institute of Applied Science and Technology");
                        Log.d("selectedSchoolID", selectedSchoolID);
                        sendDeptsRequest();
                        break;
                    case "Institute for Environment and Sanitation Studies":
                        selectedSchoolID=schools.get("Institute for Environment and Sanitation Studies");
                        Log.d("selectedSchoolID", selectedSchoolID);
                        sendDeptsRequest();
                        break;
                    case "School of Physical and Mathematical Sciences":
                        selectedSchoolID=schools.get("School of Physical and Mathematical Sciences");
                        Log.d("selectedSchoolID", selectedSchoolID);
                        sendDeptsRequest();
                        break;
                    case "School of Veterinary Medicine":
                        selectedSchoolID=schools.get("School of Veterinary Medicine");
                        Log.d("selectedSchoolID", selectedSchoolID);
                        sendDeptsRequest();
                        break;
                }
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //retrieve levels
    private void sendLevelsRequest() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("request", "levels");

        CustomArrayRequest jsObjRequest = new CustomArrayRequest(Request.Method.POST,
                KEY_BASE_URL.concat(KEY_REGISTER_FINAL_URL), params,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        parseLevelResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Toast.makeText(getApplicationContext(), "Please check internet connection\n" +
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }
    private void parseLevelResponse(JSONArray response) {
        if (response == null || response.length() == 0) {
            levels.add("No levels available");
            LEVELS = levels.toArray(new String[levels.size()]);
            level.setAdapter(new MyAdapter(FinalSignUp.this, R.layout.custom, LEVELS));
        }
        try {
            String lev;
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                lev=obj.getString("level_number");
                levels.add(lev);
            }

            LEVELS = levels.toArray(new String[levels.size()]);
            Log.d("Levels", LEVELS.toString());

            // Setting a Custom Adapter to the Spinner
            level.setAdapter(new MyAdapter(FinalSignUp.this, R.layout.custom, LEVELS));
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //Retrieve Colleges
    private void sendCollegesRequest() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("request", "colleges");

        CustomArrayRequest jsObjRequest = new CustomArrayRequest(Request.Method.POST,
                KEY_BASE_URL.concat(KEY_REGISTER_FINAL_URL), params,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        parseCollegeResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {

                Toast.makeText(getApplicationContext(), "Unable to retrieve College Details\n" +
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }
    private void parseCollegeResponse(JSONArray response) {
        if (response == null || response.length() == 0) {
            colleges.add("No colleges available");
            COLLEGES = colleges.toArray(new String[colleges.size()]);
            col.setAdapter(new MyAdapter(FinalSignUp.this, R.layout.custom, COLLEGES));
        }
        try {
            String coll;
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                coll = obj.getString("college_name");
                colleges.add(coll);
            }
            COLLEGES = colleges.toArray(new String[colleges.size()]);

            // Setting a Custom Adapter to the Spinner
            col.setAdapter(new MyAdapter(FinalSignUp.this, R.layout.custom, COLLEGES));

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    //Retrieve Schools
    private void sendSchoolsRequest() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("request", "schools");
        params.put("college_id", selectedCollegeID);

        CustomArrayRequest jsObjRequest = new CustomArrayRequest(Request.Method.POST,
                KEY_BASE_URL.concat(KEY_REGISTER_FINAL_URL), params,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        parseSchoolsResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {

                Toast.makeText(getApplicationContext(), "Unable to retrieve School Details\n" +
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }
    private void parseSchoolsResponse(JSONArray response) {
        if (response == null || response.length() == 0) {
            SCHOOLS[0] = "No schools available";
            sch.setAdapter(new MyAdapter(FinalSignUp.this, R.layout.custom, SCHOOLS));
        }
        try {
            schools.clear();
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                schools.put(obj.getString("school_name"), obj.getString("school_id"));
            }

            SCHOOLS = schools.keySet().toArray(new String[schools.keySet().size()]);
            sch.setAdapter(new MyAdapter(FinalSignUp.this, R.layout.custom, SCHOOLS));
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //Retrieve Depts
    private void sendDeptsRequest(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("request", "depts");
        params.put("schoolID", selectedSchoolID);

        CustomArrayRequest jsObjRequest = new CustomArrayRequest(Request.Method.POST,
                KEY_BASE_URL.concat(KEY_REGISTER_FINAL_URL), params,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        parseDeptRequest(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Toast.makeText(getApplicationContext(), "Unable to retrieve Department Details\n" +
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }
    private void parseDeptRequest(JSONArray response){
        if (response == null || response.length() == 0) {
            DEPTS[0] = "No departments available";
        }
        try {
            depts.clear();
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                depts.put(obj.getString("dept_name"), obj.getString("dept_id"));
            }

            DEPTS = depts.keySet().toArray(new String[depts.keySet().size()]);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    //retrieve courses
    private void sendCoursesRequest(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("request", "courses");
        params.put("dept_ids", selectedDeptIDs);
        params.put("level", level.getSelectedItem().toString());

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST,
                KEY_BASE_URL.concat(KEY_REGISTER_FINAL_URL), params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        parseCoursesJsonRequest(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Log.d("Retrieve Courses Error", error.getMessage());
                Toast.makeText(getApplicationContext(), "Unable to retrieve Courses Details\n" +
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }
    private void parseCoursesJsonRequest(JSONObject response){
        if (response == null || response.length() == 0) {
            COURSES[0] = "No courses available";
        }
        try {
            courses.clear();
            int numQueries=selectedDepts.size();
            for (int i = 0; i < numQueries; i++) {
                JSONArray arr = response.getJSONArray("query" + i);
                for (int j=0;j<arr.length();j++){
                    JSONObject obj=arr.getJSONObject(j);
                    courses.put(obj.getString("course_title"), obj.getString("course_id"));
                }
            }
            COURSES = courses.keySet().toArray(new String[courses.size()]);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    // Creating an Adapter Class
    public class MyAdapter extends ArrayAdapter {
        String[] objects;

        public MyAdapter(Context context, int textViewResourceId,
                         String[] objects) {
            super(context, textViewResourceId, objects);
            this.objects = objects;
        }

        public View getCustomView(int position, View convertView,
                                  ViewGroup parent) {
            // Inflating the layout for the custom Spinner
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom, parent, false);

            // Declaring and Typecasting the textview in the inflated layout
            TextView listItems = (TextView) layout.findViewById(R.id.list_item);
            listItems.setTypeface(face, Typeface.BOLD);

            // Setting the text using the array
            listItems.setText(objects[position]);


            return layout;
        }

        // It gets a View that displays in the drop down popup the data at the specified position
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        // It gets a View that displays the data at the specified position
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
    }


}
