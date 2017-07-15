package com.toosame.weather.utils;

/**
 * Created by Administrator on 2017/4/27.
 */

public interface IHttpCallback {
    <T> void onSuccess(T result,boolean isSuccess);
}
