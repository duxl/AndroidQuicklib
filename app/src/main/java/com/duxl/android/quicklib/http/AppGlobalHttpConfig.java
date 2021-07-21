package com.duxl.android.quicklib.http;

import com.duxl.baselib.utils.Utils;

import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * AppGlobalHttpConfig
 * create by duxl 2020/8/18
 */
public class AppGlobalHttpConfig extends com.duxl.baselib.http.GlobalHttpConfig {

    @Override
    public String getBaseUrl() {
        // baseUrl必须斜杠结尾
        return "http://rap2api.taobao.org/";
    }

    @Override
    public HashMap<String, String> getHeaders(Interceptor.Chain chain) {
        HashMap<String, String> map = new HashMap<>();
        // 这里可以放一些全局header参数
        map.put("app_version_name", Utils.getVersionName());
        map.put("app_version_code", String.valueOf(Utils.getVersionCode()));
        return map;
    }

    @Override
    public boolean isDEBUG() {
        return true;
    }

    @Override
    public void configurationOKHttp(OkHttpClient.Builder builder) {
        //builder.sslSocketFactory(socketFactory);
        //解决报错javax.net.ssl.SSLPeerUnverifiedException: Hostname 127.0.0.1 not verified
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                System.out.println("duxl:主机:" + s);
                return true;
            }
        });
    }
}
