package com.github.diov.dilyweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.diov.dilyweather.model.WeatherModel;
import com.google.gson.Gson;

/**
 * Description:
 * <p/>
 * Created by dio_v on 上午10:33.
 */
public class PreferenceUtil {

    public static final String FIRST_LOGIN = "is_first_login";
    private static final String WEATHER_PREFERENCES = "DilyWeatherPreferences";
    public static final String PREFERENCE_LOCAL = WEATHER_PREFERENCES + ".local";
    private static final String PREFERENCE_DILY = WEATHER_PREFERENCES + ".dily";

    private PreferenceUtil() {
        //no instance
    }

    public static void writeLoginStatu(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(FIRST_LOGIN, false);
        editor.apply();
    }

    public static void writeWeather(Context context, String dilyWeather) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(PREFERENCE_DILY, dilyWeather);
        editor.apply();
    }

    public static void writeLocal(Context context, String local) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(PREFERENCE_LOCAL, local);
        editor.apply();
    }

    public static WeatherModel getWeather(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        final String dilyWeather = preferences.getString(PREFERENCE_DILY, null);
        final WeatherModel weatherModel;
        Gson gson = new Gson();
        weatherModel = gson.fromJson(dilyWeather, WeatherModel.class);
        return weatherModel;
    }

    public static boolean isFirstLogin(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        final boolean isFirstLogin = preferences.getBoolean(FIRST_LOGIN, true);
        return isFirstLogin;
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(WEATHER_PREFERENCES, Context.MODE_PRIVATE);
    }

}
