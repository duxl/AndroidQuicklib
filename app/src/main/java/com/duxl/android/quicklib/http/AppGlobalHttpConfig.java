package com.duxl.android.quicklib.http;

import com.duxl.baselib.utils.Utils;

import org.apache.http.conn.ssl.X509HostnameVerifier;

import java.io.IOException;
import java.net.Proxy;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
        //解决报错javax.net.ssl.SSLPeerUnverifiedException: Hostname 127.0.0.1 not verified
        //builder.proxy(Proxy.NO_PROXY); // 配置网络直连，不走代理
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                System.out.println("duxl:主机:" + s);
                return true;
            }
        });

        //创建TrustManager
        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        };

        try {
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{xtm}, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory, xtm);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
