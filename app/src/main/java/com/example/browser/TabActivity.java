package com.example.browser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class TabActivity extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView recyclerView;
    ArrayList<String> website_id, website_title, website_url;
    CustomAdapter customAdapter;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        recyclerView = findViewById(R.id.tabView);

        website_id = new ArrayList<>();
        website_url = new ArrayList<>();
        website_title = new ArrayList<>();

        db = new DatabaseHelper(TabActivity.this);

        storeTabDataInArray();

        customAdapter = new CustomAdapter(TabActivity.this,this, website_id, website_title, website_url, this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TabActivity.this));
    }

    public void storeTabDataInArray(){
        Cursor cursor = db.readTabData();
        if(cursor.getCount()==0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                website_id.add(cursor.getString(0));
                website_title.add(cursor.getString(1));
                website_url.add(cursor.getString(2));
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("url", website_url.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        db.deleteOneTab(String.valueOf(position));
        customAdapter.notifyItemRemoved(position);
        finish();
    }
}