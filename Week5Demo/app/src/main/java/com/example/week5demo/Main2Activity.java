package com.example.week5demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private TextView textView_Name, textView_details;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    initUI();
    Bundle bundle=getIntent().getExtras();
    if(bundle!=null)
    {
        Contact contact=(Contact)bundle.getSerializable("contact");
        textView_Name.setText(contact.getName());
        textView_details.setText(contact.toString());
        imageView.setImageResource(contact.getGender().equals("male")?R.drawable.male: R.drawable.female);
    }
    }

    private void initUI() {
    textView_Name=findViewById(R.id.textView_name);
    textView_details=findViewById(R.id.textView_details);
    imageView=findViewById(R.id.imageView2);

    }


}