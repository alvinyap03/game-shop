package com.example.tutorial3android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class game_editlist_Activity extends AppCompatActivity {

    private static final String TAG = "GameEditListActivity";

    private RecyclerView recyclerView;
    private Game_dbManager gameDbManager;
    private List<String> originalGameNames;
    private GameListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_editlist);

        Log.d(TAG, "onCreate: Activity created");

        recyclerView = findViewById(R.id.recyclerView);
        gameDbManager = new Game_dbManager(this);

        originalGameNames = gameDbManager.getAllGameNames();

        adapter = new GameListAdapter(originalGameNames);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new GameListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String selectedGameName = originalGameNames.get(position);
                startDeleteGameActivity(selectedGameName);
            }
        });

        EditText editTextSearch = findViewById(R.id.editTextSearch);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: Text changed to " + charSequence.toString());
                filterList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Button for clearing all data (without affecting RecyclerView)
        Button clearAllDataButton = findViewById(R.id.clearAllDataButton);
        clearAllDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete all games from the database
                gameDbManager.deleteAllGames();

                // Clear the list in the adapter without updating the RecyclerView
                adapter.clearList();
            }
        });

        // Button for navigating back
        Button backButton = findViewById(R.id.button15);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Back button clicked");
                Intent intent = new Intent(game_editlist_Activity.this, adminpage.class);
                startActivity(intent);
            }
        });
    }

    private void filterList(String query) {
        Log.d(TAG, "filterList: Filtering with query: " + query);
        List<String> filteredList = new ArrayList<>();

        for (String gameName : originalGameNames) {
            if (gameName != null && (query == null || gameName.toLowerCase().contains(query.toLowerCase()))) {
                filteredList.add(gameName);
            }
        }

        Log.d(TAG, "filterList: Original list size: " + originalGameNames.size());
        Log.d(TAG, "filterList: Filtered list size: " + filteredList.size());

        for (String name : filteredList) {
            Log.d(TAG, "filterList: Filtered game name: " + name);
        }

        // Update the adapter with the filtered list
        adapter.updateList(filteredList);
    }


    private void startDeleteGameActivity(String selectedGameName) {
        game_data selectedGameData = gameDbManager.getGameByName(selectedGameName);

        Intent intent = new Intent(game_editlist_Activity.this, DeletegameActivity.class);
        intent.putExtra("selectedGameData", (Parcelable) selectedGameData);
        startActivity(intent);
    }
}
