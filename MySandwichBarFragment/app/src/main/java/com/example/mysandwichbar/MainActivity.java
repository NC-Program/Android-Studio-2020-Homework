package com.example.mysandwichbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,FragmentOne.onFragmentInteractionListener,FragmentTwo.onFragmentInteractionListener{
    private static final String TAG = "Swipe Position";
    private float x1, x2, y1, y2;
    private static int MIN_DISTANCE = 150;
    private GestureDetector gestureDetector;
    private TextView textView_page_1,textView_page_2;
    private int page=1;

    private TextView textView_filling, textView_side, textView_total_price;
    private Button btn_reset, btn_order;

    private double price_total = 0;
    private ArrayList<CheckBox> checkBox_filling_list = new ArrayList<>();
    private ArrayList<CheckBox> checkBox_side_list = new ArrayList<>();
    private ArrayList<RadioButton> radioButton_size_list = new ArrayList<>();
    private LinkedHashMap<String, String> side_map = new LinkedHashMap<String, String>();
    private ArrayList<String> filling_display_array = new ArrayList<>();
    private ArrayList<String> side_display_array = new ArrayList<>();
    HashMap<String, Double> filling_hash_map = new HashMap<>();
    HashMap<String, Double> side_hash_map = new HashMap<>();
    HashMap<String, Double> size_hash_map = new HashMap<>();
    private FragmentOne fragmentOne=new FragmentOne();
    private FragmentTwo fragmentTwo=new FragmentTwo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        this.gestureDetector = new GestureDetector(MainActivity.this, this);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_layout,fragmentOne)
                .addToBackStack(null)
                .commit();

    }

    //This function is to initialize the User Interface and set the default choice.
    private void initUI() {
        textView_page_1=findViewById(R.id.textView_page_1);
        textView_page_2=findViewById(R.id.textView_page_2);
    }

    //This function will calculate the total price according to the size, side and the filling chosen.
    private void calculateTotal() {
        double filling_total = 0, side_total = 0, size_total = 0;
        price_total = 0;

        size_total = calculateSizeTotal();
        side_total = calculateSideTotal();
        filling_total = calculateFillingTotal();

        price_total = filling_total + side_total + size_total;
        textView_total_price.setText(String.format("%.2f", price_total));
    }

    //This function will return the filling additional price
    private double calculateFillingTotal() {
        int filling_check_count = 0;
        double smallest = 100, filling_total = 0;
        ArrayList<Double> filling_price_list = new ArrayList<>();
        for (CheckBox filling_box : checkBox_filling_list) {
            if (filling_box.isChecked()) {
                filling_total += filling_hash_map.get(filling_box.getTag().toString());
                filling_price_list.add(filling_hash_map.get(filling_box.getTag().toString()));
                filling_check_count += 1;
            }
        }

        if (filling_check_count <= 1)
            return 0;
        else {
            for (double a : filling_price_list) {
                if (smallest > a)
                    smallest = a;
            }
            return filling_total -= smallest;
        }
    }

    //This function will return the side additional price.
    private double calculateSideTotal() {
        int side_check_count = 0;
        double smallest = 100, side_total = 0;
        ArrayList<Double> side_price_list = new ArrayList<>();

        for (CheckBox side_box : checkBox_side_list) {
            if (side_box.isChecked()) {
                side_total += side_hash_map.get(side_box.getTag().toString());
                side_price_list.add(side_hash_map.get(side_box.getTag().toString()));
                side_check_count += 1;
            }
        }

        if (side_check_count <= 1)
            return 0;
        else {
            for (double a : side_price_list) {
                if (smallest > a)
                    smallest = a;
            }
            return side_total -= smallest;
        }
    }

    //This function will return the prize of the size chosen.
    private double calculateSizeTotal() {
        for (RadioButton radioButton : radioButton_size_list) {
            if (radioButton.isChecked())
                return size_hash_map.get(radioButton.getTag().toString());
        }
        return 0;
    }

    public void onCheckboxClickedFilling(View view) {
        fragmentOne.onCheckboxClickedFilling(view);
    }

    //This function will update the side order text view and update total amount when the side check box was clilcked.
    public void onCheckboxClickedSide(View view) {
       fragmentOne.onCheckboxClickedSide(view);
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

//    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1=event.getX();
                y1=event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2=event.getX();
                y2=event.getY();
                break;
        }
        float valueX=x2-x1;
        float valueY=y2-y1;
        //Swipe Left
        if(valueX>MIN_DISTANCE&&page==2)
        {
            onBackPressed();
            fragmentOne.checkFillingMax();
            textView_page_1.setBackground(getDrawable(R.drawable.circleselected));
            textView_page_2.setBackground(getDrawable(R.drawable.circle));
        }
        //Swipe Right
        else if(valueX<-MIN_DISTANCE&&page==1)
        {
            if(fragmentOne.getFilling_display_array().size()==0)
            {
                Toast toast = Toast.makeText((getApplicationContext()),
                        "Please select at least 1 filling",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
            else {


                fragmentOne.nextPage();
                page++;
                textView_page_1.setBackground(getDrawable(R.drawable.circle));
                textView_page_2.setBackground(getDrawable(R.drawable.circleselected));

            }
        }
                return super.onTouchEvent(event);
    }



    @Override
    public void passData(ArrayList<String> filling_selected_array, ArrayList<String> side_selected_array, String size_selected) {
        FragmentTwo fragmentTwo=FragmentTwo.newInstance(filling_selected_array,side_selected_array,size_selected);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout,fragmentTwo)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void reset() {
        getSupportFragmentManager().beginTransaction().detach(fragmentOne).commit();
        fragmentOne=new FragmentOne();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_layout,fragmentOne)
                .commit();
        page-=1;
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if(page==2)
            page-=1;
        super.onBackPressed();
    }
}
