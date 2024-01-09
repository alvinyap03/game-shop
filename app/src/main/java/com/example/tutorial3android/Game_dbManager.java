package com.example.tutorial3android;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Game_dbManager extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "GAMES";
    private static final String _ID = "_id";
    private static final String GAME_NAME = "name";
    private static final String GAME_PRICE = "price";
    private static final String GAME_DESCRIPTION = "description";
    private static final String GAME_GENRE = "genre";
    private static final String GAME_IMAGE = "image";

    // Database Information
    static final String DB_NAME = "FINANCIAL_GOAL.DB";
    // Initial database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            GAME_NAME + " TEXT NOT NULL, " +
            GAME_PRICE + " DOUBLE NOT NULL, " +
            GAME_DESCRIPTION + " TEXT NOT NULL, " +
            GAME_GENRE + " TEXT NOT NULL, " +
            GAME_IMAGE + " BLOB);";

    public Game_dbManager(Context context) {
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

    public void insert(String name, String description, double price, String genre, byte[] image) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(GAME_NAME, name);
        contentValue.put(GAME_DESCRIPTION, description);
        contentValue.put(GAME_PRICE, price);
        contentValue.put(GAME_GENRE, genre);
        contentValue.put(GAME_IMAGE, image);
        database.insert(TABLE_NAME, null, contentValue);
        database.close();
    }

    public Cursor fetch() {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[]{_ID, GAME_NAME, GAME_DESCRIPTION, GAME_PRICE, GAME_GENRE, GAME_IMAGE};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        database.close();
        return cursor;
    }

    public int update(Integer _id, String name, String description, double price, String genre, byte[] image) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GAME_NAME, name);
        contentValues.put(GAME_DESCRIPTION, description);
        contentValues.put(GAME_PRICE, price);
        contentValues.put(GAME_GENRE, genre);
        contentValues.put(GAME_IMAGE, image);
        int i = database.update(TABLE_NAME, contentValues, _ID + " = " + _id, null);
        database.close();
        return i;
    }

    public void delete(long _id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, _ID + "=" + _id, null);
        database.close();
    }

    public Cursor searchByName(String gameName) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[]{_ID, GAME_NAME, GAME_DESCRIPTION, GAME_PRICE, GAME_GENRE, GAME_IMAGE};

        // 使用查询方法执行搜索
        Cursor cursor = database.query(
                TABLE_NAME,
                columns,
                GAME_NAME + " LIKE ?",
                new String[]{"%" + gameName + "%"}, // 使用通配符 % 来模糊匹配名称
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        database.close();
        return cursor;
    }

    public void deleteGameByName(String gameName) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, GAME_NAME + "=?", new String[]{gameName});
        database.close();
    }

    @SuppressLint("Range")
    public byte[] getImageBytes(Cursor cursor) {
        return cursor.getBlob(cursor.getColumnIndex(GAME_IMAGE));
    }

}


