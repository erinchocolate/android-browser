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
    ArrayList<String> websiteId, websiteTitle, websiteUrl;
    CustomAdapter customAdapter;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        recyclerView = findViewById(R.id.tabView);

        websiteId = new ArrayList<>();
        websiteUrl = new ArrayList<>();
        websiteTitle = new ArrayList<>();

        db = new DatabaseHelper(TabActivity.this);

        storeTabDataInArray();

        customAdapter = new CustomAdapter(TabActivity.this,this, websiteId, websiteTitle, websiteUrl, this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TabActivity.this));
    }

    public void storeTabDataInArray(){
        Cursor cursor = db.readTabData();
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

    //Go to the website saved in the tab when user clicks the tab
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("url", websiteUrl.get(position));
        startActivity(intent);
    }

    //Delete tab when user clicks the tab longer
    @Override
    public void onItemLongClick(int position) {
        String id = websiteId.get(position);
        db.deleteOneTab(id);
        customAdapter.notifyItemRemoved(position);
        finish();
    }
}