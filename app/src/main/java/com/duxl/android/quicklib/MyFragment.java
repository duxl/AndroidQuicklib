package com.duxl.android.quicklib;

import android.view.View;

import com.duxl.baselib.ui.fragment.BaseFragment;
import com.duxl.baselib.widget.SimpleOnLoadListener;

/**
 * MyFragment
 * create by duxl 2020/8/16
 */
public class MyFragment extends BaseFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View v) {
        setTitle("测试Fragment");
        showActionBar();
        setEnableRefresh(true);
        setOnLoadListener(new SimpleOnLoadListener() {
            @Override
            public void onRefresh() {
                v.postDelayed(() -> finishRefresh(), 1000);
            }
        });
    }
}
