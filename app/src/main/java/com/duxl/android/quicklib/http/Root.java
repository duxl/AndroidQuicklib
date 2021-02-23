package com.duxl.android.quicklib.http;

import com.duxl.baselib.http.BaseRoot;

/**
 * Root
 * create by duxl 2020/8/18
 */
public class Root<T> implements BaseRoot {

    public int code;

    public String msg;

    public T data;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public boolean isSuccess() {
        return getCode() == 100;
    }

}
