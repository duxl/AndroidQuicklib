package com.duxl.android.quicklib;

import android.view.View;

import com.duxl.baselib.ui.activity.BaseActivity;

/**
 * TestFragmentActivity
 * create by duxl 2020/8/16
 */
public class TestFragmentActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView(View v) {
        hideActionBar();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, new MyFragment()).commit();
    }
}
