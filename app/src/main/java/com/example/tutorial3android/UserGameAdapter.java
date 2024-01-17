package com.example.tutorial3android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserGameAdapter extends RecyclerView.Adapter<UserGameAdapter.ViewHolder> {

    private List<String> games = new ArrayList<>();

    public void setGames(List<String> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_genre_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(games.get(position));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView gameTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            gameTextView = itemView.findViewById(R.id.messageTextView);
        }

        void bind(String game) {
            gameTextView.setText(game);
        }
    }
}
