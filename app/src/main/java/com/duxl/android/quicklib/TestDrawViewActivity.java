package com.duxl.android.quicklib;

import android.graphics.Color;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.duxl.android.quicklib.databinding.ActivityTestDrawViewBinding;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.utils.EmptyUtils;
import com.duxl.baselib.utils.SPUtils;
import com.duxl.baselib.utils.ToastUtils;
import com.duxl.baselib.widget.drawview.DrawData;
import com.duxl.baselib.widget.drawview.DrawView;
import com.google.gson.Gson;

/**
 * 测试绘图
 */
public class TestDrawViewActivity extends BaseActivity {

    protected ActivityTestDrawViewBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_draw_view;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("绘图");

        mBinding = ActivityTestDrawViewBinding.bind(v);
        mBinding.btnReset.setOnClickListener(it -> {
            mBinding.drawView.reset();
        });
        mBinding.btnRevoke.setOnClickListener(it -> {
            mBinding.drawView.revoke();
        });

        mBinding.btnSave.setOnClickListener(it -> {
            DrawData drawData = mBinding.drawView.getDrawData();
            SPUtils.getInstance().put("drawData", new Gson().toJson(drawData));
            ToastUtils.show("已保存");
        });

        mBinding.btnRestore.setOnClickListener(it -> {
            String json = SPUtils.getInstance().getString("drawData");
            if (EmptyUtils.isEmpty(json)) {
                ToastUtils.show("没有绘图数据");
            } else {
                DrawData drawData = new Gson().fromJson(json, DrawData.class);
                boolean result = mBinding.drawView.restoreDrawData(drawData);
                if (result) {
                    ToastUtils.show("已恢复");
                } else {
                    ToastUtils.show("恢复失败");
                }
            }
        });

        // 初始化一些参数
        mBinding.drawView.setPaintColor(Color.BLUE);
        mBinding.drawView.setPaintWidth(mBinding.seekbarPaintWidth.getProgress());

        mBinding.groupColor.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbtn_red) {
                mBinding.drawView.setPaintColor(Color.RED);
            } else if (checkedId == R.id.rbtn_green) {
                mBinding.drawView.setPaintColor(Color.GREEN);
            } else if (checkedId == R.id.rbtn_blue) {
                mBinding.drawView.setPaintColor(Color.BLUE);
            }
            mBinding.drawView.invalidate();
        });

        mBinding.seekbarPaintWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBinding.drawView.setPaintWidth(progress);
                mBinding.drawView.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mBinding.drawView.setOnChangedListener(view -> {
            if (view.canRevoke()) {
                mBinding.btnReset.setEnabled(true);
                mBinding.btnRevoke.setEnabled(true);
            } else {
                mBinding.btnReset.setEnabled(false);
                mBinding.btnRevoke.setEnabled(false);
            }
        });
    }
}