package com.example.tutorial3android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorial3android.UI.GenreUI;
import com.example.tutorial3android.data.Game;
import com.example.tutorial3android.data.GameGenre;
import com.example.tutorial3android.data.Genre;
import com.example.tutorial3android.manager.GameManager;
import com.example.tutorial3android.manager.GameGenreManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class pick_genreActivity extends AppCompatActivity {

    private static final String TAG = "pick_genreActivity";

    private ListView genreListView;
    private Button backButton, nextButton;
    private GenreUI genreUI;  // Added this line
    private PickGenreDataAdapter adapter;  // Changed from GenreDataAdapter
    private String selectedGameName;
    private Set<Genre> selectedGenres = new HashSet<>(); // to keep track of selected genres
    private String gameName;
    private double price;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_genre);

        genreUI = new GenreUI(this);  // Added this line

        genreListView = findViewById(R.id.listViewGenre119);
        backButton = findViewById(R.id.button7);
        nextButton = findViewById(R.id.button200);

        Intent intent = getIntent();
        gameName = intent.getStringExtra("gameName");
        price = intent.getDoubleExtra("price", 0.0);
        description = intent.getStringExtra("description");

        initializeViews();
        setupListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Handle cleanup as needed
    }

    private void initializeViews() {
        // Fetch genres using GenreUI
        List<Genre> genreList = genreUI.getAllGenresList();

        for (Genre genre : genreList) {
            if (genre != null) {
                Log.d(TAG, "Genre: " + genre.getGenreName());
            } else {
                Log.d(TAG, "Genre is null");
            }
        }

        adapter = new PickGenreDataAdapter(this, genreList, selectedGenres);
        genreListView.setAdapter(adapter);

        genreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleGenreSelection(view, position);
            }
        });
    }

    private void setupListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAddGame();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAdminPage();
            }
        });
    }

    private void handleGenreSelection(View view, int position) {
        Log.d(TAG, "Selected position: " + position);

        Genre selectedGenre = adapter.getItem(position);

        if (selectedGenres.contains(selectedGenre)) {
            selectedGenres.remove(selectedGenre);
            Log.d(TAG, "Deselected position: " + position);
        } else {
            selectedGenres.add(selectedGenre);
            Log.d(TAG, "Selected position: " + position);
        }
        adapter.updateData(selectedGenres);
    }

    private void navigateToAddGame() {
        Intent adminIntent = new Intent(pick_genreActivity.this, AddGameActivity.class);
        startActivity(adminIntent);    }

    private void navigateToAdminPage() {
        // Use the game information from the intent
        Intent intent = getIntent();
        String gameName = intent.getStringExtra("gameName");
        double price = intent.getDoubleExtra("price", 0.0);
        String description = intent.getStringExtra("description");

        // Save the game information to SQLite using GameManager
        Game game = new Game(gameName, price, description);
        long gameId = new GameManager(pick_genreActivity.this).insertGame(game);

        // Continue with gamegenre insertion if gameId is valid
        if (gameId != -1) {
            // Successfully inserted game, now insert genres if any are selected
            if (!selectedGenres.isEmpty()) {
                GameGenreManager gameGenreManager = new GameGenreManager(pick_genreActivity.this);

                for (Genre genre : selectedGenres) {
                    GameGenre gameGenre = new GameGenre(gameName, genre.getGenreName());

                    // Insert each selected genre
                    boolean isGenreInserted = gameGenreManager.insertGameGenre(gameGenre);

                    if (!isGenreInserted) {
                        Toast.makeText(pick_genreActivity.this, "Failed to save genre: " + genre.getGenreName(), Toast.LENGTH_SHORT).show();
                        return; // Exit if any genre insertion fails
                    }
                }
            }

            // Successfully inserted, navigate to adminpage
            Intent adminIntent = new Intent(pick_genreActivity.this, adminpage.class);
            adminIntent.putExtra("gameId", gameId);
            startActivity(adminIntent);
        } else {
            Toast.makeText(pick_genreActivity.this, "Failed to save game information", Toast.LENGTH_SHORT).show();
        }
    }
}
