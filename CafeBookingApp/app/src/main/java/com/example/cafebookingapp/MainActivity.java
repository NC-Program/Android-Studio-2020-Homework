package com.example.cafebookingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private ArrayList<CafeRestaurants> cafeRestaurantsList = new ArrayList<>();
    private ArrayList<String> serendipityCafeCuisineList = new ArrayList<>();
    private ArrayList<String> oldAsianCafeCuisineList = new ArrayList<>();
    private ArrayList<String> internationalTasteRestaurantCuisineList = new ArrayList<>();
    private ArrayList<String> gardenhouseCafeCuisineList = new ArrayList<>();
    private ArrayList<String> spiceCafeCuisineList = new ArrayList<>();
    private Reservation reservation;
    private int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        recyclerView = findViewById(R.id.recyclerView_cafe_restaurants);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        myAdapter = new MyAdapter(cafeRestaurantsList);
        recyclerView.setAdapter(myAdapter);
    }

    private void initUI() {
        cafeRestaurantsList.add(new CafeRestaurants(getResources().getString(R.string.serendipityCafe_name), getResources().getString(R.string.serendipityCafe_address), getResources().getString(R.string.serendipityCafe_openingHours), getResources().getString(R.string.serendipityCafe_cuisine), 75, 10, R.drawable.cafe1_icon, R.drawable.cafe1));
        cafeRestaurantsList.add(new CafeRestaurants(getResources().getString(R.string.oldAsianCafe_name), getResources().getString(R.string.oldAsianCafe_address), getResources().getString(R.string.oldAsianCafe_openingHours), getResources().getString(R.string.oldAsianCafe_cuisine), 120, 40, R.drawable.cafe2_icon, R.drawable.cafe2));
        cafeRestaurantsList.add(new CafeRestaurants(getResources().getString(R.string.internationalTasteRestaurant_name), getResources().getString(R.string.internationalTasteRestaurant_address), getResources().getString(R.string.internationalTasteRestaurant_openingHours), getResources().getString(R.string.internationalTasteRestaurant_cuisine), 30, 100, R.drawable.cafe3_icon, R.drawable.cafe3));
        cafeRestaurantsList.add(new CafeRestaurants(getResources().getString(R.string.gardenhouseCafe_name), getResources().getString(R.string.gardenhouseCafe_address), getResources().getString(R.string.gardenhouseCafe_openingHours), getResources().getString(R.string.gardenhouseCafe_cuisine), 85, 50, R.drawable.cafe4_icon, R.drawable.cafe4));
        cafeRestaurantsList.add(new CafeRestaurants(getResources().getString(R.string.spiceCafe_name), getResources().getString(R.string.spiceCafe_address), getResources().getString(R.string.spiceCafe_openingHours), getResources().getString(R.string.spiceCafe_cuisine), 65, 45, R.drawable.cafe5_icon, R.drawable.cafe5));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    setContentView(R.layout.reserve_layout);
                    TextView textView_cafe_restaurant_name_details = findViewById(R.id.textView_cafe_restaurant_name_details);
                    TextView textView_date_details = findViewById(R.id.textView_date_details);
                    TextView textView_time_details = findViewById(R.id.textView_time_details);
                    TextView textView_numberOfDiners_details = findViewById(R.id.textView_numberOfDiners_details);
                    TextView textView_cuisine_details = findViewById(R.id.textView_cuisine_details);
                    ImageView imageView_cafe_restaurant_image_details = findViewById(R.id.imageView_cafe_restaurant_image_details);

                    reservation = data.getParcelableExtra("Reservation");

                    String tempCuisine = "";
                    for (int a = 0; a < reservation.getSelectedCuisineList().size(); a++) {
                        if (a == reservation.getSelectedCuisineList().size() - 1)
                            tempCuisine += reservation.getSelectedCuisineList().get(a);
                        else
                            tempCuisine += reservation.getSelectedCuisineList().get(a) + ", ";
                    }

                        textView_cafe_restaurant_name_details.setText(reservation.getCafeRestaurantName());
                        textView_date_details.setText("Date: " + reservation.getDate());
                        textView_time_details.setText("Time: " + reservation.getTime());
                        textView_numberOfDiners_details.setText("Number of diners: " + reservation.getNumberOfDiners().toString());
                        textView_cuisine_details.setText("Cuisine: "+tempCuisine);
                        imageView_cafe_restaurant_image_details.setImageResource(reservation.getImage());

                    }
                }
            }
        }
    }