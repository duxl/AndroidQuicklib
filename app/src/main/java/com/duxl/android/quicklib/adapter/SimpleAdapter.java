package com.duxl.android.quicklib.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SimpleAdapter extends RecyclerView.Adapter {

    private int mItemCount;

    public SimpleAdapter(int count) {
        this.mItemCount = count;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView tv = holder.itemView.findViewById(android.R.id.text1);
        tv.setText("Position" + position);
        tv.setBackgroundColor(Color.parseColor(position % 2 == 0 ? "#EEEEEE" : "#DDDDDD"));
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }
}
