package com.example.week6demo;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;


public class FragmentOne extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RadioGroup radioGroup1,radioGroup2;
    private Button btnSubmit;
    private boolean cough=false,fever=false;
    private onFragmentInteractionListener fragmentListener;



    public interface onFragmentInteractionListener {
        public void passData(boolean cough, boolean fever);

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_one, container, false);

        radioGroup1= view.findViewById(R.id.radioGrp1);
        radioGroup2=view.findViewById(R.id.radioGrp2);
        btnSubmit=view.findViewById(R.id.button_submit);
        btnSubmit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        getCheckedButton(radioGroup1.getCheckedRadioButtonId());
        getCheckedButton(radioGroup2.getCheckedRadioButtonId());
        fragmentListener.passData(cough,fever);
    }

    private void getCheckedButton(int radBtnId)
    {
        switch (radBtnId){
            case R.id.radioButton_q1_yes:
                cough=true;
                break;
            case R.id.radioButton_q1_no:
                cough=false;
                break;
            case R.id.radioButton_q2_yes:
                fever=true;
                break;
            case R.id.radioButton_q2_no:
                fever=false;
                break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            fragmentListener=(onFragmentInteractionListener)context;
        }catch(ClassCastException e)
        {
            throw new ClassCastException(context.toString()+"must implement onFragmentInteractionListener");
        }
    }
}