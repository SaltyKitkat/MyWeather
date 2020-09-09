package com.saltykitkat.myweather;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

public class WeatherXMLParser {

    public Result result = new Result();

    public WeatherXMLParser(String input) {
        parse(input);
    }

    protected void parse(String xmldata) {
        try {
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
            XmlPullParser parser = fac.newPullParser();
            parser.setInput(new StringReader(xmldata));
            int eventType = parser.getEventType();
            Log.d("myWeather", "xmlparsing");
            int i = 0, j = 0;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String tagName = parser.getName();
                        eventType = parser.next();
                        if (tagName.equals("weather")) {
                            i++;
                            if (i >= 10) {
                                return;
                            }
                        } else if (tagName.equals("day")) {
                            j = 0;
                        } else if (tagName.equals("night")) {
                            j = 1;
                        }
                        if ((eventType != XmlPullParser.TEXT)) {
                            break;
                        }
                        if (tagName.equals("city")) { //city
                            result.city = parser.getText();
                        } else if (tagName.equals("updatetime")) { //updatetime
                            result.updatetime = parser.getText();
                        } else if (tagName.equals("shidu")) { //shidu
                            result.humidity = parser.getText();
                        } else if (tagName.equals("wendu")) { //wendu
                            result.currentTemperature = parser.getText();
                        } else if (tagName.equals("pm25")) { //pm25
                            result.pm2_5 = parser.getText();
                        } else if (tagName.equals("quality")) { //quality
                            result.air_quality = parser.getText();
                        } else if (tagName.equals("date")) { //date
                            result.date[i] = parser.getText();
                        } else if (tagName.equals("high")) { //high
                            result.high[i] = parser.getText();
                        } else if (tagName.equals("low")) { //low
                            result.low[i] = parser.getText();
                        } else if (tagName.equals("fengxiang")) { //fangxiang
                            result.wind_direction[i][j] = parser.getText();
                        } else if (tagName.equals("fengli")) { //fengli
                            result.wind_force[i][j] = parser.getText();
                        } else if (tagName.equals("type")) { //type
                            result.weather_details[i][j] = parser.getText();
                        }
                        break;
                    default:
                        eventType = parser.next();
                        break;
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
}

class Result {
    String city;
    String[] date = new String[10];
    String updatetime;
    String humidity;
    String currentTemperature;
    String[] high = new String[10];
    String[] low = new String[10];
    String pm2_5 = "无数据";
    String air_quality;
    String[][] wind_force = new String[10][2];
    String[][] wind_direction = new String[10][2];
    String[][] weather_details = new String[10][2];
}
