package com.example.tutorial3android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorial3android.data.Income;
import com.example.tutorial3android.data.UserGame;
import com.example.tutorial3android.manager.IncomeManager;
import com.example.tutorial3android.manager.NotificationManager;
import com.example.tutorial3android.data.Notification;
import com.example.tutorial3android.manager.UserGameManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class tngActivity extends AppCompatActivity {

    private static final String TAG = "FpxActivity"; // Define a tag for your logs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tng);

        ArrayList<String> selectedGameNames = getIntent().getStringArrayListExtra("selectedGameNames");
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);

        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        // Display total in TextView
        TextView totalTextView = findViewById(R.id.total3);
        totalTextView.setText(String.format("%.2f", totalPrice));

        Button tng_payment_success = findViewById(R.id.tng_payment_success);
        tng_payment_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePayment(selectedGameNames, totalPrice, username);
            }
        });

        Button tng_payment = findViewById(R.id.tng_payment);
        tng_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tngActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handlePayment(List<String> selectedGameNames, double totalPrice, String username) {
        UserGameManager userGameManager = new UserGameManager(tngActivity.this);

        for (String gameName : selectedGameNames) {
            UserGame userGame = new UserGame(username, gameName);
            userGameManager.insertUserGame(userGame);
        }

        userGameManager.closeDatabase();

        // Concatenate the game names into a single string
        StringBuilder boughtGamesMessage = new StringBuilder("Bought ");
        for (int i = 0; i < selectedGameNames.size(); i++) {
            boughtGamesMessage.append(selectedGameNames.get(i));
            if (i < selectedGameNames.size() - 1) {
                boughtGamesMessage.append(", ");
            }
        }

        // Create NotificationData
        String receiver = username;  // Concatenate the username
        Notification notificationData = new Notification(
                "system",
                boughtGamesMessage.toString(),
                receiver,
                String.valueOf(new Date().getTime())
        );

        // Insert notificationData into the database
        NotificationManager notificationManager = new NotificationManager(tngActivity.this);
        notificationManager.insertNotification(notificationData);
        notificationManager.closeDatabase();

        // Create Income data
        IncomeManager incomeManager = new IncomeManager(tngActivity.this);
        incomeManager.insertIncome(new Income(username, totalPrice, null, String.valueOf(new Date().getTime())));  // Assuming you don't have a message for Income

        // Navigate to payment_successActivity
        Intent intent = new Intent(tngActivity.this, payment_successActivity.class);
        startActivity(intent);
    }
}
