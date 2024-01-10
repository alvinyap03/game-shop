package com.example.tutorial3android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class genre_game_list_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GameListAdapter gameListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_game_list);

        TextView genreNameTextView = findViewById(R.id.textView16);
        recyclerView = findViewById(R.id.notification1);

        // Receive the selected genre data from the intent
        Intent intent = getIntent();
        if (intent.hasExtra("selectedGenre")) {
            GenreData selectedGenre = (GenreData) intent.getParcelableExtra("selectedGenre");

            // Log the size and contents of the received genre list
            if (selectedGenre != null && selectedGenre.getGenres() != null) {
                Log.d("GenreList", "GenreList size: " + selectedGenre.getGenres().size());
                Log.d("GenreList", "Genres: " + selectedGenre.getGenres());
            } else {
                Log.d("GenreList", "Received genre is null or empty");
            }

            // Display the genre name
            if (selectedGenre != null && selectedGenre.getGenres() != null && !selectedGenre.getGenres().isEmpty()) {
                List<String> genres = selectedGenre.getGenres();

                // Assuming the genreNameTextView is a single TextView to display the genre name
                String genreName = genres.get(0); // Assuming the genre list contains at least one genre
                genreNameTextView.setText(genreName);

                // Fetch games for the selected genre using GameManager
                Game_dbManager gameDbManager = new Game_dbManager(this);


                // Fetch games based on the selected genre ID
                List<String> gameNamesForGenre = gameDbManager.getImageBytes(selectedGenre.getId());

                // Log the size and contents of the gameNamesForGenre
                Log.d("GenreList", "GameNamesForGenre size: " + gameNamesForGenre.size());
                Log.d("GenreList", "GameNamesForGenre contents: " + gameNamesForGenre);

                // Close the database connection
                gameDbManager.close();

                // Initialize and set up the RecyclerView
                setupRecyclerView(gameNamesForGenre);
            } else {
                // Handle the case where selectedGenre or its genres are null
            }
        }

        Button backButton = findViewById(R.id.button22);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(genre_game_list_Activity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerView(List<String> gameNames) {
        // Initialize the RecyclerView and set up the adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gameListAdapter = new GameListAdapter(gameNames);

        // Set the item click listener
        gameListAdapter.setOnItemClickListener(new GameListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click, for example, start DeletegameActivity
                String selectedGameName = gameNames.get(position);
                if (selectedGameName != null) {
                    // Implement the logic to start DeletegameActivity or perform any action
                    startDeleteGameActivity(selectedGameName);
                } else {
                    // Handle the case where the selectedGameName is null
                    Log.e("GenreDebug", "Invalid game name at position " + position);
                }
            }
        });

        recyclerView.setAdapter(gameListAdapter);
    }

    // Method to start DeletegameActivity
    private void startDeleteGameActivity(String selectedGameName) {
        // Implement the logic to start DeletegameActivity
        // ...
    }
}
