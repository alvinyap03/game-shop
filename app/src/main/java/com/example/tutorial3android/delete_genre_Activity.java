package com.example.tutorial3android;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorial3android.helper.DatabaseHelper;
import com.example.tutorial3android.manager.GameGenreManager;
import com.example.tutorial3android.manager.GenreManager;

import java.util.ArrayList;
import java.util.List;

public class delete_genre_Activity extends AppCompatActivity {

    private Button backButton, adminPageButton;
    private ListView listView;
    private DatabaseHelper dbHelper;
    private GenreManager genreManager;
    private GameGenreManager gameGenreManager;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_genre);

        backButton = findViewById(R.id.button10);
        adminPageButton = findViewById(R.id.button14);
        listView = findViewById(R.id.listview31);

        dbHelper = new DatabaseHelper(this);
        genreManager = new GenreManager(this);
        gameGenreManager = new GameGenreManager(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adminPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(delete_genre_Activity.this, adminpage.class);
                startActivity(intent);
            }
        });

        // Load and display the genres in the ListView
        loadGenres();

        // Set item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected genre name
                String selectedGenre = (String) parent.getItemAtPosition(position);

                // Delete the selected genre from the database
                deleteGenre(selectedGenre);
            }
        });
    }

    private void loadGenres() {
        // Open the GenreManager's database connection
        genreManager.open();

        // Retrieve all genres from the database using GenreManager's getAllGenres method
        Cursor cursor = genreManager.getAllGenres();

        // Create a list to store genre names
        List<String> genreNames = new ArrayList<>();

        // Check if the cursor is not null and move to the first row
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Retrieve the genre name from the cursor using the column name
                String name = cursor.getString(cursor.getColumnIndex(GenreManager.COL_GENRE_NAME));

                // Add the genre name to the list
                genreNames.add(name);
            } while (cursor.moveToNext());  // Move to the next row if available
        }

        // Close the cursor if it is not null
        if (cursor != null) {
            cursor.close();
        }

        // Close the GenreManager's database connection
        genreManager.close();

        // Create an ArrayAdapter to adapt the genreNames list to the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, genreNames);

        // Set the adapter to the ListView
        listView.setAdapter(adapter);
    }

    private void deleteGenre(String genreName) {
        genreManager.open();

        // Implement the deletion logic here using genreManager
        boolean isGenreDeleted  = genreManager.deleteGenreByName(genreName);

        genreManager.close();

        // If the genre is successfully deleted, also delete associated games
        if (isGenreDeleted) {
            deleteGamesByGenre(genreName);
        }

        // Refresh the ListView to reflect changes
        loadGenres();
    }

    private void deleteGamesByGenre(String genreName) {
        // Use your GameGenreManager to delete games by genre
        gameGenreManager.deleteGamesByGenre(genreName);
    }
}