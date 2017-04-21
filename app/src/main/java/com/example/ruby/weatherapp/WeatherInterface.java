package com.example.ruby.weatherapp;

import com.example.ruby.weatherapp.Model.CurrentWeather;
import com.example.ruby.weatherapp.Model.WeatherBundle;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ruby on 4/21/2017.
 */

public interface WeatherInterface {
    String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    @GET("weather?units=metric")
    Call<CurrentWeather> getWeather(@Query("appid") String key, @Query("id") String id);

    @GET("forecast?units=metric")
    Call<WeatherBundle> getForecast(@Query("appid") String key, @Query("id") String id);
}
