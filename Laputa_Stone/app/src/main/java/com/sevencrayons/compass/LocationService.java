package com.sevencrayons.compass;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

/**
 * Created by kaorihirata on 2017-08-18.
 */

public class LocationService implements LocationListener {
    private LocationManager mLocationManager;
    public double mUserLocationLat;
    public double mUserLocationLng;
    private String TAG ="LocationService";
    private Callback mCallback;

    interface Callback {
        void onLocationChanged(double lat, double lng);
    }

    LocationService(Activity activity, Callback callback) {
        mLocationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        mCallback = callback;

            ////// PERMISSION ////////
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            // CHECK PERMISSIONS
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 99);
            } else {
                // PERMISSION HAS BEEN GRANTED
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
            }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mUserLocationLat = location.getLatitude();
        mUserLocationLng = location.getLongitude();
        Log.i(TAG,"Latitude"+ location.getLatitude() + ", Longitude:" + location.getLongitude());
        mCallback.onLocationChanged(mUserLocationLat,mUserLocationLat);
    }

    public double getUserLocationLat() {
        return mUserLocationLat;
    }

    public double getUserLocationLng() {
        return mUserLocationLng;
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

    public Callback getCallback() {
        return mCallback;
    }
}
