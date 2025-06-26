package com.duxl.android.quicklib;

import android.view.View;

import com.duxl.android.quicklib.databinding.ActivityTestAppStatusViewBinding;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.widget.SimpleOnLoadListener;

/**
 * 测试AppStatusView
 */
public class TestAppStatusViewActivity extends BaseActivity {

    private ActivityTestAppStatusViewBinding binding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_app_status_view;
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        setTitle("测试AppStatusView");
        binding = ActivityTestAppStatusViewBinding.bind(v);
        binding.btnEmpty.setOnClickListener(v1 -> {
            getStatusView().showEmpty();
        });
        binding.btnLoading.setOnClickListener(v1 -> {
            getStatusView().showLoading();
        });
        getStatusView().setEmptyVisibility(0, 0, 0);
        getStatusView().setEmptyText("整个页面empty状态");
        getStatusView().setEmptyBtnText("显示Content");

        getStatusView().setLoadingVisibility(0, 0, 0);
        getStatusView().setLoadingText("整个页面loading状态");
        getStatusView().setLoadingBtnText("显示Content");
        setOnLoadListener(new SimpleOnLoadListener() {
            @Override
            public void onEmptyClick() {
                super.onEmptyClick();
                getStatusView().showContent();
                binding.simpleStatusView.showContent();
            }

            @Override
            public void onLoadingClick() {
                super.onLoadingClick();
                getStatusView().showContent();
                binding.simpleStatusView.showContent();
            }
        });


        // 页面部分状态
        binding.btnEmpty2.setOnClickListener(v1 -> binding.simpleStatusView.showEmpty());
        binding.btnLoading2.setOnClickListener(v1 -> binding.simpleStatusView.showLoading());
        binding.btnContent2.setOnClickListener(v1 -> binding.simpleStatusView.showContent());
        binding.simpleStatusView.setOnLoadListener(new SimpleOnLoadListener() {
            @Override
            public void onEmptyClick() {
                super.onEmptyClick();
                binding.btnContent2.callOnClick();
            }
        });

    }
}
