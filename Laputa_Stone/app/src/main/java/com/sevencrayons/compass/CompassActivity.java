package com.sevencrayons.compass;

import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;


public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "CompassActivity";

    private ImageView image;
    private float currentDegree = 0f;
    private SensorManager mSensorManager;
    private Location location = new Location("A");
    private Location target = new Location("B");
    private LocationManager locationManager;
    private GeomagneticField geoField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compass_activity);

        image = (ImageView) findViewById(R.id.main_image_hands);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        Bundle getBundle = null;
        getBundle = this.getIntent().getExtras();
        if (getBundle != null) {
            final Double selectedPlaceLat = getBundle.getDouble("LatLocationKey");
            final Double selectedPlaceLng = getBundle.getDouble("LngLocationKey");
            Log.i(TAG, "Bundle selectedLat: " + selectedPlaceLat + "Bundle selectedLng:  " + selectedPlaceLng);

            //GET USER LOCATION
            GPSuserLocation getuserLocation = new GPSuserLocation();
            //getuserLocation.onLocationChanged(location);
            double userLocationLat = getuserLocation.mUserLocationLat;
            double userLocationLng = getuserLocation.mUserLocationLng;
            Log.i(TAG,"userLocationLat: "+userLocationLat +"userLocationLng:  "+ userLocationLng);

            location.setLatitude(54.903535);
            location.setLongitude(23.979342);

            target.setLatitude(selectedPlaceLat);
            target.setLongitude(selectedPlaceLng);

            geoField = new GeomagneticField(
                    (float) location.getLatitude(),
                    (float) location.getLongitude(),
                    (float) location.getAltitude(),
                    System.currentTimeMillis());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this); // to stop the listener and save battery
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);
        degree += geoField.getDeclination();

        float bearing = location.bearingTo(target);
        degree = (bearing - degree) * -1;
        degree = normalizeDegree(degree);

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;

    }
    private float normalizeDegree(float value) {
        if (value >= 0.0f && value <= 180.0f) {
            return value;
        } else {
            return 180 + (180 + value);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
       // not in use
    }
}
