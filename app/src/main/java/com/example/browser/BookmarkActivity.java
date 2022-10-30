package com.example.browser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView recyclerView;
    ArrayList<String> website_id, website_title, website_url;
    DatabaseHelper db;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        recyclerView = findViewById(R.id.recyclerView);

        website_id = new ArrayList<>();
        website_url = new ArrayList<>();
        website_title = new ArrayList<>();

        db = new DatabaseHelper(BookmarkActivity.this);

        storeBookmarkDataInArray();

        customAdapter = new CustomAdapter(BookmarkActivity.this,this, website_id, website_title, website_url, this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BookmarkActivity.this));
    }


    public void storeBookmarkDataInArray(){
        Cursor cursor = db.readBookmarkData();
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

    }
}