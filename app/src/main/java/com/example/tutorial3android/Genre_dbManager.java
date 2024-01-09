package com.example.tutorial3android;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;


public class Genre_dbManager extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Game_Genre";
    public static final String _ID = "_id";
    public static final String GENRE_NAME = "genre";

    // Database Information
    static final String DB_NAME = "GENRE.DB";
    // Initial database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            GENRE_NAME + " TEXT NOT NULL);";

    public Genre_dbManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DatabaseVersion", "Upgrading database from version " + oldVersion + " to " + newVersion);

        // Example upgrade logic
        if (oldVersion < 2) {
            // Upgrade to version 2
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN new_column TEXT;");
        }

        if (oldVersion < 3) {
            // Upgrade to version 3
            db.execSQL("CREATE TABLE new_table (_id INTEGER PRIMARY KEY, name TEXT);");
        }
        // ... Add more upgrade logic as needed
    }

    public void insert(String genre) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(GENRE_NAME, genre);
        database.insert(TABLE_NAME, null, contentValue);
        database.close();
    }

    public Cursor fetch() {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[]{_ID, GENRE_NAME};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        database.close();
        return cursor;
    }

    public int update(Integer _id, String genre) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GENRE_NAME, genre);
        int i = database.update(TABLE_NAME, contentValues, _ID + " = " + _id, null);
        database.close();
        return i;
    }

    public void delete(long _id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, _ID + "=" + _id, null);
        database.close();
    }

    public GenreData getGenreById(int genreId) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[]{_ID, GENRE_NAME};

        Cursor cursor = database.query(TABLE_NAME, columns, _ID + " = " + genreId, null, null, null, null);

        GenreData genre = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") int _id = cursor.getInt(cursor.getColumnIndex(_ID));
                @SuppressLint("Range") String genre_name = cursor.getString(cursor.getColumnIndex(GENRE_NAME));

                genre = new GenreData(_id, genre_name, new ArrayList<>());
            }

            cursor.close();
        }
        database.close();
        return genre;
    }
}
