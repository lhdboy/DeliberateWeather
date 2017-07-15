package com.toosame.weather.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Administrator on 2017/4/27.
 */

public class HttpClient {
    public static final String WEATHER_TYPE_BASE = "base";
    public static final String WEATHER_TYPE_ALL = "all";
    private static Gson jsonConvert = new Gson();

    private static OkHttpClient okHttpClient = new OkHttpClient();

    private static final String SERVER_HOST = "http://restapi.amap.com/v3/weather/weatherInfo?";

    public static <T> void query(String adcode, String type, final Class<T> tClass, final IHttpCallback callback){
        //http://lbs.amap.com/api/webservice/guide/api/weatherinfo
        String parameters = "key=*******你的web服务API类型KEY********"
                + "&city=" + adcode
                + "&extensions=" + type
                + "&output=JSON";

        Request request = new Request.Builder()
                .url(SERVER_HOST + parameters)
                .get()
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callback.onSuccess(null,false);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String jsonData = response.body().string();
                Log.d("TAG", "onResponse: " + jsonData);
                T result = jsonConvert.fromJson(jsonData,tClass);
                callback.onSuccess(result,true);
            }
        });
    }
}
