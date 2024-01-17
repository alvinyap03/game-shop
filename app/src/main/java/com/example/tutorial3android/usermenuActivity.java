package com.example.tutorial3android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class usermenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermenu);

        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        Button adminpage = findViewById(R.id.btn_addgame);
        if (username.contains("@admin")) {
            adminpage.setVisibility(View.VISIBLE);
        } else {
            adminpage.setVisibility(View.GONE);
        }

        Button usermenu_search = findViewById(R.id.usermenu_search);
        usermenu_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usermenuActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        Button searchuser = findViewById(R.id.usermenu_search2);
        searchuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usermenuActivity.this, usergameActivity.class);
                startActivity(intent);
            }
        });

        Button notification = findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usermenuActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        Button profile = findViewById(R.id.user_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usermenuActivity.this, profileActivity.class);
                startActivity(intent);
            }
        });

        adminpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usermenuActivity.this, adminpage.class);
                startActivity(intent);
            }
        });
    }
}