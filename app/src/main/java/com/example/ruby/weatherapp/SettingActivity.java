package com.example.ruby.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.example.ruby.weatherapp.Model.City;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingActivity extends AppCompatActivity {

    private AutoCompleteTextView countryInput;
    private List<City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        countryInput = (AutoCompleteTextView)findViewById(R.id.country_input);

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/gentifa576/WeatherApp/master/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CityInterface cityInterface = retrofit.create(CityInterface.class);

        Call<List<City>> callCity = cityInterface.getCities();
        callCity.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if(response.isSuccessful()){
                    cities = response.body();
                    City[] citiesArr = cities.toArray(new City[cities.size()]);
                    String[] citiesStr = new String[citiesArr.length];

                    for(int i = 0; i < citiesStr.length; i++){
                        citiesStr[i] = citiesArr[i].getName() + ", " + citiesArr[i].getCountry();
                    }

                    ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.text, citiesStr);

                    countryInput.setAdapter(autoCompleteAdapter);
                    Toast.makeText(SettingActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SettingActivity.this, "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Toast.makeText(SettingActivity.this, "Fail " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
