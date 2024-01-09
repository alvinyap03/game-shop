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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class pick_gerne2Activity extends AppCompatActivity {

    private static final String TAG = "pick_genre2Activity";

    private ListView genreListView;
    private Button backButton, nextButton;
    private Genre_dbManager genreDbManager;
    private List<GenreData> genreDataList;
    private GenreDataAdapter adapter;
    private Set<GenreData> selectedGenres = new HashSet<>();
    private String gameName;
    private double price;
    private String description;
    private long selectedGameId;
    private List<GenreData> preSelectedGenres = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_genre);

        genreListView = findViewById(R.id.listViewGenre119);
        backButton = findViewById(R.id.button7);
        nextButton = findViewById(R.id.button8);

        genreDbManager = new Genre_dbManager(this);
        genreDbManager.open();

        Intent intent = getIntent();
        gameName = intent.getStringExtra("gameName");
        price = intent.getDoubleExtra("price", 0.0);
        description = intent.getStringExtra("description");
        selectedGameId = intent.getLongExtra("gameId", -1);

        initializeViews();
        setupListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (genreDbManager != null) {
            genreDbManager.close();
        }
    }

    private void initializeViews() {
        genreDataList = genreDbManager.getAllGenresList();

        for (GenreData genre : genreDataList) {
            if (genre != null) {
                Log.d(TAG, "Genre: " + genre.getGenres());
            } else {
                Log.d(TAG, "Genre is null");
            }
        }

        // Fetch pre-selected genres from the database based on the selected game
        adapter = new GenreDataAdapter(this, genreDataList, selectedGenres, preSelectedGenres);
        genreListView.setAdapter(adapter);
    }

    private void setupListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAddGenre();
            }
        });

        genreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleGenreSelection(view, position);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAdminPage();
            }
        });
    }

    private void navigateToAddGenre() {
        Intent intent = new Intent(pick_gerne2Activity.this, DeletegameActivity.class);
        startActivity(intent);
    }

    private void handleGenreSelection(View view, int position) {
        Log.d(TAG, "Selected position: " + position);

        GenreData selectedGenre = genreDataList.get(position);

        if (selectedGenres.contains(selectedGenre)) {
            selectedGenres.remove(selectedGenre);
            Log.d(TAG, "Deselected position: " + position);
        } else {
            selectedGenres.add(selectedGenre);
            Log.d(TAG, "Selected position: " + position);
        }

        // Update the adapter to reflect the changes in selected genres
        adapter.updateData(genreDataList, selectedGenres);
    }

    private void navigateToAdminPage() {
        if (!selectedGenres.isEmpty()) {
            game_data gameData = new GameHelper(
                    findViewById(R.id.editTextText),
                    findViewById(R.id.editTextText2),
                    findViewById(R.id.editTextText3))
                    .getGameData(
                            gameName,
                            price,
                            description,
                            convertGenreSetToList(selectedGenres));

            long gameId = new Game_dbManager(pick_gerne2Activity.this).updateGame(gameData);

            if (gameId != -1) {
                Intent adminIntent = new Intent(pick_gerne2Activity.this, game_editlist_Activity.class);
                adminIntent.putExtra("gameId", gameId);
                startActivity(adminIntent);
            } else {
                Toast.makeText(pick_gerne2Activity.this, "Failed to update game information", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(pick_gerne2Activity.this, "Please select at least one genre", Toast.LENGTH_SHORT).show();
        }
    }

    private List<String> convertGenreSetToList(Set<GenreData> genreDataSet) {
        List<String> genreList = new ArrayList<>();
        for (GenreData genre : genreDataSet) {
            genreList.addAll(genre.getGenres());
        }
        return genreList;
    }
}
