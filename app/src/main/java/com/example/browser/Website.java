package com.example.browser;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class Website {
    private String url;
    private String title;
    private int id;
    private Bitmap icon;

    public Website(String url) {
        this.url = url;
    }

    public Website(String url,String title){
        this.url=url;
        this.title=title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getImage() {
        return icon;
    }

}
