package com.duxl.android.quicklib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duxl.android.quicklib.adapter.SimpleAdapter;
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
        mRecyclerView.setAdapter(new SimpleAdapter(mListCount));
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
                if (position == 0) {
                    return getResources().getDrawable(R.mipmap.ic_time);
                }
                return super.getDot(position);
            }

        };
        mRecyclerView.addItemDecoration(timelineDecoration);
    }

    // 完全自定义
    public void onClickCustomAll(View v) {
        removeAll();
        int outRight = DisplayUtil.dip2px(this, 40);
        TimelineDecoration timelineDecoration = new TimelineDecoration(this, 0, new TimelineDecoration.DrawCallback() {
            @Override
            public void drawItem(@NonNull Canvas canvas, @NonNull Paint paint, @NonNull RecyclerView parent, @NonNull View child, int position) {
                paint.reset();
                paint.setAntiAlias(true);
                if (position % 2 == 0) {
                    paint.setColor(Color.RED);
                } else {
                    paint.setColor(Color.GREEN);
                }
                int lineWidth = DisplayUtil.dip2px(TestTimelineDecorationActivity.this, 4);
                // 画线条
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(lineWidth);
                int startX = child.getRight() + outRight / 2;
                int startY = child.getTop();
                int endX = startX;
                int endY = child.getBottom();

                if (position == 0) {
                    startY += child.getHeight() / 2;
                } else if (position == parent.getAdapter().getItemCount() - 1) {
                    endY -= child.getHeight() / 2;
                }
                canvas.drawLine(startX, startY, endX, endY, paint);

                // 画圆点
                paint.setColor(Color.parseColor("#440000FF"));
                int radius = DisplayUtil.dip2px(TestTimelineDecorationActivity.this, 10);
                int centerY = child.getTop() + child.getHeight() / 2;
                canvas.drawCircle(startX, centerY, radius, paint);

                // 画编号
                int textSize = DisplayUtil.sp2px(TestTimelineDecorationActivity.this, 14);
                paint.setTextSize(textSize);
                paint.setAlpha(255);
                paint.setColor(Color.BLUE);
                paint.setTextAlign(Paint.Align.CENTER);
                Paint.FontMetrics fontMetrics = paint.getFontMetrics();
                int baseLine = (int) (child.getTop() - fontMetrics.top + (child.getHeight() - (fontMetrics.bottom - fontMetrics.top)) / 2);
                canvas.drawText(String.valueOf(position), startX, baseLine, paint);
            }
        }) {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                //super.getItemOffsets(outRect, view, parent, state);
                outRect.right = outRight;
            }
        };

        mRecyclerView.addItemDecoration(timelineDecoration);
    }

    // 完全自定义2
    public void onClickCustomAll2(View v) {
        mRecyclerView.setAdapter(new SimpleAdapter(3));
        removeAll();
        int outLeft = DisplayUtil.dip2px(this, 40);
        TimelineDecoration timelineDecoration = new TimelineDecoration(this, outLeft, new TimelineDecoration.DrawCallback() {

            @Override
            public void beforeDrawItem(@NonNull Canvas canvas, @NonNull Paint paint, @NonNull RecyclerView parent) {
                paint.reset();
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(4);
                paint.setColor(Color.GREEN);

                View firstView = parent.getChildAt(0);
                int firstPosition = parent.getChildAdapterPosition(firstView);
                int centerX = outLeft / 2;
                int startY = firstView.getTop();
                if (firstPosition == 0) {
                    startY = firstView.getHeight() / 2;
                }

                int endY = parent.getHeight();
                canvas.drawLine(centerX, startY, centerX, endY, paint);
            }

            @Override
            public void drawItem(@NonNull Canvas canvas, @NonNull Paint paint, @NonNull RecyclerView parent, @NonNull View child, int position) {
                paint.reset();
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.BLUE);

                int radius = DisplayUtil.dip2px(TestTimelineDecorationActivity.this, 10);
                int centerX = outLeft / 2;
                int centerY = child.getTop() + child.getHeight() / 2;
                canvas.drawCircle(centerX, centerY, radius, paint);
            }
        });

        mRecyclerView.addItemDecoration(timelineDecoration);
    }
}
