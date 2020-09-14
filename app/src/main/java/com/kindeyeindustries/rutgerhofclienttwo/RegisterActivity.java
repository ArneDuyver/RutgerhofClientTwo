package com.kindeyeindustries.rutgerhofclienttwo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "RegisterActivity";
    private TextView error,text;
    private Button btn;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_layout);

        btn = findViewById(R.id.registerActivity_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
        error = findViewById(R.id.websiteMenu_tv_error);
        text = findViewById(R.id.registerActivity_tv_text);

        if (isConnectedToInternet()){
            WebView webView = findViewById(R.id.registerActivity_webView);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSdwBYcrg2jO3T2D6WAdi9ESnwJ0nRrtuntXX-WT-TheiEsgLQ/viewform?usp=sf_link");
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
        } else {
            error.setText(R.string.mainActivity_noInternetConnection);
        }
    }

    public boolean isConnectedToInternet(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

        return connected;
    }
}