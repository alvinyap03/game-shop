package com.example.tutorial3android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {

    private EditText et_username, et_password;
    private user_dbmanager userDBManager;
    private admin_dbmanager adminDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView textView = findViewById(R.id.register_turn_page_text);
        Button btn_login = findViewById(R.id.login_button);
        et_username = findViewById(R.id.username_login_edit_text);
        et_password = findViewById(R.id.password_login_edit_text);
        userDBManager = new user_dbmanager(this);
        adminDBManager = new admin_dbmanager(this);

        String text = "Register";

        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(loginActivity.this, registerActivity.class);
                startActivity(intent);
            }
        };

        ss.setSpan(clickableSpan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(loginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }else {
                    userLoginProcess(username,password);
                }
            }

            private void userLoginProcess(String username, String password) {
                // Check if the username contains "@admin"
                if (username.contains("@admin")) {
                    // This is an admin login
                    UserData admin = adminDBManager.getAdminByUsername(username);

                    if (admin != null && admin.getPassword().equals(password)) {
                        // Admin credentials are correct
                        // Store username in SharedPreferences
                        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("username", username);
                        editor.apply();
                        // You can add the logic to navigate to the admin page here
                        Toast.makeText(loginActivity.this, "Admin login successful", Toast.LENGTH_SHORT).show();
                        // Example: Navigate to admin page
                        Intent adminIntent = new Intent(loginActivity.this, adminpage.class);
                        startActivity(adminIntent);
                    } else {
                        // Admin credentials are incorrect
                        Toast.makeText(loginActivity.this, "Invalid admin", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // This is a user login
                    UserData user = userDBManager.getUserByUsername(username);

                    if (user != null && user.getPassword().equals(password)) {
                        // User credentials are correct
                        // Store username in SharedPreferences
                        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("username", username);
                        editor.apply();                        // You can add the logic to navigate to the user page here
                        Toast.makeText(loginActivity.this, "User login successful", Toast.LENGTH_SHORT).show();
                        // Example: Navigate to user page
                        Intent userIntent = new Intent(loginActivity.this, usermenuActivity.class);
                        startActivity(userIntent);
                    } else {
                        // User credentials are incorrect
                        Toast.makeText(loginActivity.this, "Invalid user", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


}
