package com.example.browser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
// I learned how to use RecycleView and adapter from this tutorial:
// https://www.youtube.com/watch?v=Mc0XT58A1Z4&t=1309s
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {
    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private Activity activity;
    private ArrayList websiteId, websiteTitle, websiteUrl;

    CustomAdapter(Activity activity, Context context, ArrayList website_id, ArrayList website_title, ArrayList website_url, RecyclerViewInterface recyclerViewInterface){
        this.activity = activity;
        this.context = context;
        this.websiteId = website_id;
        this.websiteTitle = website_title;
        this.websiteUrl = website_url;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_row, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.website_id.setText(String.valueOf(websiteId.get(position)));
        holder.website_title.setText(String.valueOf(websiteTitle.get(position)));
        holder.website_url.setText(String.valueOf(websiteUrl.get(position)));
    }

    @Override
    public int getItemCount() {
        return websiteId.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView website_id, website_title, website_url;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            website_id = itemView.findViewById(R.id.websiteId);
            website_title = itemView.findViewById(R.id.websiteTitle);
            website_url = itemView.findViewById(R.id.websiteUrl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!= null){
                        int pos = getAdapterPosition();

                        if(pos!= RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(recyclerViewInterface!= null){
                        int pos = getAdapterPosition();

                        if(pos!= RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemLongClick(pos);
                        }
                    }
                    return true;
                }
            });
        }
    }
}
