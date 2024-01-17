package com.example.tutorial3android.manager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tutorial3android.data.Game;
import com.example.tutorial3android.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private DatabaseHelper dbHelper;

    public GameManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertGame(Game game) {
        try (SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("game_name", game.getGameName());
            values.put("price", game.getPrice());
            values.put("description", game.getDescription());

            return database.insert("Game", null, values);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @SuppressLint("Range")
    public List<String> getAllGameNames() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<String> gameNames = new ArrayList<>();

        try (Cursor cursor = database.query(
                "Game",
                new String[]{"game_name"},
                null,
                null,
                null,
                null,
                null
        )) {
            while (cursor.moveToNext()) {
                gameNames.add(cursor.getString(cursor.getColumnIndex("game_name")));
            }
        } finally {
            database.close();
        }

        return gameNames;
    }

    public Game getGameByName(String gameName) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String[] projection = {"game_name", "price", "description"};
        String selection = "game_name = ?";
        String[] selectionArgs = {gameName};

        try (Cursor cursor = database.query(
                "Game",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            if (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("game_name"));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex("price"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));

                return new Game(name, price, description);
            }
        } finally {
            database.close();
        }

        return null;
    }

    public long updateGame(String gameName, Game updatedGameData) {
        try (SQLiteDatabase database = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("game_name", updatedGameData.getGameName());
            values.put("price", updatedGameData.getPrice());
            values.put("description", updatedGameData.getDescription());

            String selection = "game_name = ?";
            String[] selectionArgs = {gameName};

            return database.update("Game", values, selection, selectionArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean deleteGame(String gameName) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        try {
            database.beginTransaction();

            // Step 2: Delete the game entry
            String deleteGameQuery = "DELETE FROM Game WHERE game_name = ?";
            database.execSQL(deleteGameQuery, new String[]{gameName});

            // Set the transaction as successful before ending it
            database.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // End the transaction and close the database
            database.endTransaction();
            database.close();
        }
    }

    public boolean isGameExist(String gameName) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String[] projection = {"game_name"};
        String selection = "game_name = ?";
        String[] selectionArgs = {gameName};

        try (Cursor cursor = database.query(
                "Game",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            return cursor.getCount() > 0;
        } finally {
            database.close();
        }
    }

    // Add methods specific to Game entity
    // ...
}
