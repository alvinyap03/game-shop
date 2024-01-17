package com.example.tutorial3android;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tutorial3android.data.Game;

import java.util.ArrayList;
import java.util.List;


public class FPX_Fragment extends Fragment {

    private List<Game> selectedGamesList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f_p_x_, container, false);


        // 获取传递给片段的数据
        Bundle arguments = getArguments();
        if (arguments != null) {
            ArrayList<String> selectedGameNames = arguments.getStringArrayList("selectedGameNames");
            double totalPrice = arguments.getDouble("totalPrice", 0.0);

            Button next_btn = view.findViewById(R.id.enter_fpx);

            next_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), fpxActivity.class);
                    intent.putExtra("selectedGameNames", selectedGameNames);
                    intent.putExtra("totalPrice", totalPrice);
                    startActivity(intent);
                }
            });
        }

        return view;
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