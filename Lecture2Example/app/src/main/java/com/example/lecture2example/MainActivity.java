package com.example.lecture2example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private EditText editTextInput;
    private Button btnConvert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
        restoreState(savedInstanceState);
    }

    private void initializeUI() {
        textViewResult=findViewById(R.id.textViewResult);
        editTextInput=findViewById(R.id.editTextCelcius);
        btnConvert=findViewById(R.id.buttonConvert);
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result=ConvertToFH(editTextInput.getText().toString());
                textViewResult.setText(result);
            }
        });
    }

    private String ConvertToFH(String inputCelcius){
        try {
            double c = Double.parseDouble(inputCelcius);
            double f = c * (9.0 / 5.0) + 32.0;
            return String.format("%3.2f", f);
        }catch(NumberFormatException nfe)
        {
            return "Invalid Input";
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String result=textViewResult.getText().toString();
        outState.putString("RESULT",result);
    }

    private void restoreState(Bundle bundle){
        if(bundle == null)
            return;
        String result=bundle.getString("RESULT");
        textViewResult.setText(result);
    }
}