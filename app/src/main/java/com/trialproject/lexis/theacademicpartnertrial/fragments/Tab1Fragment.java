package com.trialproject.lexis.theacademicpartnertrial.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.trialproject.lexis.theacademicpartnertrial.R;
import com.trialproject.lexis.theacademicpartnertrial.api.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;
import static com.trialproject.lexis.theacademicpartnertrial.api.Keys.JsonItems.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String user;

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    String API = "https://api.github.com"; //BASE URL
    Button button;
    TextView tv;
    EditText edit;
    ProgressBar pbar;

    public Tab1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1Fragment newInstance(String param1, String param2) {
        Tab1Fragment fragment = new Tab1Fragment();
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
        View layout = inflater.inflate(R.layout.fragment_tab1, container, false);
        button = (Button) layout.findViewById(R.id.button);
        tv = (TextView) layout.findViewById(R.id.click);
        edit = (EditText) layout.findViewById(R.id.edit);
        pbar = (ProgressBar) layout.findViewById(R.id.proBar);
        pbar.setVisibility(View.INVISIBLE);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                user = edit.getText().toString();
                pbar.setVisibility(View.VISIBLE);

                //Volley section start from here...
                volleySingleton = VolleySingleton.getInstance();
                requestQueue = volleySingleton.getRequestQueue();
                sendJsonRequest();

            }

        });
        return layout;
    }

    private void sendJsonRequest(){
        JSONObject obj=null;
        JsonObjectRequest request = new JsonObjectRequest(Method.GET, API + user, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                parseJSONResponse(response);
                pbar.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setText(error.getMessage());
                pbar.setVisibility(View.INVISIBLE);
            }
        });
        requestQueue.add(request);

    }

    private void parseJSONResponse(JSONObject response){
        if (response==null || response.length()==0){
            return;
        }
        try{
            if (response.has(KEY_NAME)){
                String name = "Github Name :"+response.getString(KEY_NAME);
                tv.setText( name);
            }

        }catch (JSONException e){
            tv.setText(e.getMessage());

        }
    }

}
