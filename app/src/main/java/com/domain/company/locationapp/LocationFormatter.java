package com.domain.company.locationapp;

import android.location.Location;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationFormatter {
    public static String f(String info, Location location) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String timeNow = dateFormat.format(date);

        date = new Date(location.getTime());
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
        String timeFromLocation = formatter.format(date);

        double lng = location.getLongitude();
        double lat = location.getLatitude();

        String s = String.format(
                "%s\nTime now %s\nTime of reading: %s\n%s\nLatitude (N/S): %s (%s)\nLongitude(E/W): %s (%s)\nBearing: %.2f(deg)\nAltitude: %.2f(m)\nSpeed: %.2f(m/s), %,2f(km/h)",
                info,
                timeNow,
                timeFromLocation,
                location.describeContents(),
                Location.convert(lat, Location.FORMAT_SECONDS), lat < 0.0 ? "S" : "N",
                Location.convert(lng, Location.FORMAT_SECONDS), lng < 0.0 ? "W" : "E",
                location.getBearing(),
                location.getAltitude(),
                location.getSpeed(), (18.0 * location.getSpeed() / 5.0)
        );
        return s;
    }
}
