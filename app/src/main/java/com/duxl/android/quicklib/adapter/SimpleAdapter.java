package com.duxl.android.quicklib.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SimpleAdapter extends RecyclerView.Adapter {

    private int mItemCount;
    private boolean isFullScreen;
    private int[] colors = {Color.RED, Color.BLUE, Color.BLACK, Color.YELLOW};

    public SimpleAdapter(int count) {
        this.mItemCount = count;
    }

    public SimpleAdapter(int count, boolean isFullScreen) {
        this(count);
        this.isFullScreen = isFullScreen;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        if (isFullScreen) {
            view.setLayoutParams(new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.MATCH_PARENT
            ));
            ((TextView) view).setGravity(Gravity.CENTER);
            ((TextView) view).setTextSize(30);
        }
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView tv = holder.itemView.findViewById(android.R.id.text1);
        tv.setText("Position# " + position);
        tv.setBackgroundColor(colors[position % colors.length]);
        tv.setTextColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }
}
