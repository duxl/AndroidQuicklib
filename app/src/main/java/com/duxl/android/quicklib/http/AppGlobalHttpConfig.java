package com.duxl.android.quicklib.http;

import com.duxl.baselib.utils.Utils;

import java.util.HashMap;

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
    public HashMap<String, String> getHeaders() {
        HashMap<String, String> map = new HashMap<>();
        // 这里可以放一些全局header参数
        map.put("app_version_name", Utils.getVersionName());
        map.put("app_version_code", String.valueOf(Utils.getVersionCode()));
        return map;
    }
}
