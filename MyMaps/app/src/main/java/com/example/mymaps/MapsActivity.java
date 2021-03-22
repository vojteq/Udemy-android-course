package com.example.mymaps;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng mountEverest = new LatLng(28.001377, 86.928729);
    private LatLng mountKilimanjaro = new LatLng(-3.075558, 37.344363);
    private LatLng theAlps = new LatLng(47.368955, 9.702579);

    private Marker everestMarker;
    private Marker kilimanjaroMarker;
    private Marker alpsMarker;
    private ArrayList<Marker> markerArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        everestMarker = mMap.addMarker(new MarkerOptions()
                .position(mountEverest)
                .title("Mount Everest")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        markerArrayList.add(everestMarker);
        kilimanjaroMarker = mMap.addMarker(new MarkerOptions()
                .position(mountKilimanjaro)
                .title("Mount Kilimanjaro")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        markerArrayList.add(kilimanjaroMarker);
        alpsMarker = mMap.addMarker(new MarkerOptions()
                .position(theAlps)
                .title("The Alps")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        markerArrayList.add(alpsMarker);



//        // Add a marker in Sydney and move the camera
//        LatLng binga = new LatLng(-19.7766658, 33.0444344);
//        LatLng sydney = new LatLng(-34, 151);
//
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).alpha(0.75f));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        //        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 20));
    }
}