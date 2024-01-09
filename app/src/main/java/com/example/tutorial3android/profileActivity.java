package com.example.tutorial3android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class profileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        TextView usernameTextView = findViewById(R.id.username);
        usernameTextView.setText(username);

        Button logout = findViewById(R.id.button25);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the stored username from SharedPreferences on logout
                SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("username");
                editor.apply();

                // Navigate back to the login activity
                Intent intent = new Intent(profileActivity.this, loginActivity.class);
                startActivity(intent);
                // Finish the current activity to prevent going back to it using the back button
                finish();
            }
        });

        Button game = findViewById(R.id.button24);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profileActivity.this, playgamelistActivity.class);
                startActivity(intent);
            }
        });

        Button user_menu = findViewById(R.id.button26);
        user_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profileActivity.this, usermenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
