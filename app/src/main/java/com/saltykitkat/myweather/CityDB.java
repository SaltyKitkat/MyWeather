package com.saltykitkat.myweather;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CityDB {
    public static final String CITY_DB_NAME = "city.db";
    private static final String CITY_TABLE_NAME = "city";
    private static final String SRC_DB_PATH = "/data/data/com.saltykitkat.myweather/databases/";
    private static final String SRC_DB_FILENAME = "city.db";
    private SQLiteDatabase db;
    Context context;

    public CityDB(Context context) {
        this.context = context;
        File dir = new File(SRC_DB_PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            InputStream inputStream = context.getResources().getAssets().open(CITY_DB_NAME);
            FileOutputStream fileOutputStream = new FileOutputStream(SRC_DB_PATH+SRC_DB_FILENAME);
            byte[] buf = new byte[1024 * 4];
            int len = 0;
            while ((len = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf);
            }
            inputStream.close();
            fileOutputStream.close();
            db = SQLiteDatabase.openOrCreateDatabase(SRC_DB_PATH+SRC_DB_FILENAME,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<City> getAllCity() {
        List<City> list = new ArrayList<City>();
        Cursor c = db.rawQuery("SELECT * FROM " + CITY_TABLE_NAME, null);
        while (c.moveToNext()) {
            list.add(new City(
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6)
            ));
        }
        return list;
    }
}

class City {

    final String province;
    final String city;
    final String number;
    final String firstPY;
    final String allPY;
    final String allFirstPY;

    public City(String province, String city, String number, String firstPY, String allPY, String allFirstPY) {
        this.province = province;
        this.city = city;
        this.number = number;
        this.firstPY = firstPY;
        this.allFirstPY = allFirstPY;
        this.allPY = allPY;
    }

    @Override
    public String toString() {
        return province + ' ' + city + ' ' + number ;
    }
}

