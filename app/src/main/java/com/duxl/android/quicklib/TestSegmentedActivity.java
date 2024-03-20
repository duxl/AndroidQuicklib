package com.duxl.android.quicklib;

import android.view.View;

import com.duxl.android.quicklib.databinding.ActivityTestSegmentedBinding;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.utils.ToastUtils;
import com.duxl.baselib.widget.segmented.SegmentedControlItem;

/**
 * 测试滑块tab
 */
public class TestSegmentedActivity extends BaseActivity {

    private ActivityTestSegmentedBinding binding;

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

        binding.scv.setOnSegItemClickListener((item, position) -> ToastUtils.show("onItemClick#" + position));
    }
}
