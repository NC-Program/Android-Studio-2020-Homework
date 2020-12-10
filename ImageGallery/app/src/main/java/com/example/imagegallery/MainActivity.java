package com.example.imagegallery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    GridView gridView;
    Button button_delete, button_undo;
    GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        gridAdapter = new GridAdapter(this, Image.LoadImage());

        gridView.setAdapter(gridAdapter);
    }

    private void initUI() {
        gridView = findViewById(R.id.gridView);
        button_delete = findViewById(R.id.button_delete);
        button_undo = findViewById(R.id.button_undo);
        button_delete.setOnClickListener(this);
        button_undo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_delete:
                boolean clear = false;
                for (int a = gridAdapter.getCheckedList().size() - 1; a >= 0; a--)
                    if (gridAdapter.getCheckedList().get(a) == true)
                        clear = true;
                if (clear) {
                    gridAdapter.clearRecyclerImage();
                    for (int a = gridAdapter.getCheckedList().size() - 1; a >= 0; a--) {
                        if (gridAdapter.getCheckedList().get(a) == true) {
                            gridAdapter.addRecyclerImage(gridAdapter.getImageArrayList().get(a), a);
                            gridAdapter.getImageArrayList().remove(a);
                            gridAdapter.getCheckedList().remove(a);
                        }
                    }
                    gridAdapter.setCountView(0);
                    gridAdapter.notifyDataSetChanged();
                    gridView.setAdapter(gridAdapter);
                }

                break;
            case R.id.button_undo:
                gridAdapter.recoverRecyclerImage();
                gridAdapter.setCountView(0);
                gridAdapter.notifyDataSetChanged();
                gridView.setAdapter(gridAdapter);
                gridAdapter.clearRecyclerImage();
                break;
        }
    }
}