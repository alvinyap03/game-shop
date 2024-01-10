package com.example.tutorial3android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class Genre_List extends AppCompatActivity {

    RecyclerView recyclerView;

    Genre_dbManager genreDB;
    ArrayList<String> genre_id, genre_name;
    Genre_view_Adapter genre_view_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);

        recyclerView = findViewById(R.id.recyclerView_genre_list);

        genreDB = new Genre_dbManager(Genre_List.this);
        genre_id = new ArrayList<>();
        genre_name = new ArrayList<>();

        StoreDataInArrays();

        genre_view_adapter = new Genre_view_Adapter(Genre_List.this, genre_id, genre_name);
        recyclerView.setAdapter(genre_view_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Genre_List.this));
    }

    void StoreDataInArrays(){
        Cursor cursor = genreDB.fetch();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                genre_id.add(cursor.getString(0));
                genre_name.add(cursor.getString(1));
            }
        }
    }
}