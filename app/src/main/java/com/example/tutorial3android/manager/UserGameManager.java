package com.example.tutorial3android.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tutorial3android.data.UserGame;
import com.example.tutorial3android.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class UserGameManager {

    private DatabaseHelper dbHelper;

    public UserGameManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertUserGame(UserGame userGame) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", userGame.getUsername());
        values.put("game_name", userGame.getGameName());

        long newRowId = -1;

        try {
            newRowId = db.insertWithOnConflict("UserGame", null, values, SQLiteDatabase.CONFLICT_IGNORE);
        } catch (SQLiteConstraintException e) {
            // Handle the unique constraint violation, e.g., log the error
            Log.e("UserGameManager", "Error inserting user game: " + e.getMessage());
        } finally {
            db.close();
        }

        return newRowId;
    }

    public List<String> getGamesForUser(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> userGames = new ArrayList<>();

        String[] projection = {"game_name"};
        String selection = "username = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                "UserGame",  // Use the correct table name here
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String gameName = cursor.getString(cursor.getColumnIndexOrThrow("game_name"));
            userGames.add(gameName);
        }

        cursor.close();
        db.close();
        return userGames;
    }


    public void closeDatabase() {
        // Implement database closing logic
        dbHelper.close();
    }
}
