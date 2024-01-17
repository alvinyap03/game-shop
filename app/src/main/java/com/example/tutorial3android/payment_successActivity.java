package com.example.tutorial3android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class payment_successActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        Intent originalIntent = getIntent();

        Button payment_success_menu = findViewById(R.id.payment_success_menu);
        payment_success_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(payment_successActivity.this, usermenuActivity.class);
                startActivity(intent);
            }
        });


        Button payment_success_receipt = findViewById(R.id.payment_success_receipt);
        payment_success_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(payment_successActivity.this, recieptActivity.class);
                intent.putExtras(originalIntent);
                startActivity(intent);
            }
        });
    }
}