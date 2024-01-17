package com.example.tutorial3android;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorial3android.data.Game;
import com.example.tutorial3android.data.GameGenre;
import com.example.tutorial3android.data.Genre;
import com.example.tutorial3android.helper.DatabaseHelper;
import com.example.tutorial3android.manager.GameManager;
import com.example.tutorial3android.manager.GameGenreManager;
import com.example.tutorial3android.manager.GenreManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class pick_gerne2Activity extends AppCompatActivity {

    private static final String TAG = "pick_genre2Activity";

    private ListView genreListView;
    private Button backButton, nextButton;
    private GenreManager genreManager;
    private GameGenreManager gameGenreManager;
    private GameManager gameManager;
    private DatabaseHelper dbHelper;
    private List<Genre> genreList;
    private PickGerne2DataAdapter adapter;
    private Set<Genre> selectedGenres = new HashSet<>();
    private String selectedGameName;
    private String updatedGameName;
    private double updatedGamePrice;
    private String updatedGameDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_gerne2);

        dbHelper = new DatabaseHelper(this);

        genreListView = findViewById(R.id.listViewGenre119);
        backButton = findViewById(R.id.button19);
        nextButton = findViewById(R.id.editbtn20);
        nextButton.setVisibility(View.INVISIBLE);

        gameManager = new GameManager(this);
        gameGenreManager = new GameGenreManager(this);
        genreManager = new GenreManager(this);
        genreManager.open();

        Intent intent = getIntent();
        selectedGameName = intent.getStringExtra("selectedGameName");
        updatedGameName = intent.getStringExtra("updatedGameName");
        updatedGamePrice = intent.getDoubleExtra("updatedGamePrice", 0.0);
        updatedGameDescription = intent.getStringExtra("updatedGameDescription");

        initializeViews();
        setupListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (genreManager != null) {
            genreManager.close();
        }
    }

    private void initializeViews() {
        genreList = genreManager.getAllGenresList();

        List<String> selectedGenresForGame = new GameGenreManager(this).getGenreByGameName(selectedGameName);

        for (String genreName : selectedGenresForGame) {
            Log.d(TAG, "Selected genre for the game: " + genreName);
        }

        for (Genre genre : genreList) {
            if (genre != null) {
                Log.d(TAG, "Genre: " + genre.getGenreName());

                if (selectedGenresForGame.contains(genre.getGenreName())) {
                    genre.setSelected(true);
                    selectedGenres.add(genre); // Preselect the genre
                } else {
                    genre.setSelected(false);
                }
            } else {
                Log.d(TAG, "Genre is null");
            }
        }

        adapter = new PickGerne2DataAdapter(this, genreList, selectedGenres);
        genreListView.setAdapter(adapter);
    }

    private void setupListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAddGenreDeletegame();
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
                if (!selectedGenres.isEmpty()) {
                    SQLiteDatabase db = null;
                    try {
                        db = dbHelper.getWritableDatabase();
                        // Begin the transaction
                        db.beginTransaction();

                        // Update the game information
                        long rowsUpdated = gameManager.updateGame(selectedGameName, new Game(updatedGameName, updatedGamePrice, updatedGameDescription));

                        if (rowsUpdated != -1) {
                            try {
                                // If the game update is successful, delete associated genres
                                gameGenreManager.deleteGenresByGameName(selectedGameName);

                                // Insert new genre associations
                                for (Genre selectedGenre : selectedGenres) {
                                    gameGenreManager.insertGameGenre(new GameGenre(updatedGameName, selectedGenre.getGenreName()));
                                }

                                // Set the transaction as successful
                                db.setTransactionSuccessful();

                                // Navigate to the admin page on successful transaction
                                Intent adminIntent = new Intent(pick_gerne2Activity.this, game_editlist_Activity.class);
                                startActivity(adminIntent);
                                finish(); // Finish the current activity to prevent going back to it from the next activity
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                // End the transaction
                                db.endTransaction();
                            }
                        } else {
                            Toast.makeText(pick_gerne2Activity.this, "Failed to update game information", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        // Close the database
                        if (db != null && db.isOpen()) {
                            db.close();
                        }
                    }
                } else {
                    Toast.makeText(pick_gerne2Activity.this, "Please select at least one genre", Toast.LENGTH_SHORT).show();
                }            }
        });

    }

    private void navigateToAddGenreDeletegame() {
        Intent intent = new Intent(pick_gerne2Activity.this, DeletegameActivity.class);
        intent.putExtra("selectedGameName", selectedGameName);
        startActivity(intent);
    }

    private void handleGenreSelection(View view, int position) {
        Genre selectedGenre = genreList.get(position);
        Log.d(TAG, "Selected genre: " + selectedGenre.getGenreName());

        boolean isRelatedToSelectedGame = new GameGenreManager(this).getGenreByGameName(selectedGameName)
                .contains(selectedGenre.getGenreName());

        if (selectedGenre.isSelected()) {
            selectedGenres.remove(selectedGenre);
            selectedGenre.setSelected(false);
            view.setBackgroundColor(getResources().getColor(isRelatedToSelectedGame ? R.color.selected_color : R.color.white));
            Log.d(TAG, "Deselected genre: " + selectedGenre.getGenreName());
        } else {
            selectedGenres.add(selectedGenre);
            selectedGenre.setSelected(true);
            view.setBackgroundColor(getResources().getColor(R.color.selected_color));
            Log.d(TAG, "Selected genre: " + selectedGenre.getGenreName());
        }

        adapter.updateData(selectedGenres);
    }

    private void navigateToAdminPage() {

    }

}
