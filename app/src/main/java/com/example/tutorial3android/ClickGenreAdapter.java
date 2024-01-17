package com.example.tutorial3android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial3android.data.Genre;

import java.util.List;

public class ClickGenreAdapter extends RecyclerView.Adapter<ClickGenreAdapter.ViewHolder> {

    private Context context;
    private List<Genre> genreList;

    public ClickGenreAdapter(Context context, List<Genre> genreList) {
        this.context = context;
        this.genreList = genreList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_genre_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Genre currentGenre = genreList.get(position);
        holder.genreName.setText(currentGenre.getGenreName());

        // You can customize the appearance based on your requirements
        holder.itemView.setBackgroundColor(Color.TRANSPARENT);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedGenreName = currentGenre.getGenreName();
                // Log the selected genre name
                Log.d("SearchActivity", "Selected Genre: " + selectedGenreName);
                // Start genre_game_list_Activity and pass the selected genre name
                Intent intent = new Intent(context, genre_game_list_Activity.class);
                intent.putExtra("selectedGenreName", selectedGenreName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView genreName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            genreName = itemView.findViewById(R.id.messageTextView);
        }
    }
}
