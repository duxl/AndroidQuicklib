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
 * 带三角尖的图
 */
public class TestTriangleDrawableActivity extends BaseActivity {

    private ImageView ivImg;

    private TextView tvTriangleSizes;
    private SeekBar seekTriangleSizes;
    private int currentTriangleSizes;

    private TextView tvTriangleOffset;
    private SeekBar seekTriangleOffset;
    private int currentTriangleOffset;

    private TextView tvCornerSizes;
    private SeekBar seekCornerSizes;
    private int currentCornerSizes;

    private int currentFillColor = Color.RED;
    private boolean inside = true;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_triangle_drawable;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("测试阴影");
        ivImg = v.findViewById(R.id.iv_img);

        // 三角尖的大小
        tvTriangleSizes = v.findViewById(R.id.tv_triangle);
        seekTriangleSizes = v.findViewById(R.id.seek_triangle);
        currentTriangleSizes = seekTriangleSizes.getProgress();
        seekTriangleSizes.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTriangleSizes.setText("triangleSize(" + progress + "px)");
                currentTriangleSizes = progress;
                resetBg();
            }
        });

        // 三角尖的偏移量
        tvTriangleOffset = v.findViewById(R.id.tv_offset);
        seekTriangleOffset = v.findViewById(R.id.seek_offset);
        currentTriangleOffset = seekTriangleSizes.getProgress();
        seekTriangleOffset.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTriangleOffset.setText("triangleOffset(" + progress + "%)");
                currentTriangleOffset = progress;
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
                tvCornerSizes.setText("cornerSizes(" + progress + "%)");
                currentCornerSizes = progress;
                resetBg();
            }
        });

        // 填充颜色
        v.<RadioGroup>findViewById(R.id.group_fill_color).setOnCheckedChangeListener((group, checkedId) -> {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    currentFillColor = ((RadioButton) group.getChildAt(i)).getCurrentTextColor();
                    resetBg();
                    break;
                }
            }
        });

        // 内外
        v.<RadioGroup>findViewById(R.id.group_inside).setOnCheckedChangeListener((group, checkedId) -> {
            inside = checkedId == R.id.radio_in;
            resetBg();
        });

        resetBg();
    }

    private void resetBg() {
        float triangleOffsetPx = -ivImg.getWidth() / 2 + currentTriangleOffset / 100f * ivImg.getWidth();
        float cornerSizesPx = ivImg.getHeight() / 2f * currentCornerSizes / 100f;
        ivImg.setBackground(MaterialUtils.getTriangleDrawable(currentFillColor, currentTriangleSizes, (int) triangleOffsetPx, cornerSizesPx, inside));
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
