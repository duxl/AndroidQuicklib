package com.duxl.android.quicklib;

import android.view.View;
import android.widget.ImageView;

import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.widget.LayerLayout;

/**
 * 叠放Layer布局示例
 */
public class TestLayerLayoutActivity extends BaseActivity {

    private LayerLayout mLayerLayout;
    private int bgs[] = {
            R.mipmap.ic_launcher_round,
            R.drawable.shape_round_red,
            R.drawable.shape_round_green,
            R.drawable.shape_round_blue
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_layerlayout;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("叠放Layer布局示例");

        mLayerLayout = v.findViewById(R.id.layer_layout);
        v.findViewById(R.id.btn_add).setOnClickListener(btnAdd -> {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(bgs[mLayerLayout.getChildCount() % bgs.length]);
            mLayerLayout.addView(imageView);
        });
        v.findViewById(R.id.btn_revert).setOnClickListener(btnRevert->{
            mLayerLayout.setItemReverse(!mLayerLayout.isItemReverse());
        });
    }
}
