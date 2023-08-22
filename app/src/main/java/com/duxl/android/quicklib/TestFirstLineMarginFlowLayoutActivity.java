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

    private FirstLineMarginFlowLayout mMarginFlowLayout;
    private String[] items = {"首行左右间距示例", "First", "Margin", "FlowLayout"};

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_first_line_margin_flow_layout;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        mMarginFlowLayout = findViewById(R.id.firstLineMarginFlowLayout);
        findViewById(R.id.btn_add_view).setOnClickListener(btnAddView -> {
            TextView tv = new TextView(this);
            tv.setText(items[new Random().nextInt(items.length)]);
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundColor(Color.RED);
            tv.setPadding(30, 10, 30, 10);
            mMarginFlowLayout.addView(tv);
        });
        findViewById(R.id.btn_clear_view).setOnClickListener(btnClearView -> mMarginFlowLayout.removeAllItemViews());

        mMarginFlowLayout.setOnLeftViewClickListener(leftView -> {
            ToastUtils.show("筛选");
        });

        mMarginFlowLayout.setOnRightViewClickListener(rightView -> {
            ToastUtils.show("保存");
        });
    }
}
