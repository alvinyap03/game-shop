package com.example.tutorial3android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddGameActivity extends AppCompatActivity {

    private EditText gameNameEditText, priceEditText, descriptionEditText;
    private Button uploadButton, backButton;
    private GameHelper gameHelper;
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgame);

        gameNameEditText = findViewById(R.id.editTextText);
        priceEditText = findViewById(R.id.editTextText2);
        descriptionEditText = findViewById(R.id.editTextText3);
        uploadButton = findViewById(R.id.button4);
        backButton = findViewById(R.id.button5);

        gameHelper = new GameHelper(gameNameEditText, priceEditText, descriptionEditText);
        gameManager = new GameManager(this);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertGameAndNavigate();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGameActivity.this, adminpage.class);
                startActivity(intent);
            }
        });
    }

    private void insertGameAndNavigate() {
        // Retrieve values from EditText widgets
        String gameName = gameNameEditText.getText().toString();
        double price = Double.parseDouble(priceEditText.getText().toString());
        String description = descriptionEditText.getText().toString();

        // Retrieve selected genres (you already have this logic)
        List<String> selectedGenres = getSelectedGenres();

        // Pass the retrieved values to getGameData method
        game_data gameData = gameHelper.getGameData(gameName, price, description, selectedGenres);

        // Don't insert into the database yet, just navigate to the next activity
        Intent intent = new Intent(AddGameActivity.this, pick_genreActivity.class);
        intent.putExtra("gameData", gameData); // Pass the gameData as an extra
        intent.putExtra("gameName", gameName); // Pass the game name as an extra
        intent.putExtra("price", price); // Pass the game price as an extra
        intent.putExtra("description", description); // Pass the game description as an extra
        startActivity(intent);
    }

    private List<String> getSelectedGenres() {
        List<String> selectedGenres = new ArrayList<>();
        // Add your logic to retrieve selected genres from your UI components
        // For example, if you have a list of checkboxes for genres, iterate over them and add selected genres to the list
        // Replace this with your actual logic
        return selectedGenres;
    }
}