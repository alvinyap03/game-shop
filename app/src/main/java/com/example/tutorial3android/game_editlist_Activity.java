package com.example.tutorial3android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial3android.manager.GameManager;

import java.util.ArrayList;
import java.util.List;

public class game_editlist_Activity extends AppCompatActivity {

    private static final String TAG = "GameEditListActivity";

    private RecyclerView recyclerView;
    private GameManager gameManager;
    private List<String> originalGameNames;
    private GameListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_editlist);

        Log.d(TAG, "onCreate: Activity created");

        recyclerView = findViewById(R.id.playgamelist);
        gameManager = new GameManager(this);

        originalGameNames = gameManager.getAllGameNames();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query text changes
                // Perform search operation based on newText
                performSearch(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void performSearch(String query) {
        Log.d(TAG, "performSearch: 正在搜索 " + query);

        List<String> filteredList = new ArrayList<>();

        // 如果查询为空，则显示原始列表
        if (query.isEmpty()) {
            filteredList.addAll(originalGameNames);
        } else {
            // 根据查询过滤原始列表
            for (String gameName : originalGameNames) {
                if (gameName.toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(gameName);
                }
            }
        }

        // 使用过滤后的列表更新适配器
        adapter.updateList(filteredList);
    }

    private void startDeleteGameActivity(String selectedGameName) {
        Intent intent = new Intent(game_editlist_Activity.this, DeletegameActivity.class);
        intent.putExtra("selectedGameName", selectedGameName);
        startActivity(intent);
    }
}
