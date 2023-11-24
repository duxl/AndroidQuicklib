package com.duxl.android.quicklib;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.utils.ToastUtils;
import com.duxl.baselib.widget.FirstLineMarginFlowLayout;

import java.util.Random;

/**
 * 首行左右间距示例
 */
public class TestFirstLineMarginFlowLayoutActivity extends BaseActivity {

    private FirstLineMarginFlowLayout mMarginFlowLayout1;
    private FirstLineMarginFlowLayout mMarginFlowLayout2;
    private String[] items = {"内容很长导致崩溃1234567890abcdefghijklmn1234567890abcdefghijklmnOver","首行左右间距示例", "First", "Margin", "FlowLayout", "自定义View", "测量大小", "布局child的位置"};

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_first_line_margin_flow_layout;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("首行可设置margin的FlowLayout");
        mMarginFlowLayout1 = findViewById(R.id.firstLineMarginFlowLayout1);
        mMarginFlowLayout2 = findViewById(R.id.firstLineMarginFlowLayout2);
        findViewById(R.id.btn_add_view).setOnClickListener(btnAddView -> {
            String text = items[new Random().nextInt(items.length)];
            mMarginFlowLayout1.addView(createTextView(text));
            mMarginFlowLayout2.addView(createTextView(text));
        });
        findViewById(R.id.btn_clear_view).setOnClickListener(btnClearView -> mMarginFlowLayout1.removeAllItemViews());

        mMarginFlowLayout1.setOnLeftViewClickListener(leftView -> {
            ToastUtils.show("筛选1");
        });

        mMarginFlowLayout1.setOnRightViewClickListener(rightView -> {
            ToastUtils.show("保存1");
        });

        mMarginFlowLayout2.setOnRightViewClickListener(rightView -> {
            ToastUtils.show("保存2");
        });
    }

    private TextView createTextView(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundColor(Color.RED);
        tv.setPadding(30, 10, 30, 10);
        tv.setOnClickListener(v->ToastUtils.show(text));
        return tv;
    }
}
