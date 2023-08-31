package com.duxl.android.quicklib;

import android.view.View;

import com.duxl.android.quicklib.databinding.ActivityTestWidgetBinding;
import com.duxl.baselib.ui.activity.BaseActivity;

import java.util.concurrent.TimeUnit;

/**
 * create by duxl 2021/11/11
 */
public class TestWidgetActivity extends BaseActivity {


    private ActivityTestWidgetBinding mBinding;

    private int count;
    private int duration = 1000;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_widget;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("自定义控件示例");
        mBinding = ActivityTestWidgetBinding.bind(v);

        mBinding.tvHeartbeat.setInterval(duration, TimeUnit.MILLISECONDS); // 设置心跳间隔1秒
        mBinding.tvHeartbeat.setOnHeartbeatListener(v1 -> {
            count++;
            mBinding.tvHeartbeat.setText("心跳TextView:间隔= " + duration + "毫秒, count=" + count);
        });

        mBinding.btnDel.setOnClickListener(btnDel -> {
            duration += 50;
            mBinding.tvHeartbeat.setInterval(duration, TimeUnit.MILLISECONDS);
        });

        mBinding.btnAdd.setOnClickListener(btnAdd -> {
            if (duration > 0) {
                duration -= 50;
                mBinding.tvHeartbeat.setInterval(duration, TimeUnit.MILLISECONDS);
            }
        });
    }
}
