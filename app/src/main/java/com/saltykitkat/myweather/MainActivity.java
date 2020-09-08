package com.saltykitkat.myweather;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkNetState.getNetState(this) == checkNetState.NetState.NONE) {
            Log.d("myWeather", "Net failed");
            Toast.makeText(MainActivity.this, "无法连接网络", Toast.LENGTH_LONG).show();
        } else {
            Log.d("myWeather", "Network is OK");
        }
        Weather weather0 = new Weather("101010100");
        weather0.equery();
    }
}