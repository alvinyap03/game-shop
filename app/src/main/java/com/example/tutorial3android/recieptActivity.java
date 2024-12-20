package com.example.tutorial3android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class recieptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciept);

        // Retrieve the intent that started this activity
        Intent intent = getIntent();

        // Retrieve values from the intent
        String username = intent.getStringExtra("username");
        String dateString = intent.getStringExtra("date");
        String paymentMethod = intent.getStringExtra("payment_method");
        double total = intent.getDoubleExtra("total", 0.0);

        // Format the date
        String formattedDate = formatDate(dateString);

        // Display values in TextViews
        TextView usernameTextView = findViewById(R.id.usernamer);
        usernameTextView.setText(username);

        // TextView dateTextView = findViewById(R.id.dater);
        // dateTextView.setText(formattedDate);

        TextView methodTextView = findViewById(R.id.methodr);
        methodTextView.setText(paymentMethod);

        TextView totalTextView = findViewById(R.id.totalr);
        totalTextView.setText(String.format("%.2f", total));


        Button mainmenu = findViewById(R.id.reciept_menu);
        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(recieptActivity.this, usermenuActivity.class);
                startActivity(intent);
            }
        });

    }

    private String formatDate(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = inputFormat.parse(dateString);

            // Specify the desired date format
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");

            // Format the date and return
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString; // Return original string if parsing fails
        }
    }
}
