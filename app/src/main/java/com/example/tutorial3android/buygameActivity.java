package com.example.tutorial3android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorial3android.data.Game;
import com.example.tutorial3android.manager.GameManager;
import com.example.tutorial3android.manager.UserGameManager;

import java.io.Serializable;
import java.util.List;

public class buygameActivity extends AppCompatActivity {

    private static final String TAG = "buygame";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buygame);

        // Assuming you have TextViews and Button in your layout file with these IDs
        TextView gameTitleTextView = findViewById(R.id.textView9);
        TextView priceTextView = findViewById(R.id.textView75);
        TextView descriptionTextView = findViewById(R.id.textView70);
        // Button mainMenuButton = findViewById(R.id.csgo_menu2);
        Button backButton = findViewById(R.id.csgo_fps2);
        Button buyButton = findViewById(R.id.buygame);

        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        // Retrieve the game name from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedGameName")) {
            String selectedGameName = intent.getStringExtra("selectedGameName");

            // Use the GameManager to get the game information
            GameManager gameManager = new GameManager(this);
            Game selectedGame = gameManager.getGameByName(selectedGameName);

            if (selectedGame != null) {
                // Display game information
                gameTitleTextView.setText(selectedGame.getGameName());
                priceTextView.setText(String.valueOf(selectedGame.getPrice()));
                descriptionTextView.setText(selectedGame.getDescription());

                // Check if the user already has the game
                UserGameManager userGameManager = new UserGameManager(buygameActivity.this);
                List<String> userGames = userGameManager.getGamesForUser(username);

                if (userGames.contains(selectedGameName)) {
                    // User has the game, change button to "Play"
                    buyButton.setText("Play");

                    // Handle "Play" action, e.g., start the game
                    // Add your custom logic here
                }
            } else {
                Log.e(TAG, "onCreate: Selected Game is null");
            }
        } else {
            Log.e(TAG, "onCreate: Intent or selectedGameName is null");
        }

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String displayedGameName = gameTitleTextView.getText().toString();
                double displayedGamePrice = Double.parseDouble(priceTextView.getText().toString());

                // User doesn't have the game, handle "Buy" action
                Intent mainIntent = new Intent(buygameActivity.this, MainActivity.class);
                mainIntent.putExtra("selectedGameName", displayedGameName);
                mainIntent.putExtra("selectedGamePrice", displayedGamePrice);
                startActivity(mainIntent);
            }
        });

        /* mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(buygameActivity.this, usermenuActivity.class);
                startActivity(intent);
            }
        });*/

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(buygameActivity.this, searchgameActivity.class);
                startActivity(intent);
            }
        });
    }
}

