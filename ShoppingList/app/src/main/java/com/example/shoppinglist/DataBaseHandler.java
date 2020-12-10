package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ShoppingList "; //Database Name
    private static final String TABLE_NAME = "Shopping_Table"; //Table Name
    private static final String KEY_ITEM_ID = "ITEM_ID"; // ID's Name
    private static final String KEY_ITEM_NAME = "ITEM_NAME"; // Item's name's Name
    private static final String KEY_ITEM_DETAILS = "DETAIL"; //Detail's Name
    private static final String KEY_ITEM_SIZE = "SIZE"; // Size's Name
    private static final String KEY_ITEM_QTY = "QUANTITY"; // Quantity's Name
    private static final String KEY_ITEM_URGENT = "URGENT"; // Urgent's Name
    private static final String KEY_ITEM_BOUGHT = "BOUGHT"; // Bought's Name
    private static final String KEY_ITEM_DATE = "DATE"; // Date's Name

    public DataBaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_ITEM_NAME + " TEXT NOT NULL, "
                + KEY_ITEM_DETAILS + " TEXT, "
                + KEY_ITEM_SIZE + " TEXT NOT NULL, "
                + KEY_ITEM_QTY + " INTEGER DEFAULT 0, "
                + KEY_ITEM_URGENT + " INTEGER DEFAULT 0, "
                + KEY_ITEM_BOUGHT + " INTEGER DEFAULT 0, "
                + KEY_ITEM_DATE + " TEXT)";
        sqLiteDatabase.execSQL(create_table);
        System.out.println("Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
        System.out.println("Table dropped");
    }

    public long updateShoppingItem(ShoppingItem shoppingItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ITEM_NAME, shoppingItem.getName());
        contentValues.put(KEY_ITEM_DETAILS, shoppingItem.getDetails());
        contentValues.put(KEY_ITEM_URGENT, shoppingItem.getUrgent());
        contentValues.put(KEY_ITEM_SIZE, shoppingItem.getSize());
        contentValues.put(KEY_ITEM_QTY, shoppingItem.getQty());
        contentValues.put(KEY_ITEM_BOUGHT,shoppingItem.getBought());
        contentValues.put(KEY_ITEM_DATE,shoppingItem.getBoughtDate());
        return db.update(TABLE_NAME,contentValues, KEY_ITEM_ID + "=?", new String[]{String.valueOf(shoppingItem.getId())});
    }

    public long addShoppingItem(ShoppingItem shoppingItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ITEM_NAME, shoppingItem.getName());
        contentValues.put(KEY_ITEM_DETAILS, shoppingItem.getDetails());
        contentValues.put(KEY_ITEM_URGENT, shoppingItem.getUrgent());
        contentValues.put(KEY_ITEM_SIZE, shoppingItem.getSize());
        contentValues.put(KEY_ITEM_QTY, shoppingItem.getQty());
        contentValues.put(KEY_ITEM_BOUGHT,shoppingItem.getBought());
        contentValues.put(KEY_ITEM_DATE,shoppingItem.getBoughtDate());
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public List<ShoppingItem> getAllShoppingItem() {
        List<ShoppingItem> shoppingItemList = new ArrayList<ShoppingItem>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ShoppingItem shoppingItem = new ShoppingItem();
                shoppingItem.setId(Integer.parseInt(cursor.getString(0)));
                shoppingItem.setName(cursor.getString(1));
                shoppingItem.setDetails(cursor.getString(2));
                shoppingItem.setSize(cursor.getString(3));
                shoppingItem.setQty(cursor.getInt(4));
                shoppingItem.setUrgent(cursor.getInt(5));
                shoppingItem.setBought(cursor.getInt(6));
                shoppingItem.setBoughtDate(cursor.getString(7));
                shoppingItemList.add(shoppingItem);
            } while (cursor.moveToNext());
        }
        return shoppingItemList;
    }

    public List<ShoppingItem> getHomeShoppingItem() {
        List<ShoppingItem> shoppingItemList = new ArrayList<ShoppingItem>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ShoppingItem shoppingItem = new ShoppingItem();
                shoppingItem.setId(Integer.parseInt(cursor.getString(0)));
                shoppingItem.setName(cursor.getString(1));
                shoppingItem.setDetails(cursor.getString(2));
                shoppingItem.setSize(cursor.getString(3));
                shoppingItem.setQty(cursor.getInt(4));
                shoppingItem.setUrgent(cursor.getInt(5));
                shoppingItem.setBought(cursor.getInt(6));
                shoppingItem.setBoughtDate(cursor.getString(7));
                if (shoppingItem.getBought() == 0)
                    shoppingItemList.add(shoppingItem);
            } while (cursor.moveToNext());
        }
        return shoppingItemList;
    }
    public List<ShoppingItem> getUrgentListShoppingItem() {
        List<ShoppingItem> shoppingItemList = new ArrayList<ShoppingItem>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ShoppingItem shoppingItem = new ShoppingItem();
                shoppingItem.setId(Integer.parseInt(cursor.getString(0)));
                shoppingItem.setName(cursor.getString(1));
                shoppingItem.setDetails(cursor.getString(2));
                shoppingItem.setSize(cursor.getString(3));
                shoppingItem.setQty(cursor.getInt(4));
                shoppingItem.setUrgent(cursor.getInt(5));
                shoppingItem.setBought(cursor.getInt(6));
                shoppingItem.setBoughtDate(cursor.getString(7));
                if (shoppingItem.getBought() == 0&& shoppingItem.getUrgent()==1)
                    shoppingItemList.add(shoppingItem);
            } while (cursor.moveToNext());
        }
        return shoppingItemList;
    }
    public List<ShoppingItem> getCompletedListShoppingItem() {
        List<ShoppingItem> shoppingItemList = new ArrayList<ShoppingItem>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ShoppingItem shoppingItem = new ShoppingItem();
                shoppingItem.setId(Integer.parseInt(cursor.getString(0)));
                shoppingItem.setName(cursor.getString(1));
                shoppingItem.setDetails(cursor.getString(2));
                shoppingItem.setSize(cursor.getString(3));
                shoppingItem.setQty(cursor.getInt(4));
                shoppingItem.setUrgent(cursor.getInt(5));
                shoppingItem.setBought(cursor.getInt(6));
                shoppingItem.setBoughtDate(cursor.getString(7));
                if (shoppingItem.getBought() == 1)
                    shoppingItemList.add(shoppingItem);
            } while (cursor.moveToNext());
        }
        return shoppingItemList;
    }
    public int deleteShoppingItem(ShoppingItem shoppingItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, KEY_ITEM_ID + "=?", new String[]{String.valueOf(shoppingItem.getId())});
    }

    public ShoppingItem getShoppingItem(int shoppingItemID) {
        ShoppingItem shoppingItem = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ITEM_ID, KEY_ITEM_NAME, KEY_ITEM_DETAILS, KEY_ITEM_SIZE, KEY_ITEM_QTY, KEY_ITEM_URGENT, KEY_ITEM_BOUGHT, KEY_ITEM_DATE},
                KEY_ITEM_ID + "=?", new String[]{String.valueOf(shoppingItemID)},
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            shoppingItem = new ShoppingItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7), cursor.getString(8));
        }
        return shoppingItem;
    }
}
