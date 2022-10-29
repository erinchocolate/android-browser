package com.example.browser;

import static java.net.Proxy.Type.HTTP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener, View.OnFocusChangeListener{
    private WebView webView;
    private EditText url;
    private ImageView go;
    private ImageView webIcon;
    private InputMethodManager manager;
    private String currentUrl = "https://www.google.com";
    private String currentTitle;
    private Bitmap currentIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initWeb();

    }

    public void init(){
        manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        webView = findViewById(R.id.webView);
        webIcon = findViewById(R.id.webIcon);
        go = findViewById(R.id.go);
        url = findViewById(R.id.url);
        go.setOnClickListener(this);
        url.setOnFocusChangeListener(this);
        url.setOnKeyListener(this);
    }

    public void initWeb(){
        webView.loadUrl("https://www.google.com");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                currentUrl = url;
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                // Display website name
                url.setText(title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                // Display website icon
                webIcon.setImageBitmap(icon);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (url.hasFocus()) {
            if (manager.isActive()) {
                manager.hideSoftInputFromWindow(url.getApplicationWindowToken(), 0);
            }
            String input = url.getText().toString();
            if (!isValidUrl(input)) {
                input = "https://www.google.com/search?q=" + input;
            }
            webView.loadUrl(input);
            url.clearFocus();
        }
        else {
            webView.reload();
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if(hasFocus){
            //Display current website url address
            url.setText(webView.getUrl());
            //Cursor at the end
            url.setSelection(url.getText().length());
            //Default icon
            webIcon.setImageResource(R.drawable.internet);
        }
        else{
            //Display current website title
            url.setText(webView.getTitle());
            //Display current website favicon
            webIcon.setImageBitmap(webView.getFavicon());
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if(keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
            go.callOnClick();
            url.clearFocus();
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            webView.getClass().getMethod("onPause").invoke(webView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            webView.getClass().getMethod("onResume").invoke(webView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isValidUrl(String url){
        String regex = "((http|https)://)(www.)?"
                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                + "{2,256}\\.[a-z]"
                + "{2,6}\\b([-a-zA-Z0-9@:%"
                + "._\\+~#?&//=]*)";

        Pattern p = Pattern.compile(regex.trim());
        if (url == null) {
            return false;
        }
        Matcher m = p.matcher(url);
        return m.matches();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_newTab:
                newTabPressed();
                break;
            case R.id.menu_back:
                backPressed();
                break;
            case R.id.menu_forward:
                forwardPressed();
                break;
            case R.id.menu_refresh:
                refreshPressed();
                break;
            case R.id.menu_home:
                homePressed();
                break;
            case R.id.menu_share:
                sharePressed();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void newTabPressed(){
        Website tab = new Website(currentUrl, currentTitle);
        tab.setIcon(currentIcon);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void homePressed(){
        webView.loadUrl("https://www.google.com");
    }

    public void backPressed(){
        if(webView.canGoBack()){
            webView.goBack();
        }
        else{
            finish();
        }
    }

    public void forwardPressed() {
        if (webView.canGoForward()) {
            webView.goForward();
        }
        else{
            Toast.makeText(this, "can't go further", Toast.LENGTH_SHORT).show();
        }
    }

    public void refreshPressed(){
        webView.reload();
    }

    public void sharePressed(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, currentUrl);
        Intent chooser = Intent.createChooser(intent, "Share URL with friends");
        try {
            startActivity(chooser);
        } catch (ActivityNotFoundException e) {
            Log.d("error", "share failed");
        }
    }


}