package com.kindeyeindustries.rutgerhofclienttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

public class WebsiteMenu extends AppCompatActivity {
    public static final String TAG = "WebsiteMenu";
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.website_menu_layout);

        if (isConnectedToInternet()){
            WebView webView = new WebView(this);
            setContentView(webView);
            webView.loadUrl("https://rutgerhof.be/menu/");
        } else {
            error = findViewById(R.id.websiteMenu_tv_error);
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

    //<editor-fold desc="Menu">
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home:
                startActivity(new Intent(WebsiteMenu.this, MainActivity.class));
                return true;
            case R.id.menu_menu:
                startActivity(new Intent(WebsiteMenu.this, WebsiteMenu.class));
                return true;
            case R.id.menu_website:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://rutgerhof.be"));
                startActivity(browserIntent);
                return true;
            case R.id.menu_info:
                //TODO: make info activity and point to it
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //</editor-fold>
}