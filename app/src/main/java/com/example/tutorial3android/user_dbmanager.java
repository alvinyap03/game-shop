package com.example.tutorial3android;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class user_dbmanager extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "UserList";

    // Table columns
    public static final String _ID = "_id";
    public static final String Gmail = "gmail";
    public static final String Username = "username";
    public static final String Password = "password";


    // Database Information
    static final String DB_NAME = "User.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Gmail + " TEXT NOT NULL, " +
            Username + " TEXT, " +
            Password + " TEXT);";

    public user_dbmanager(Context context) {
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

    public void insert(String gmail, String username, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(Gmail, gmail);
        contentValue.put(Username, username);
        contentValue.put(Password, password);
        database.insert(TABLE_NAME, null, contentValue);
        database.close();
    }

    public Cursor fetch() {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[]{_ID, Gmail, Username, Password};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        database.close();
        return cursor;
    }

    public int update(long _id, String Gmail, String username, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Gmail, Gmail);
        contentValues.put(Username, username);
        contentValues.put(Password, password);
        int i = database.update(TABLE_NAME, contentValues, _ID + " = " + _id, null);
        database.close();
        return i;
    }

    public void delete(long _id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, _ID + "=" + _id, null);
        database.close();
    }

    public UserData getUserById(int userId) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[]{_ID, Gmail, Username, Password};

        Cursor cursor = database.query(TABLE_NAME, columns, _ID + " = " + userId, null, null, null, null);

        UserData user = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") int _id = cursor.getInt(cursor.getColumnIndex(_ID));
                @SuppressLint("Range") String gmail = cursor.getString(cursor.getColumnIndex(Gmail));
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(Username));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(Password));

                user = new UserData(_id, gmail, username, password);
            }

            cursor.close();
        }
        database.close();
        return user;
    }

    public UserData getUserByUsername(String username) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[]{_ID, Gmail, Username, Password};

        Cursor cursor = database.query(
                TABLE_NAME,
                columns,
                Username + " = ?",
                new String[]{username},
                null,
                null,
                null
        );

        UserData user = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") int _id = cursor.getInt(cursor.getColumnIndex(_ID));
                @SuppressLint("Range") String gmail = cursor.getString(cursor.getColumnIndex(Gmail));
                @SuppressLint("Range") String fetchedUsername = cursor.getString(cursor.getColumnIndex(Username));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(Password));

                if (fetchedUsername.equals(username)) {
                    user = new UserData(_id, gmail, fetchedUsername, password);
                }
            }

            cursor.close();
        }
        database.close();
        return user;
    }

    public void updateUserData(UserData userData) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Gmail, userData.getGmail());
        contentValues.put(Username, userData.getUsername());
        contentValues.put(Password, userData.getPassword());

        database.update(
                TABLE_NAME,
                contentValues,
                Username + " = ?",
                new String[]{userData.getUsername()}
        );
    }

    public boolean isUserExists(String username) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[]{_ID, Username};

        Cursor cursor = database.query(
                TABLE_NAME,
                columns,
                Username + " = ?",
                new String[]{username},
                null,
                null,
                null
        );

        boolean exists = cursor != null && cursor.moveToFirst();
        if (cursor != null) {
            cursor.close();
        }
        database.close();
        return exists;
    }

    public boolean isGmailUserExists(String username) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[]{_ID, Gmail};

        Cursor cursor = database.query(
                TABLE_NAME,
                columns,
                Gmail + " = ?",
                new String[]{username},
                null,
                null,
                null
        );

        boolean exists = cursor != null && cursor.moveToFirst();
        if (cursor != null) {
            cursor.close();
        }
        database.close();
        return exists;
    }

}