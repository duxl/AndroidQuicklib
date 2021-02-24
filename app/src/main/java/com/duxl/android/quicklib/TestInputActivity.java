package com.duxl.android.quicklib;

import android.view.View;
import android.widget.TextView;

import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.utils.WindowSoftInputCompat;

/**
 * 键盘遮挡输入框问题
 * create by duxl 2021/2/23
 */
public class TestInputActivity extends BaseActivity {

    protected String msg = "需要设置 android:windowSoftInputMode=\"adjustResize\"\n同时调用 WindowSoftInputCompat.assistActivity(activity)";

    @Override
    protected int getLayoutResId() {
        setTitle("普通Activity");
        return R.layout.activity_test_input;
    }

    @Override
    protected void initView(View v) {
        ((TextView) v.findViewById(R.id.tv_top)).setText(msg);
        WindowSoftInputCompat.assist(this);
    }
}
