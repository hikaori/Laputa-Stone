package com.sevencrayons.compass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.R.attr.data;
import static android.R.attr.name;

public class Main_Activity extends Activity implements PlaceSelectionListener {
    ImageView mLaputaBtn;
    Button mPlacePickerBtn;

    private final int PLACE_PICKER_REQUEST = 1;
    private String mSelectedPlaceName;
    private LatLng mSelectedLatLong;

    private String TAG = "MainActivity";

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

        mPlacePickerBtn = (Button) findViewById(R.id.place_picker_btn);
        mPlacePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity();
            }
        });

        mLaputaBtn = (ImageView) findViewById(R.id.laputa_btn);
        mLaputaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_Activity.this, CompassActivity.class);
                intent.putExtra("LatLocationKey", mSelectedLatLong.latitude);
                intent.putExtra("LngLocationKey", mSelectedLatLong.longitude);
                startActivity(intent);
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

    // this(onActivityResult) method is called after selecting place from PlacePicker.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
                displaySelectedPlaceFromPlacePicker(data);
            }
        }
    }

    private void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = PlacePicker.getPlace(data, this);

        mSelectedPlaceName = placeSelected.getAddress().toString();
        TextView selectedPlaceNameText = (TextView) findViewById(R.id.show_selected_place_name);
        selectedPlaceNameText.setText(mSelectedPlaceName);
        mSelectedLatLong =placeSelected.getLatLng();
        Log.i(TAG,"PlacePicer selected Lat long : " +placeSelected.getLatLng());
    }

    /**
     * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
     */
    @Override
    public void onPlaceSelected(Place place) {
        Log.i(TAG, "Place Selected long : " + place.getLatLng());
        mSelectedLatLong =place.getLatLng();
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


