package com.github.diov.dilyweather.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 * <p/>
 * Created by dio_v on 上午11:31.
 */
public class TimeUtil {
    public static final int DAY = 1;
    public static final int NIGHT = 2;

    public static int isDayorNight() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hour = sdf.format(new Date());
        int k = Integer.parseInt(hour);
        if ((k >= 0 && k < 6) || (k >= 18 && k < 24)) {
            return NIGHT;
        } else {
            return DAY;
        }
    }
}
