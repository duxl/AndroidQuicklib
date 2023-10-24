package com.duxl.android.quicklib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.SeekBar;

import com.duxl.android.quicklib.databinding.ActivityTestBlurBinding;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.utils.ImageUtils;

/**
 * 测试高斯模糊
 */
public class TestBlurActivity extends BaseActivity {

    private ActivityTestBlurBinding binding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_blur;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("高斯模糊");
        binding = ActivityTestBlurBinding.bind(v);
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeBlur(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void changeBlur(int radios) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.city_cq);
        if (radios > 0) {
            bitmap = ImageUtils.blurBitmap(this, bitmap, radios);
        }
        binding.ivImg.setImageBitmap(bitmap);
    }
}
