package com.sevencrayons.compass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.R.attr.data;
import static android.R.attr.name;

public class Main_Activity extends Activity implements PlaceSelectionListener {
    ImageView mbtn;
    Button mbtn2;

    private final int PLACE_PICKER_REQUEST = 1;

    //    public static final String LatLocationKey ="LatLocationKey";
    public static final String LngLocationKey = "LngLocationKey";

    private String mAdressSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        // Retrieve the PlaceAutocompleteFragment.
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        autocompleteFragment.setOnPlaceSelectedListener(this);


        mbtn = (ImageView) findViewById(R.id.btn);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] latlng = getLocationFromPlaceName(Main_Activity.this, mAdressSelected);
                if(hasAddress(latlng)==false){
                    TextView maddressErrorMSG =(TextView) findViewById(R.id.addressErrorMSG);
                    maddressErrorMSG.setText("Please Serect more specific place");
                }
                else {
                double lat = latlng[0];
                double lng = latlng[1];
                Intent intent = new Intent(Main_Activity.this, CompassActivity.class);
                intent.putExtra("LatLocationKey", lat);
                intent.putExtra(LngLocationKey, lng);
                System.out.println("intent To Compass lat: " + lat);
                startActivity(intent);
                }
            }
        });

        mbtn2 = (Button) findViewById(R.id.btn2);
        mbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity();
            }
        });
    }

    private void startPlacePickerActivity() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
                displaySelectedPlaceFromPlacePicker(data);
            }
        }
    }

    private void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = PlacePicker.getPlace(data, this);

        mAdressSelected = placeSelected.getAddress().toString();
        String pickedName = placeSelected.getName().toString();

        TextView enterCurrentLocation = (TextView) findViewById(R.id.show_selected_location);
        enterCurrentLocation.setText(pickedName);
    }

    public static double[] getLocationFromPlaceName(Context context, String name) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> location = geocoder.getFromLocationName(name, 1);
            if (location == null || location.size() < 1) {
                return null;
            }

            Address address = location.get(0);
            double[] latlng = {address.getLatitude(), address.getLongitude()};
            return latlng;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
     */
    @Override
    public void onPlaceSelected(Place place) {
//        Log.i(TAG, "Place Selected: " + place.getName());

        mAdressSelected = place.getAddress().toString();

    }

    @Override
    public void onError(Status status) {

    }

    /**
     * after selected place check address.
     */
    public boolean hasAddress(double[] address) {
        if (address == null) {
            return false;
        }
        return true;
    }

}


