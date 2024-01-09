package com.example.tutorial3android;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class addgenreActivity extends AppCompatActivity {

    private EditText genreNameEditText;
    private Button backButton, viewButton, addButton;
    private GenreHelper genreHelper;
    private Genre_dbManager genreDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgenre);

        genreNameEditText = findViewById(R.id.editTextText11);
        backButton = findViewById(R.id.button12);
        viewButton = findViewById(R.id.button13);
        addButton = findViewById(R.id.button11);

        // Pass the context (which is 'this') to the GenreHelper constructor
        genreHelper = new GenreHelper(this);
        genreDbManager = new Genre_dbManager(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertGenre();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addgenreActivity.this, adminpage.class);
                startActivity(intent);
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addgenreActivity.this, delete_genre_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void insertGenre() {
        String genreName = genreNameEditText.getText().toString();

        if (genreName.isEmpty()) {
            // Handle the case where the genre name is empty
            genreHelper.showToast("Genre name cannot be empty");
            return;
        }

        try {
            genreDbManager.open();

            // Check if the genre name already exists in the database
            if (genreDbManager.isGenreExists(genreName)) {
                genreHelper.showToast("Genre already exists");
            } else {
                // Pass appropriate arguments to the GenreData constructor
                GenreData genreData = new GenreData(null, genreName, null);  // Assuming genres can be null

                // Check if the genreData is not null before attempting to insert
                if (genreData != null) {
                    long result = genreDbManager.insertGenre(genreData);

                    if (result != -1) {
                        genreHelper.showToast("Genre added successfully!");
                        genreHelper.clearFields(); // Now this should work if clearFields is implemented in GenreHelper
                    } else {
                        genreHelper.showToast("Failed to add genre.");
                    }
                } else {
                    // Handle the case where genreData is null
                    genreHelper.showToast("GenreData is null");
                }
            }
        } catch (SQLiteException e) {
            e.printStackTrace(); // Handle the exception as needed
        } finally {
            genreDbManager.close();
        }
    }

}
