package com.example.ruby.weatherapp;

import com.example.ruby.weatherapp.Model.WeatherBundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ruby on 4/21/2017.
 */

public interface WeatherInterface {
    String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    @GET("weather?appid={key}&id={id}&units=metric")
    Call<WeatherBundle> getWeather(@Path("key") String key, @Path("id") String id);

    @GET("forecast?appid={key}&id={id}")
    Call<WeatherBundle> getForecast(@Path("key") String key, @Path("id") String id);
}
