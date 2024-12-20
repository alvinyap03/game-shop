package com.example.tutorial3android.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.tutorial3android.data.User;
import com.example.tutorial3android.helper.DatabaseHelper;

public class UserManager implements BaseColumns {
    private DatabaseHelper dbHelper;

    // Define the table name and column names as constants
    private static final String TABLE_NAME = "User";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_GMAIL = "gmail";
    private static final String COLUMN_PASSWORD = "password";

    public UserManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_GMAIL, user.getGmail());
        values.put(COLUMN_PASSWORD, user.getPassword());

        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    public boolean isUserExists(String username) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String[] columns = new String[]{COLUMN_USER_ID, COLUMN_USERNAME};

        Cursor cursor = database.query(
                TABLE_NAME,
                columns,
                COLUMN_USERNAME + " = ?",
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

    public boolean isGmailUserExists(String gmail) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String[] columns = new String[]{COLUMN_USER_ID, COLUMN_GMAIL};  // Change _ID to COLUMN_USER_ID

        Cursor cursor = database.query(
                TABLE_NAME,
                columns,
                COLUMN_GMAIL + " = ?",
                new String[]{gmail},
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

    public boolean isUserExists(String username, String password) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String[] columns = new String[]{COLUMN_USER_ID, COLUMN_USERNAME, COLUMN_PASSWORD};

        Cursor cursor = database.query(
                TABLE_NAME,
                columns,
                COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{username, password},
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

    public void close() {
        dbHelper.close();
    }

    public Cursor fetchAllUsers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                COLUMN_USER_ID,
                COLUMN_USERNAME
                // Add other columns if needed
        };

        return db.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }

    public int updateUserPassword(String username, String newPassword) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);

        // Define the WHERE clause to identify the specific user by username
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        // Update the user's password
        int numRowsUpdated = db.update(TABLE_NAME, values, selection, selectionArgs);

        db.close();
        return numRowsUpdated;
    }

    public User getUserByGmail(String gmail) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USERNAME,
                COLUMN_GMAIL,
                COLUMN_PASSWORD
        };

        Cursor cursor = database.query(
                TABLE_NAME,
                columns,
                COLUMN_GMAIL + " = ?",
                new String[]{gmail},
                null,
                null,
                null
        );

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_GMAIL)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
            );
            user.setUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
        }

        if (cursor != null) {
            cursor.close();
        }
        database.close();

        return user;
    }

    // Add methods specific to User entity
    // ...
}
