package com.github.diov.dilyweather.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Description:
 * <p/>
 * Created by dio_v on 上午11:51.
 */
public class DbUtil {

    private DbUtil() {
    }

    public static void excuteSql(Context context, String sqlFile) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (!dbHelper.tabIsExist("city", db)) {
            BufferedReader in = null;
            AssetManager assetManager = null;
            try {
                assetManager = context.getAssets();
                in = new BufferedReader(new InputStreamReader(assetManager.open(sqlFile)));

                String line;
                String buffer = "";
                while ((line = in.readLine()) != null) {
                    buffer += line;
                    if (line.trim().endsWith(";")) {
                        db.execSQL(buffer.replace(";", ""));
                        buffer = "";
                    }
                }
            } catch (IOException e) {
                Log.e("db-error", e.toString());
            } finally {
                try {
                    if (in != null)
                        in.close();
                } catch (IOException e) {
                    Log.e("db-error", e.toString());
                }
                assetManager.close();
            }
        }
    }
}
