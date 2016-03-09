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

import com.trialproject.lexis.theacademicpartnertrial.R;
import com.trialproject.lexis.theacademicpartnertrial.api.gitapi;
import com.trialproject.lexis.theacademicpartnertrial.model.gitmodel;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String API = "https://api.github.com";                         //BASE URL

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button button;
    TextView tv;
    EditText edit;
    ProgressBar pbar;


    public Tab2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab2Fragment newInstance(String param1, String param2) {
        Tab2Fragment fragment = new Tab2Fragment();
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
        View layout = inflater.inflate(R.layout.fragment_tab2, container, false);
        button = (Button) layout.findViewById(R.id.button);
        tv = (TextView) layout.findViewById(R.id.click);
        edit = (EditText) layout.findViewById(R.id.edit);
        pbar = (ProgressBar) layout.findViewById(R.id.proBar);
        pbar.setVisibility(View.INVISIBLE);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String user = edit.getText().toString();
                pbar.setVisibility(View.VISIBLE);

                //Retrofit section start from here...
                RestAdapter restAdapter = new RestAdapter.Builder()

                //create an adapter for retrofit with base url
                .setEndpoint(API).build();

                //creating a service for adapter with our GET class
                gitapi git = restAdapter.create(gitapi.class);

                //Now ,we need to call for response
                //Retrofit using gson for JSON-POJO conversion
                git.getFeed(user, new Callback<gitmodel>() {

                    @Override
                    public void success(gitmodel gitmodel, Response response) {

                        //we get json object from github server to our POJO or model class
                        String name= "Github Name :"+gitmodel.getName();
                        String blog="\nWebsite :"+gitmodel.getBlog();
                        String company="\nCompany Name :"+gitmodel.getCompany();
                        String string=name+blog+company;

                        tv.setText(string);

                        //disable progressbar
                        pbar.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        tv.setText(error.getMessage());

                        //disable progressbar
                        pbar.setVisibility(View.INVISIBLE);
                    }

                });

            }

        });


        return layout;
    }

}
