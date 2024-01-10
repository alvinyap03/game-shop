package com.example.tutorial3android;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Set;

public class GenreDataAdapter extends ArrayAdapter<GenreData>  {

    private Context context;
    private List<GenreData> genreDataList;
    private Set<GenreData> selectedGenres;
    private List<GenreData> preSelectedGenres; // Add this variable to store pre-selected genres

    public GenreDataAdapter(Context context, List<GenreData> genreDataList, Set<GenreData> selectedGenres, List<GenreData> preSelectedGenres) {
        super(context, 0, genreDataList);
        this.context = context;
        this.genreDataList = genreDataList;
        this.selectedGenres = selectedGenres;
        this.preSelectedGenres = preSelectedGenres;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item_genre_layout, parent, false);
        }

        GenreData currentGenre = genreDataList.get(position);

        TextView genreName = listItem.findViewById(R.id.textViewGameName);
        genreName.setText(currentGenre.getGenreName());

        // Check if the current genre is pre-selected
        if (preSelectedGenres != null && preSelectedGenres.contains(currentGenre)) {
            // Change the background color to indicate pre-selection
            listItem.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_color));
        } else if (selectedGenres.contains(currentGenre)) {
            // Change the background color when selected
            listItem.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_color));
        } else {
            // Set the default background color
            listItem.setBackgroundColor(Color.TRANSPARENT);
        }

        return listItem;
    }

    public void updateData(List<GenreData> genreDataList, Set<GenreData> selectedGenres) {
        this.genreDataList = genreDataList;
        this.selectedGenres = selectedGenres;
        notifyDataSetChanged();
    }
}
