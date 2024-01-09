package com.example.tutorial3android;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameViewHolder> {

    private static final String TAG = "GameListAdapter";
    private List<String> gameNames;
    private OnItemClickListener onItemClickListener; // Declare the interface reference

    // Interface for handling item clicks
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public GameListAdapter(List<String> gameNames) {
        this.gameNames = new ArrayList<>(gameNames);
    }

    public void clearList() {
        gameNames.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_genre_layout, parent, false);
        return new GameViewHolder(itemView);
    }

    public void handleItemClick(int position) {
        String selectedGameName = gameNames.get(position);
        if (!TextUtils.isEmpty(selectedGameName)) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        } else {
            // Handle the case where the game name is empty or null, e.g., show an error message
            Log.e(TAG, "handleItemClick: Invalid game name at position " + position);
            // You might want to show an error message to the user
        }
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        String gameName = gameNames.get(position);
        holder.gameNameTextView.setText(gameName);

        // Set click listener for the item
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
        return gameNames.size();
    }

    public void updateList(List<String> newList) {
        if (newList != null) {
            Log.d(TAG, "updateList: Updating list with new size: " + newList.size());
            int oldSize = gameNames.size();

            // Clear the list only if the new list is not empty
            if (!newList.isEmpty()) {
                gameNames.clear();
                gameNames.addAll(newList);
                Log.d(TAG, "updateList: List updated with new data");
            }

            int newSize = gameNames.size();
            if (oldSize > newSize) {
                notifyItemRangeRemoved(newSize, oldSize - newSize);
            } else if (oldSize < newSize) {
                notifyItemRangeInserted(oldSize, newSize - oldSize);
            }
        } else {
            Log.e(TAG, "updateList: Attempted to update with a null list");
        }
    }

    // Setter method for the OnItemClickListener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView gameNameTextView;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            gameNameTextView = itemView.findViewById(R.id.textViewGameName);
        }
    }

    public void onItemClick(int position) {
        String selectedGameName = gameNames.get(position);
        if (!TextUtils.isEmpty(selectedGameName)) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        } else {
            // Handle the case where the game name is empty or null, e.g., show an error message
            Log.e(TAG, "onItemClick: Invalid game name at position " + position);
        }
    }

    // Method to start DeletegameActivity
    private void startDeleteGameActivity(String selectedGameName) {
        // Implement the logic to start DeletegameActivity
        // ...
    }
}
