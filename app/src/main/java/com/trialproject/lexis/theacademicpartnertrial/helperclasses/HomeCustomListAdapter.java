package com.trialproject.lexis.theacademicpartnertrial.helperclasses;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.trialproject.lexis.theacademicpartnertrial.R;

/**
 * Created by Lexis on 3/26/2016.
 */
public class HomeCustomListAdapter extends ArrayAdapter {

    String[] mainListObjects;
    String[] subListObjecs;

    public HomeCustomListAdapter(Context context, int textViewResourceId,
                     String[] mainListObjects, String[] subListObjecs) {
        super(context, textViewResourceId, mainListObjects);
        this.mainListObjects = mainListObjects;
        this.subListObjecs=subListObjecs;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View layout = convertView;

        if (layout == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            layout = vi.inflate(R.layout.custom_home_list_row, null);
        }

        Typeface face=Typeface.createFromAsset(getContext().getAssets(), "Quicksand_Light.ttf");

        // Declaring and Typecasting the textview in the inflated layout
        TextView mainText = (TextView) layout.findViewById(R.id.mainText);
        TextView subText = (TextView) layout.findViewById(R.id.subText);

        mainText.setTypeface(face, Typeface.BOLD);
        subText.setTypeface(face, Typeface.BOLD);

        mainText.setText(mainListObjects[position]);
        subText.setText(subListObjecs[position]);

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
