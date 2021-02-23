package com.duxl.android.quicklib;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.utils.ToastUtils;
import com.duxl.baselib.widget.SimpleOnLoadListener;

import java.util.Random;

import butterknife.OnClick;

/**
 * TestActivityActivity
 * create by duxl 2020/8/15
 */
public class TestActivityActivity extends BaseActivity {

    private boolean mStatusDark = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("TestActivityActivity");

        setStateBarColor(Color.parseColor("#EEEEEE"));
        setActionBarColor(Color.parseColor("#EEEEEE"));

        setEnableRefresh(true);
        setEnableLoadMore(true);
        setOnLoadListener(new SimpleOnLoadListener() {
            @Override
            public void onRefresh() {
                getActionBarView().postDelayed(() -> {
                    switch (new Random().nextInt(3)) {
                        case 0:
                            setTitle("Demo:0");
                            getStatusView().showEmpty();
                            break;
                        case 1:
                            setTitle("Demo:1");
                            getStatusView().showContent();
                            break;
                        case 2:
                            setTitle("Demo:2");
                            getStatusView().showError(0);
                            break;
                    }

                }, 2000);
            }

            @Override
            public void onLoadMore() {
                onRefresh();
            }

            @Override
            public void onErrorClick(int errCode) {
                ToastUtils.show("onErrorClick");
                getRefreshLayout().autoLoadMore();
            }

            @Override
            public void onEmptyClick() {
                ToastUtils.show("onEmptyClick");
                getRefreshLayout().autoRefresh();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_custom_action_bar;
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7})
    public void onClickView(View v) {
        if (v.getId() == R.id.button1) {
            setTitle("设置一个很长的标题，这个标题确实很长，会怎么样显示呢");

        } else if (v.getId() == R.id.button2) {
            getActionBarView().getIvClose().setVisibility(View.VISIBLE);
            getActionBarView().setBottomLineVisible(View.VISIBLE);
            getActionBarView().resetTitleToCenter();

        } else if (v.getId() == R.id.button3) {
            getActionBarView().setRightText("编辑");
            getActionBarView().getTvRight().setVisibility(View.VISIBLE);
            getActionBarView().getIvRight().setVisibility(View.VISIBLE);
            getActionBarView().resetTitleToCenter();

        } else if (v.getId() == R.id.button4) {
            if (getActionBarView().isShown()) {
                hideActionBar();
            } else {
                showActionBar();
            }

        } else if (v.getId() == R.id.button5) {
            if (getStateBar().isShown()) {
                hideStateBar();
            } else {
                showStateBar();
            }

        } else if (v.getId() == R.id.button6) {
            mStatusDark = !mStatusDark;
            if (mStatusDark) {
                setStateBarDarkMode();
                ToastUtils.show("深色");
            } else {
                setStateBarLightMode();
                ToastUtils.show("浅色");
            }

        } else if (v.getId() == R.id.button7) {
            setActionBarFloat(!isActionBarFloat());
            //getActionBarView().getRlBar().setBackgroundColor(Color.RED);
            getActionBarView().setBarBackgroundColor(Color.RED);
            getStateBar().setBackgroundColor(Color.RED);
            if (isActionBarFloat()) {
                getActionBarView().setAlpha(0.5f);
                getStateBar().setAlpha(0.5f);
            } else {
                getActionBarView().setAlpha(1f);
                getStateBar().setAlpha(1f);
            }
        }
    }

    @Override
    protected void onClickActionBack(View v) {
        super.onClickActionBack(v);
        ToastUtils.show("back");
    }

    @Override
    protected void onClickActionClose(View v) {
        super.onClickActionClose(v);
        ToastUtils.show("onClickClose");
    }

    @Override
    protected void onClickActionTvRight(View v) {
        ToastUtils.show("点击了右边的文字");
    }

    @Override
    protected void onClickActionIvRight(View v) {
        ToastUtils.show("点击了右边的图标");
    }

//    @Override
//    protected IStatusView initStatusView() {
//        IStatusView statusView = super.initStatusView();
//        statusView.setLoadingText("数据加载中");
//        statusView.setLoadingImgRes(R.drawable.ic_action_bar_done);
//
//        statusView.setEmptyImgRes(R.mipmap.ic_launcher);
//        statusView.setEmptyText("空空如也");
//        statusView.setEmptyBtnText("刷新");
//
//        statusView.setErrorImgRes(R.drawable.ic_action_bar_close);
//        statusView.setErrorText("出错啦");
//        statusView.setErrorBtnText("点击重试");
//        return statusView;
//    }
}
