package com.example.browser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView recyclerView;
    ArrayList<String> websiteId, websiteTitle, websiteUrl;
    DatabaseHelper db;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        recyclerView = findViewById(R.id.recyclerView);

        websiteId = new ArrayList<>();
        websiteUrl = new ArrayList<>();
        websiteTitle = new ArrayList<>();

        db = new DatabaseHelper(HistoryActivity.this);

        storeHistoryDataInArray();

        customAdapter = new CustomAdapter(HistoryActivity.this,this, websiteId, websiteTitle, websiteUrl, this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
    }

    public void storeHistoryDataInArray(){
        Cursor cursor = db.readHistoryData();
        if(cursor.getCount()==0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                websiteId.add(cursor.getString(0));
                websiteTitle.add(cursor.getString(1));
                websiteUrl.add(cursor.getString(2));
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("url", websiteUrl.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {

    }
}