package com.trialproject.lexis.theacademicpartnertrial.activities;

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

    TextView appName, colTV, schTV, deptTV, courseTV, finish;
    Spinner col, sch;
    Button dept, course;
    boolean[] mSelection = null;
    private static String[] COLLEGES, SCHOOLS, DEPTS, COURSES;
    private List<String> selectedDepts=new ArrayList<>();
    private List<String> selectedCourses=new ArrayList<>();
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    Typeface face;
    private ArrayList<String> colleges = new ArrayList<>();
    private Map<String, String> schools = new HashMap<>();
    private Map<String, String> depts =  new HashMap<>();
    private Map<String, String> courses =  new HashMap<>();
    private String selectedCollegeID, selectedSchoolID, selectedDeptIDs;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_sign_up);

        appName = (TextView) findViewById(R.id.appName);
        colTV = (TextView) findViewById(R.id.collegeTextView);
        schTV = (TextView) findViewById(R.id.schoolTextView);
        deptTV = (TextView) findViewById(R.id.deptTextView);
        courseTV = (TextView) findViewById(R.id.coursesTextView);
        finish = (TextView) findViewById(R.id.finish);
        col = (Spinner) findViewById(R.id.college);
        sch = (Spinner) findViewById(R.id.school);
        dept = (Button) findViewById(R.id.dept);
        course = (Button) findViewById(R.id.courses);
        student= (Student)getIntent().getSerializableExtra("student");

        face = Typeface.createFromAsset(getAssets(), "Quicksand_Light.ttf");
        appName.setTypeface(face);
        colTV.setTypeface(face);
        schTV.setTypeface(face);
        deptTV.setTypeface(face);
        courseTV.setTypeface(face);
        finish.setTypeface(face);

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

        getCollegeData();
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
                        dept.setText(selectedDepts.toString());
                        selectedDeptIDs="";
                        for (int i=0;i<selectedDepts.size();i++){
                            if (selectedDepts.size()-1 == i) {
                                selectedDeptIDs+=depts.get(selectedDepts.get(i));
                                break;
                            }
                            selectedDeptIDs+=(depts.get(selectedDepts.get(i))+",");
                            Toast.makeText(getApplicationContext(), selectedDeptIDs, Toast.LENGTH_SHORT).show();
                        }
                        Log.d("selected depts & codes", student.getLevel() + "\n" + selectedDepts.toString() + "\n" + selectedDeptIDs);
                        getCoursesData();
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
                Intent intent = new Intent(getApplicationContext(), SignUpSuccess.class);
                student.setDeptartments(selectedDepts.toArray(new String[selectedDepts.size()]));
                student.setCourses(selectedCourses.toArray(new String[selectedCourses.size()]));
                intent.putExtra("student", student);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.college:
                Toast.makeText(getApplicationContext(), "selected " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                Log.d("selected college", (String) parent.getItemAtPosition(position));
                switch ((String) parent.getItemAtPosition(position)){
                    case "Basic and Applied Sciences":
                        selectedCollegeID = ID_COLLEGE_BASIC_AND_APPLIED_SCIENCES;
                        getSchoolsData();
                        break;
                    case "Education":
                        selectedCollegeID = ID_COLLEGE_EDUCATION;
                        getSchoolsData();
                        break;
                    case "Health Sciences":
                        selectedCollegeID = ID_COLLEGE_HEALTH_SCIENCES;
                        getSchoolsData();
                        break;
                    case "Humanities":
                        selectedCollegeID = ID_COLLEGE_HUMANITIES;
                        getSchoolsData();
                        break;
                }
                break;
            case R.id.school:
                Log.d("selected school", (String) parent.getItemAtPosition(position));
                Log.d("school_id", schools.get( parent.getItemAtPosition(position)));
                switch ((String) parent.getItemAtPosition(position)){
                    case "Agriculture":
                        selectedSchoolID = schools.get("Agriculture");
                        Log.d("selectedSchoolID", selectedSchoolID);
                        getDeptData();
                        break;
                    case "Biotechnology Research Centre":
                        selectedSchoolID = schools.get("Biotechnology Research Centre");
                        Log.d("selectedSchoolID", selectedSchoolID);
                        getDeptData();
                        break;
                    case "Biological Sciences":
                        selectedSchoolID = schools.get("Biological Sciences");
                        Log.d("selectedSchoolID", selectedSchoolID);
                        getDeptData();
                        break;
                    case "Engineering Sciences":
                        selectedSchoolID = schools.get("Engineering Sciences");
                        Log.d("selectedSchoolID", selectedSchoolID);
                        getDeptData();
                        break;
                    case "Institute of Applied Science and Technology":
                        selectedSchoolID=schools.get("Institute of Applied Science and Technology");
                        Log.d("selectedSchoolID", selectedSchoolID);
                        getDeptData();
                        break;
                    case "Institute for Environment and Sanitation Studies":
                        selectedSchoolID=schools.get("Institute for Environment and Sanitation Studies");
                        Log.d("selectedSchoolID", selectedSchoolID);
                        getDeptData();
                        break;
                    case "School of Physical and Mathematical Sciences":
                        selectedSchoolID=schools.get("School of Physical and Mathematical Sciences");
                        Log.d("selectedSchoolID", selectedSchoolID);
                        getDeptData();
                        break;
                    case "School of Veterinary Medicine":
                        selectedSchoolID=schools.get("School of Veterinary Medicine");
                        Log.d("selectedSchoolID", selectedSchoolID);
                        getDeptData();
                        break;
                }
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Retrieve Colleges
    private void getCollegeData() {
        sendCollegeJsonRequest();
    }

    private void sendCollegeJsonRequest() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("collegeCheck", "true");
        params.put("schoolCheck", "false");

        CustomArrayRequest jsObjRequest = new CustomArrayRequest(Request.Method.POST, KEY_BASE_URL.concat(KEY_REGISTER_FINAL_URL), params,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        parseCollegeJSONResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Log.d("College Error", error.getMessage());
                Toast.makeText(getApplicationContext(), "Unable to retrieve College Details\n" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }

    private void parseCollegeJSONResponse(JSONArray response) {
        Toast.makeText(getApplicationContext(), "Entered parsecollegeJson", Toast.LENGTH_SHORT).show();
        if (response == null || response.length() == 0) {
            return;
        }
        try {
            Log.d("Json String", response.toString());
            String coll;
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                coll = obj.getString("college_name");
                colleges.add(coll);
            }
            Log.d("Colleges String", colleges.toString());

            COLLEGES = colleges.toArray(new String[colleges.size()]);
            Log.d("Colleges", COLLEGES.toString());

            // Setting a Custom Adapter to the Spinner
            col.setAdapter(new MyAdapter(FinalSignUp.this, R.layout.custom, COLLEGES));

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    //Retrieve Schools
    private void getSchoolsData() {
        sendSchoolsJsonRequest();
    }

    private void sendSchoolsJsonRequest() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("schoolCheck", "true");
        params.put("collegeCheck", "false");
        params.put("collegeID", selectedCollegeID);

        CustomArrayRequest jsObjRequest = new CustomArrayRequest(Request.Method.POST, KEY_BASE_URL.concat(KEY_REGISTER_FINAL_URL), params,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        parseSchoolsJSONResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Log.d("Retrieve School Error", error.getMessage());
                Toast.makeText(getApplicationContext(), "Unable to retrieve School Details\n" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }

    private void parseSchoolsJSONResponse(JSONArray response) {
        if (response == null || response.length() == 0) {
            return;
        }
        try {
            Log.d("Json String", response.toString());
            schools.clear();
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                schools.put(obj.getString("school_name"), obj.getString("school_id"));
            }
            Log.d("Schools String", schools.toString());

            SCHOOLS = schools.keySet().toArray(new String[schools.keySet().size()]);
            Log.d("Schools", SCHOOLS.toString());

            sch.setAdapter(new MyAdapter(FinalSignUp.this, R.layout.custom, SCHOOLS));
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    //Retrieve Depts
    private void getDeptData(){
        sendDeptJsonRequest();
    }

    private void sendDeptJsonRequest(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("schoolCheck", "false");
        params.put("collegeCheck", "false");
        params.put("deptCheck", "true");
        params.put("collegeID", selectedCollegeID);
        params.put("schoolID", selectedSchoolID);

        CustomArrayRequest jsObjRequest = new CustomArrayRequest(Request.Method.POST, KEY_BASE_URL.concat(KEY_REGISTER_FINAL_URL), params,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        parseDeptJsonRequest(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Log.d("Retrieve Dept Error", error.getMessage());
                Toast.makeText(getApplicationContext(), "Unable to retrieve Department Details\n" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }

    private void parseDeptJsonRequest(JSONArray response){
        if (response == null || response.length() == 0) {
            return;
        }
        try {
            Log.d("Json String", response.toString());
            depts.clear();
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                depts.put(obj.getString("dept_name"), obj.getString("dept_id"));
            }
            Log.d("Depts String", depts.toString());

            DEPTS = depts.keySet().toArray(new String[depts.keySet().size()]);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    //retrieve courses
    private void getCoursesData(){
        sendCoursesJsonRequest();
    }

    private void sendCoursesJsonRequest(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("schoolCheck", "false");
        params.put("collegeCheck", "false");
        params.put("deptCheck", "false");
        params.put("courseCheck", "true");
        params.put("deptIDs", selectedDeptIDs);
        params.put("level", "" + student.getLevel());



        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, KEY_BASE_URL.concat(KEY_REGISTER_FINAL_URL), params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        parseCoursesJsonRequest(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Log.d("Retrieve Courses Error", error.getMessage());
                Toast.makeText(getApplicationContext(), "Unable to retrieve Courses Details\n" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }

    private void parseCoursesJsonRequest(JSONObject response){
        if (response == null || response.length() == 0) {
            return;
        }
        try {
            Log.d("Json String", response.toString());
            courses.clear();
            int numQueries=selectedDepts.size();
            for (int i = 0; i < numQueries; i++) {
                JSONArray arr = response.getJSONArray("query" + i);
                for (int j=0;j<arr.length();j++){
                    JSONObject obj=arr.getJSONObject(j);
                    courses.put(obj.getString("course_title"), obj.getString("course_id"));
                }
            }
            Log.d("Courses String", courses.toString());

            COURSES = courses.keySet().toArray(new String[courses.size()]);
            Log.d("Courses", courses.toString());
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
