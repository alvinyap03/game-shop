package com.example.tutorial3android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

public class buygameActivity extends AppCompatActivity {

    private static final String TAG = "buygame"; // Define TAG here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buygame);

        // Assuming you have TextViews and Button in your layout file with these IDs
        TextView gameTitleTextView = findViewById(R.id.textView9);
        TextView priceTextView = findViewById(R.id.textView75);
        TextView descriptionTextView = findViewById(R.id.textView70);
        Button mainMenuButton = findViewById(R.id.csgo_menu2);
        Button backButton = findViewById(R.id.csgo_fps2);
        Button buyButton = findViewById(R.id.buygame);

        // Retrieve selected game data from the intent
        game_data selectedGameData = (game_data) getIntent().getSerializableExtra("selectedGameData");

        if (selectedGameData != null) {
            Log.d(TAG, "onCreate: Selected Game Data - Name: " + selectedGameData.getName()
                    + ", Price: " + selectedGameData.getPrice()
                    + ", Description: " + selectedGameData.getDescription());

            // Display game information
            gameTitleTextView.setText(selectedGameData.getName());
            priceTextView.setText(String.valueOf(selectedGameData.getPrice()));
            descriptionTextView.setText(selectedGameData.getDescription());

            // Check if the user owns the game and update the button accordingly
            if (userOwnsGame(selectedGameData.getName())) {
                buyButton.setText("Play");
                // Add onClickListener for playing the game
                buyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Add logic to start playing the game
                        // For example, you can start a new activity or perform the necessary actions
                    }
                });
            } else {
                // User doesn't own the game, set "Buy" button behavior
                buyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Start MainActivity with the intent and pass the selected game's data
                        Intent mainIntent = new Intent(buygameActivity.this, MainActivity.class);
                        mainIntent.putExtra("selectedGameData", (Serializable) selectedGameData);
                        startActivity(mainIntent);

                        // You can choose whether to finish buygameActivity here or not
                        // finish();
                    }
                });
            }
        } else {
            Log.e(TAG, "onCreate: Selected Game Data is null");
        }

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(buygameActivity.this, usermenuActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(buygameActivity.this, searchgameActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to check if the user owns the game
    private boolean userOwnsGame(String gameName) {
        String username = getCurrentUsername(); // Implement this method to get the current username
        List<String> userGames = UserGameAssociationManager.getInstance().getGamesForUser(username);
        return userGames.contains(gameName);
    }

    // Implement this method to get the current username
    private String getCurrentUsername() {
        // You need to implement a way to get the current username (e.g., from shared preferences, database, etc.)
        // For now, let's assume it's stored in SharedPreferences
        return getSharedPreferences("user_info", MODE_PRIVATE).getString("username", "");
    }
}
