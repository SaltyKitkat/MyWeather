package com.saltykitkat.myweather;

import android.app.Application;

public class WeatherApp extends Application {
    private static final String TAG = "myweather";
    private static WeatherApp app;

    public static WeatherApp getInstance(){
        return app;
    }
}
