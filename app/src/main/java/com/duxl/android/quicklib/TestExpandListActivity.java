package com.duxl.android.quicklib;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.ui.adapter.BaseExpandableAdapter;
import com.duxl.baselib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 可折叠的列表（两级）
 */
public class TestExpandListActivity extends BaseActivity {

    protected RecyclerView mRecyclerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_expand_list;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("可折叠的列表（两级）");
        mRecyclerView = v.findViewById(R.id.recyclerview);

        setAdapter();
    }

    protected void setAdapter() {
        List<MonthItem> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(new MonthItem(i));
        }
        MonthAdapter adapter = new MonthAdapter();
        mRecyclerView.setAdapter(adapter);
        adapter.setNewInstance(list);
        // 点击月展开和折叠操作
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            MonthItem item = adapter.getItem(position);
            item._isExpand = !item._isExpand;
            adapter.notifyItemExpandChanged(position);
        });

        // 点击日期，toast打印
        adapter.setOnChildItemClickListener((adapterChildren, childView, positionChild, positionGroup) -> {
            MonthItem monthItem = list.get(positionGroup);
            String day = monthItem.getChildren().get(positionChild);
            ToastUtils.show("click: " + monthItem.month + " " + day);
        });
    }

    public static class MonthItem implements BaseExpandableAdapter.GroupItemEntity<String> {

        public boolean _isExpand;
        public String month;
        public List<String> days;

        public MonthItem(int month) {
            this.month = month + "月";
            int dayCount;
            if (month == 2) {
                dayCount = 28;
            } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                dayCount = 31;
            } else {
                dayCount = 30;
            }
            this.days = new ArrayList();
            for (int i = 1; i <= dayCount; i++) {
                days.add(i + "日");
            }
        }

        @Override
        public boolean isExpand() {
            return _isExpand;
        }

        @Override
        public List<String> getChildren() {
            return days;
        }
    }

    public static class MonthAdapter extends BaseExpandableAdapter<MonthItem, String> {

        public MonthAdapter() {
            super(android.R.layout.simple_list_item_1, android.R.layout.simple_list_item_1);
        }

        @Override
        protected void bindGroup(@NonNull View viewGroup, MonthItem dataGroup, int positionGroup) {
            TextView textView = viewGroup.findViewById(android.R.id.text1);
            textView.setText(dataGroup.month);
            textView.setBackgroundColor(Color.parseColor(positionGroup % 2 == 0 ? "#EEEEEE" : "#DDDDDD"));
        }

        @Override
        protected void bindChild(@NonNull RecyclerView recyclerChildren, @NonNull View childView, MonthItem dataGroup, int positionGroup, String dataChild, int positionChild) {
            TextView textView = childView.findViewById(android.R.id.text1);
            textView.setText("----------" + dataChild);
        }
    }
}
