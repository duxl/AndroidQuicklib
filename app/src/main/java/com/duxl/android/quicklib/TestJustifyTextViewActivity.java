package com.duxl.android.quicklib;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.widget.JustifyTextView;

/**
 * 测试文字分散对齐
 */
public class TestJustifyTextViewActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_justify_text;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("两端分散对齐");
    }

    /**
     * 其它TextView需要使用分散对齐的功能示例
     */
    public static class OtherTextView extends AppCompatTextView {
        public OtherTextView(Context context) {
            super(context, null);
        }

        public OtherTextView(Context context, @Nullable AttributeSet attrs) {
            this(context, attrs, android.R.attr.textViewStyle);
        }

        public OtherTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (!JustifyTextView.justifyDraw(canvas, this, false)) {
                super.onDraw(canvas);
            }
        }
    }
}
