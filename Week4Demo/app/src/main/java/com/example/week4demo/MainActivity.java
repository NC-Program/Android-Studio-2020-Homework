package com.example.week4demo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private Button button_order;
    private TextView textView_order,textView_total;
    private int REQUEST_CODE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        linearLayout=findViewById(R.id.linear_layout);
        button_order=findViewById(R.id.button_order);
        textView_order=findViewById(R.id.textView_order);
        textView_total=findViewById(R.id.textView_total);
        button_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getApplicationContext(),MainActivity2.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                if(data !=null)
                {
                    String food_order="";
                    Double total=0.0;
                    ArrayList<Order>order_list=data.getParcelableArrayListExtra("ORDER_LIST");
                    for(Order order:order_list)
                    {
                        food_order+=order.toString()+ "\n";
                        if(order.getOrder().equals("pizza"))
                            total=total + (order.getQty()*18.00);
                        else if (order.getOrder().equals("burger"))
                            total=total+(order.getQty()*5.00);
                    }
                    linearLayout.setVisibility(View.VISIBLE);
                    textView_order.setText(food_order);
                    textView_total.setText("Total: RM "+String.format("%.2f",total));
                }
            }
        }
    }
}