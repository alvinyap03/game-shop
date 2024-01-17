package com.example.tutorial3android;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial3android.R;
import com.example.tutorial3android.manager.UserManager;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Cursor cursor;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public UserAdapter(Context context, Cursor cursor, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.cursor = cursor;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(String username);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageTextView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_genre_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        int usernameIndex = cursor.getColumnIndex(UserManager.COLUMN_USERNAME);
        final String username = cursor.getString(usernameIndex);
        holder.messageTextView.setText(username);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(username);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 0 : cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}

