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

import java.util.List;

public class playgamelistActivity extends AppCompatActivity {

    private RecyclerView recyclerView; // Declare recyclerView
    private GameListAdapter gameListAdapter; // Declare gameListAdapter
    private List<String> userGameNames; // Store the game names associated with the user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playgamelist);

        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        // Retrieve the list of games associated with the user
        UserGameDAO userGameDAO = new UserGameDAO(playgamelistActivity.this);
        userGameNames = userGameDAO.getGamesForUser(username);
        userGameDAO.closeDatabase();

        // Initialize the RecyclerView and its adapter
        recyclerView = findViewById(R.id.playgamelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Log before setting adapter data
        Log.d("playgamelistActivity", "Before setting adapter data");

        if (!userGameNames.isEmpty()) {
            gameListAdapter = new GameListAdapter(userGameNames);
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
            // Handle the case when userGameNames is empty (no games for the user)
            Log.d("playgamelistActivity", "No games found for the user.");
            // You might want to show a message to the user indicating that there are no games.
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
}