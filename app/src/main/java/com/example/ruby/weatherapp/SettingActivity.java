package com.example.ruby.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import com.example.ruby.weatherapp.Model.City;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class SettingActivity extends AppCompatActivity {

    MultiAutoCompleteTextView countryInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        countryInput = (MultiAutoCompleteTextView)findViewById(R.id.country_input);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/gentifa576/WeatherApp/master/")
                .build();

        CityInterface cityInterface = retrofit.create(CityInterface.class);

        List<City> cities;
        City[] citiesArr = null;

        try {
            cities = cityInterface.getCities().execute().body();
            citiesArr = cities.toArray(new City[cities.size()]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] citiesName = new String[citiesArr.length];
        for(int i = 0; i < citiesName.length; i++){
            citiesName[i] = citiesArr[i].getName() + ", " + citiesArr[i].getCountry();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.text, citiesName);

        countryInput.setAdapter(adapter);
    }
}
