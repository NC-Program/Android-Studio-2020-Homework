package com.example.mysandwichbar;

import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTwo extends Fragment implements View.OnClickListener {
    private TextView textView_addOn;
    private RadioGroup radioGroup_addOn;
    private RadioButton radioButton_tea, radioButton_coffee, radioButton_frenchFries;
    private TextView textView_total_price;
    private Button btn_reset, btn_order;
    private double price_total;
    HashMap<String, Double> filling_hash_map = new HashMap<>();
    HashMap<String, Double> side_hash_map = new HashMap<>();
    HashMap<String, Double> size_hash_map = new HashMap<>();
    HashMap<String, Double> addOn_hash_map = new HashMap<>();
    private onFragmentInteractionListener fragmentListener;
    private View fragmentView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private ArrayList<String> filling_selected_list = new ArrayList<>();
    private ArrayList<String> side_selected_list = new ArrayList<>();
    private String size_selected;

    public FragmentTwo() {
        // Required empty public constructor
    }
    public interface onFragmentInteractionListener {
        public void reset();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentListener = (FragmentTwo.onFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement onFragmentInteractionListener");
        }
    }
    //    public static FragmentTwo newInstance(ArrayList<String> param1, ArrayList<String> param2, String param3) {
    public static FragmentTwo newInstance(ArrayList<String> param1, ArrayList<String> param2, String param3) {
        FragmentTwo fragment = new FragmentTwo();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, param1);
        args.putStringArrayList(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filling_selected_list = getArguments().getStringArrayList(ARG_PARAM1);
            side_selected_list = getArguments().getStringArrayList(ARG_PARAM2);
            size_selected = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        fragmentView = view;
        textView_addOn = view.findViewById(R.id.textView_addOn);
        radioGroup_addOn = view.findViewById(R.id.radioGroup_addOn);
        radioButton_tea = view.findViewById(R.id.radioButton_tea);
        radioButton_coffee = view.findViewById(R.id.radioButton_coffee);
        radioButton_frenchFries = view.findViewById(R.id.radioButton_frenchFries);
        textView_total_price = view.findViewById(R.id.textView_total_price);
        btn_order = view.findViewById(R.id.button_order);
        btn_reset = view.findViewById(R.id.button_reset);

        radioButton_frenchFries.setOnClickListener(this);
        radioButton_coffee.setOnClickListener(this);
        radioButton_tea.setOnClickListener(this);
        btn_order.setOnClickListener(this);
        btn_reset.setOnClickListener(this);

        initUI();
        calculateTotal();
        return view;
    }

    private void initUI() {

        filling_hash_map.put(getResources().getString(R.string.hamSlices), 2.50);
        filling_hash_map.put(getResources().getString(R.string.roastedChicken), 2.00);
        filling_hash_map.put(getResources().getString(R.string.beefSteak), 4.50);
        filling_hash_map.put(getResources().getString(R.string.grilledSalmon), 3.70);
        filling_hash_map.put(getResources().getString(R.string.kebab), 4.00);

        side_hash_map.put(getResources().getString(R.string.tomato), 1.00);
        side_hash_map.put(getResources().getString(R.string.lettuce), 1.20);
        side_hash_map.put(getResources().getString(R.string.onion), 0.50);
        side_hash_map.put(getResources().getString(R.string.cheese), 1.50);

        size_hash_map.put(getResources().getString(R.string.smallSize), 7.00);
        size_hash_map.put(getResources().getString(R.string.mediumSize), 9.50);
        size_hash_map.put(getResources().getString(R.string.largeSize), 13.00);

        addOn_hash_map.put(getResources().getString(R.string.tea), 3.50);
        addOn_hash_map.put(getResources().getString(R.string.coffee), 4.50);
        addOn_hash_map.put(getResources().getString(R.string.frenchFries), 5.00);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_order:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(createOrderMessage());
                AlertDialog.Builder builder = alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Object button;
                        Toast.makeText(getContext(), "注文成功", Toast.LENGTH_SHORT).show();
                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setTitle("Your Order");
                alertDialog.show();
                break;
            case R.id.button_reset:
                fragmentListener.reset();
                break;
        }
        calculateTotal();
    }

    //This function will calculate the total price according to the size, side and the filling chosen.
    private void calculateTotal() {
        double filling_total = 0, side_total = 0, size_total = 0, addOn_total;
        price_total = 0;

        size_total = calculateSizeTotal();
        side_total = calculateSideTotal();
        filling_total = calculateFillingTotal();
        addOn_total = calculateAddOn();

        price_total = filling_total + side_total + size_total + addOn_total;
        textView_total_price.setText(String.format("%.2f", price_total));
    }

    //This function will return the filling additional price
    private double calculateFillingTotal() {
        int filling_check_count = 0;
        double smallest = 100, filling_total = 0;
        ArrayList<Double> filling_price_list = new ArrayList<>();
        for (String a : filling_selected_list) {
            filling_total += filling_hash_map.get(a);
            filling_price_list.add(filling_hash_map.get(a));
            filling_check_count += 1;
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
        for (String a : side_selected_list) {
            side_total += side_hash_map.get(a);
            side_price_list.add(side_hash_map.get(a));
            side_check_count += 1;
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
        return size_hash_map.get(size_selected);
    }

    private double calculateAddOn() {
        double addOn = 0;
        RadioButton addOnButton;
        if (radioGroup_addOn.getCheckedRadioButtonId() != -1) {
            addOnButton = fragmentView.findViewById(radioGroup_addOn.getCheckedRadioButtonId());
            if(filling_selected_list.size()>=3)
            return addOn_hash_map.get(addOnButton.getTag().toString())*0.8;
                else
            return addOn_hash_map.get(addOnButton.getTag().toString());
        } else
            return 0;
    }

    //Finalise the order message.
    private String createOrderMessage() {
        String orderMessage = "";
        HashMap<String, Double> filling_hash_map_temp = new HashMap<>();
        HashMap<String, Double> side_hash_map_temp = new HashMap<>();

        orderMessage += "Size: " + size_selected + " RM " + String.format("%.2f", size_hash_map.get(size_selected)) + "\n";

        for (int i = 0; i < filling_selected_list.size(); i++) {
            filling_hash_map_temp.put(filling_selected_list.get(i), filling_hash_map.get(filling_selected_list.get(i)));
        }

        for (Map.Entry map : filling_hash_map_temp.entrySet()) {
            if (map.getValue().toString().equals(Collections.min(filling_hash_map_temp.values()).toString()))
                orderMessage += map.getKey().toString() + ": RM " + "0.00" + "\n";
            else
                orderMessage += map.getKey().toString() + ": RM " + String.format("%.2f", map.getValue()) + "\n";
        }
        for (int i = 0; i < side_selected_list.size(); i++) {
            filling_hash_map_temp.put(side_selected_list.get(i), side_hash_map.get(side_selected_list.get(i)));
        }
        for (Map.Entry map : side_hash_map_temp.entrySet()) {
            if (map.getValue().toString().equals(Collections.min(side_hash_map_temp.values()).toString()))
                orderMessage += map.getKey().toString() + ": RM " + "0.00" + "\n";
            else
                orderMessage += map.getKey().toString() + ": RM " + String.format("%.2f", map.getValue()) + "\n";
        }

        RadioButton addOnButton;
        if (radioGroup_addOn.getCheckedRadioButtonId() != -1) {
            addOnButton = fragmentView.findViewById(radioGroup_addOn.getCheckedRadioButtonId());
            if(filling_selected_list.size()>=3)
            { double actualPrice=addOn_hash_map.get(addOnButton.getTag())*0.8;
            double discount=addOn_hash_map.get(addOnButton.getTag())*0.2;
                orderMessage+= addOnButton.getTag().toString() + ": RM" + String.format("%.2f", actualPrice)+" (-RM"+String.format("%.2f",discount )+")"+"\n";
            }
                else
            orderMessage += addOnButton.getTag().toString() + ": RM" + String.format("%.2f", addOn_hash_map.get(addOnButton.getTag().toString()))+"\n";
        }

        orderMessage += "**********************\n";
        orderMessage += "Total: RM" + String.format("%.2f", price_total) + "\n";
        orderMessage += "**********************";
        return orderMessage;
    }
}