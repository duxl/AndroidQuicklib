package com.duxl.android.quicklib;

import android.view.View;

import com.duxl.android.quicklib.databinding.ActivityTestRxBinding;
import com.duxl.baselib.rx.LifecycleTransformer;
import com.duxl.baselib.ui.activity.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * TestRxActivity
 * create by duxl 2020/8/16
 */
public class TestRxActivity extends BaseActivity {

    private ActivityTestRxBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_rx;
    }

    @Override
    protected void initView(View v) {
        setTitle("TestRxActivity");
        mBinding = ActivityTestRxBinding.bind(v);

        Observable
                .interval(500, TimeUnit.MILLISECONDS)
                //.compose(bindUntilEvent(ActivityEvent.DESTROY))
                .compose(new LifecycleTransformer<Long>(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("duxl.debug:: onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        mBinding.tvText.setText(String.valueOf(aLong));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("duxl.debug:: onComplete");
                    }
                });
    }
}
