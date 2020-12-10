package com.example.week4demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    private CheckBox checkBox_pizza,checkBox_burger;
    private EditText editText_pizza_qty,editText_burger_qty;
    private Button btn_order;
    private ArrayList<Order>order_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initUI();
    }

    private void initUI() {
        checkBox_burger=findViewById(R.id.checkBox_burger);
        checkBox_pizza=findViewById(R.id.checkBox_pizza);
        editText_burger_qty=findViewById(R.id.editText_burger_qty);
        editText_pizza_qty=findViewById(R.id.editText_pizza_qty);
        btn_order=findViewById(R.id.button_order);
        btn_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        order_list=new ArrayList<Order>();
        if (checkBox_pizza.isChecked())
        {
            Order order=new Order(checkBox_pizza.getText().toString().toLowerCase(),Integer.parseInt(editText_pizza_qty.getText().toString()));
            order_list.add(order);
        }
        if (checkBox_burger.isChecked())
        {
            Order order = new Order(checkBox_burger.getText().toString().toLowerCase(), Integer.parseInt(editText_burger_qty.getText().toString()));
            order_list.add(order);
        }

        Intent resultIntent=new Intent();
        resultIntent.putParcelableArrayListExtra("ORDER_LIST",order_list);
        setResult(RESULT_OK,resultIntent);
        finish();
    }
}