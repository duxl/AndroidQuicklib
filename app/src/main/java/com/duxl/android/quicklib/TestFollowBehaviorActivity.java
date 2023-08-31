package com.duxl.android.quicklib;

import android.view.View;

import com.duxl.android.quicklib.adapter.SimpleAdapter;
import com.duxl.android.quicklib.databinding.ActivityTestFollowBehaviorBinding;
import com.duxl.baselib.ui.activity.BaseActivity;

/**
 * 测试跟随Behavior
 */
public class TestFollowBehaviorActivity extends BaseActivity {

    private ActivityTestFollowBehaviorBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_follow_behavior;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("跟随布局");
        mBinding = ActivityTestFollowBehaviorBinding.bind(v);
        mBinding.recyclerview.setAdapter(new SimpleAdapter(50));
    }
}
