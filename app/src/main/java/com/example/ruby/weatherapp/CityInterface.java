package com.example.ruby.weatherapp;

import com.example.ruby.weatherapp.Model.City;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ruby on 4/21/2017.
 */

public interface CityInterface {
    String BASE_URL = "https://raw.githubusercontent.com/gentifa576/WeatherApp/master/";

    @GET("city.list.json")
    Call<List<City>> getCities();
}
