package com.saltykitkat.myweather;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SelectCity extends AppCompatActivity {

    private ImageButton btnBack;
    private EditText itxtSearch;
    private ImageButton btnSearch;
    private ListView lstCitys;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> mArrayList;
    private List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        btnBack = findViewById(R.id.btn_Back);
        itxtSearch = findViewById(R.id.itxt_search);
        btnSearch = findViewById(R.id.btn_search);
        lstCitys = findViewById(R.id.lst_citys);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                SelectCity.this.finish();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        cityList = new CityDB(this).getAllCity();
        lstCitys.setAdapter(
                new ArrayAdapter<City>(
                        this,
                        android.R.layout.simple_list_item_1,
                        cityList
                )
        );

        lstCitys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra(
                        "cityCode",
                        cityList.get(i).number
                );
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        itxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                search();
            }
        });

    }

    void search() {
        final String input_text = itxtSearch.getText().toString();
        cityList = new ArrayList<>();
        new CityDB(this).getAllCity().forEach(new Consumer<City>() {
            @Override
            public void accept(City city) {
                if (
                        city.allPY.equalsIgnoreCase(input_text)
                                || city.allFirstPY.equalsIgnoreCase(input_text)
                                || city.city.equalsIgnoreCase(input_text)
                ) {
                    cityList.add(city);
                }
            }
        });
        lstCitys.setAdapter(new ArrayAdapter<City>(
                SelectCity.this,
                android.R.layout.activity_list_item,
                cityList
        ));
    }
}