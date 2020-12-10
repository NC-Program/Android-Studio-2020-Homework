package com.example.rolldice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button btn_roll;
    TextView textView_score;
    ImageView imageView_1, imageView_2;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        boolean oddOrEven;
        int randomNumber1 = 0, randomNumber2 = 0;

        int addScore;
        super.onConfigurationChanged(newConfig);
        if (score % 2 == 0)
            oddOrEven = true;
        else
            oddOrEven = false;

        if (oddOrEven == true) {
            do {
                Random random = new Random();
                randomNumber1 = generateRandom();
                randomNumber2 = generateRandom();
                addScore = randomNumber1 + randomNumber2;
            } while ((addScore % 2) == 0);
        } else if (oddOrEven == false) {
            do {
                randomNumber1 = generateRandom();
                randomNumber2 = generateRandom();
                addScore = randomNumber1 + randomNumber2;
            } while ((addScore % 2) == 1);
        }
        updateImage(randomNumber1, imageView_1);
        updateImage(randomNumber2, imageView_2);
        updateScore(randomNumber1, randomNumber2);
    }

    private void initUI() {
        btn_roll = findViewById(R.id.button_roll);
        textView_score = findViewById(R.id.textView_score);
        imageView_1 = findViewById(R.id.imageView_1);
        imageView_2 = findViewById(R.id.imageView_2);

        btn_roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int randomNumber1 = generateRandom();
                int randomNumber2 = generateRandom();
                updateImage(randomNumber1, imageView_1);
                updateImage(randomNumber2, imageView_2);
                updateScore(randomNumber1, randomNumber2);

            }


        });
    }

    private void updateScore(int randomNumber1, int randomNumber2) {
        int addScore = 0;
        if (randomNumber1 == 6 && randomNumber2 == 6)
            addScore = (randomNumber1 + randomNumber2) * 2;
        else if (randomNumber1 == 4 && randomNumber2 == 4)
            addScore = -(randomNumber1 + randomNumber2) * 2;
        else
            addScore = randomNumber1 + randomNumber2;
        score += addScore;
        textView_score.setText("Score: " + String.valueOf(score));
    }

    public int generateRandom() {
        Random random = new Random();
        int randomNumber = random.nextInt(6) + 1;
        return randomNumber;
    }

    private void updateImage(int randomNumber, ImageView imageView) {
        switch (randomNumber) {
            case 1:
                imageView.setImageResource(R.drawable.dice_one);
                break;
            case 2:
                imageView.setImageResource(R.drawable.dice_two);
                break;
            case 3:
                imageView.setImageResource(R.drawable.dice_three);
                break;
            case 4:
                imageView.setImageResource(R.drawable.dice_four);
                break;
            case 5:
                imageView.setImageResource(R.drawable.dice_five);
                break;
            case 6:
                imageView.setImageResource(R.drawable.dice_six);
                break;
        }
        }


}