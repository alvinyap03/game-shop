package com.example.tutorial3android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserGameDAO {

    private UserGameHelper dbHelper;
    private SQLiteDatabase db;

    public UserGameDAO(Context context) {
        dbHelper = new UserGameHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void addUserGameAssociation(String username, String gameName) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("game_name", gameName);

        db.insert("user_game_associations", null, values);
    }

    public List<String> getGamesForUser(String username) {
        List<String> userGames = new ArrayList<>();

        String[] projection = {"game_name"};
        String selection = "username = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                "user_game_associations",
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
        return userGames;
    }

    public void closeDatabase() {
        db.close();
        dbHelper.close();
    }
}
