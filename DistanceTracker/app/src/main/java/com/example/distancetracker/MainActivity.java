package com.example.distancetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView textView_distance;
    RadioGroup radioGroup_location;
    private double distance_latitude, distance_longitude, distance;
    private boolean validate_location_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        textView_distance = findViewById(R.id.textView_distance);
        radioGroup_location = findViewById(R.id.radioGroup_location);
        radioGroup_location.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButton_spring:
                        distance_latitude = 1.5355458;
                        distance_longitude = 110.3582016;

                        break;
                    case R.id.radioButton_vivacity:
                        distance_latitude = 1.5265568;
                        distance_longitude = 110.3696401;
                        break;
                }
                checkLocationService();
                if (validate_location_service) { // Update latitude and longitude to service class
                    Intent intent = new Intent(getApplicationContext(), LocationService.class);
                    intent.putExtra("distance_latitude", distance_latitude);
                    intent.putExtra("distance_longitude", distance_longitude);
                    startService(intent);
                }

            }
        });
    }


    private void checkLocationService() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            validate_location_service = false;
        else
            validate_location_service = true;
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @SuppressLint("DefaultLocale")
        @Override
        public void onReceive(Context context, Intent intent) {
            distance = intent.getDoubleExtra("distance", 0.0);
            String output;
            if (distance == 0.0)
                output = getText(R.string.error_message).toString();
            else
                output = getText(R.string.distance) + String.format("%.5f", distance) + " KM";
            textView_distance.setText(output);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(LocationService.INTENT_NAME));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}