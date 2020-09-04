package com.kindeyeindustries.rutgerhofclienttwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private FirebaseFirestore db;
    private Button menu,restaurant,taverne,terras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        menu = findViewById(R.id.mainActivity_btn_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WebsiteMenu.class));
            }
        });

        restaurant = findViewById(R.id.mainActivity_btn_restaurant);
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFirebase(1);
            }
        });
        taverne = findViewById(R.id.mainActivity_btn_taverne);
        taverne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFirebase(2);
            }
        });
        terras = findViewById(R.id.mainActivity_btn_terras);
        terras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFirebase(3);
            }
        });


    }

    @SuppressLint("ResourceAsColor")
    private void setFirebase(int index) {
        if (isConnectedToInternet()){
            DocumentReference rutgerhofRef = db.collection("Rutgerhof").document("N91YwwPaBhR4p1vy09TK");
            String field = null;
            switch (index){
                case 1:
                    field = "Restaurant";
                    break;
                case 2:
                    field = "Taverne";
                    break;
                case 3:
                    field = "Terras";
                    break;
            }
            assert field != null;
            rutgerhofRef.update(field,true)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully updated!");
                            showToast(R.string.mainActivity_ToastMessage);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating document", e);
                            showToast(R.string.mainActivity_ToastFail);
                        }
                    });
        }
        else {
            showToast(R.string.mainActivity_noInternetConnection);
        }
    }

    public void showToast(int message){
        Toast toast = Toast.makeText(this,message,Toast.LENGTH_LONG);
        /*
        View view = toast.getView();
        Log.d(TAG, "setFirebase: "+(toast == null));

        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(R.color.colorAccent, PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(R.color.toastText);
        */
        toast.show();
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
                //startActivity(new Intent(MainActivity.this, MainActivity.class));
                return true;
            case R.id.menu_menu:
                startActivity(new Intent(MainActivity.this, WebsiteMenu.class));
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