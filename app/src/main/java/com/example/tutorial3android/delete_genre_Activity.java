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

import java.util.ArrayList;
import java.util.List;

public class delete_genre_Activity extends AppCompatActivity {

    private Button backButton, adminPageButton;
    private ListView listView;
    private Genre_dbManager genreDbManager;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_genre);

        backButton = findViewById(R.id.button10);
        adminPageButton = findViewById(R.id.button14);
        listView = findViewById(R.id.listview31);

        genreDbManager = new Genre_dbManager(this);

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
        Cursor cursor = genreDbManager.fetch();

        List<String> genreNames = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(GENRE_NAME));
                genreNames.add(name);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        genreDbManager.close();

        // Create and set the adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, genreNames);
        listView.setAdapter(adapter);
    }

    private void deleteGenre(String genreName) {

        // Implement the deletion logic here using genreManager
        boolean isDeleted = genreDbManager.delete(genreName);

        if (isDeleted) {
            // If deletion is successful, show a message
            genreHelper.showToast("Genre deleted successfully!");
        } else {
            // If deletion fails, show an error message
            genreHelper.showToast("Failed to delete genre.");
        }

        genreDbManager.close();

        // Refresh the ListView to reflect changes
        loadGenres();
    }
}