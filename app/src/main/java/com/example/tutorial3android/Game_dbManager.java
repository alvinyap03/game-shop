package com.example.tutorial3android;


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
            GAME_GENRE + " TEXT NOT NULL);";

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

    public void insert(String name, String description, double price, String genre) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(GAME_NAME, name);
        contentValue.put(GAME_DESCRIPTION, description);
        contentValue.put(GAME_PRICE, price);
        contentValue.put(GAME_GENRE, genre);
        database.insert(TABLE_NAME, null, contentValue);
        database.close();
    }

    public Cursor fetch() {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[]{_ID, GAME_NAME, GAME_DESCRIPTION, GAME_PRICE, GAME_GENRE};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        database.close();
        return cursor;
    }

    public int update(Integer _id, String name, String description, double price, String genre) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GAME_NAME, name);
        contentValues.put(GAME_DESCRIPTION, description);
        contentValues.put(GAME_PRICE, price);
        contentValues.put(GAME_GENRE, genre);
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
        String[] columns = new String[]{_ID, GAME_NAME, GAME_DESCRIPTION, GAME_PRICE, GAME_GENRE};

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

}
    /*public GameManager(Context context) {
        this.context = context;
        openDatabase();
        createGamesTable();
    }

    private void openDatabase() {
        database = context.openOrCreateDatabase("GameDatabase", Context.MODE_PRIVATE, null);
    }

    private void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    private void createGamesTable() {
        // Create the Games table if not exists
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME_GAMES + " (" +
                GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GAME_NAME + " TEXT, " +
                GAME_PRICE + " REAL, " +
                GAME_DESCRIPTION + " TEXT, " +
                GAME_GENRE + " TEXT);");
    }

    public void open() {
        openDatabase();
    }

    public void close() {
        closeDatabase();
    }
    public void deleteAllGames() {
        database.delete(TABLE_NAME_GAMES, null, null);
    }

    public long updateGame(game_data gameData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(GAME_NAME, gameData.getName());
        contentValues.put(GAME_PRICE, gameData.getPrice());
        contentValues.put(GAME_DESCRIPTION, gameData.getDescription());

        // Assuming getGenres() returns a list of genres
        List<String> genres = gameData.getGenres();
        String genreString = "";
        if (genres != null && !genres.isEmpty()) {
            genreString = TextUtils.join(",", genres);
        }
        contentValues.put(GAME_GENRE, genreString);

        String whereClause = GAME_ID + "=?";
        String[] whereArgs = {String.valueOf(gameData.get_id())};

        // Update the game information in the database
        return database.update(TABLE_NAME_GAMES, contentValues, whereClause, whereArgs);
    }

    public long insertGame(game_data gameData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(GAME_NAME, gameData.getName());
        contentValues.put(GAME_PRICE, gameData.getPrice());
        contentValues.put(GAME_DESCRIPTION, gameData.getDescription());

        // Assuming getGenres() returns a list of genres
        List<String> genres = gameData.getGenres();
        String genreString = "";
        if (genres != null && !genres.isEmpty()) {
            genreString = TextUtils.join(",", genres);
        }
        contentValues.put(GAME_GENRE, genreString);

        return database.insert(TABLE_NAME_GAMES, null, contentValues);
    }

    public List<String> getGameNamesByGenre(int genreId) {
        List<String> gameNames = new ArrayList<>();

        Cursor cursor = database.query(TABLE_NAME_GAMES, new String[]{GAME_NAME}, GAME_GENRE + " LIKE ?", new String[]{"%" + genreId + "%"}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(GAME_NAME));
                gameNames.add(name);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return gameNames;
    }

    public List<game_data> getAllGames() {
        List<game_data> games = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME_GAMES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(GAME_ID));
                String name = cursor.getString(cursor.getColumnIndex(GAME_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(GAME_PRICE));
                String description = cursor.getString(cursor.getColumnIndex(GAME_DESCRIPTION));
                String genreString = cursor.getString(cursor.getColumnIndex(GAME_GENRE));

                // Convert the genreString into a list of genres
                List<String> genres = Arrays.asList(TextUtils.split(genreString, ","));

                game_data gameData = new game_data(id, name, price, description, genres);
                games.add(gameData);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return games;
    }

    public List<String> getAllGameNames() {
        List<String> gameNames = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME_GAMES, new String[]{GAME_NAME}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(GAME_NAME));
                gameNames.add(name);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return gameNames;
    }

    public game_data getGameByName(String gameName) {
        if (gameName == null) {
            // Handle the case where gameName is null, e.g., show an error message
            showToast("Invalid game name");
            return null;
        }

        String selection = GAME_NAME + "=?";
        String[] selectionArgs = {gameName};
        Cursor cursor = database.query(TABLE_NAME_GAMES, null, selection, selectionArgs, null, null, null);

        game_data gameData = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(GAME_ID));
            String name = cursor.getString(cursor.getColumnIndex(GAME_NAME));
            double price = cursor.getDouble(cursor.getColumnIndex(GAME_PRICE));
            String description = cursor.getString(cursor.getColumnIndex(GAME_DESCRIPTION));
            String genreString = cursor.getString(cursor.getColumnIndex(GAME_GENRE));

            // Convert the genreString into a list of genres
            List<String> genres = Arrays.asList(TextUtils.split(genreString, ","));

            gameData = new game_data(id, name, price, description, genres);
        }

        cursor.close();
        return gameData;
    }

    public boolean deleteGameById(int gameId) {
        String whereClause = GAME_ID + "=?";
        String[] whereArgs = {String.valueOf(gameId)};

        // Delete the game and get the number of affected rows
        int rowsDeleted = database.delete(TABLE_NAME_GAMES, whereClause, whereArgs);

        // Check if any row was deleted
        return rowsDeleted > 0;
    }

    public boolean deleteGameByName(String gameName) {
        // Check if the gameName is null or empty
        if (TextUtils.isEmpty(gameName)) {
            // Handle the case where gameName is empty, e.g., show an error message
            showToast("Invalid game name");
            return false;
        }

        String whereClause = GAME_NAME + "=?";
        String[] whereArgs = {gameName};

        // Delete the game and get the number of affected rows
        int rowsDeleted = database.delete(TABLE_NAME_GAMES, whereClause, whereArgs);

        // Check if any row was deleted
        return rowsDeleted > 0;
    }

    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }*/

