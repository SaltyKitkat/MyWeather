package com.saltykitkat.myweather;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

public class WeatherXMLParser {

    public Result result = new Result();

    static final String[] rule = {
            "city",
            "updatetime",
            "shidu",
            "wendu",
            "pm25",
            "quality",
            "fengxiang",
            "fengli",
            "date",
            "high",
            "low"
    };

    public void parse(String xmldata) {
        int windDirectionCount = 0;
        int windPowerCount = 0;
        int dateCount = 0;
        int highCount = 0;
        int lowCount = 0;
        int typeCount = 0;
        try {
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
            XmlPullParser parser = fac.newPullParser();
            parser.setInput(new StringReader(xmldata));
            int eventType = parser.getEventType();
            Log.d("myWeather", "xmlparsing");
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tagName = parser.getName();
                        eventType = parser.next();
                        if (tagName.equals(rule[0])) { //city
                            result.city = parser.getText();
                        } else if (tagName.equals(rule[1])) { //updatetime
                            result.updatetime = parser.getText();
                        } else if (tagName.equals(rule[2])) { //shidu
                            result.humidity = parser.getText();
                        } else if (tagName.equals(rule[3])) { //wendu
                            result.tempreture = parser.getText();
                        } else if (tagName.equals(rule[4])) { //pm25
                            result.pm2_5 = parser.getText();
                        } else if (tagName.equals(rule[5])) { //quality
                            result.air_quality = parser.getText();
                        } else if (tagName.equals(rule[6])) { //fangxiang
                            result.wind_direction = parser.getText();
                        } else if (tagName.equals(rule[7])) { //fengli
                            result.wind_force = parser.getText();
                        } else if (tagName.equals(rule[8])) { //date
                            result.date = parser.getText();
                        } else if (tagName.equals(rule[9])) { //high
                            result.high = parser.getText();
                        } else if (tagName.equals(rule[10])) { //low
                            result.low = parser.getText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
}

class Result {
    String city;
    String date;
    String updatetime;
    String humidity;
    String tempreture;
    String high;
    String low;
    String pm2_5;
    String air_quality;
    String wind_force;
    String wind_direction;
}
