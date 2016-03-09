package com.trialproject.lexis.theacademicpartnertrial.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trialproject.lexis.theacademicpartnertrial.R;

/**
 * Created by Lexis on 12/01/2016.
 */
public class MyFragment extends Fragment {

    private TextView text;

    public static MyFragment getInstance(int position){
        MyFragment myFragment=new MyFragment();
        Bundle args=new Bundle();
        args.putInt("position", position);
        myFragment.setArguments(args);
        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_my, container, false);
        text=(TextView) layout.findViewById(R.id.position);
        Bundle bundle=getArguments();
        if (bundle != null){
            text.setText("The Page Selected Is "+bundle.getInt("position"));
        }
        return layout;
    }
}
