package com.sevencrayons.compass;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by kaorihirata on 2017-07-04.
 */

public class GPSuserLocation extends Activity implements LocationListener {

    protected LocationManager locationManager;
    public double mUserLocationLat;
    public double mUserLocationLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getlatlng();
    }

    public void getlatlng(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //  ARGUMENT('PROVIDER',minTime(minimum time interval between location updates in milliseconds),
        //                              minDistance( minimum distance between location updates, in meters),LocationListener)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        ////// PERMISSION ////////
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 99);
        } else {
            // permission has been granted, continue as usual
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

    ////  MUST OVERRIDE METHOD BE CAUSE OF 'LocationListener'.
    @Override
    public void onLocationChanged(Location location) {
        mUserLocationLat =location.getLatitude();
        mUserLocationLng =location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }
}
