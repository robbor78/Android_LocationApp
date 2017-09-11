package com.domain.company.locationapp;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyLocationListener implements LocationListener {

    private static final String TAG = "MyLocationListener";

    private MainActivity a = null;

    public MyLocationListener(MainActivity a) {
        this.a = a;
    }

    public void onLocationChanged(Location location) {
        Log.d(TAG, location.getLatitude() + " " + location.getLongitude());
        TextView tv = (TextView) a.findViewById(R.id.tv);
        tv.setText(LocationFormatter.f("Latest",location));
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }

}
