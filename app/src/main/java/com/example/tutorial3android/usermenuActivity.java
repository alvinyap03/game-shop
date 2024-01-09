package com.example.tutorial3android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class usermenuActivity extends AppCompatActivity {

    private ImageButton user_game_btn, user_shop_btn, user_friend_btn, user_profile_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermenu);

        ImageButton user_game = findViewById(R.id.user_game_btn);
        ImageButton user_shop = findViewById(R.id.user_shop_btn);
        ImageButton user_friend = findViewById(R.id.user_friend_btn);
        ImageButton user_profile = findViewById(R.id.user_profile_btn);

        user_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        user_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        user_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        /*SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        Button adminpage = findViewById(R.id.btn_addgame);
        if (username.equals("admin")) {
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
        });*/
    }
}