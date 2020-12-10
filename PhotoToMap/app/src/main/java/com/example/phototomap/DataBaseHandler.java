package com.example.phototomap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LocationDB"; //Database Name
    private static final String TABLE_NAME = "Location_Table"; //Table Name
    private static final String KEY_LOCATION_ID="id";
    private static final String KEY_LOCATION_LATITUDE = "latitude"; // Latitude Name
    private static final String KEY_LOCATION_LONGITUDE = "longitude"; // longitude
    private static final String KEY_LOCATION_ADDRESS = "address"; //Address
    private static final String KEY_LOCATION_IMAGES = "image";//Image
    public DataBaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_LOCATION_LATITUDE + " REAL, "
                + KEY_LOCATION_LONGITUDE + " REAL, "
                + KEY_LOCATION_ADDRESS + " TEXT,"
                + KEY_LOCATION_IMAGES+" BLOB)";
        sqLiteDatabase.execSQL(create_table);
        System.out.println("Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
        System.out.println("Table dropped");
    }

    public long addLocation(Location location,byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LOCATION_LATITUDE,location.getLatitude());
        contentValues.put(KEY_LOCATION_LONGITUDE,location.getLongitude());
        contentValues.put(KEY_LOCATION_ADDRESS,location.getAddress());
        contentValues.put(KEY_LOCATION_IMAGES,image);
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public Location getLocation(int locationID) {
        Location location=null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_LOCATION_ID, KEY_LOCATION_LATITUDE, KEY_LOCATION_LONGITUDE,  KEY_LOCATION_ADDRESS},
                KEY_LOCATION_ID + "=?", new String[]{String.valueOf(locationID)},
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            location = new Location(cursor.getInt(0), cursor.getDouble(1), cursor.getDouble(2),
                    cursor.getString(3),DbBitmapUtility.getImage(cursor.getBlob(4)));
        }
        return location;
    }

    public List<Location> getAllLocation() {
        List<Location> locationList = new ArrayList<Location>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Location location = new Location();
                location.setId(Integer.parseInt(cursor.getString(0)));
                location.setLatitude(Double.parseDouble(cursor.getString(1)));
                location.setLongitude(Double.parseDouble(cursor.getString(2)));
                location.setAddress(cursor.getString(3));
                location.setBitmap(DbBitmapUtility.getImage(cursor.getBlob(4)));
                locationList.add(location);
            } while (cursor.moveToNext());
        }
        return locationList;
    }
    public int deleteLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, KEY_LOCATION_ID + "=?", new String[]{String.valueOf(location.getId())});
    }

}
