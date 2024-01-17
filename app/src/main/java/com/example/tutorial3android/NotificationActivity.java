package com.example.tutorial3android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tutorial3android.data.Notification;
import com.example.tutorial3android.manager.NotificationManager;

import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private static final String TAG = "NotificationActivity";

    private com.example.tutorial3android.manager.NotificationManager notificationManager; // Fully qualified name
    private NotificationAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        Log.d(TAG, "Receiver (username): " + username);

        notificationManager = new com.example.tutorial3android.manager.NotificationManager(this);

        recyclerView = findViewById(R.id.notification1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateRecyclerView(username);

        Button back = findViewById(R.id.button6);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, usermenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateRecyclerView(String username) {
        List<Notification> notificationDataList = notificationManager.getNotificationsForUser(username);

        Log.d(TAG, "NotificationDataList size: " + notificationDataList.size());

        int notificationCount = notificationDataList.size();
        for (Notification notification : notificationDataList) {
            Log.d(TAG, "Notification Message: " + notification.getMessage());
        }
        Log.d(TAG, "Notification count: " + notificationCount);

        adapter = new NotificationAdapter(notificationDataList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (notificationManager != null) {
            notificationManager.closeDatabase();
        }
    }
}
