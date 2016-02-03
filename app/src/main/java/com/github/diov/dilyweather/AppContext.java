package com.github.diov.dilyweather;

import android.app.Application;

import okhttp3.OkHttpClient;

/**
 * Description: Base Application
 * <p/>
 * Created by dio_v on 上午9:21.
 */
public class AppContext extends Application {

    private static OkHttpClient okHttpClient;

    public static OkHttpClient getClient() {
        return okHttpClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (null == okHttpClient) {
            okHttpClient = new OkHttpClient();
        }
    }
}
