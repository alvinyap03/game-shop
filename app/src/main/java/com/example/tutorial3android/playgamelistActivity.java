package com.example.tutorial3android;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial3android.manager.UserGameManager;

import java.util.ArrayList;
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
        UserGameManager userGameDAO = new UserGameManager(playgamelistActivity.this);
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

    private void performSearch(String newText) {
        // 用于调试的记录搜索查询
        Log.d("playgamelistActivity", "使用查询进行搜索: " + newText);

        // 创建一个新列表来存储过滤后的游戏名称
        List<String> filteredList = new ArrayList<>();

        // 如果搜索查询为空，显示所有userGameNames
        if (newText.isEmpty()) {
            filteredList.addAll(userGameNames);
        } else {
            // 根据搜索查询过滤userGameNames
            for (String gameName : userGameNames) {
                if (gameName.toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(gameName);
                }
            }
        }

        // 使用过滤后的列表更新RecyclerView适配器
        gameListAdapter.updateList(filteredList);
    }


}