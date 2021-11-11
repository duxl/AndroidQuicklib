package com.duxl.android.quicklib;

import android.view.View;
import android.widget.TextView;

import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.widget.HeartbeatTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

/**
 * create by duxl 2021/11/11
 */
public class TestWidgetActivity extends BaseActivity {

    @BindView(R.id.tv_heartbeat)
    HeartbeatTextView mTvHeartbeat;


    private int count;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_widget;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("自定义控件示例");

        mTvHeartbeat.setInterval(1, TimeUnit.SECONDS); // 设置心跳间隔1秒
        mTvHeartbeat.setOnHeartbeatListener(v1 -> {
            count++;
            mTvHeartbeat.setText("心跳TextView: " + count);
        });
    }
}
