package com.example.seguradora;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public String getLocalName(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String localName = extras.getString(nearest.EXTRA_MESSAGE_LOCAL_NAME);
            return  localName;
        }
        return "City";
    }

    public double getLatitude() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            double lat = extras.getDouble(nearest.EXTRA_MESSAGE_LATITUDE);
            return lat;
        }
        return 0;
    }

    public double getLongitude() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            double log = extras.getDouble(nearest.EXTRA_MESSAGE_LONGITUDE);
            return log;
        }
        return 0;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double latitude = getLatitude();
        double longitude = getLongitude();

        LatLng local = new LatLng(latitude, longitude);
        mMap.setMinZoomPreference(16.0f);
        mMap.setMaxZoomPreference(16.0f);
        mMap.addMarker(new MarkerOptions().position(local).title("Marker in "+getLocalName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(local));
    }
}