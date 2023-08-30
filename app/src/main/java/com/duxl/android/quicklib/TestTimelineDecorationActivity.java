package com.duxl.android.quicklib;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.utils.DisplayUtil;
import com.duxl.baselib.widget.decoration.TimelineDecoration;

/**
 * 测试时间线装饰器
 */
public class TestTimelineDecorationActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private int mListCount = 20;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_timeline_decoration;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("时间线装饰器");
        mRecyclerView = v.findViewById(R.id.recyclerview);
        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(TestTimelineDecorationActivity.this).inflate(android.R.layout.simple_list_item_1, parent, false);
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
                return mListCount;
            }
        });
    }

    private void removeAll() {
        for (int i = mRecyclerView.getItemDecorationCount() - 1; i >= 0; i--) {
            mRecyclerView.removeItemDecorationAt(i);
        }
    }

    // 默认样式
    public void onClickDefault(View v) {
        removeAll();
        int outLeft = DisplayUtil.dip2px(this, 20);
        int radius = DisplayUtil.dip2px(this, 3.5f);
        int centerX = outLeft / 2 - radius;
        TimelineDecoration timelineDecoration = new TimelineDecoration(this, outLeft, radius, centerX);
        mRecyclerView.addItemDecoration(timelineDecoration);
    }

    // 更改颜色
    public void onClickColor(View v) {
        removeAll();
        int outLeft = DisplayUtil.dip2px(this, 20);
        int radius = DisplayUtil.dip2px(this, 3.5f);
        int centerX = outLeft / 2 - radius;
        TimelineDecoration timelineDecoration = new TimelineDecoration(this, outLeft, radius, centerX);
        timelineDecoration.setDotColor(Color.RED);
        timelineDecoration.setLineColor(Color.GREEN);
        mRecyclerView.addItemDecoration(timelineDecoration);
    }

    // 圆点偏移
    public void onClickDotOffset(View v) {
        removeAll();
        int outLeft = DisplayUtil.dip2px(this, 20);
        int radius = DisplayUtil.dip2px(this, 3.5f);
        int centerX = outLeft / 2 - radius;
        int centerY = radius;
        TimelineDecoration timelineDecoration = new TimelineDecoration(this, outLeft, radius, centerX, centerY);
        mRecyclerView.addItemDecoration(timelineDecoration);
    }

    // item偏移
    public void onClickItemOffset(View v) {
        removeAll();
        int outLeft = DisplayUtil.dip2px(this, 20);
        int radius = DisplayUtil.dip2px(this, 3.5f);
        int centerX = outLeft / 2 - radius;
        TimelineDecoration timelineDecoration = new TimelineDecoration(this, outLeft, radius, centerX);
        timelineDecoration.setExcludeStartCount(1);
        timelineDecoration.setExcludeEndCount(1);
        mRecyclerView.addItemDecoration(timelineDecoration);
    }

    // 自定义
    public void onClickCustom(View v) {
        removeAll();
        int outLeft = DisplayUtil.dip2px(this, 20);
        int radius = DisplayUtil.dip2px(this, 3.5f);
        int centerX = outLeft / 2 - radius;
        TimelineDecoration timelineDecoration = new TimelineDecoration(this, outLeft, radius, centerX) {
            @Override
            protected void initLinePaint(Paint paint) {
                super.initLinePaint(paint);
                paint.setColor(Color.RED);
            }

            @Override
            public Drawable getDot(int position) {
                if(position == 0) {
                    return getResources().getDrawable(R.mipmap.ic_time);
                }
                return super.getDot(position);
            }

        };
        mRecyclerView.addItemDecoration(timelineDecoration);
    }
}
