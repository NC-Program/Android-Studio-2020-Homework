package com.example.week3demonstration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    private EditText editText_name,getEditText_email;
    private RadioGroup radioGroup;
    private Button btn_submit;
    private String err1="Enter your name";
    private String err2="Enter your email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI(){
        editText_name=findViewById(R.id.editText_name);
        getEditText_email=findViewById(R.id.editText_email);
        radioGroup=findViewById(R.id.radioGroup_gender);
        btn_submit=findViewById(R.id.button_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editText_name.getText().toString().trim();
                String email=getEditText_email.getText().toString().trim();

                if(!isEmpty(name,editText_name,err1) && !isEmpty(email,getEditText_email,err2))
                {
                    submitProfile(name, email);
                }
            }
        });
    }

    private  Boolean isEmpty(String info, EditText editText, String err){
        if(TextUtils.isEmpty(info))
        {
            editText.setError(err);
            return true;
        }
        return false;
    }
    private  void submitProfile(String name, String email)
    {
        Bundle bundle=new Bundle();
        bundle.putString("Name",name);
        bundle.putString("Email",email);
        bundle.putInt("Gender",getGender());

        Intent intent= new Intent();
        intent.setClass(getApplicationContext(),MainActivity2.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private int getGender(){
          int icon=0;
          switch (radioGroup.getCheckedRadioButtonId()){
              case R.id.radioButton_male:
                  icon=R.drawable.image3;
                  break;
              case R.id.radioButton2_female:
                  icon=R.drawable.image1;
                  break;
          }
          return icon;
    }
}