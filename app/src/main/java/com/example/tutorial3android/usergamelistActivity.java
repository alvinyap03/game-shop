package com.example.tutorial3android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.tutorial3android.manager.UserGameManager;

import java.util.List;

public class usergamelistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserGameAdapter userGameAdapter;
    private TextView whatuserTextView;
    private UserGameManager userGameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usergamelist);

        // Initialize views
        recyclerView = findViewById(R.id.all_user2);
        whatuserTextView = findViewById(R.id.whatuser);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userGameAdapter = new UserGameAdapter();
        recyclerView.setAdapter(userGameAdapter);

        // Initialize UserGameManager
        userGameManager = new UserGameManager(this);

        // Get username from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("username")) {
            String username = intent.getStringExtra("username");
            whatuserTextView.setText(username);

            // Load and display games for the specified username
            loadUserGames(username);
        }

        // Back button click listener
        Button backButton = findViewById(R.id.button31);
        backButton.setOnClickListener(v -> finish());
    }

    private void loadUserGames(String username) {
        // Use UserGameManager to fetch games for the specified username
        List<String> userGames = userGameManager.getGamesForUser(username);

        // Update RecyclerView with the list of games
        userGameAdapter.setGames(userGames);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database when the activity is destroyed
        userGameManager.closeDatabase();
    }
}
