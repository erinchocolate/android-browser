package com.example.browser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private EditText url;
    private ImageView refresh;
    private ImageView home;
    private ImageView back;
    private ImageView forward;
    private ImageView go;
    private ImageView page;
    private ImageView webIcon;
    private InputMethodManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        init();
        initWeb();
    }

    public void init(){
        webView = findViewById(R.id.webView);
        webIcon = findViewById(R.id.webIcon);
        refresh = findViewById(R.id.refresh);
        back = findViewById(R.id.back);
        forward = findViewById(R.id.forward);
        go = findViewById(R.id.go);
        home = findViewById(R.id.home);
        page = findViewById(R.id.page);
        url = findViewById(R.id.url);

        //Setup click events
        View.OnClickListener clickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    //Refresh
                    case R.id.refresh:
                        webView.reload();
                        break;

                    //Forward
                    case R.id.forward:
                        onForwardPressed();
                        break;

                    //Back
                    case R.id.back:
                        onBackPressed();
                        break;

                    //Home page
                    case R.id.home:
                        webView.loadUrl("https://www.google.com");
                        break;
                    //Go
                    case R.id.go:
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
                            // 地址栏没焦点，是刷新
                            webView.reload();
                        }

                        break;
                }
            }
        };
        refresh.setOnClickListener(clickListener);
        go.setOnClickListener(clickListener);
        forward.setOnClickListener(clickListener);
        back.setOnClickListener(clickListener);
        home.setOnClickListener(clickListener);
        page.setOnClickListener(clickListener);

        url.setOnFocusChangeListener(new View.OnFocusChangeListener(){

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
        });

        url.setOnKeyListener(new View.OnKeyListener(){

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    go.callOnClick();
                    url.clearFocus();
                }
                return false;
            }
        });

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


    public void initWeb(){
        webView.loadUrl("https://www.google.com");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_back:
                onBackPressed();
                break;
            case R.id.menu_forward:
                onForwardPressed();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        if(webView.canGoBack()){
            webView.goBack();
        }
        else{
            finish();
        }
    }

    public void onForwardPressed() {
        if (webView.canGoForward()) {
            webView.goForward();
        }
        else{
            Toast.makeText(this, "can't go further", Toast.LENGTH_SHORT).show();
        }
    }

}