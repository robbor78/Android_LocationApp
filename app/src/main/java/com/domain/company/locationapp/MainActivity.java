package com.domain.company.locationapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private LocationManager locationManager = null;
    private MyLocationListener locationListener = null;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        if (canAccessLocation()) {
            setupLocation();
        } else {

            requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        }


    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onStop() {
        super.onStop();

        try {
            Log.d(TAG, "Stopping locationManager...");
            if (locationManager != null) {
                locationManager.removeUpdates(locationListener);
                Log.d(TAG,"locationManager removed listener");
            }
            Log.d(TAG,"Stopping locationManager end.");
        } catch (SecurityException e) {
            e.printStackTrace();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            finish();
            //moveTaskToBack(true);
            //android.os.Process.killProcess(android.os.Process.myPid());
            //System.exit(1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean canAccessLocation() {
        return (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP || hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult");
        setupLocation();
    }

    private void setupLocation() {
        try {

            Log.d(TAG, "setupLocation");


            locationListener = new MyLocationListener(this);
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();

        }

        try {
            Log.d(TAG, "getLastKnownLocation");
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location l = locationNet != null ? locationNet : locationGPS != null ? locationGPS : null;
            if (l != null) {
                Log.d(TAG, l.getLatitude() + " " + l.getLongitude());
                TextView tv = (TextView) findViewById(R.id.tv);
                tv.setText(LocationFormatter.f("Last Known Location",l));
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}