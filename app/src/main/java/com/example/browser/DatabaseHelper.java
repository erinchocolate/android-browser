package com.example.browser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Browser.db";
    private static final int DATABASE_VERSION = 1;
    private static final String BOOKMARK_TABLE = "Bookmark";
    private static final String TAB_TABLE = "Tab";
    private static final String HISTORY_TABLE = "History";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "website_title";
    private static final String COLUMN_URL = "website_url";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String bookmarkQuery = "CREATE TABLE " + BOOKMARK_TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " + COLUMN_URL + " TEXT);";

        String tabQuery = "CREATE TABLE " + TAB_TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " + COLUMN_URL + " TEXT);";

        String historyQuery = "CREATE TABLE " + HISTORY_TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " + COLUMN_URL + " TEXT);";
        db.execSQL(tabQuery);
        db.execSQL(bookmarkQuery);
        db.execSQL(historyQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + BOOKMARK_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TAB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + HISTORY_TABLE);
        onCreate(db);
    }

    void addBookmark(String title, String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_URL, url);
        long result = db.insert(BOOKMARK_TABLE, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Bookmark saved", Toast.LENGTH_SHORT).show();
        }
    }

    void addTab(String title, String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_URL, url);
        long result = db.insert(TAB_TABLE, null, cv);
        if(result == -1){
            Toast.makeText(context, "Tab Insertion Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "New Tab", Toast.LENGTH_SHORT).show();
        }
    }

    void addHistory(String title, String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_URL, url);
        long result = db.insert(HISTORY_TABLE, null, cv);
        if(result == -1){
            Toast.makeText(context, "History Insertion Failed", Toast.LENGTH_SHORT).show();
        }
        else{

        }
    }

    void deleteOneTab(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TAB_TABLE, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Tab Delete Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Tab Delete Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteHistory(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + HISTORY_TABLE);
    }

    void updateData(String row_id, String title, String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_URL, url);
        long result = db.update(BOOKMARK_TABLE, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Bookmark update failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Bookmark update Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readBookmarkData(){
        String query = "SELECT * FROM " + BOOKMARK_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readTabData(){
        String query = "SELECT * FROM " + TAB_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readHistoryData(){
        String query = "SELECT * FROM " + HISTORY_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

}
