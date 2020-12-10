package com.example.phototomap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {
    private FloatingActionButton floatingActionButton_camera, floatingActionButton_location;
    public static int FINE_LOCATION = 100;
    public static int COARSE_LOCATION = 200;
    private ImageView imageView_location;
    private TextView textView_address;
    private DataBaseHandler databaseHandler;
    public static List<Location> locationList = new ArrayList<>();
    private ResultReceiver resultReceiver;
    private RequestQueue mQueue;
    private String addressSelected = "";
    private double myLatitude=0,myLongitude=0;
    private LocationManager locationManager;
    private int UPDATE_TIME_INTERVAL=5000;
    //Initialize fragment
    private MapFragment fragment = new MapFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHandler = new DataBaseHandler(this, null, null, 1);
        initUI();
        RequestLocation();
        mQueue = Volley.newRequestQueue(this);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
        }


        //Open fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout_map, fragment)
                .commit();
    }

    private void initUI() {
        imageView_location = findViewById(R.id.imageView_location);
        textView_address = findViewById(R.id.textView_address);
        floatingActionButton_camera = findViewById(R.id.floatingActionButton_camera);
        floatingActionButton_location = findViewById(R.id.floatingActionButton_location);
        floatingActionButton_camera.setOnClickListener(this);
        floatingActionButton_location.setOnClickListener(this);
    }

    public void updateLocationList() {
        if (locationList != null)
            locationList.clear();
        locationList = databaseHandler.getAllLocation();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.floatingActionButton_camera):
                System.out.println("Camera Button pressed");
                fetchAddressFromLatLong();
                takePicture();

                break;
            case (R.id.floatingActionButton_location):
                LatLng latLng=new LatLng(myLatitude,myLongitude);
                fragment.renewMarker(latLng);
                RequestLocation();
//                GetAddressAsyncTask getImageAsyncTask=new GetAddressAsyncTask();
//                getImageAsyncTask.execute("https://maps.googleapis.com/maps/api/geocode/json?latlng=1.5355458,110.3582016&sensor=true&key=AIzaSyBnYlCIq0OhiwHArdaQGQ5vhdSSV-rSwRY");
                System.out.println("Location Button pressed");
                break;
        }
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    public void updateStatus(String address, Bitmap bitmap) {
        textView_address.setText(address);
        imageView_location.setImageBitmap(bitmap);
    }

    private void fetchAddressFromLatLong() {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + String.valueOf(fragment.latitudeSelected) + "," + String.valueOf(fragment.longitudeSelected) + "&sensor=true&key=AIzaSyBnYlCIq0OhiwHArdaQGQ5vhdSSV-rSwRY";
        System.out.println(url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    JSONObject jsonObject = jsonArray.getJSONObject(1);
                    addressSelected = jsonObject.getString("formatted_address");
                    System.out.println(addressSelected);
                    textView_address.setText(addressSelected);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("No such address");
            }
        });
        mQueue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 100) {
            //Get Capture Image
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            databaseHandler.addLocation(new Location(fragment.latitudeSelected, fragment.longitudeSelected, addressSelected), DbBitmapUtility.getBytes(captureImage));
            updateLocationList();
            System.out.println("LOCATION LIST NUMBER: " + locationList.size());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onLocationChanged(@NonNull android.location.Location location) {
        myLatitude=location.getLatitude();
        myLongitude=location.getLongitude();
        System.out.println("aaaaaa");
    }

    private void RequestLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        boolean enabledGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        System.out.println("bbbbbbbbbbbbbbb in request location");
        if(enabledGPS) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION);
                }
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, COARSE_LOCATION);
                }

                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,UPDATE_TIME_INTERVAL, 0, this); // Update every 5 seconds
            System.out.println(myLatitude);
            System.out.println(myLongitude);
        }
    }

}