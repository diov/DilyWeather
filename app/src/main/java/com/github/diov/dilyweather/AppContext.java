package com.github.diov.dilyweather;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Description: Base Application
 * <p/>
 * Created by dio_v on 上午9:21.
 */
public class AppContext extends Application {
    public static final String TAG = AppContext.class.getSimpleName();

    private RequestQueue mQueue;
    private static AppContext mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static AppContext getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (null == mQueue) {
            mQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        // set the default tag if tag is empty
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object tag) {
        if (mQueue != null) {
            mQueue.cancelAll(tag);
        }
    }
}
