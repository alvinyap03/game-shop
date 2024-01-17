package com.example.tutorial3android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tutorial3android.data.Income;
import com.example.tutorial3android.manager.IncomeManager;

import java.util.List;
import java.util.Locale;

public class IncomeActivity extends AppCompatActivity {

    private RecyclerView incomeRecyclerView;
    private IncomeAdapter incomeAdapter;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        incomeRecyclerView = findViewById(R.id.incomelist);
        totalTextView = findViewById(R.id.textView20);

        // Initialize the IncomeAdapter
        incomeAdapter = new IncomeAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        incomeRecyclerView.setLayoutManager(layoutManager);
        incomeRecyclerView.setAdapter(incomeAdapter);

        Button back = findViewById(R.id.button29);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IncomeActivity.this, adminpage.class);
                startActivity(intent);
            }
        });

        // Load and display income data
        loadIncomeData();
    }

    private void loadIncomeData() {
        // Replace "dummy_username" with the actual logged-in username
        List<Income> incomeDataList = new IncomeManager(this).getAllIncome();
        incomeAdapter.setIncomeDataList(incomeDataList);

        // Calculate total and update the textView20
        double total = incomeAdapter.calculateTotal();
        totalTextView.setText(String.format(Locale.getDefault(), "Total: $%.2f", total));
    }
}
