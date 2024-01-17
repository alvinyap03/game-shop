package com.example.tutorial3android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorial3android.manager.GameGenreManager;
import com.example.tutorial3android.manager.GameManager;
import com.example.tutorial3android.data.Game;

public class DeletegameActivity extends AppCompatActivity {

    private EditText gameNameEditText, gamepriceEditText, gamedescriptionEditText;
    private GameManager gameManager;
    private GameGenreManager gameGenreManager;
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
        gameManager = new GameManager(this);
        gameGenreManager = new GameGenreManager(this);

        Intent intent = getIntent();
        String selectedGameName = intent.getStringExtra("selectedGameName");

        // Populate the EditText with the selected game data
        Game selectedGameData = gameManager.getGameByName(selectedGameName);
        if (selectedGameData != null) {
            gameNameEditText.setText(selectedGameData.getGameName());
            gamepriceEditText.setText(String.valueOf(selectedGameData.getPrice()));
            gamedescriptionEditText.setText(selectedGameData.getDescription());
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Delete the game data from SQLite and related GameGenre data
                boolean success = gameManager.deleteGame(selectedGameName);

                if (success) {
                    gameGenreManager.deleteGenresByGameName(selectedGameName);
                    // If deletion is successful, go back to game_editlist_Activity
                    Intent intent = new Intent(DeletegameActivity.this, game_editlist_Activity.class);
                    startActivity(intent);
                    finish(); // Finish the current activity to prevent going back to it from the next activity
                } else {
                    // show a message that fail to delete
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the updated values from the EditText fields
                String updatedGameName = gameNameEditText.getText().toString();
                String updatedGamePriceText = gamepriceEditText.getText().toString();
                String updatedGameDescription = gamedescriptionEditText.getText().toString();

                // Retrieve the old game name
                Intent intent = getIntent();
                String selectedGameName = intent.getStringExtra("selectedGameName");

                // Check for empty fields and handle parsing errors
                if (TextUtils.isEmpty(updatedGameName) || TextUtils.isEmpty(updatedGamePriceText)) {
                    Toast.makeText(DeletegameActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double updatedGamePrice = Double.parseDouble(updatedGamePriceText);

                    // Start an activity for editing the game, passing both old and updated data
                    Intent editIntent = new Intent(DeletegameActivity.this, pick_gerne2Activity.class);
                    editIntent.putExtra("selectedGameName", selectedGameName);
                    editIntent.putExtra("updatedGameName", updatedGameName);
                    editIntent.putExtra("updatedGamePrice", updatedGamePrice);
                    editIntent.putExtra("updatedGameDescription", updatedGameDescription);
                    startActivity(editIntent);
                } catch (NumberFormatException e) {
                    // Handle the case where the user entered an invalid double
                    Toast.makeText(DeletegameActivity.this, "Invalid price format", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGameEditListActivity();
            }
        });
    }

    private void navigateToGameEditListActivity() {
        Intent intent = new Intent(DeletegameActivity.this, game_editlist_Activity.class);
        startActivity(intent);
        finish();
    }
}
