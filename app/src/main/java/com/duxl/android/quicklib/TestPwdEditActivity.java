package com.duxl.android.quicklib;

import android.view.View;

import com.duxl.baselib.ui.activity.BaseActivity;

/**
 * 测试密码输入框
 */
public class TestPwdEditActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_pwd_edit;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("测试密码输入框");
    }
}
