package com.duxl.android.quicklib;

import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.duxl.android.quicklib.adapter.SimpleAdapter;
import com.duxl.android.quicklib.databinding.ActivityTestSegmentedBinding;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.widget.segmented.SegmentedControlItem;

/**
 * 测试滑块tab
 */
public class TestSegmentedActivity extends BaseActivity {

    private ActivityTestSegmentedBinding binding;
    private boolean isDragging; // 是否正在拖拽ViewPager

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_segmented;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("测试滑块tab");
        binding = ActivityTestSegmentedBinding.bind(v);
        binding.scv.addItem(new SegmentedControlItem("Java"));
        binding.scv.addItem(new SegmentedControlItem("Kotlin"));
        binding.scv.addItem(new SegmentedControlItem("Android"));
        binding.scv.setOnSegItemClickListener((item, position) -> {
            binding.viewPager.setCurrentItem(position);
        });


        binding.viewPager.setAdapter(new SimpleAdapter(3, true));
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (isDragging) {
                    if (position == binding.viewPager.getCurrentItem()) {
                        binding.scv.setItemOffset(positionOffset);
                    } else {
                        binding.scv.setItemOffset(positionOffset - 1);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                binding.scv.setSelectedItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                isDragging = state == SCROLL_STATE_DRAGGING;
            }
        });
    }
}
