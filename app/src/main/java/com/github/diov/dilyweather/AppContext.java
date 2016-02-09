package com.github.diov.dilyweather;

import android.app.Application;

import com.github.diov.dilyweather.model.Contants;
import com.github.diov.dilyweather.utils.DbUtil;
import com.github.diov.dilyweather.utils.PreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);
        if (PreferenceUtil.isFirstLogin(this)) {
            EventBus.getDefault().post("LoginStatu");
            PreferenceUtil.writeLoginStatu(this);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void loadSql(String loginStatu) {
        DbUtil.excuteSql(this, Contants.SQL_FILE);
    }
}
