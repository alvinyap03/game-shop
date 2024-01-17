package com.example.tutorial3android;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial3android.manager.UserManager;

public class usergameActivity extends AppCompatActivity {

    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usergame);

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.all_user2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter
        userAdapter = new UserAdapter(this, null, new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String username) {
                // Handle item click, e.g., start the usergamelistActivity with the selected username
                Intent intent = new Intent(usergameActivity.this, usergamelistActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(userAdapter);

        // Load and display users
        loadUsers();

        Button back = findViewById(R.id.button30);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usergameActivity.this, usermenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadUsers() {
        UserManager userManager = new UserManager(this);
        Cursor cursor = userManager.fetchAllUsers();
        userAdapter.swapCursor(cursor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userAdapter != null) {
            userAdapter.swapCursor(null); // Close the cursor when the activity is destroyed
        }
    }
}
