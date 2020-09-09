package com.saltykitkat.myweather;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather {
    private String sourceName;
    private String sourceUrl = "http://wthrcdn.etouch.cn/WeatherApi?citykey=";
    private String cityCode;
    public Result result;

    public void setSource(String name, String url) {
        sourceName = name;
        sourceUrl = url;
    }

    public void setCity(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCity() {
        return cityCode;
    }

    public Weather(String cityCode) {
        setCity(cityCode);
    }

    public Result query() {
        final String address = sourceUrl + cityCode;
        Log.d("myWeather", "getting data from " + address);
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            InputStream inStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
            StringBuilder response = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                response.append(str);
            }
            connection.disconnect();
            return new WeatherXMLParser(response.toString()).result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
