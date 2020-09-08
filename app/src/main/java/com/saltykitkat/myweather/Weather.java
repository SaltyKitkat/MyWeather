package com.saltykitkat.myweather;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.transform.Source;

public class Weather {
    String sourceName;
    String sourceUrl = "https://wthrcdn.etouch.cn/WeatherApi?citykey=";
    String cityCode;

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

    public void equery() {
        final String address = sourceUrl + cityCode;
        Log.d("myWeather", "getting data from " + address);
        try {
            URL url = new URL(address);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
