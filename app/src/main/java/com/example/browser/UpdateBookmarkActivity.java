package com.example.browser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateBookmarkActivity extends AppCompatActivity {
    EditText bookmark_title, bookmark_url;
    Button update;
    String id, url, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bookmark);

        bookmark_title = findViewById(R.id.bookmark_title);
        bookmark_url = findViewById(R.id.bookmark_url);
        update = findViewById(R.id.update);

        getIntentData();
        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(UpdateBookmarkActivity.this);
                title = bookmark_title.getText().toString().trim();
                url = bookmark_url.getText().toString().trim();
                db.updateData(id, title, url);
            }
        });
    }

    void getIntentData(){
        if(getIntent().hasExtra("title")&&getIntent().hasExtra("url")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            url = getIntent().getStringExtra("url");

            //Setting Intent Data
            bookmark_title.setText(title);
            bookmark_url.setText(url);
            //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }


}