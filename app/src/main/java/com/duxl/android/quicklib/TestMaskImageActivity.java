package com.duxl.android.quicklib;

import android.view.View;

import com.duxl.baselib.ui.activity.BaseActivity;

public class TestMaskImageActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_mask_image;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("显示不规则图片");
    }
}
