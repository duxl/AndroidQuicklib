package com.duxl.android.quicklib;

import android.view.View;
import android.widget.TextView;

import com.duxl.android.quicklib.databinding.ActivityTestHttpRequestBinding;
import com.duxl.android.quicklib.http.HttpService;
import com.duxl.android.quicklib.http.Root;
import com.duxl.baselib.download.DownLoadManager;
import com.duxl.baselib.http.BaseHttpObserver;
import com.duxl.baselib.http.RetrofitManager;
import com.duxl.baselib.rx.LifecycleTransformer;
import com.duxl.baselib.rx.SimpleObserver;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.widget.SimpleOnLoadListener;
import com.google.gson.Gson;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;

import java.io.File;
import java.util.LinkedHashMap;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * TestHttpRequestActivity
 * create by duxl 2020/8/18
 */
public class TestHttpRequestActivity extends BaseActivity {

    private ActivityTestHttpRequestBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_http_request;
    }

    @Override
    protected void initView(View v) {
        setTitle("测试Http请求");
        mBinding = ActivityTestHttpRequestBinding.bind(v);
        mBinding.btnDown.setOnClickListener(this::onClickView);
        mBinding.btnGet.setOnClickListener(this::onClickView);
        mBinding.btnPost.setOnClickListener(this::onClickView);
        setOnLoadListener(new SimpleOnLoadListener() {
            @Override
            public void onErrorClick(int errCode) {
                getStatusView().showContent();
            }
        });
    }

    public void onClickView(View v) {
        if (v.getId() == R.id.btn_get) {
            getRequest();
        } else if (v.getId() == R.id.btn_post) {
            postRequest();
        } else if (v.getId() == R.id.btn_down) {
            testDown();
        }
    }

    private void testDown() {
        String url1 = "http://league.qianqianshijie.com/releases/league_latest.apk";
        String url2 = "https://hidao.pro/releases/hidao_latest.apk";
        DownLoadManager
                .getInstance()
                .downloadApk(url2, new File(DownLoadManager.EXTERNAL_FILE_DIR, System.currentTimeMillis() + "_.apk").getPath(), new FileDownloadLargeFileListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        System.out.println("duxl: pending# " + soFarBytes + "/" + totalBytes);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        System.out.println("duxl: progress# " + soFarBytes + "/" + totalBytes);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        System.out.println("duxl: completed");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        System.out.println("duxl: error# " + e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        System.out.println("duxl: warn");
                    }
                });
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
//                        mBinding.tvResponse.setText(new Gson().toJson(data));
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
//                        mBinding.tvResponse.setText(new Gson().toJson(data));
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
                        mBinding.tvResponse.setText(new Gson().toJson(root));
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
                        mBinding.tvResponse.setText(s.toString());
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
