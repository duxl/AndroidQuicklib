package com.duxl.android.quicklib;

import com.duxl.android.quicklib.http.AppGlobalHttpConfig;
import com.duxl.baselib.BaseApplication;
import com.duxl.baselib.http.GlobalHttpConfig;

/**
 * create by duxl 2020/8/15
 */
public class MyApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public GlobalHttpConfig getGlobalHttpConfig() {
        return new AppGlobalHttpConfig();
    }
}
