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

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {
    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private Activity activity;
    private ArrayList website_id, website_title, website_url;

    CustomAdapter(Activity activity, Context context, ArrayList website_id, ArrayList website_title, ArrayList website_url, RecyclerViewInterface recyclerViewInterface){
        this.activity = activity;
        this.context = context;
        this.website_id = website_id;
        this.website_title = website_title;
        this.website_url = website_url;
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
        holder.website_id.setText(String.valueOf(website_id.get(position)));
        holder.website_title.setText(String.valueOf(website_title.get(position)));
        holder.website_url.setText(String.valueOf(website_url.get(position)));

//        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, UpdateBookmarkActivity.class);
//                intent.putExtra("id", String.valueOf(website_id.get(position)));
//                intent.putExtra("title", String.valueOf(website_title.get(position)));
//                intent.putExtra("url", String.valueOf(website_url.get(position)));
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return website_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView website_id, website_title, website_url;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            website_id = itemView.findViewById(R.id.website_id);
            website_title = itemView.findViewById(R.id.website_title);
            website_url = itemView.findViewById(R.id.website_url);
            mainLayout = itemView.findViewById(R.id.mainLayout);

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
