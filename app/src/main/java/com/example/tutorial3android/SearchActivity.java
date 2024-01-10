package com.example.tutorial3android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Genre_dbManager genreDbManager = new Genre_dbManager(this);
        genreDbManager.open();

        // Fetch genres from the database
        List<GenreData> genreList = genreDbManager.getAllGenresList();

        // Log the data to verify
        for (GenreData genre : genreList) {
            Log.d("SearchActivity", "Genre: " + genre.getGenres());
        }

        // Log the size and contents of the genreList
        Log.d("SearchActivity", "GenreList size: " + genreList.size());
        Log.d("SearchActivity", "GenreList contents: " + genreList);

        // Create a set of selected genre IDs (assuming GenreData has an identifier like genreId)
        Set<GenreData> selectedGenreIds = new HashSet<>();
        // Create an adapter and connect it to the ListView
        GenreDataAdapter adapter = new GenreDataAdapter(this, genreList, selectedGenreIds, new ArrayList<>());
        ListView listView = findViewById(R.id.listview5); // Replace with your actual ListView id
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GenreData selectedGenre = genreList.get(position);

                // Log the selected genre details
                Log.d("SearchActivity", "Selected Genre: " + selectedGenre);

                // Start genre_game_list_Activity and pass the selected genre data
                Intent intent = new Intent(SearchActivity.this, genre_game_list_Activity.class);
                intent.putExtra("selectedGenre", (Parcelable) selectedGenre);
                startActivity(intent);
            }
        });

        Button search_menu = findViewById(R.id.search_menu);
        search_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, usermenuActivity.class);
                startActivity(intent);
            }
        });

        Button search_game = findViewById(R.id.search_menu2);
        search_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, searchgameActivity.class);
                startActivity(intent);
            }
        });
    }
}
