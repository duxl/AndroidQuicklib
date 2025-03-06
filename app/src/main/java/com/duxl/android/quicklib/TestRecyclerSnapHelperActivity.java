package com.duxl.android.quicklib;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.duxl.android.quicklib.databinding.ActivityTestRecyclerSnapHelperBinding;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.utils.AnimUtils;
import com.duxl.baselib.utils.DisplayUtil;
import com.duxl.baselib.utils.ToastUtils;
import com.duxl.baselib.widget.CenterLayoutManager;
import com.duxl.baselib.widget.OnItemClickListener;

/**
 * Recycler的Item居中方案示例
 */
public class TestRecyclerSnapHelperActivity extends BaseActivity {

    private ActivityTestRecyclerSnapHelperBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_recycler_snap_helper;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("Recycler的Item居中方案示例");
        mBinding = ActivityTestRecyclerSnapHelperBinding.bind(v);
        test1();

        test2();

        test3();
    }

    /**
     * 通过点击事件触发居中
     */
    private void test1() {
        Adapter adapter = new Adapter((int) (DisplayUtil.getScreenWidth(this) / 4.3), RecyclerView.LayoutParams.MATCH_PARENT);
        mBinding.recyclerview1.setAdapter(adapter);

        CenterLayoutManager centerLayoutManager = new CenterLayoutManager(mBinding.recyclerview1, CenterLayoutManager.HORIZONTAL, false);
        mBinding.recyclerview1.setLayoutManager(centerLayoutManager);
        AnimUtils.setRecyclerAnimEnable(mBinding.recyclerview1, false);

        adapter.setOnItemClickListener(new OnItemClickListener<View, Void>() {
            @Override
            public Void onItemClick(View item, int position) {
                // 滑动点击的item到水平居中（speedPer指定滑动的速率）
                centerLayoutManager.smoothScrollToPositionCenter(position, 200);

                // 切换点击的item背景色
                int oldPosition = adapter.selectPosition;
                adapter.selectPosition = position;
                adapter.notifyItemChanged(oldPosition);
                adapter.notifyItemChanged(position);

                return null;
            }
        });
    }

    /**
     * 滑动自动居中
     */
    private void test2() {
        Adapter adapter = new Adapter((int) (DisplayUtil.getScreenWidth(this) / 2.2), RecyclerView.LayoutParams.MATCH_PARENT);
        mBinding.recyclerview2.setAdapter(adapter);
        AnimUtils.setRecyclerAnimEnable(mBinding.recyclerview2, false);

        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(mBinding.recyclerview2);

        mBinding.recyclerview2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 滑动停止，获取居中的item位置
                    View view = linearSnapHelper.findSnapView(mBinding.recyclerview2.getLayoutManager());
                    if (view != null) {
                        int centerPosition = mBinding.recyclerview2.getChildAdapterPosition(view);
                        int oldPosition = adapter.selectPosition;
                        adapter.selectPosition = centerPosition;
                        adapter.notifyItemChanged(oldPosition);
                        adapter.notifyItemChanged(centerPosition);
                    }
                }
            }
        });
    }

    /**
     * 滑动自动居中
     */
    private void test3() {
        Adapter adapter = new Adapter(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        adapter.selectPosition = -1;
        mBinding.recyclerview3.setAdapter(adapter);
        //AnimUtils.clearRecyclerAnim(mBinding.recyclerview3);

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mBinding.recyclerview3);

        mBinding.recyclerview3.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 滑动停止，获取居中的item位置
                    View view = pagerSnapHelper.findSnapView(mBinding.recyclerview3.getLayoutManager());
                    if (view != null) {
                        int selectPosition = mBinding.recyclerview3.getChildAdapterPosition(view);
                        ToastUtils.show("selectPosition=" + selectPosition);
                    }
                }
            }
        });

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mBinding.recyclerview3.getLayoutManager();
        mBinding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (linearLayoutManager != null) {
                if (checkedId == R.id.radio_horizontal) {
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                } else {
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                }
            }
        });
    }


    private class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        int selectPosition = 0;
        int itemWidth;
        int itemHeight;

        public Adapter(int itemWidth, int itemHeight) {
            this.itemWidth = itemWidth;
            this.itemHeight = itemHeight;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            tv.setGravity(Gravity.CENTER);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(itemWidth, itemHeight);
            tv.setLayoutParams(layoutParams);
            tv.setTextSize(18);
            return new RecyclerView.ViewHolder(tv) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TextView tv = (TextView) holder.itemView;
            if (selectPosition == position) {
                tv.setBackgroundColor(Color.parseColor("#00CC00"));
            } else {
                tv.setBackgroundColor(Color.parseColor(position % 2 == 0 ? "#CCCCCC" : "#80CCCCCC"));
            }
            tv.setText("Item" + position);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return 15;
        }

        private OnItemClickListener<View, Void> onItemClickListener;

        public void setOnItemClickListener(OnItemClickListener<View, Void> listener) {
            this.onItemClickListener = listener;
        }
    }

}
