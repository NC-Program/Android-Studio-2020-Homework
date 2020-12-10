package com.example.week10demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BookDB";
    private static final String TABLE_NAME = "Book_Table";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_GENRE = "genre";
    private static final String KEY_IMAGE = "image";

    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_TITLE + " TEXT, " + KEY_GENRE + " TEXT, " +
                KEY_IMAGE + " INTEGER)";
        sqLiteDatabase.execSQL(create_table);
        System.out.println("Table Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
        System.out.println("Table dropped");
    }

    public long addBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, book.getTitle());
        contentValues.put(KEY_GENRE, book.getGenre());
        contentValues.put(KEY_IMAGE, book.getImage());
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public Book getBook(int bookID) {
        Book book = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_TITLE, KEY_GENRE, KEY_IMAGE},
                KEY_ID + "=?", new String[]{String.valueOf(bookID)},
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            book = new Book(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3));
        }
        return book;
    }

    public List<Book> getAllBook() {
        List<Book> bookList = new ArrayList<Book>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setTitle(cursor.getString(1));
                book.setGenre(cursor.getString(2));
                book.setImage(cursor.getInt(3));
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        return bookList;
    }

    public int deleteBook (Book book)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,KEY_ID+"=?",new String[]{String.valueOf(book.getId())});

    }

}
