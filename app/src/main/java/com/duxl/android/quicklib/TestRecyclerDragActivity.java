package com.duxl.android.quicklib;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.duxl.android.quicklib.databinding.ActivityTestRecyclerDragBinding;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.utils.DisplayUtil;
import com.duxl.baselib.widget.DragItemTouchCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 测试Recycler拖拽
 */
public class TestRecyclerDragActivity extends BaseActivity {

    private ActivityTestRecyclerDragBinding binding;
    private List<String> list1 = new ArrayList<>();
    private MyAdapter adapter1;

    private List<String> list2 = new ArrayList<>();
    private MyAdapter adapter2;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_recycler_drag;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        binding = ActivityTestRecyclerDragBinding.bind(v);
        setTitle("测试Recycler拖拽");
        list1.add("#FF0000");
        list1.add("#00FF00");
        list1.add("#0000FF");
        list1.add("#CFB53B");
        list1.add("#2F2F4F");
        list1.add("#EAADEA");
        list1.add("#00FF7F");
        list1.add("#CC3299");

        list2.addAll(list1);

        binding.recyclerview1.setAdapter(adapter1 = new MyAdapter(list1, true));
        recyclerview1Drag();

        binding.recyclerview2.setAdapter(adapter2 = new MyAdapter(list2, false));
        recyclerview2Drag();
    }


    private void recyclerview1Drag() {
        DragItemTouchCallback mDragSortCallback;
        new ItemTouchHelper(mDragSortCallback = new DragItemTouchCallback() {
            @Override
            public boolean onMove(int fromPosition, int toPosition) {
                Collections.swap(list1, fromPosition, toPosition);
                adapter1.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onBeginDrag(@Nullable RecyclerView.ViewHolder viewHolder) {
                super.onBeginDrag(viewHolder);
                viewHolder.itemView.setScaleX(1.1f);
                viewHolder.itemView.setScaleY(1.1f);
            }

            @Override
            public void onFinishDrag(RecyclerView.ViewHolder viewHolder) {
                super.onFinishDrag(viewHolder);
                viewHolder.itemView.setScaleX(1f);
                viewHolder.itemView.setScaleY(1f);
            }
        }).attachToRecyclerView(binding.recyclerview1);

        // 这里可以启用或禁用拖拽
        //mDragSortCallback.setLongPressDragEnabled(false);
    }

    private void recyclerview2Drag() {
        DragItemTouchCallback mDragSortCallback;
        new ItemTouchHelper(mDragSortCallback = new DragItemTouchCallback(DragItemTouchCallback.MovementFlags.V) {
            @Override
            public boolean onMove(int fromPosition, int toPosition) {
                Collections.swap(list2, fromPosition, toPosition);
                adapter2.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onBeginDrag(@Nullable RecyclerView.ViewHolder viewHolder) {
                super.onBeginDrag(viewHolder);
                viewHolder.itemView.setScaleX(1.1f);
                viewHolder.itemView.setScaleY(1.1f);
            }

            @Override
            public void onFinishDrag(RecyclerView.ViewHolder viewHolder) {
                super.onFinishDrag(viewHolder);
                viewHolder.itemView.setScaleX(1f);
                viewHolder.itemView.setScaleY(1f);
            }
        }).attachToRecyclerView(binding.recyclerview2);

        // 这里可以启用或禁用拖拽
        //mDragSortCallback.setLongPressDragEnabled(false);
    }


    private static class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<String> list;
        private boolean isGrid;

        public MyAdapter(List<String> list, boolean isGrid) {
            this.isGrid = isGrid;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            if (isGrid) {
                RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT,
                        DisplayUtil.dip2px(parent.getContext(), 70)
                );
                v.setLayoutParams(params);
                v.setGravity(Gravity.CENTER);
            } else {
                v.setGravity(Gravity.CENTER_VERTICAL);
            }
            return new RecyclerView.ViewHolder(v) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TextView tv = (TextView) holder.itemView;
            tv.setText(list.get(position));
            tv.setBackgroundColor(Color.parseColor(list.get(position)));

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
