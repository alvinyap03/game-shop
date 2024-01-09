package com.example.tutorial3android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class playgamelistActivity extends AppCompatActivity {

    private RecyclerView recyclerView; // Declare recyclerView
    private GameListAdapter gameListAdapter; // Declare gameListAdapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playgamelist);

        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        // Retrieve user data
        UserData userData = getUserData(username);

        if (userData != null) {
            // Retrieve the list of games associated with the user
            List<game_data> userGames = userData.getGames();

            // Initialize the RecyclerView and its adapter
            recyclerView = findViewById(R.id.playgamelist);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Log before setting adapter data
            Log.d("playgamelistActivity", "Before setting adapter data");

            if (!userGames.isEmpty()) {
                List<String> gameNames = getGameNames(userGames);
                gameListAdapter = new GameListAdapter(gameNames);
                recyclerView.setAdapter(gameListAdapter);

                // Set click listener for item clicks
                gameListAdapter.setOnItemClickListener(new GameListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        // Log inside onItemClick method
                        Log.d("playgamelistActivity", "Item clicked at position: " + position);
                        // Handle item click if needed
                    }
                });
            } else {
                // Handle the case when userGames is empty (no games for the user)
                Log.d("playgamelistActivity", "No games found for the user.");
                // You might want to show a message to the user indicating that there are no games.
            }
        }

        Button back = findViewById(R.id.button27);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(playgamelistActivity.this, profileActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<String> getGameNames(List<game_data> gameDataList) {
        // Extract game names from game_data list
        List<String> gameNames = new ArrayList<>();
        for (game_data gameData : gameDataList) {
            gameNames.add(gameData.getName());
        }
        return gameNames;
    }

    private UserData getUserData(String username) {
        user_dbmanager dbManager = new user_dbmanager(playgamelistActivity.this);
        UserData userData = dbManager.getUserByUsername(username);
        dbManager.close();
        return userData;
    }
}