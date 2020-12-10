package com.example.tutorial1task5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private Question[] mQuestionBank=new Question[]{
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true)
    };
    private TextView textViewQuestion;
    private Button btnTrue;
    private Button btnFalse;
    private Button btnNext;
    private int qNum=0;
    private Boolean receive;
    private Boolean next=false;

//    Snackbar mySnackbar = Snackbar.make(R.id.layout, R.string.question_asia, 2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();

    }

    private void initializeUI(){
        textViewQuestion=findViewById(R.id.textViewQuestion);
        btnTrue=findViewById(R.id.buttonTrue);
        btnFalse=findViewById(R.id.buttonFalse);
        btnNext=findViewById(R.id.buttonNext);
        textViewQuestion.setText(getString(mQuestionBank[qNum].GetQuestionNumber()));
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mQuestionBank[qNum].GetAnswer()==true) {
                    Snackbar snackbar = Snackbar.make(view, "Correct !!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else
                {
                    Snackbar snackbar = Snackbar.make(view, "False !!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mQuestionBank[qNum].GetAnswer()==false)
                {
                    Snackbar snackbar = Snackbar.make(view, "Correct !!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else
                {
                    Snackbar snackbar = Snackbar.make(view, "False !!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qNum<4)
                {
                    qNum+=1;
                    textViewQuestion.setText(getString(mQuestionBank[qNum].GetQuestionNumber()));
                }
                else
                btnNext.setEnabled(false);
            }
        });
    }
}
