package com.example.tutorial3android;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class addgenreActivity extends AppCompatActivity {

    private EditText genreNameEditText;
    private Button backButton, viewButton, addButton;
    private Genre_dbManager genreDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgenre);

        genreNameEditText = (EditText) findViewById(R.id.genre_edit_text);
        backButton = findViewById(R.id.button12);
        viewButton = findViewById(R.id.button13);
        addButton = findViewById(R.id.button11);

        // Pass the context (which is 'this') to the GenreHelper constructor
        genreDbManager = new Genre_dbManager(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = genreNameEditText.getText().toString();
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

    private void insertGenre(String name) {
        String genreName = genreNameEditText.getText().toString();

        if (genreName.isEmpty()) {
            // Handle the case where the genre name is empty
            Toast.makeText(addgenreActivity.this, "Genre name cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (genreDbManager.isGenreExists(name)) {
            Toast.makeText(addgenreActivity.this, "This Genre already exists", Toast.LENGTH_SHORT).show();
        }else {
            try {
                genreDbManager.insert(name);
                Toast.makeText(addgenreActivity.this, "Add Genre successful", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                genreDbManager.close();
            }
        }


    }
}
