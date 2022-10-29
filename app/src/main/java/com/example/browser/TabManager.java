package com.example.browser;

import java.util.ArrayList;

public class TabManager{
    ArrayList<Website> tabs;

    public TabManager(){
        tabs = new ArrayList<Website>();
    }

    public void addTab(Website website){
        tabs.add(website);
    }

    public void removeTab(Website website){
        tabs.remove(website);
    }

    public int getTabNumber(){
        return tabs.size();
    }

}
