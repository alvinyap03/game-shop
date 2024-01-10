package com.example.tutorial3android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Genre_view_Adapter extends RecyclerView.Adapter<Genre_view_Adapter.MyViewHolder> {

    Context context;
    ArrayList genre_id, genre_name;

    Genre_view_Adapter(Context context,
                       ArrayList genre_id,
                       ArrayList genre_name){
        this.context = context;
        this.genre_id = genre_id;
        this.genre_name = genre_name;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.genre_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Genre_view_Adapter.MyViewHolder holder, int position) {
    holder.Genre_id_text_view.setText(String.valueOf(genre_id.get(position)));
        holder.Genre_name_text_view.setText(String.valueOf(genre_name.get(position)));
    }

    @Override
    public int getItemCount() {
        return genre_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Genre_id_text_view,Genre_name_text_view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Genre_id_text_view = itemView.findViewById(R.id.Genre_id_text_view);
            Genre_name_text_view = itemView.findViewById(R.id.Genre_name_text_view);

        }
    }
}
