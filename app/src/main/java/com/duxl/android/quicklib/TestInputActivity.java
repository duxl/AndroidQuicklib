package com.duxl.android.quicklib;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.utils.DisplayUtil;
import com.duxl.baselib.utils.ViewClickDelayUtil;
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
        WindowSoftInputCompat.assistV2(this, new WindowSoftInputCompat.OnHeightChangeListener() {
            @Override
            public void onHeightChanged(int softHeight) {
                Log.i("duxl.log", "软键盘的高度：" + softHeight);
            }

            @Override
            public void onStart(boolean targetVisible, int fullHeight) {
                super.onStart(targetVisible, fullHeight);
                if (targetVisible) {
                    Log.i("duxl.log", "显示软键盘开始");
                } else {
                    Log.i("duxl.log", "隐藏软键盘开始");
                }
            }

            @Override
            public void onEnd(boolean isVisible) {
                super.onEnd(isVisible);
                if(isVisible) {
                    Log.i("duxl.log", "显示软键盘结束");
                } else {
                    Log.i("duxl.log", "隐藏软键盘结束");
                }
            }
        });

        handKeyboardVisibleChanged();
    }

    /**
     * 检测软键盘显示与隐藏
     */
    private void handKeyboardVisibleChanged() {
        View vTop = findViewById(R.id.tv_top);
        ViewClickDelayUtil.setListener(vTop, v -> {
            Log.i("duxl.log", "软键盘是否显示方法1: " + DisplayUtil.isKeyboardShow(TestInputActivity.this));
            Log.i("duxl.log", "软键盘是否显示方法2: " + DisplayUtil.isKeyboardAcceptingText(TestInputActivity.this));
            Log.i("duxl.log", "软键盘是否作用与第一个输入框: " + DisplayUtil.isKeyboardShow(TestInputActivity.this, findViewById(R.id.edit01)));
        });

        DisplayUtil.observerKeyboardVisibleChanged(vTop, isVisible -> Log.i("duxl.log", "监听到软键盘显示或隐藏: isVisible=" + isVisible));
    }
}
