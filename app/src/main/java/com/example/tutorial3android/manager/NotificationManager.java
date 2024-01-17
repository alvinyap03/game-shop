package com.example.tutorial3android.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tutorial3android.data.Notification;
import com.example.tutorial3android.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private DatabaseHelper dbHelper;

    public NotificationManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertNotification(Notification notification) {
        long newRowId = -1;

        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("sender", notification.getSender());
            values.put("message", notification.getMessage());
            values.put("receiver", notification.getReceiver());
            values.put("time", notification.getTime());

            newRowId = db.insert("Notification", null, values);
        } catch (SQLException e) {
            Log.e("NotificationManager", "Error inserting notification: " + e.getMessage());
        }

        return newRowId;
    }

    public List<Notification> getNotificationsForUser(String username) {
        List<Notification> notifications = new ArrayList<>();

        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            String[] projection = {
                    "sender",
                    "message",
                    "receiver",
                    "time"
            };

            String selection = "receiver = ?";
            String[] selectionArgs = {username};

            try (Cursor cursor = db.query(
                    "Notification",
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            )) {
                Log.d("NotificationManager", "Cursor count: " + cursor.getCount());

                while (cursor.moveToNext()) {
                    String sender = cursor.getString(cursor.getColumnIndexOrThrow("sender"));
                    String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                    String receiver = cursor.getString(cursor.getColumnIndexOrThrow("receiver"));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));

                    Log.d("NotificationManager", "Notification - Sender: " + sender
                            + ", Message: " + message
                            + ", Receiver: " + receiver
                            + ", Time: " + time);

                    Notification notification = new Notification(sender, message, receiver, time);
                    notifications.add(notification);
                }
            }
        } catch (SQLException e) {
            Log.e("NotificationManager", "Error getting notifications: " + e.getMessage());
        }

        Log.d("NotificationManager", "Notifications size: " + notifications.size());
        return notifications;
    }


    public void closeDatabase() {
        dbHelper.close();
    }
    // Add other methods specific to Notification entity if needed
    // ...
}
