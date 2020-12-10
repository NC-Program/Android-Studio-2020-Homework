package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText editText_item_name, editText_details, editText_quantity;
    private ImageButton imageButton_arrow_down, imageButton_arrow_up;
    private Spinner spinner_size;
    private CheckBox checkBox_urgent;
    private Button button_addToList;
    private Toolbar toolbar;
    private int quantity = 0;
    private ShoppingItem shoppingItem;
    private String actionMode = "Add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        initUI();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {

            if (intent.getExtras().getString("Action").equals("Edit")) {
                actionMode = "Edit";
                button_addToList.setText(R.string.button_label_update);
                toolbar.setTitle(R.string.menu_editItem);
                setSupportActionBar(toolbar);
                shoppingItem = intent.getExtras().getParcelable("SHOPPINGITEM");
                if (shoppingItem != null) {
                    editText_item_name.setText(shoppingItem.getName());
                    editText_details.setText(shoppingItem.getDetails());
                    editText_quantity.setText(String.valueOf(shoppingItem.getQty()));
                    if (shoppingItem.getSize().equals(getText(R.string.size_preset_default)))
                        spinner_size.setSelection(0);
                    else if (shoppingItem.getSize().equals(getText(R.string.size_preset_small)))
                        spinner_size.setSelection(1);
                    else if (shoppingItem.getSize().equals(getText(R.string.size_preset_medium)))
                        spinner_size.setSelection(2);
                    else if (shoppingItem.getSize().equals(getText(R.string.size_preset_large)))
                        spinner_size.setSelection(3);
                    checkBox_urgent.setChecked(shoppingItem.isUrgent());

                }

            }
        }
    }


    private void initUI() {
        editText_item_name = findViewById(R.id.editText_item_name);
        editText_details = findViewById(R.id.editText_details);
        editText_quantity = findViewById(R.id.editText_quantity);
        imageButton_arrow_up = findViewById(R.id.imageButton_arrow_up);
        imageButton_arrow_down = findViewById(R.id.imageButton_arrow_down);
        spinner_size = findViewById(R.id.spinner_size);
        checkBox_urgent = findViewById(R.id.checkBox_urgent);
        button_addToList = findViewById(R.id.button_addToList);
        imageButton_arrow_up.setOnClickListener(this);
        imageButton_arrow_down.setOnClickListener(this);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle(R.string.menu_addItem);
        setSupportActionBar(toolbar);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_details, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_size.setAdapter(adapter);
        spinner_size.setOnItemSelectedListener(this);

        button_addToList.setOnClickListener(this);
        editText_quantity.setText(String.valueOf(quantity));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton_arrow_up:
                quantity = Integer.valueOf(editText_quantity.getText().toString());
                quantity++;
                editText_quantity.setText(String.valueOf(quantity));
                break;
            case R.id.imageButton_arrow_down:

                quantity = Integer.valueOf(editText_quantity.getText().toString());
                if (quantity > 0)
                    quantity--;
                editText_quantity.setText(String.valueOf(quantity));
                break;
            case R.id.button_addToList:

                if (validificationCheck()) {
                    Intent resultIntent = new Intent();
                    if (actionMode.equals("Add"))
                        shoppingItem = new ShoppingItem(editText_item_name.getText().toString(), editText_details.getText().toString(), spinner_size.getSelectedItem().toString(), Integer.valueOf(editText_quantity.getText().toString()), checkBox_urgent.isChecked());
                    else {
                        shoppingItem.setName(editText_item_name.getText().toString());
                        shoppingItem.setDetails(editText_details.getText().toString());
                        shoppingItem.setQty(Integer.valueOf(editText_quantity.getText().toString()));
                        if (checkBox_urgent.isChecked())
                            shoppingItem.setUrgent(1);
                        else
                            shoppingItem.setUrgent(0);
                        shoppingItem.setSize(spinner_size.getSelectedItem().toString());
                    }
                    resultIntent.putExtra("SHOPPINGITEM", shoppingItem);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
                break;
        }
    }

    public boolean validificationCheck() {

        if (TextUtils.isEmpty(editText_item_name.getText().toString())) {
            editText_item_name.setError(getText(R.string.error_itemNotSelected));
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}