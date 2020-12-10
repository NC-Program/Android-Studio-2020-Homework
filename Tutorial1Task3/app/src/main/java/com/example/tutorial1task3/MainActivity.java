package com.example.tutorial1task3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnPushMe;
    private Button btnClickMe;
    private TextView textViewResult;
    private int clickNumber=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
        btnPushMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewResult.setTextColor(Color.parseColor("#0000ff"));
                textViewResult.setText("Button 1 pressed");
            }
        });
        btnClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickNumber+=1;
                String message="I was clicked " +clickNumber+" Times";
                if(clickNumber%2==0)
                    textViewResult.setTextColor(Color.parseColor("#FF0000"));
                else
                    textViewResult.setTextColor(Color.parseColor("#0000ff"));

                textViewResult.setText(message);

            }
        });
    }

    private void initializeUI(){
    btnPushMe=findViewById(R.id.buttonPushMe);
    btnClickMe=findViewById(R.id.buttonClickMe);
    textViewResult=findViewById(R.id.textViewResult);
    }


}