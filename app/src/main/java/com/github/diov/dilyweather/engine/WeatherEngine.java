package com.github.diov.dilyweather.engine;

import com.github.diov.dilyweather.model.Contants;
import com.github.diov.dilyweather.model.WeatherModel;
import com.github.diov.dilyweather.utils.NetworkUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Description:
 * <p/>
 * Created by dio_v on 下午4:13.
 */
public class WeatherEngine {
    public static void checkWeather() {
        String url = Contants.URL + "?city=shanghai" + "&key=" + Contants.APIkey;
        NetworkUtil.get(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                body = body.replace("HeWeather data service 3.0", "data");
                Gson gson = new Gson();
                WeatherModel result = gson.fromJson(body, WeatherModel.class);
                EventBus.getDefault().post(result);
            }
        });
    }
}
