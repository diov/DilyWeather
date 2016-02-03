package com.github.diov.dilyweather.utils;

import com.github.diov.dilyweather.AppContext;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * Description: 连接网络的工具类
 * <p/>
 * Created by dio_v on 上午11:07.
 */
public class NetworkUtil {

    public static void get(String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();

        AppContext.getClient().newCall(request).enqueue(callback);
    }
}
