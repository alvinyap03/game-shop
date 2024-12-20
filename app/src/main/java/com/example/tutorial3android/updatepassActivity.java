package com.example.tutorial3android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Add this import statement for UserManager
import com.example.tutorial3android.data.User;
import com.example.tutorial3android.manager.UserManager;

public class updatepassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepass);

        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        final String username = preferences.getString("username", "");

        final EditText enterGmailEditText = findViewById(R.id.entergmail);
        final EditText enterNewPassEditText = findViewById(R.id.enternewpass);
        Button changeButton = findViewById(R.id.button20);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredGmail = enterGmailEditText.getText().toString();
                String enteredNewPass = enterNewPassEditText.getText().toString();

                // Use the fully qualified class name for UserManager
                com.example.tutorial3android.manager.UserManager userManager =
                        new com.example.tutorial3android.manager.UserManager(updatepassActivity.this);

                User user = userManager.getUserByGmail(enteredGmail);

                if (user != null && user.getUsername().equals(username)) {
                    // Update the password for the user
                    int numRowsUpdated = userManager.updateUserPassword(username, enteredNewPass);

                    if (numRowsUpdated > 0) {
                        Toast.makeText(updatepassActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(updatepassActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(updatepassActivity.this, "Invalid Gmail or username", Toast.LENGTH_SHORT).show();
                }

                userManager.close();
            }
        });

        Button back = findViewById(R.id.button8);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(updatepassActivity.this, profileActivity.class);
                startActivity(intent);
            }
        });
    }
}
