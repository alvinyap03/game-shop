package com.example.tutorial3android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tutorial3android.data.Game;

import java.util.ArrayList;
import java.util.List;

public class choose_payment_Activity extends AppCompatActivity {

    private List<Game> selectedGamesList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_payment);

        ArrayList<String> selectedGameNames = getIntent().getStringArrayListExtra("selectedGameNames");
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);


        Button payment_tng_btn = findViewById(R.id.payment_tng_btn);
        payment_tng_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Tng_Fragment(), selectedGameNames, totalPrice);
            }
        });

        Button payment_fpx_btn = findViewById(R.id.payment_fpx_btn);
        payment_fpx_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FPX_Fragment(), selectedGameNames, totalPrice);
            }
        });

        Button debit_btn = findViewById(R.id.debit_btn);
        debit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new debit_Fragment(), selectedGameNames, totalPrice);
            }
        });
    }

    private void replaceFragment(Fragment fragment, ArrayList<String> selectedGameNames, double totalPrice) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // 将数据传递给片段
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("selectedGameNames", selectedGameNames);
        bundle.putDouble("totalPrice", totalPrice);
        fragment.setArguments(bundle);

        // 替换片段
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }


    private ArrayList<String> getSelectedGameNames() {
        ArrayList<String> selectedGameNames = new ArrayList<>();
        for (Game game : selectedGamesList) {
            selectedGameNames.add(game.getGameName());
        }
        return selectedGameNames;
    }

    private double calculateTotalPrice() {
        double total = 0;
        for (Game game : selectedGamesList) {
            total += game.getPrice();
        }
        return total;
    }
}