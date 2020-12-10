package com.example.phototomap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment {
    public double latitudeSelected = 0;
    public double longitudeSelected = 0;
    public GoogleMap mgoogleMap;

    public MapFragment() {
        // Required empty public constructor
    }

    public void AddSelectedMarker() {
        for (Location selectedLocation : MainActivity.locationList) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(selectedLocation.getLatitude(), selectedLocation.getLongitude()));
            mgoogleMap.addMarker(new MarkerOptions().position(new LatLng(selectedLocation.getLatitude(), selectedLocation.getLongitude())).title(String.valueOf(selectedLocation.getId())));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        //Async Map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                mgoogleMap = googleMap;
                mgoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        System.out.println("CLICKED");
                        for (Location a : MainActivity.locationList)
                            if (marker.getTitle().equals(String.valueOf(a.getId())))
                                ((MainActivity) getActivity()).updateStatus(a.getAddress(), a.getBitmap());
                        return false;
                    }
                });
                //When map is loaded
                mgoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        renewMarker(latLng);
//                        //When clicked on map
//                        //Initialize marker options
//                        MarkerOptions markerOptions = new MarkerOptions();
//                        mgoogleMap.clear();
//                        AddSelectedMarker();
//
//                        //Set positions of marker
//                        markerOptions.position(latLng);
//
//                        //Set title of marker
//                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//                        latitudeSelected = latLng.latitude;
//                        longitudeSelected = latLng.longitude;
//                        //Remove all marker
//
//                        mgoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                        //Add marker on map
//                        mgoogleMap.addMarker(markerOptions);
//                        System.out.println(MainActivity.locationList.size());
                    }
                });
            }
        });
        return view;
    }

    public void renewMarker(LatLng latLng) {
        //When clicked on map
        //Initialize marker options
        MarkerOptions markerOptions = new MarkerOptions();
        mgoogleMap.clear();
        AddSelectedMarker();

        //Set positions of marker
        markerOptions.position(latLng);

        //Set title of marker
        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
        latitudeSelected = latLng.latitude;
        longitudeSelected = latLng.longitude;
        //Remove all marker

        mgoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        //Add marker on map
        mgoogleMap.addMarker(markerOptions);
    }


}