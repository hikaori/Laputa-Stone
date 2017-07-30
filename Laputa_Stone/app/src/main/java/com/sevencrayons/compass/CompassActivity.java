package com.sevencrayons.compass;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.util.Log;


public class CompassActivity extends ActionBarActivity {

    private static final String TAG = "CompassActivity";

    private Compass compass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compass_activity);

        Bundle getBundle = null;
        getBundle = this.getIntent().getExtras();
        if (getBundle != null) {
            final Double editedlat = getBundle.getDouble("LatLocationKey");
            final Double editedlng = getBundle.getDouble("LngLocationKey");
            System.out.println("Extra intent from EnterAdressActivity :lat" + editedlat + "=======================");

         //GET USER LOCATION
            GPSuserLocation getuserLocation = new GPSuserLocation();
            System.out.println("GPSuserLocation==========================");

            double userLocationLat = getuserLocation.mUserLocationLat;
            double userLocationlng = getuserLocation.mUserLocationLng;
            System.out.println("GPSgetLat======="+userLocationLat+"===================");

//        compass = new Compass(this);
            compass = new Compass(this, editedlat, editedlng,userLocationLat,userLocationlng);
            compass.arrowView = (ImageView) findViewById(R.id.main_image_hands);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "start compass");
        compass.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compass.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        compass.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "stop compass");
        compass.stop();
    }
}
