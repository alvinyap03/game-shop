package com.example.tutorial3android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Game_view_Adapter extends RecyclerView.Adapter<Game_view_Adapter.MyViewHolder> {

    Context context;
    ArrayList Game_picture, Game_name, Game_price;

    Game_view_Adapter(Context context,
                      ArrayList Game_picture,
                       ArrayList Game_name,
                       ArrayList Game_price){
        this.context = context;
        this.Game_picture = Game_picture;
        this.Game_name = Game_name;
        this.Game_price = Game_price;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.game_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Game_view_Adapter.MyViewHolder holder, int position) {
        holder.game_imageView.setText(String.valueOf(Game_picture.get(position)));
        holder.Game_name_text_view.setText(String.valueOf(Game_name.get(position)));
        holder.Game_price_text_view.setText(String.valueOf(Game_price.get(position)));
    }

    @Override
    public int getItemCount() {
        return Game_name.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView game_imageView,Game_name_text_view, Game_price_text_view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            game_imageView = itemView.findViewById(R.id.game_imageView);
            Game_name_text_view = itemView.findViewById(R.id.Game_name_text_view);
            Game_price_text_view = itemView.findViewById(R.id.Game_price_text_view);
        }
    }
}
