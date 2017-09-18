package com.example.shawyoungtang.facebooksearch.Utils;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by ShawYoungTang on 4/9/2017.
 */

public class MyLocationListener implements LocationListener {
    double lat;
    double log;

    public MyLocationListener(){
        lat = 10000;
        log = 10000;
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        log = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public double getLat() {
        return lat;
    }

    public double getLog() {
        return log;
    }
}
