package com.example.browser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {

    private Context context;
    private Activity activity;
    private ArrayList website_id, website_title, website_url;

    CustomAdapter(Activity activity, Context context, ArrayList website_id, ArrayList website_title, ArrayList website_url){
        this.activity = activity;
        this.context = context;
        this.website_id = website_id;
        this.website_title = website_title;
        this.website_url = website_url;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.website_id.setText(String.valueOf(website_id.get(position)));
        holder.website_title.setText(String.valueOf(website_title.get(position)));
        holder.website_url.setText(String.valueOf(website_url.get(position)));
    }

    @Override
    public int getItemCount() {
        return website_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView website_id, website_title, website_url;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            website_id = itemView.findViewById(R.id.website_id);
            website_title = itemView.findViewById(R.id.website_title);
            website_url = itemView.findViewById(R.id.website_url);

        }
    }
}
