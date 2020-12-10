package com.example.mysandwichbar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOne extends Fragment implements View.OnClickListener {
    private static final String TAG = "Swipe Position";
    private float x1, x2, y1, y2;
    private static int MIN_DISTANCE = 150;
    private GestureDetector gestureDetector;
    private View fragmentView;

    private CheckBox checkBox_cut_ham, checkBox_chicken, checkBox_beef, checkBox_salmon, checkBox_kebab;
    private CheckBox checkBox_tomato, checkBox_lettuce, checkBox_onion, checkBox_cheese;
    private RadioGroup radioGroup_size;
    private RadioButton radioButton_6inch, radioButton_9inch, radioButton_12inch;
    private TextView textView_filling, textView_side;


    private ArrayList<CheckBox> checkBox_filling_list = new ArrayList<>();
    private ArrayList<CheckBox> checkBox_side_list = new ArrayList<>();
    private ArrayList<RadioButton> radioButton_size_list = new ArrayList<>();
    private LinkedHashMap<String, String> side_map = new LinkedHashMap<String, String>();
    private ArrayList<String> filling_display_array = new ArrayList<String>();
    private ArrayList<String> side_display_array = new ArrayList<String>();

    private onFragmentInteractionListener fragmentListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public void onClick(View view) {

    }


    public interface onFragmentInteractionListener {
        public void passData(ArrayList<String> filling_selected_array, ArrayList<String> side_selected_array, String size_selected);
    }

    public FragmentOne() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOne.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentOne newInstance(String param1, String param2) {
        FragmentOne fragment = new FragmentOne();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    public void nextPage() {
        String selected_size = "";
        RadioButton chosen_size = fragmentView.findViewById(radioGroup_size.getCheckedRadioButtonId());
        selected_size = chosen_size.getTag().toString();
        fragmentListener.passData(filling_display_array, side_display_array, selected_size);
    }

    public ArrayList<String> getFilling_display_array() {
        return filling_display_array;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        fragmentView = view;

        checkBox_cut_ham = view.findViewById(R.id.checkBox_cut_ham);
        checkBox_chicken = view.findViewById(R.id.checkBox_chicken);
        checkBox_beef = view.findViewById(R.id.checkBox_beef);
        checkBox_salmon = view.findViewById(R.id.checkBox_salmon);
        checkBox_kebab = view.findViewById(R.id.checkBox_kebab);
        checkBox_tomato = view.findViewById(R.id.checkBox_tomato);
        checkBox_lettuce = view.findViewById(R.id.checkBox_lettuce);
        checkBox_onion = view.findViewById(R.id.checkBox_onion);
        checkBox_cheese = view.findViewById(R.id.checkBox_cheese);

        radioGroup_size = view.findViewById(R.id.radioGroup_size);
        radioButton_6inch = view.findViewById(R.id.radioButton_6inch);
        radioButton_9inch = view.findViewById(R.id.radioButton_9inch);
        radioButton_12inch = view.findViewById(R.id.radioButton_12inch);

        textView_filling = view.findViewById(R.id.textView_filling);
        textView_side = view.findViewById(R.id.textView_side);


        radioButton_6inch.setChecked(true);
        checkBox_filling_list.clear();
        checkBox_side_list.clear();
        radioButton_size_list.clear();
        initUI();
        checkFillingMax();
        return view;
    }

    private void initUI() {
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
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentListener = (onFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement onFragmentInteractionListener");
        }
    }

    //This function will update the filling order text view and update total amount when the side check box was clilcked.
    public void onCheckboxClickedFilling(View view) {
        CheckBox checkBox = view.findViewById(view.getId());
        if (checkBox.isChecked())
            filling_display_array.add(checkBox.getTag().toString());
        else
            filling_display_array.remove(checkBox.getTag().toString());
        if (getFilling_display_array().size() == 0)
            textView_filling.setText("Filling | ");
        else
            textView_filling.setText("Filling | " + filling_display_array.get(filling_display_array.size() - 1));
        checkFillingMax();
    }

    //This function will update the side order text view and update total amount when the side check box was clilcked.
    public void onCheckboxClickedSide(View view) {
        CheckBox checkBox = view.findViewById(view.getId());
        if (checkBox.isChecked())
            side_display_array.add(checkBox.getTag().toString());
        else
            side_display_array.remove(checkBox.getTag().toString());
        if (getFilling_display_array().size() == 0)
            textView_side.setText("Side | ");
        else
            textView_side.setText("Side | " + side_display_array.get(side_display_array.size() - 1));
    }

    //This function checks whether the user has selected a maximum or 3 filling.
    public void checkFillingMax() {
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


}