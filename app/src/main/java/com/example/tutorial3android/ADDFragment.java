package com.example.tutorial3android;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ADDFragment extends Fragment {

     Button create_game, create_genre;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a_d_d, container, false);

        create_genre = view.findViewById(R.id.create_genre_btn);
        create_game = view.findViewById(R.id.create_game_btn);

        create_genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start YourNewActivity when create game button is clicked
                Intent intent = new Intent(getActivity(), addgenreActivity.class);
                startActivity(intent);
            }
        });

        create_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start YourNewActivity when create game button is clicked
                Intent intent = new Intent(getActivity(), AddGameActivity.class);
                startActivity(intent);
            }
        });




        return view;
    }


}