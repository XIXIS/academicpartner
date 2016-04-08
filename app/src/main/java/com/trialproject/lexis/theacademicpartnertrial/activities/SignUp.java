package com.trialproject.lexis.theacademicpartnertrial.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.trialproject.lexis.theacademicpartnertrial.R;
import com.trialproject.lexis.theacademicpartnertrial.api.CustomArrayRequest;
import com.trialproject.lexis.theacademicpartnertrial.api.CustomRequest;
import com.trialproject.lexis.theacademicpartnertrial.api.VolleySingleton;
import com.trialproject.lexis.theacademicpartnertrial.helperclasses.Student;
import com.trialproject.lexis.theacademicpartnertrial.projectactivities.FinalSignUp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.ID_LEVEL_100;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.ID_LEVEL_200;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.ID_LEVEL_300;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.ID_LEVEL_400;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.ID_LEVEL_600;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_BASE_URL;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_MESSAGE;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_REGISTER_URL;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_SUCCESS;

public class SignUp extends AppCompatActivity {
    TextView idNumberTV, pinTV, levelTV, alreadySUTV, signInTV,
            appName, next;
    EditText idNumber, pin;
    Spinner level;
    Typeface face;
    private Student student;
    private String studentLevel;
    private static String[] LEVELS;
    private VolleySingleton volleySingleton;
    private String studentID, studentPin, selectedLevel;
    private RequestQueue requestQueue;
    private ArrayList<String> levels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        student = new Student();
        idNumberTV = (TextView) findViewById(R.id.idNumberTextView);
        pinTV = (TextView) findViewById(R.id.pinTextView);
        levelTV = (TextView) findViewById(R.id.levelTextView);
        alreadySUTV = (TextView) findViewById(R.id.alreadySignedUp);
        signInTV = (TextView) findViewById(R.id.signIn);
        appName = (TextView) findViewById(R.id.appName);
        next = (TextView) findViewById(R.id.next);
        idNumber = (EditText) findViewById(R.id.idNumber);
        pin = (EditText) findViewById(R.id.pin);
        level = (Spinner) findViewById(R.id.level);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        face = Typeface.createFromAsset(getAssets(), "Quicksand_Light.ttf");
        idNumberTV.setTypeface(face);
        idNumber.setTypeface(face, Typeface.BOLD);
        pinTV.setTypeface(face);
        pin.setTypeface(face, Typeface.BOLD);
        levelTV.setTypeface(face);
        alreadySUTV.setTypeface(face);
        signInTV.setTypeface(face, Typeface.BOLD);
        appName.setTypeface(face);
        next.setTypeface(face);

        signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,SignIn.class));
                finish();
            }
        });

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

        getLevelsData();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentID = idNumber.getText().toString();
                studentPin = pin.getText().toString();
                studentLevel = level.getSelectedItem().toString();
                switch(studentLevel){
                    case "100":
                        selectedLevel=ID_LEVEL_100;
                        break;
                    case "200":
                        selectedLevel=ID_LEVEL_200;
                        break;
                    case "300":
                        selectedLevel=ID_LEVEL_300;
                        break;
                    case "400":
                        selectedLevel=ID_LEVEL_400;
                        break;
                    case "600":
                        selectedLevel=ID_LEVEL_600;
                        break;
                }
                sendFirstSignUpJsonRequest();
            }
        });

    }

    private void getLevelsData() {
        sendLevelsJsonRequest();
    }

    private void sendLevelsJsonRequest() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("levelCheck", "true");

        CustomArrayRequest jsObjRequest = new CustomArrayRequest(Request.Method.POST, KEY_BASE_URL.concat(KEY_REGISTER_URL), params,
                new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                parseLevelJSONResponse(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection\n" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }

    private void parseLevelJSONResponse(JSONArray response) {
        if (response == null || response.length() == 0) {
            return;
        }
        try {
            Log.d("Json String", response.toString());
            String lev;
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                lev=obj.getString("level_number");
                levels.add(lev);
            }
            Log.d("Levels String", levels.toString());

            LEVELS = levels.toArray(new String[levels.size()]);
            Log.d("Levels", LEVELS.toString());

            // Setting a Custom Adapter to the Spinner
            level.setAdapter(new MyAdapter(SignUp.this, R.layout.custom, LEVELS));
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    private void sendFirstSignUpJsonRequest() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("studentID", studentID);
        params.put("studentPin", studentPin);
        params.put("level_id", selectedLevel);


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, KEY_BASE_URL.concat(KEY_REGISTER_URL),
                params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                parseFirstSignUpJSONResponse(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {

                Toast.makeText(getApplicationContext(), "ID already in use\n" + error.getMessage(), Toast.LENGTH_LONG).show();


            }
        });

        requestQueue.add(jsObjRequest);
    }

    private void parseFirstSignUpJSONResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "Entered parseFirstSignUpJson", Toast.LENGTH_SHORT).show();
        if (response == null || response.length() == 0) {
            return;
        }
        try {
            Log.d("Json String", response.toString());
            boolean goToFinalSignUp=false;
            if (response.has(KEY_SUCCESS) && response.has(KEY_MESSAGE)) {
                int success = response.getInt(KEY_SUCCESS);
                String message = response.getString(KEY_MESSAGE);
                if (success == 1 && message.equalsIgnoreCase("user successfully added!")) {
                    student.setId(Integer.parseInt(studentID));
                    student.setPin(Integer.parseInt(studentPin));
                    student.setLevel(Integer.parseInt(studentLevel));
                    goToFinalSignUp = true;
                }
                if (goToFinalSignUp) {
                    Intent intent = new Intent(getApplicationContext(), FinalSignUp.class);
                    intent.putExtra("student", student);
                    startActivity(intent);

                }
            }

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
