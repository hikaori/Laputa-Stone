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
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by kaorihirata on 2017-07-04.
 */

public class GPSuserLocation extends Activity implements LocationListener {

    protected LocationManager locationManager;
    public double mUserLocationLat;
    public double mUserLocationLng;
    private String TAG ="GPSuserLocation";

    private Compass compass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"Create");

        setContentView(R.layout.compass_activity);

        Bundle getBundle = null;
        getBundle = this.getIntent().getExtras();
        if (getBundle != null) {
            final Double selectedPlaceLat = getBundle.getDouble("LatLocationKey");
            final Double selectedPlaceLng = getBundle.getDouble("LngLocationKey");
            Log.i(TAG,"Bundle selectedLat: "+selectedPlaceLat +"Bundle selectedLng:  "+ selectedPlaceLng);

            //GET USER LOCATION
            double userLocationLat = mUserLocationLat;
            double userLocationLng = mUserLocationLng;
            Log.i(TAG,"userLocationLat: "+userLocationLat +"userLocationLng:  "+ userLocationLng);

            compass = new Compass(this, selectedPlaceLat, selectedPlaceLng,49.2860,-123.1130);
            compass.arrowView = (ImageView) findViewById(R.id.main_image_hands);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
