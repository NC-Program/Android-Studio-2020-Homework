package com.example.shoppinglist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayItemActivity extends AppCompatActivity {
    private TextView textView_shoppingItem_detail, textView_detail_detail, textView_quantity_detail, textView_size_detail;
    private ImageView imageView_urgent;
    private ImageButton imageButton_edit;
    private ShoppingItem shoppingItem;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_item);

        initUI();
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            shoppingItem = intent.getExtras().getParcelable("SHOPPINGITEM");
            if (shoppingItem != null) {
                textView_shoppingItem_detail.setText(shoppingItem.getName());
                textView_size_detail.setText(shoppingItem.getSize());
                textView_detail_detail.setText(shoppingItem.getDetails());
                textView_quantity_detail.setText(String.valueOf(shoppingItem.getQty()));
                if (shoppingItem.getUrgent() == 1) {
                    imageView_urgent.setImageResource(R.drawable.checked);
                } else {
                    imageView_urgent.setImageResource(R.drawable.unchecked);
                }

                // edit button won't show for completed items
                if (shoppingItem.getBought() == 1) {
                        imageButton_edit.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void initUI() {
        textView_shoppingItem_detail = findViewById(R.id.textView_shoppingItem_detail);
        textView_detail_detail = findViewById(R.id.textView_detail_detail);
        textView_quantity_detail = findViewById(R.id.textView_quantity_detail);
        textView_size_detail = findViewById(R.id.textView_size_detail);
        imageView_urgent = findViewById(R.id.imageView_urgent);
        imageButton_edit = findViewById(R.id.imageButton_edit);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.menu_displayItem);
        setSupportActionBar(toolbar);
        imageButton_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Action", "Edit");
                intent.putExtra("SHOPPINGITEM", shoppingItem);
                intent.setClass(getApplicationContext(), AddItemActivity.class);
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getParcelableExtra("SHOPPINGITEM") != null) {
                ShoppingItem editShoppingItem = data.getParcelableExtra("SHOPPINGITEM");
                String action = data.getStringExtra("Action");
                if (requestCode == 2) {
                    if (resultCode == RESULT_OK) {
                        Intent intent = new Intent();
                        intent.putExtra("SHOPPINGITEM", editShoppingItem);
                        intent.putExtra("Action", action);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        }
    }
}