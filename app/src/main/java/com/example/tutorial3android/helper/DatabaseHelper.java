package com.example.tutorial3android.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "your_database_name.db";
    private static final int DATABASE_VERSION = 1;

    // Table creation queries
    private static final String CREATE_USER_TABLE = "CREATE TABLE User (user_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, gmail TEXT, password TEXT);";
    private static final String CREATE_GAME_TABLE = "CREATE TABLE Game (game_id INTEGER PRIMARY KEY AUTOINCREMENT, game_name TEXT, price REAL, description TEXT);";
    private static final String CREATE_GENRE_TABLE = "CREATE TABLE Genre (genre_id INTEGER PRIMARY KEY AUTOINCREMENT, genre_name TEXT);";
    private static final String CREATE_GAME_GENRE_TABLE = "CREATE TABLE GameGenre (game_name TEXT, genre_name TEXT, PRIMARY KEY (game_name, genre_name), FOREIGN KEY (game_name) REFERENCES Game(game_name), FOREIGN KEY (genre_name) REFERENCES Genre(genre_name));";
    private static final String CREATE_USER_GAME_TABLE = "CREATE TABLE UserGame (username TEXT, game_name TEXT, PRIMARY KEY (username, game_name), FOREIGN KEY (username) REFERENCES User(username), FOREIGN KEY (game_name) REFERENCES Game(game_name));";
    private static final String CREATE_NOTIFICATION_TABLE = "CREATE TABLE Notification (notification_id INTEGER PRIMARY KEY AUTOINCREMENT, sender TEXT, message TEXT, receiver TEXT, time TEXT);";
    private static final String CREATE_INCOME_TABLE = "CREATE TABLE Income (income_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, total REAL, message TEXT, time TEXT);";
    private static final String CREATE_REPORT_TABLE = "CREATE TABLE Report (report_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, title TEXT, comment TEXT);";

    private static SQLiteDatabase database; // Single instance of SQLiteDatabase

    private static DatabaseHelper instance; // Single instance of DatabaseHelper

    // Make the constructor public
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
            database = instance.getWritableDatabase();
        }
        return instance;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_GAME_TABLE);
        db.execSQL(CREATE_GENRE_TABLE);
        db.execSQL(CREATE_GAME_GENRE_TABLE);
        db.execSQL(CREATE_USER_GAME_TABLE);
        db.execSQL(CREATE_NOTIFICATION_TABLE);
        db.execSQL(CREATE_INCOME_TABLE);
        db.execSQL(CREATE_REPORT_TABLE);
        // Add other table creation queries as needed
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        // Example:
        if (oldVersion < 2) {
            // Upgrade from version 1 to version 2
            db.execSQL("ALTER TABLE User ADD COLUMN new_column TEXT;");
        }
    }
}
