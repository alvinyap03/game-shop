package com.example.tutorial3android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class game_list extends AppCompatActivity {
    RecyclerView recyclerView;

    Game_dbManager gameDB;
    ArrayList<String> Game_image, Game_name, Game_price;
    Game_view_Adapter Game_view_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        recyclerView = findViewById(R.id.recyclerView_game_list);

        gameDB = new Game_dbManager(game_list.this);
        Game_image = new ArrayList<>();
        Game_name = new ArrayList<>();
        Game_price = new ArrayList<>();
        StoreDataInArrays();

        Game_view_adapter = new Game_view_Adapter(game_list.this, Game_image, Game_name, Game_price);
        recyclerView.setAdapter(Game_view_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(game_list.this));
    }

    void StoreDataInArrays(){
        Cursor cursor = gameDB.fetch();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                Game_image.add(cursor.getString(0));
                Game_name.add(cursor.getString(1));
                Game_price.add(cursor.getString(2));
            }
        }
    }
}