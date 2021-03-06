package com.duxl.android.quicklib;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.duxl.baselib.ui.activity.BaseActivity;
import com.duxl.baselib.ui.fragment.webview.BaseWebFragment;
import com.duxl.baselib.utils.ToastUtils;
import com.duxl.baselib.widget.BaseJSInterface;

/**
 * create by duxl 2021/5/18
 */
public class TestWebActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_web;
    }

    @Override
    protected void initView(View v) {
        hideStateBar();
        hideActionBar();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, WebFragment.newInstance(null, "file:///android_asset/DemoCallWebWebApi.html", null));
        transaction.commit();
    }

    public static class WebFragment extends BaseWebFragment {
        public static WebFragment newInstance(String title, String url, String content) {
            Bundle args = new Bundle();
            args.putString("title", title);
            args.putString("url", url);
            args.putString("content", content);
            WebFragment fragment = new WebFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        protected String getUserAgentString(WebSettings webSettings) {
            String userAgent = webSettings.getUserAgentString();
            userAgent += " AndroidDemoApp";
            return userAgent;
        }

        @Override
        protected void setJavascriptInterface(WebView webView) {
            webView.addJavascriptInterface(new JSInterface(), "AppAndroid");
        }

        /**
         * 继承BaseJSInterface可以扩展api
         */
        class JSInterface extends BaseJSInterface {

            @Override
            protected BaseActivity getWebActivity() {
                return (BaseActivity) getActivity();
            }

            @Override
            protected com.duxl.baselib.ui.fragment.BaseFragment getWebFragment() {
                return WebFragment.this;
            }

            @Override
            protected void loadImage(ImageView view, String url) {
                Glide.with(view).load(url).into(view);
            }

            /**
             * 扩充方法
             */
            @JavascriptInterface
            public void sayHello() {
                runOnUiThread(() -> {
                    ToastUtils.show("hello，我是自定义方法");
                });
            }
        }

    }
}
