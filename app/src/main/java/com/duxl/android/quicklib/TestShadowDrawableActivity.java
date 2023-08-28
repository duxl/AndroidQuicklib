package com.duxl.android.quicklib;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.utils.MaterialUtils;

/**
 * 测试阴影
 */
public class TestShadowDrawableActivity extends BaseActivity {

    private ImageView ivImg;

    private TextView tvElevation;
    private SeekBar seekElevation;
    private int currentElevation;

    private TextView tvCornerSizes;
    private SeekBar seekCornerSizes;
    private int currentCornerSizes;

    private int currentFillColor = Color.RED;
    private int currentShadowColor = Color.BLUE;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_shadow_drawable;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("测试阴影");
        ivImg = v.findViewById(R.id.iv_img);

        // 阴影高度
        tvElevation = v.findViewById(R.id.tv_elevation);
        seekElevation = v.findViewById(R.id.seek_elevation);
        currentElevation = seekElevation.getProgress();
        seekElevation.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvElevation.setText("elevation(" + progress + "px)");
                currentElevation = progress;
                resetBg();
            }
        });

        // 圆角大小
        tvCornerSizes = v.findViewById(R.id.tv_cornerSizes);
        seekCornerSizes = v.findViewById(R.id.seek_cornerSizes);
        currentCornerSizes = seekCornerSizes.getProgress();
        seekCornerSizes.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvCornerSizes.setText("cornerSizes(" + progress + "px)");
                currentCornerSizes = progress;
                resetBg();
            }
        });

        // 填充颜色
        v.<RadioGroup>findViewById(R.id.group_fill_color).setOnCheckedChangeListener((group, checkedId) -> {
            for (int i = 0; i < group.getChildCount(); i++) {
                if(group.getChildAt(i).getId() == checkedId) {
                    currentFillColor = ((RadioButton)group.getChildAt(i)).getCurrentTextColor();
                    resetBg();
                    break;
                }
            }
        });

        // 阴影颜色
        v.<RadioGroup>findViewById(R.id.group_shadow_color).setOnCheckedChangeListener((group, checkedId) -> {
            for (int i = 0; i < group.getChildCount(); i++) {
                if(group.getChildAt(i).getId() == checkedId) {
                    currentShadowColor = ((RadioButton)group.getChildAt(i)).getCurrentTextColor();
                    resetBg();
                    break;
                }
            }
        });

        resetBg();
    }

    private void resetBg() {
        ivImg.setBackground(MaterialUtils.getShadowDrawable(getContext(), currentFillColor, currentShadowColor, currentElevation, currentCornerSizes));
    }

    private static class SimpleOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
