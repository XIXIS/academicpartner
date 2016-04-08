package com.trialproject.lexis.theacademicpartnertrial.other;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trialproject.lexis.theacademicpartnertrial.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Lexis on 09/01/2016.
 */
public class RecViewAdpater extends RecyclerView.Adapter <RecViewAdpater.MyViewHolder>{

    private LayoutInflater inflater;
    List<ViewInfo> data= Collections.emptyList();

    public RecViewAdpater(Context context, List<ViewInfo> data){
        inflater=LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ViewInfo currentInfo=data.get(position);
        holder.title.setText(currentInfo.title);
        holder.icon.setImageResource(currentInfo.iconId);

    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.listText);
            icon=(ImageView) itemView.findViewById(R.id.listIcon);
        }
    }

}
