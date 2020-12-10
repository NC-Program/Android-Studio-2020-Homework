package com.example.week6demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentOne.onFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentOne fragmentOne=new FragmentOne();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_layout,fragmentOne)
                .commit();
    }

    @Override
    public void passData(boolean cough, boolean fever) {
        FragmentTwo fragmentTwo=FragmentTwo.newInstance(cough,fever);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout,fragmentTwo)
                .addToBackStack(null)
                .commit();
    }
}