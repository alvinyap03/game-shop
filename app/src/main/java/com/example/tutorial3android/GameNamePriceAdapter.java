package com.example.tutorial3android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial3android.data.Game;

import java.util.ArrayList;
import java.util.List;

public class GameNamePriceAdapter extends RecyclerView.Adapter<GameNamePriceAdapter.GameNamePriceViewHolder> {

    private List<Game> gameList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public GameNamePriceAdapter(List<Game> gameList, OnItemClickListener onItemClickListener) {
        this.gameList = new ArrayList<>(gameList);
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public GameNamePriceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game, parent, false);
        return new GameNamePriceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameNamePriceViewHolder holder, int position) {
        Game game = gameList.get(position);
        holder.gameNumberTextView.setText(String.valueOf(position + 1));
        holder.gameNameTextView.setText(game.getGameName());
        holder.gamePriceTextView.setText(String.valueOf(game.getPrice()));

        // Set click listener for the delete button
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete button click here
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        // Set click listener for the entire item view (if needed)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public void updateList(List<Game> newList) {
        if (newList != null) {
            int oldSize = gameList.size();

            if (!newList.isEmpty()) {
                gameList.clear();
                gameList.addAll(newList);
            }

            int newSize = gameList.size();
            if (oldSize > newSize) {
                notifyItemRangeRemoved(newSize, oldSize - newSize);
            } else if (oldSize < newSize) {
                notifyItemRangeInserted(oldSize, newSize - oldSize);
            }
        }
    }

    public static class GameNamePriceViewHolder extends RecyclerView.ViewHolder {
        TextView gameNumberTextView;
        TextView gameNameTextView;
        TextView gamePriceTextView;
        ImageButton deleteButton;

        public GameNamePriceViewHolder(@NonNull View itemView) {
            super(itemView);
            gameNumberTextView = itemView.findViewById(R.id.gameNumberTextView);
            gameNameTextView = itemView.findViewById(R.id.gameNameTextView);
            gamePriceTextView = itemView.findViewById(R.id.gamePriceTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public void removeItem(int position) {
        if (position >= 0 && position < gameList.size()) {
            gameList.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged(); // This line ensures proper update
        }
    }
}
