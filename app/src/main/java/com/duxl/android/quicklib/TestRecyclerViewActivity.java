package com.duxl.android.quicklib;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.duxl.android.quicklib.databinding.ActivityTestRecyclerviewBinding;
import com.duxl.android.quicklib.http.HttpService;
import com.duxl.android.quicklib.http.Root;
import com.duxl.baselib.http.BaseRecyclerViewHttpObserver;
import com.duxl.baselib.http.HttpExceptionReasons;
import com.duxl.baselib.http.RetrofitManager;
import com.duxl.baselib.rx.LifecycleTransformer;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.utils.ToastUtils;
import com.duxl.baselib.widget.SimpleOnLoadListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * TestRecyclerViewActivity
 * create by duxl 2020/8/16
 */
public class TestRecyclerViewActivity extends BaseActivity {

    private int pageNum = 1;


    private ActivityTestRecyclerviewBinding mBinding;

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1) {
        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
            baseViewHolder.setText(android.R.id.text1, "【" + getItemPosition(s) + "】" + s);
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_recyclerview;
    }

    @Override
    protected void initView(View v) {
        setTitle("测试RecylcerView分页加载数据");
        mBinding = ActivityTestRecyclerviewBinding.bind(v);
        getActionBarView().setBarBackgroundColor(Color.GREEN);
        getActionBarView().setTitleColor(Color.WHITE);
        getStateBar().setBackgroundColor(Color.GREEN);
        setStateBarLightMode();
        mBinding.smartRecyclerView.getStatusView().setErrorText("怎么又出错啦");

        mBinding.smartRecyclerView.getContentView().setAdapter(mAdapter);
        mBinding.smartRecyclerView.getStatusView().showEmpty();

        mBinding.smartRecyclerView.setOnLoadListener(new SimpleOnLoadListener() {
            @Override
            public void onRefresh() {
                ToastUtils.show("下拉刷新");
                pageNum = 1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                ToastUtils.show("加载更多");
                pageNum++;
                loadData();
            }

            @Override
            public void onErrorClick(int errCode) {
                ToastUtils.show("onErrorClick");
            }

            @Override
            public void onEmptyClick() {
                ToastUtils.show("onEmptyClick");
                loadData();
                mBinding.smartRecyclerView.getStatusView().showContent();
            }
        });
    }

    private void loadData() {
        RetrofitManager
                .getInstance()
                .create(HttpService.class)
                .getList(pageNum, 20)
                .compose(new LifecycleTransformer<Root<List<String>>>(this))
                // 如果刷新和状态view都是页面，这里可以传Activity.this作为参数，第一个参数adapter是必须的，后面两个是可选的
                // 普通接口可以用 BaseHttpObserver
                //.subscribe(new RVHttpObserver<Root<List<String>>, String>(mAdapter, this, this) {
                .subscribe(new BaseRecyclerViewHttpObserver<Root<List<String>>, String>(mAdapter, mBinding.smartRecyclerView, mBinding.smartRecyclerView) {
                    @Override
                    public List<String> getListData(Root<List<String>> root) {
                        return root.data;
                    }

                    @Override
                    public boolean isFirstPage() {
                        return pageNum == 1;
                    }

                    @Override
                    public boolean hasMoreData(Root<List<String>> root) {
                        return pageNum < 3;
                    }

                    @Override
                    public void onError(int code, String msg, Root<List<String>> root) {
                        if (code == HttpExceptionReasons.CONNECT_NO.getCode()) {
                            // 这里可以特殊处理，比如弹框去设置打开wifi等
                        } else {
                            super.onError(code, msg, root);
                        }
                    }
                });
    }

}
