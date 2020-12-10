package com.example.week3demonstration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private TextView textView_name, textView_email;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initUI();

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null)
        {
            String name= bundle.getString("Name");
            String email=bundle.getString("Email");
            int img=bundle.getInt("Gender");
            displayProfile(name,email,img);
        }
    }
    private void initUI(){
        textView_name=findViewById(R.id.textView_name);
        textView_email=findViewById(R.id.textView_email);
        imageView=findViewById(R.id.imageView_gender);

    }
    private void displayProfile(String name, String email, int icon)
    {
        textView_name.setText("Name: " + name);
        textView_email.setText("Email: "+email);
        imageView.setImageResource(icon);
    }
}