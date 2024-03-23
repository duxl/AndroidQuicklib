package com.duxl.android.quicklib;

import android.view.View;

import com.duxl.baselib.ui.activity.BaseActivity;

/**
 * 常规阴影实现
 */
public class TestShadowActivity extends BaseActivity {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_shadow;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("常规阴影实现");

    }
}
