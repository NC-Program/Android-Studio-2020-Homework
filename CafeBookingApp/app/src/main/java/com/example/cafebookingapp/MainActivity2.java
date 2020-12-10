package com.example.cafebookingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private CuisineAdapter cuisineAdapter;
    private String cuisineList;
    private ImageView imageView_cafeRestaurant;
    private TextView textView_date, textView_time, textView_number_people;
    private Button btn_decrease_people, btn_increase_people;
    private int mYear, mMonth, mDay, mHour, mMinute, numberOfPeople = 0;
    private Button btn_reserve;

    private CafeRestaurants selectedCafeRestaurants;
    private Reservation reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initUI();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            selectedCafeRestaurants = (CafeRestaurants) bundle.getParcelable("cafeRestaurant");
            cuisineList = selectedCafeRestaurants.getCuisineText();
            imageView_cafeRestaurant.setImageResource(selectedCafeRestaurants.getImage());
        }

        recyclerView = findViewById(R.id.recyclerView_cuisine);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        cuisineAdapter = new CuisineAdapter(cuisineList);
        recyclerView.setAdapter(cuisineAdapter);
    }

    private void initUI() {
        imageView_cafeRestaurant = findViewById(R.id.imageView_cafeRestaurant);
        textView_date = findViewById(R.id.textView_date);
        textView_time = findViewById(R.id.textView_time);
        textView_number_people = findViewById(R.id.textView_number_people);
        btn_decrease_people = findViewById(R.id.button_decrese_people);
        btn_increase_people = findViewById(R.id.button_increase_people);
        btn_reserve = findViewById(R.id.button_reserve);

        textView_date.setOnClickListener(this);
        textView_time.setOnClickListener(this);
        btn_increase_people.setOnClickListener(this);
        btn_decrease_people.setOnClickListener(this);
        btn_reserve.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == textView_date) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            textView_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        }
        if (view == textView_time) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            String a[] = selectedCafeRestaurants.getOpeningHours().split("hours:");
                            String b[] = a[1].split("-");
                            int startHour, startMinute, endHour, endMinute;
                            String startTime = b[0].trim();
                            String endTime = b[1].trim();
                            boolean timeValidation = false;
                            if (startTime.contains("am")) {
                                System.out.println(startTime);
                                startTime = startTime.split("am")[0];
                                startHour = Integer.valueOf(startTime.split(":")[0].trim());
                                startMinute = Integer.valueOf(startTime.split(":")[1].trim());

                            } else {
                                System.out.println(startTime);
                                startTime = startTime.split("pm")[0];
                                startHour = Integer.valueOf(startTime.split(":")[0].trim()) + 12;
                                startMinute = Integer.valueOf(startTime.split(":")[1].trim());

                            }
                            if (endTime.contains("am")) {
                                System.out.println(endTime);
                                endTime = endTime.split("am")[0];
                                endHour = Integer.valueOf(endTime.split(":")[0].trim());
                                endMinute = Integer.valueOf(endTime.split(":")[1].trim());

                            } else {
                                System.out.println(endTime);
                                endTime = endTime.split("pm")[0];
                                endHour = Integer.valueOf(endTime.split(":")[0].trim()) + 12;
                                endMinute = Integer.valueOf(endTime.split(":")[1].trim());

                            }

                            if (hourOfDay >= startHour && hourOfDay <= endHour) {
                                if (hourOfDay == startHour) {
                                    if (minute >= startMinute)
                                        timeValidation = true;
                                } else if (hourOfDay == endHour) {
                                    if (minute <= endMinute)
                                        timeValidation = true;
                                } else
                                    timeValidation = true;
                            }
                            if (timeValidation) {

                                String AM_PM = " AM";
                                String mm_precede = "";
                                if (hourOfDay >= 12) {
                                    AM_PM = " PM";
                                    if (hourOfDay >= 13 && hourOfDay < 24) {
                                        hourOfDay -= 12;
                                    } else {
                                        hourOfDay = 12;
                                    }
                                } else if (hourOfDay == 0) {
                                    hourOfDay = 12;
                                }
                                if (minute < 10) {
                                    mm_precede = "0";
                                }
                                textView_time.setText(hourOfDay + ":" + mm_precede + minute + AM_PM);
                            } else {
                                Toast toast = Toast.makeText((getApplicationContext()),
                                        "Hi boys and girls, your selected time is not within the operating time. Please reselect.",
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if (view == btn_decrease_people) {
            if (numberOfPeople > 0)
                numberOfPeople -= 1;
            textView_number_people.setText(String.valueOf(numberOfPeople));
        }
        if (view == btn_increase_people) {
            if (numberOfPeople < selectedCafeRestaurants.getMaxDiners())
                numberOfPeople += 1;
            textView_number_people.setText(String.valueOf(numberOfPeople));
        }

        if (view == btn_reserve) {
            if (textView_date.getText().toString().matches("")) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Please fill in the date",
                        Toast.LENGTH_SHORT);
                toast.show();
            } else if (textView_time.getText().toString().matches("")) {
                Toast toast = Toast.makeText((getApplicationContext()),
                        "Please fill in the time",
                        Toast.LENGTH_SHORT);
                toast.show();
            } else if (numberOfPeople == 0) {
                Toast toast = Toast.makeText((getApplicationContext()),
                        "Please choose number of people more than 0",
                        Toast.LENGTH_SHORT);
                toast.show();
            } else if (cuisineAdapter.getSelectedCount() <= 0) {
                Toast toast = Toast.makeText((getApplicationContext()),
                        "Please choose at least 1 cuisine",
                        Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Intent resultIntent = new Intent();
                reservation = new Reservation(selectedCafeRestaurants.getName(), textView_date.getText().toString(), textView_time.getText().toString(), numberOfPeople, cuisineAdapter.getSelectedCuisineList(), selectedCafeRestaurants.getImage());
                Bundle bundle = new Bundle();
                bundle.putParcelable("Reservation", reservation);
                resultIntent.putExtras(bundle);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        }
    }
}