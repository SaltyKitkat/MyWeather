package com.saltykitkat.myweather;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnChooseCity;
    private TextView txtTitle;
    private ImageButton btnLocation;
    private ImageButton btnShare;
    private ImageButton btnUpdate;
    private TextView txtCityName;
    private TextView txtUpdateTime;
    private TextView txtHumidity;
    private ImageView imgWeatherIndicator;
    private ImageView imgAirIndicator;
    private TextView txtPm25;
    private TextView txtAirQuality;
    private TextView txtDate;
    private TextView txtTemperature;
    private TextView txtWeatherDetails;
    private TextView txtWind;
    private TextView txtCurrentTemperature;

    Weather weather0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChooseCity = findViewById(R.id.btn_chooseCity);
        txtTitle = findViewById(R.id.txt_title);
        btnLocation = findViewById(R.id.btn_location);
        btnShare = findViewById(R.id.btn_share);
        btnUpdate = findViewById(R.id.btn_update);
        txtCityName = findViewById(R.id.txt_cityName);
        txtUpdateTime = findViewById(R.id.txt_updateTime);
        txtHumidity = findViewById(R.id.txt_humidity);
        imgWeatherIndicator = findViewById(R.id.img_weatherIndicator);
        imgAirIndicator = findViewById(R.id.img_airIndicator);
        txtPm25 = findViewById(R.id.txt_pm25);
        txtAirQuality = findViewById(R.id.txt_airQuality);
        txtDate = findViewById(R.id.txt_date);
        txtTemperature = findViewById(R.id.txt_temperature);
        txtWeatherDetails = findViewById(R.id.txt_weatherDetails);
        txtWind = findViewById(R.id.txt_wind);
        txtCurrentTemperature = findViewById(R.id.txt_CurrentTemperature);

        btnChooseCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectCity.class);
                startActivityForResult(intent, 1);
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        weather0 = new Weather("101010100");

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                NetState netstate = (NetState) msg.obj;
                if (netstate == NetState.NONE) {
                    Log.d("myWeather", "Net failed");
                    Toast.makeText(MainActivity.this, "无法连接网络", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("myWeather", "Network is OK");
                    update();
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("myWeather", "checknetwork");
                Message msg = new Message();
                msg.obj = checkNetState.getNetState(MainActivity.this);
                handler.sendMessage(msg);
            }
        }).start();
    }


    protected void update() {
        txtUpdateTime.setText("updating...");
        Log.d("mtWeather", "update");
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                draw((Result) msg.obj);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.obj = weather0.query();
                handler.sendMessage(msg);
            }
        }).start();
    }

    protected void draw(Result result) {
        if (result == null) {
            Toast.makeText(MainActivity.this, "update error", Toast.LENGTH_SHORT).show();
        }
        txtTitle.setText(result.city + "天气");
        txtCityName.setText(" " + result.city);
        txtUpdateTime.setText(result.updatetime + "发布");
        txtHumidity.setText("湿度" + result.humidity);
        txtCurrentTemperature.setText((result.currentTemperature + "℃"));
        txtPm25.setText(result.pm2_5);
        txtAirQuality.setText(result.air_quality);
        txtDate.setText(result.date[1]);
        txtTemperature.setText(result.high[1] + "\n" + result.low[1]);
        txtWeatherDetails.setText(result.weather_details[1][0]);
        txtWind.setText(result.wind_direction[1][0] + ":" + result.wind_force[1][0]);
        switch (result.weather_details[1][0]) {
            case "晴":
                imgWeatherIndicator.setImageResource(R.drawable.biz_plugin_weather_qing);
                break;
            case "多云":
                imgWeatherIndicator.setImageResource(R.drawable.biz_plugin_weather_duoyun);
                break;
            case "阴":
                imgWeatherIndicator.setImageResource(R.drawable.biz_plugin_weather_yin);
                break;
            case "小雨":
                imgWeatherIndicator.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
                break;
            case "中雨":
                imgWeatherIndicator.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
                break;
            case "大雨":
                imgWeatherIndicator.setImageResource(R.drawable.biz_plugin_weather_dayu);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode != RESULT_CANCELED) {
            String cityCode = data.getStringExtra("cityCode");
            if (cityCode != null) {
                weather0.setCity(cityCode);
                update();
            }
        }
    }
}