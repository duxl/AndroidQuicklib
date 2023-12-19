package com.duxl.android.quicklib;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.duxl.android.quicklib.databinding.ActivityTestLockedTableBinding;
import com.duxl.android.quicklib.databinding.TableTestDataColumnBinding;
import com.duxl.android.quicklib.databinding.TableTestLockedColumnBinding;
import com.duxl.baselib.widget.LockedColumnTableView;

import java.util.ArrayList;
import java.util.List;

public class TestLockedTableActivity extends AppCompatActivity {

    private ActivityTestLockedTableBinding mBinding;
    private Adapter mAdapter;
    private List<String> mItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityTestLockedTableBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.btnRemove.setOnClickListener(it -> {
            mItems.remove(0);
            mAdapter.notifyItemRemoved(0);
        });

        mItems = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mItems.add(String.valueOf(i));
        }
        mAdapter = new Adapter(mItems);
        mBinding.lockedColumnTableView.setAdapter(mAdapter);
    }

    private class Adapter extends LockedColumnTableView.Adapter {
        private List<String> mItems;

        public Adapter(List<String> items) {
            this.mItems = items;
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        @Override
        public void onBindLockedViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TableTestLockedColumnBinding binding = TableTestLockedColumnBinding.bind(holder.itemView);
            binding.getRoot().setBackgroundColor(Color.parseColor("#EEEEEE"));
            binding.tvNo.setText("No---" + mItems.get(position));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TableTestDataColumnBinding binding = TableTestDataColumnBinding.bind(holder.itemView);
            binding.getRoot().setBackgroundColor(Color.WHITE);
            binding.tvName.setText("张三" + mItems.get(position));
            binding.tvSex.setText("男");
            binding.tvHeight.setText("183cm");
            binding.tvWeight.setText("159斤");
            binding.tvEthnic.setText("汉");
            binding.tvHobby.setText("摄影");
            binding.tvCompany.setText("哈希");
        }
    }
}
