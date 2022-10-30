package com.example.browser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView recyclerView;
    ArrayList<String> website_id, website_title, website_url;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.historyView);

        website_title = getIntent().getStringArrayListExtra("title");
        website_id = getIntent().getStringArrayListExtra("id");
        website_url = getIntent().getStringArrayListExtra("url");

        customAdapter = new CustomAdapter(HistoryActivity.this,this, website_id, website_title, website_url, this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("url", website_url.get(position));
        startActivity(intent);
    }
}