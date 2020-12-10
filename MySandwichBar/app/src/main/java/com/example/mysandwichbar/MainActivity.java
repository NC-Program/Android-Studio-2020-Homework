package com.example.mysandwichbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView_filling, textView_side, textView_total_price;
    private CheckBox checkBox_cut_ham, checkBox_chicken, checkBox_beef, checkBox_salmon, checkBox_kebab;
    private CheckBox checkBox_tomato, checkBox_lettuce, checkBox_onion, checkBox_cheese;
    private RadioButton radioButton_6inch, radioButton_9inch, radioButton_12inch;
    private RadioGroup radioGroup_size;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        calculateTotal();

    }

    //This function is to initialize the User Interface and set the default choice.
    private void initUI() {
        textView_filling = findViewById(R.id.textView_filling);
        textView_side = findViewById(R.id.textView_side);
        textView_total_price = findViewById(R.id.textView_total_price);
        checkBox_cut_ham = findViewById(R.id.checkBox_cut_ham);
        checkBox_chicken = findViewById(R.id.checkBox_chicken);
        checkBox_beef = findViewById(R.id.checkBox_beef);
        checkBox_salmon = findViewById(R.id.checkBox_salmon);
        checkBox_kebab = findViewById(R.id.checkBox_kebab);
        checkBox_tomato = findViewById(R.id.checkBox_tomato);
        checkBox_lettuce = findViewById(R.id.checkBox_lettuce);
        checkBox_onion = findViewById(R.id.checkBox_onion);
        checkBox_cheese = findViewById(R.id.checkBox_cheese);
        radioGroup_size = findViewById(R.id.radioGroup_size);
        radioButton_6inch = findViewById(R.id.radioButton_6inch);
        radioButton_9inch = findViewById(R.id.radioButton_9inch);
        radioButton_12inch = findViewById(R.id.radioButton_12inch);
        btn_order = findViewById(R.id.button_order);
        btn_reset = findViewById(R.id.button_reset);

        checkBox_filling_list.add(checkBox_cut_ham);
        checkBox_filling_list.add(checkBox_chicken);
        checkBox_filling_list.add(checkBox_beef);
        checkBox_filling_list.add(checkBox_salmon);
        checkBox_filling_list.add(checkBox_kebab);

        checkBox_side_list.add(checkBox_tomato);
        checkBox_side_list.add(checkBox_lettuce);
        checkBox_side_list.add(checkBox_onion);
        checkBox_side_list.add(checkBox_cheese);

        radioButton_size_list.add(radioButton_6inch);
        radioButton_size_list.add(radioButton_9inch);
        radioButton_size_list.add(radioButton_12inch);

        btn_order.setOnClickListener(this);
        btn_reset.setOnClickListener(this);

        radioButton_6inch.setChecked(true);

        filling_hash_map.put("Ham Slices", 2.50);
        filling_hash_map.put("Roasted Chicken", 2.00);
        filling_hash_map.put("Beef Steak", 4.50);
        filling_hash_map.put("Grilled Salmon", 3.70);
        filling_hash_map.put("Kebab", 4.00);

        side_hash_map.put("Tomato", 1.00);
        side_hash_map.put("Lettuce", 1.20);
        side_hash_map.put("Onion", 0.50);
        side_hash_map.put("Cheese", 1.50);

        size_hash_map.put("6 inch", 7.00);
        size_hash_map.put("9 inch", 9.50);
        size_hash_map.put("12 inch", 13.00);

        filling_display_array.add("");
        side_display_array.add("");

        radioGroup_size.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                calculateTotal();
            }
        });
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

    //This function will update the filling order text view and update total amount when the side check box was clilcked.
    public void onCheckboxClickedFilling(View view) {
        CheckBox checkBox = findViewById(view.getId());
        if (checkBox.isChecked())
            filling_display_array.add(checkBox.getTag().toString());
        else
            filling_display_array.remove(checkBox.getTag().toString());
        textView_filling.setText("Filling | " + filling_display_array.get(filling_display_array.size() - 1));
        checkFillingMax();
        calculateTotal();
    }

    //This function checks whether the user has selected a maximum or 3 filling.
    private void checkFillingMax() {
        int filling_count = 0;
        boolean filling_full = false;
        for (CheckBox a : checkBox_filling_list) {
            if (a.isChecked())
                filling_count += 1;
        }
        if (filling_count >= 3)
            filling_full = true;
        else
            filling_full = false;
        setFillingAvailability(filling_full);
    }

    //This function will set the filling availability.
    //If there are 3 filling being chosen it, the other filling will be disable.
    private void setFillingAvailability(boolean filling_full) {
        if (filling_full == true) {
            for (CheckBox a : checkBox_filling_list) {
                if (!a.isChecked())
                    a.setEnabled(false);
            }
        } else {
            for (CheckBox a : checkBox_filling_list) {
                a.setEnabled(true);
            }
        }
    }

    //This function will update the side order text view and update total amount when the side check box was clilcked.
    public void onCheckboxClickedSide(View view) {
        CheckBox checkBox = findViewById(view.getId());
        if (checkBox.isChecked())
            side_display_array.add(checkBox.getTag().toString());
        else
            side_display_array.remove(checkBox.getTag().toString());
        textView_side.setText("Side | " + side_display_array.get(side_display_array.size() - 1));
        calculateTotal();
    }


    //This is the function for order button and the reset button
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.button_order): {
                if (!checkBox_cut_ham.isChecked() && !checkBox_chicken.isChecked() && !checkBox_beef.isChecked() && !checkBox_salmon.isChecked() && !checkBox_kebab.isChecked()) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setMessage("You have to select at least 1 filling");
                    AlertDialog.Builder builder = alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Object button;
                            Toast.makeText(MainActivity.this, "Remember to select at least 1 filling", Toast.LENGTH_SHORT).show();
                        }
                    });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    break;
                } else {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setMessage(createOrderMessage());
                    AlertDialog.Builder builder = alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Object button;
                            Toast.makeText(MainActivity.this, "注文成功", Toast.LENGTH_SHORT).show();
                        }
                    });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.setTitle("Your Order");
                    alertDialog.show();
                    break;
                }
            }
            case (R.id.button_reset): {
                for (CheckBox a : checkBox_filling_list) {
                    a.setChecked(false);
                }
                for (CheckBox a : checkBox_side_list) {
                    a.setChecked(false);
                }
                radioButton_6inch.setChecked(true);
                calculateTotal();
                break;
            }
        }


    }

    //Finalise the order message.
    private String createOrderMessage() {
        String orderMessage = "";
        HashMap<String, Double> filling_hash_map_temp = new HashMap<>();
        HashMap<String, Double> side_hash_map_temp = new HashMap<>();
        filling_hash_map_temp.putAll(filling_hash_map);
        side_hash_map_temp.putAll(side_hash_map);
        for (RadioButton radioButton : radioButton_size_list)
            if (radioButton.isChecked()) {
                orderMessage +="Size: "+ radioButton.getTag().toString() + " RM " + String.format("%.2f", size_hash_map.get(radioButton.getTag().toString())) + "\n";
            }
        for (CheckBox checkBox : checkBox_filling_list)
            if (!checkBox.isChecked()) {
                filling_hash_map_temp.remove(checkBox.getTag().toString());
            }
        for (Map.Entry map : filling_hash_map_temp.entrySet()) {
            if (map.getValue().toString().equals(Collections.min(filling_hash_map_temp.values()).toString()))
                orderMessage += map.getKey().toString() + ": RM " +"0.00" + "\n";
                else
                orderMessage += map.getKey().toString() + ": RM " + String.format("%.2f", map.getValue())+ "\n";
        }
        for (CheckBox checkBox : checkBox_side_list)
            if (!checkBox.isChecked()) {
                side_hash_map_temp.remove(checkBox.getTag().toString());
            }
        for (Map.Entry map : side_hash_map_temp.entrySet()) {
            if (map.getValue().toString().equals(Collections.min(side_hash_map_temp.values()).toString()))
                orderMessage += map.getKey().toString() + ": RM " +"0.00" + "\n";
            else
                orderMessage += map.getKey().toString() + ": RM " + String.format("%.2f", map.getValue())+ "\n";
        }
        orderMessage += "**********************\n";
        orderMessage += "Total: RM" + String.format("%.2f", price_total) + "\n";
        orderMessage += "**********************";
        return orderMessage;
    }
}
