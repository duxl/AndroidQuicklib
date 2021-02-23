package com.duxl.android.quicklib;

import android.view.View;
import android.widget.TextView;

import com.duxl.android.quicklib.http.HttpService;
import com.duxl.android.quicklib.http.Root;
import com.duxl.baselib.http.BaseHttpObserver;
import com.duxl.baselib.http.RetrofitManager;
import com.duxl.baselib.rx.LifecycleTransformer;
import com.duxl.baselib.rx.SimpleObserver;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.widget.SimpleOnLoadListener;
import com.google.gson.Gson;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * TestHttpRequestActivity
 * create by duxl 2020/8/18
 */
public class TestHttpRequestActivity extends BaseActivity {

    @BindView(R.id.tv_response)
    TextView mTvResponse;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_http_request;
    }

    @Override
    protected void initView(View v) {
        setTitle("测试Http请求");
        setOnLoadListener(new SimpleOnLoadListener() {
            @Override
            public void onErrorClick(int errCode) {
                getStatusView().showContent();
            }
        });
    }

    @OnClick({R.id.btn_get, R.id.btn_post})
    public void onClickView(View v) {
        if (v.getId() == R.id.btn_get) {
            getRequest();
        } else if (v.getId() == R.id.btn_post) {
            postRequest();
        }
    }


//    private void getRequest() {
//        RetrofitManager
//                .getInstance()
//                .create(HttpService.class)
//                .getServerUrlConfig()
//                .compose(new LifecycleTransformer<Root<LinkedHashMap<String, String>>>(this))
//                .subscribe(new Observer<Root<LinkedHashMap<String, String>>>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        getStatusView().showLoading();
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Root<LinkedHashMap<String, String>> data) {
//                        mTvResponse.setText(new Gson().toJson(data));
//                        getStatusView().showContent();
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        getStatusView().setErrorText(e.getMessage());
//                        getStatusView().showError();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//
//    }

    private void getRequest() {
        RetrofitManager
                .getInstance()
                .create(HttpService.class)
                .getServerUrlConfig()
                .compose(new LifecycleTransformer<Root<LinkedHashMap<String, String>>>(this))
                .subscribe(new BaseHttpObserver<Root<LinkedHashMap<String, String>>>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        getStatusView().showLoading();
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Root<LinkedHashMap<String, String>> data) {
//                        mTvResponse.setText(new Gson().toJson(data));
//                        getStatusView().showContent();
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        getStatusView().setErrorText(e.getMessage());
//                        getStatusView().showError();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getStatusView().showLoading();
                    }

                    @Override
                    public void onSuccess(Root<LinkedHashMap<String, String>> root) {
                        mTvResponse.setText(new Gson().toJson(root));
                        getStatusView().showContent();
                    }

                    @Override
                    public void onError(int code, String msg, Root<LinkedHashMap<String, String>> root) {
                        //super.onError(code, msg, root);
                        getStatusView().setErrorText(msg);
                        getStatusView().showError(code);
                    }
                });

    }

    private void postRequest() {
        RetrofitManager
                .getInstance()
                .create(HttpService.class)
                .postTest("001", 2)
                .compose(new LifecycleTransformer<Object>(this))
                .subscribe(new SimpleObserver<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getStatusView().showLoading();
                    }

                    @Override
                    public void onNext(@NonNull Object s) {
                        mTvResponse.setText(s.toString());
                        getStatusView().showContent();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getStatusView().setErrorText(e.getMessage());
                        getStatusView().showError(0);
                    }
                });
    }
}
