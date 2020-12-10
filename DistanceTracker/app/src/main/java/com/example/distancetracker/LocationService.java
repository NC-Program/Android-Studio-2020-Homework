package com.example.distancetracker;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.Timer;
import java.util.TimerTask;

public class LocationService extends Service implements LocationListener {
    private static int UPDATE_TIME_INTERVAL = 5000;
    private double my_latitude, my_longitude, distance_latitude, distance_longitude, distance;
    public static String INTENT_NAME = "Distance";
    private Intent intent;
    private Handler handler;
    private Timer timer;
    private LocationManager locationManager;
//
    public LocationService() {
        my_latitude = 0;
        my_longitude = 0;
        distance_latitude = 0;
        distance_longitude = 0;
        distance = 0;
        handler = new Handler();
        timer = new Timer();
        locationManager = null;
    }

    @Override
    public void onCreate() {
        intent = new Intent(INTENT_NAME);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        distance_latitude = intent.getDoubleExtra("distance_latitude", 0.0);
        distance_longitude = intent.getDoubleExtra("distance_longitude", 0.0);

        timer = new Timer();
        timer.schedule(getTimerTask(), UPDATE_TIME_INTERVAL, UPDATE_TIME_INTERVAL);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        my_latitude = location.getLatitude();
        my_longitude = location.getLongitude();
        CalculateDistance();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(@NonNull String provider) { }

    @Override
    public void onProviderDisabled(@NonNull String provider) { }

    private void CalculateDistance() {
        if ((my_latitude == distance_latitude) && (my_longitude == distance_longitude)) {
            distance = 0.0;
        } else {
            double theta = my_longitude - distance_longitude;
            distance = Math.sin(Math.toRadians(my_latitude)) * Math.sin(Math.toRadians(distance_latitude)) + Math.cos(Math.toRadians(my_latitude)) * Math.cos(Math.toRadians(distance_latitude)) * Math.cos(Math.toRadians(theta));
            distance = Math.acos(distance);
            distance = Math.toDegrees(distance);
            distance = distance * 60 * 1.1515;
            distance = distance * 1.609344;//in Kilometer
        }
        intent.putExtra("distance", distance);
        sendBroadcast(intent);
    }

    private void RequestLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        boolean enabledGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(enabledGPS) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,UPDATE_TIME_INTERVAL, 0, this); // Update every 5 seconds
        }
    }

    public TimerTask getTimerTask(){
        return new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        RequestLocation();
                    }
                });
            }
        };
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
