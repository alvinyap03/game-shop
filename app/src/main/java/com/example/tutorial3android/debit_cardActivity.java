package com.example.tutorial3android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log; // Import Log class
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class debit_cardActivity extends AppCompatActivity {

    private static final String TAG = "DebitCardActivity"; // Define a tag for your logs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit_card);

        ArrayList<String> selectedGameNames = getIntent().getStringArrayListExtra("selectedGameNames");
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);

        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        // Display total in TextView
        TextView totalTextView = findViewById(R.id.total4);
        totalTextView.setText(String.format("%.2f", totalPrice));

        Button debit_back = findViewById(R.id.debit_back);
        debit_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(debit_cardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button debit_success = findViewById(R.id.debit_success);
        debit_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create user-game associations and store them
                // Create user-game associations and store them
                UserGameDAO userGameDAO = new UserGameDAO(debit_cardActivity.this);
                for (String gameName : selectedGameNames) {
                    userGameDAO.addUserGameAssociation(username, gameName);
                }
                userGameDAO.closeDatabase();


                // Create NotificationData
                List<String> receivers = new ArrayList<>();
                receivers.add(username);
                NotificationData notificationData = new NotificationData(null, "system",
                        "You have bought games", new Date(), receivers);

                // Log the NotificationData
                Log.d(TAG, "NotificationData: " + notificationData.toString());

                // Create IncomeData
                IncomeData incomeData = new IncomeData(username, totalPrice, new Date(), selectedGameNames);

                // Log the IncomeData
                Log.d(TAG, "IncomeData: " + incomeData.toString());

                // Save notificationData and incomeData to wherever needed
                // For example, you can store them in a database or use them in your app as required

                // Navigate to payment_successActivity
                Intent intent = new Intent(debit_cardActivity.this, payment_successActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<game_data> convertToGameData(List<String> selectedGameNames) {
        List<game_data> gameDataList = new ArrayList<>();

        for (String gameName : selectedGameNames) {
            // Assuming you want to create a new game_data object for each game name
            game_data gameData = new game_data(null, gameName, 0.0, "", null);
            gameDataList.add(gameData);
        }

        return gameDataList;
    }

    private UserData getUserData(String username) {
        // Implement this method to retrieve UserData from your data source
        // (e.g., database, shared preferences, etc.)

        user_dbmanager dbManager = new user_dbmanager(debit_cardActivity.this);  // Using the activity's context


        // Assuming you have a method to get UserData by username from your database manager
        UserData userData = dbManager.getUserByUsername(username);

        dbManager.close();

        return userData;
    }

    private void updateUserData(UserData userData) {
        // Implement this method to update UserData in your data source
        // (e.g., database, shared preferences, etc.)

        user_dbmanager dbManager = new user_dbmanager(debit_cardActivity.this);  // Using the activity's context

        // Assuming you have a method to update UserData in your database manager
        dbManager.updateUserData(userData);

        dbManager.close();
    }
}
