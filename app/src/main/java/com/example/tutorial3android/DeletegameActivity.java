package com.example.tutorial3android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DeletegameActivity extends AppCompatActivity {

    private EditText gameNameEditText, gamepriceEditText, gamedescriptionEditText;
    private Game_dbManager gameDbManager;
    private Button backButton, deleteButton, editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletegame);


        editButton = findViewById(R.id.button16);
        backButton = findViewById(R.id.button17);
        deleteButton = findViewById(R.id.button18);

        gameNameEditText = findViewById(R.id.gamename);
        gamepriceEditText = findViewById(R.id.price);
        gamedescriptionEditText = findViewById(R.id.description);
        gameDbManager = new Game_dbManager(this);

        // Retrieve the selected game data from the intent
        game_data selectedGameData = getIntent().getParcelableExtra("selectedGameData");

        // Populate the EditText with the selected game data
        if (selectedGameData != null) {
            gameNameEditText.setText(selectedGameData.getName());
            gamepriceEditText.setText(String.valueOf(selectedGameData.getPrice()));
            gamedescriptionEditText.setText(selectedGameData.getDescription());
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the game name from the EditText field
                String gameName = gameNameEditText.getText().toString();

                // Delete the game data from SQLite
                boolean success = gameDbManager.deleteGameByName(gameName);

                if (success) {
                    // If deletion is successful, go back to game_editlist_Activity
                    Intent intent = new Intent(DeletegameActivity.this, game_editlist_Activity.class);
                    startActivity(intent);
                    finish(); // Finish the current activity to prevent going back to it from the next activity
                } else {
                    // Handle deletion failure, e.g., display a toast
                    gameDbManager.showToast("Failed to delete the game");
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the game name from the EditText field
                String gameName = gameNameEditText.getText().toString();

                // Check if gameName is empty or null
                if (TextUtils.isEmpty(gameName)) {
                    gameDbManager.showToast("Please enter a valid game name");
                    return;
                }

                // Retrieve the game data from SQLite based on the game name
                game_data selectedGameData = gameDbManager.getGameByName(gameName);

                if (selectedGameData != null) {
                    // If the game data is found, go to pick_gerne2Activity
                    Intent intent = new Intent(DeletegameActivity.this, pick_gerne2Activity.class);
                    intent.putExtra("selectedGameData", (Parcelable) selectedGameData);
                    startActivity(intent);
                } else {
                    // Handle the case where the game data is not found, e.g., display a toast
                    gameDbManager.showToast("Game not found");
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeletegameActivity.this, game_editlist_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
