package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainFragment.onFragmentInteractionListener {
    private static final String MENU_HOME = "HOME";
    private static final String MENU_URGENTLIST = "URGENT";
    private static final String MENU_COMPLETEDLIST = "COMPLETED";
    private DataBaseHandler databaseHandler;
    private List<ShoppingItem> shoppingItemList;
    private FloatingActionButton floatingActionButton;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler = new DataBaseHandler(this, null, null, 1);

        toolbar = findViewById(R.id.toolbar);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        toolbar.setTitle(R.string.menu_shoppingList);
        setSupportActionBar(toolbar);

        populateDB();
        updateShoppingItemList(MENU_HOME);
        Fragment fragment = new MainFragment(shoppingItemList, getApplicationContext());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AddItemActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_urgent_list:
                        toolbar.setTitle(R.string.menu_urgentList);
                        updateShoppingItemList(MENU_URGENTLIST);
                        break;
                    case R.id.nav_home:
                        toolbar.setTitle(R.string.menu_shoppingList);
                        updateShoppingItemList(MENU_HOME);
                        break;
                    case R.id.nav_completed_list:
                        toolbar.setTitle(R.string.menu_completed);
                        updateShoppingItemList(MENU_COMPLETEDLIST);
                        System.out.println(shoppingItemList.size());
                        break;
                }
                setSupportActionBar(toolbar);
                Fragment selectedFragment = null;
                selectedFragment = new MainFragment(shoppingItemList, getApplicationContext());
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });
    }

    private void updateShoppingItemList(String menu) {

        if (shoppingItemList != null)
            shoppingItemList.clear();
        switch (menu) {
            case MENU_COMPLETEDLIST:
                shoppingItemList = databaseHandler.getCompletedListShoppingItem();
                System.out.println(MENU_COMPLETEDLIST);
                break;
            case MENU_HOME:
                shoppingItemList = databaseHandler.getHomeShoppingItem();
                System.out.println(MENU_HOME);
                break;
            case MENU_URGENTLIST:
                shoppingItemList = databaseHandler.getUrgentListShoppingItem();
                System.out.println(MENU_URGENTLIST);
                break;
        }
        //Sort the list in alphabatical order
        Collections.sort(shoppingItemList, new Comparator<ShoppingItem>() {
            @Override
            public int compare(ShoppingItem shoppingItem, ShoppingItem t1) {
                return shoppingItem.getName().compareTo(t1.getName());
            }
        });
    }

    private void populateDB() {
        databaseHandler.addShoppingItem(new ShoppingItem("Bread", "Just a bread", getText(R.string.size_preset_default).toString(), 1, false));
        databaseHandler.addShoppingItem(new ShoppingItem("Instant Noodle", "Just a instant noodle", getText(R.string.size_preset_default).toString(), 1, true));
        databaseHandler.addShoppingItem(new ShoppingItem("Chocolate Bar", "Just a chocolate bar", getText(R.string.size_preset_small).toString(), 1, true));
        databaseHandler.addShoppingItem(new ShoppingItem("Milk", "Milkyyyy milk", getText(R.string.size_preset_large).toString(), 3, false));
        databaseHandler.addShoppingItem(new ShoppingItem("Juice", "juiccccce", getText(R.string.size_preset_large).toString(), 2, true));
        databaseHandler.addShoppingItem(new ShoppingItem("鸡蛋", "我最爱的鸡蛋", getText(R.string.size_preset_default).toString(), 5, true));
        databaseHandler.addShoppingItem(new ShoppingItem("メロンパン", "可愛いのメロンパン", getText(R.string.size_preset_default).toString(), 2, true));
        databaseHandler.addShoppingItem(new ShoppingItem("コロッケ", "大好きなコロッケーーーー", getText(R.string.size_preset_default).toString(), 3, true));
        databaseHandler.addShoppingItem(new ShoppingItem("ホットドッグ", "ホットドッグはホットドッグだよね", getText(R.string.size_preset_large).toString(), 4, false));
        databaseHandler.addShoppingItem(new ShoppingItem("Macbook 13 2020", "M1 processor!!!!!", getText(R.string.size_preset_default).toString(), 1, true));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getParcelableExtra("SHOPPINGITEM") != null) {
                ShoppingItem shoppingItem = data.getParcelableExtra("SHOPPINGITEM");
                if (requestCode == 1) { // Add item
                    if (resultCode == RESULT_OK) {
                        databaseHandler.addShoppingItem(shoppingItem);
                    }
                }
                if (requestCode == 2) { // Edit Item

                    if (resultCode == RESULT_OK) {
                        databaseHandler.updateShoppingItem(shoppingItem);

                        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                        System.out.println(shoppingItem.getName());
                        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    }
                }
                updateShoppingItemListFromBN();
                Fragment newFragment = new MainFragment(shoppingItemList, getApplicationContext());
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newFragment).commit();
            }
        }
    }


    @Override
    public void onFragmentListClick(int position) {
        Intent intent = new Intent();
        intent.putExtra("SHOPPINGITEM", shoppingItemList.get(position));
        intent.setClass(this, DisplayItemActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onFragmentBoughtSwitchClick(int position) {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        shoppingItemList.get(position).setBoughtDate(formattedDate);
        shoppingItemList.get(position).setBought(1);
        databaseHandler.updateShoppingItem(shoppingItemList.get(position));
        updateShoppingItemListFromBN();
    }

    public void updateShoppingItemListFromBN() {
        switch (bottomNavigationView.getSelectedItemId()) {
            case R.id.nav_home:
                updateShoppingItemList(MENU_HOME);
                break;
            case R.id.nav_urgent_list:
                updateShoppingItemList(MENU_URGENTLIST);
                break;
            case R.id.nav_completed_list:
                updateShoppingItemList(MENU_COMPLETEDLIST);
                break;
        }
        Fragment nFragment = new MainFragment(shoppingItemList, getApplicationContext());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, nFragment).commit();
    }

    @Override
    public void onFragmentListLongClick(final int position) {
        if (bottomNavigationView.getSelectedItemId() != R.id.nav_completed_list) {
            String deleteText = "Are you sure you want to delete " + shoppingItemList.get(position).getName() + " from the list?";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getText(R.string.dialog_delete_title))
                    .setMessage(deleteText)
                    .setPositiveButton(getText(R.string.dialog_delete_yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseHandler.deleteShoppingItem(shoppingItemList.get(position));
                            updateShoppingItemList(MENU_HOME);
                            Fragment fragment = new MainFragment(shoppingItemList, getApplicationContext());
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                        }
                    })
                    .setNegativeButton(getText(R.string.dialog_delete_no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}