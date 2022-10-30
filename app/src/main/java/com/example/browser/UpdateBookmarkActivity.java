package com.example.browser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateBookmarkActivity extends AppCompatActivity {
    EditText bookmarkTitle, bookmarkUrl;
    Button update, delete;
    String id, url, title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bookmark);

        bookmarkTitle = findViewById(R.id.bookmarkTitle);
        bookmarkUrl = findViewById(R.id.bookmarkUrl);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);

        //Get edited bookmark info: url, title and id
        getIntentData();

        //Update user input into database
        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(UpdateBookmarkActivity.this);
                title = bookmarkTitle.getText().toString().trim();
                url = bookmarkUrl.getText().toString().trim();
                db.updateBookmark(id, title, url);
            }
        });

        //Update selected bookmark
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(UpdateBookmarkActivity.this);
                db.deleteOneBookmark(id);
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
            bookmarkTitle.setText(title);
            bookmarkUrl.setText(url);

        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }


}