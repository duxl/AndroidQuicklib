package com.duxl.android.quicklib;

import android.content.Intent;
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
import com.duxl.baselib.utils.EmptyUtils;
import com.duxl.baselib.utils.ToastUtils;
import com.duxl.baselib.utils.Utils;
import com.duxl.baselib.widget.BaseJSInterface;

import java.text.MessageFormat;

/**
 * create by duxl 2021/5/18
 */
public class TestWebActivity extends BaseActivity {

    private String mUrl;
    private String mTitle;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test_web;
    }

    @Override
    protected void initParams(Intent args) {
        super.initParams(args);
        mUrl = args.getStringExtra("url");
        mTitle = args.getStringExtra("title");
    }

    @Override
    protected void initView(View v) {
        hideStateBar();
        hideActionBar();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (EmptyUtils.isNotEmpty(mUrl)) {
            transaction.add(R.id.container, WebFragment.newInstance(mTitle, mUrl, null));
        } else {
            transaction.add(R.id.container, WebFragment.newInstance(null, "file:///android_asset/DemoCallWebWebApi.html?js_file=app.js", null));
        }
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
            userAgent += MessageFormat.format(" AndroidDemoApp/{0}/{1}", Utils.getVersionCode(), Utils.getVersionName());
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
            protected WebFragment getWebFragment() {
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
