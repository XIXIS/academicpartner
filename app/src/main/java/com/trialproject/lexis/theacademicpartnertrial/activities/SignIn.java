package com.trialproject.lexis.theacademicpartnertrial.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.trialproject.lexis.theacademicpartnertrial.R;
import com.trialproject.lexis.theacademicpartnertrial.api.CustomRequest;
import com.trialproject.lexis.theacademicpartnertrial.api.VolleySingleton;
import com.trialproject.lexis.theacademicpartnertrial.projectactivities.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import android.os.Handler;

import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_BASE_URL;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_LOGIN_URL;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_MESSAGE;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.KEY_SUCCESS;

public class SignIn extends AppCompatActivity {

    TextView idNumberTV, pinTV, appName, signIn;
    EditText idNumber, pin;
    private boolean loginSuccessful = false;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String studentID;
    private String studentPin;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        idNumberTV = (TextView) findViewById(R.id.idNumberTextView);
        pinTV = (TextView) findViewById(R.id.pinTextView);
        appName = (TextView) findViewById(R.id.appName);
        signIn = (TextView) findViewById(R.id.signIn);
        idNumber = (EditText) findViewById(R.id.idNumber);
        pin = (EditText) findViewById(R.id.pin);

        Typeface face = Typeface.createFromAsset(getAssets(), "Quicksand_Light.ttf");
        idNumberTV.setTypeface(face);
        pinTV.setTypeface(face);
        idNumber.setTypeface(face, Typeface.BOLD);
        pin.setTypeface(face, Typeface.BOLD);
        appName.setTypeface(face);
        signIn.setTypeface(face, Typeface.BOLD);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pDialog = new ProgressDialog(SignIn.this);
                pDialog.setMessage("signing In For The First Time...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();


                studentID = idNumber.getText().toString();
                studentPin = pin.getText().toString();

                volleySingleton = VolleySingleton.getInstance();
                requestQueue = volleySingleton.getRequestQueue();

                verifyLoginDetails();

            }
        });
    }

    private void verifyLoginDetails() {
        sendJsonRequest();
    }

    private void sendJsonRequest() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("studentID", studentID);
        params.put("studentPin", studentPin);


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, KEY_BASE_URL.concat(KEY_LOGIN_URL), params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                parseJSONResponse(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {

                Runnable progressRunnable = new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Login failed Try Again\n" + error.getMessage(), Toast.LENGTH_LONG).show();
                        pDialog.dismiss();
                    }
                };

                Handler pdCanceller = new Handler();
                pdCanceller.postDelayed(progressRunnable, 3000);


            }
        });

        requestQueue.add(jsObjRequest);
    }

    private void parseJSONResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "Entered parseJson", Toast.LENGTH_SHORT).show();
        if (response == null || response.length() == 0) {
            return;
        }
        try {
            Log.d("Json String", response.toString());
            if (response.has(KEY_SUCCESS) && response.has(KEY_MESSAGE)) {
                int success = response.getInt(KEY_SUCCESS);
                String message = response.getString(KEY_MESSAGE);
                if (success == 1 && message.equalsIgnoreCase("login successful!")) {
                    loginSuccessful = true;
                }
                if (loginSuccessful) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
            }

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}
