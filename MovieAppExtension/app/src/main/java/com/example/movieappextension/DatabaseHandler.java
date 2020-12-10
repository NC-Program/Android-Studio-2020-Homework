package com.example.movieappextension;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MovieWatchList "; //Database Name
    private static final String TABLE_NAME = "Movie_Table"; //Table Name
    private static final String KEY_MOVIE_ID = "MOVIE_ID";
    private static final String KEY_MOVIE_TITLE = "MOVIE_TITLE";
    private static final String KEY_MOVIE_GENRE = "MOVIE_GENRE";
    private static final String KEY_MOVIE_OVERVIEW = "MOVIE_OVERVIEW";
    private static final String KEY_MOVIE_POSTER = "MOVIE_POSTER";
    private static final String KEY_MOVIE_BIGPOSTER = "MOVIE_BIGPOSTEER";
    private static final String KEY_MOVIE_POPULARITY = "MOVIE_POPULARITY";

    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_MOVIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_MOVIE_TITLE + " TEXT NOT NULL, "
                + KEY_MOVIE_GENRE + " TEXT, "
                + KEY_MOVIE_OVERVIEW + " TEXT NOT NULL, "
                + KEY_MOVIE_POSTER + " TEXT, "
                + KEY_MOVIE_BIGPOSTER + " TEXT, "
                + KEY_MOVIE_POPULARITY + " REAL)";
        sqLiteDatabase.execSQL(create_table);
        System.out.println("Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
        System.out.println("Table dropped");
    }

    public long addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MOVIE_TITLE, movie.getTitle());
        contentValues.put(KEY_MOVIE_GENRE, movie.getGenre());
        contentValues.put(KEY_MOVIE_OVERVIEW, movie.getOverview());
        contentValues.put(KEY_MOVIE_POSTER, movie.getPoster());
        contentValues.put(KEY_MOVIE_BIGPOSTER, movie.getBigPoster());
        contentValues.put(KEY_MOVIE_POPULARITY, movie.getPopularity());
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public List<Movie> getAllMovie() {
        List<Movie> movieList = new ArrayList<Movie>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(0)));
                movie.setTitle(cursor.getString(1));
                movie.setGenre(cursor.getString(2));
                movie.setOverview(cursor.getString(3));
                movie.setPoster(cursor.getString(4));
                movie.setBigPoster(cursor.getString(5));
                movie.setPopularity(cursor.getDouble(6));
                movieList.add(movie);
            } while (cursor.moveToNext());
        }
        return movieList;
    }

    public int deleteShoppingItem(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, KEY_MOVIE_ID + "=?", new String[]{String.valueOf(movie.getId())});
    }

    public Movie getMovie(int movieID) {
        Movie movie = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_MOVIE_ID, KEY_MOVIE_TITLE, KEY_MOVIE_GENRE, KEY_MOVIE_OVERVIEW, KEY_MOVIE_POSTER, KEY_MOVIE_BIGPOSTER, KEY_MOVIE_POPULARITY},
                KEY_MOVIE_ID + "=?", new String[]{String.valueOf(movieID)},
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            movie = new Movie(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4), cursor.getString(5), cursor.getDouble(6));
        }
        return movie;
    }
}
