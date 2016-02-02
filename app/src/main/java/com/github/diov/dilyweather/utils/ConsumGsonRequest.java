package com.github.diov.dilyweather.utils;

import com.android.volley.Response;
import com.github.diov.dilyweather.model.WeatherModel;

import java.util.Map;

/**
 * Description:
 * <p/>
 * Created by dio_v on 上午9:40.
 */
public class ConsumGsonRequest extends GsonRequest<WeatherModel> {
    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url           URL of the request to make
     * @param clazz         Relevant class object, for Gson's reflection
     * @param headers       Map of request headers
     * @param listener
     * @param errorListener
     */
    public ConsumGsonRequest(String url, Class<WeatherModel> clazz, Map<String, String> headers, Response.Listener
            <WeatherModel> listener, Response.ErrorListener errorListener) {
        super(url, clazz, headers, listener, errorListener);
    }

    @Override
    public String parseJson(String json) {
        return json.replace("HeWeather data service 3.0", "data");
    }
}
