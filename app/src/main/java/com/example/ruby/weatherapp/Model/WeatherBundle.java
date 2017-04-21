package com.example.ruby.weatherapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ruby on 4/21/2017.
 */

public class WeatherBundle {
    private List<WeatherList> list;
    @SerializedName("cnt") private int count;

    public List<WeatherList> getList() {
        return list;
    }

    public void setList(List<WeatherList> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

class WeatherList{
    private String dt;
    private MainData main;
    private List<Weather> weather;
    @SerializedName("dt_txt") private String dtText;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public MainData getMain() {
        return main;
    }

    public void setMain(MainData main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getDateText() {
        return dtText;
    }

    public void setDateText(String dateText) {
        this.dtText = dateText;
    }
}

class MainData{
    private float temp;
    @SerializedName("temp_min") private float minTemp;
    @SerializedName("temp_max") private float maxTemp;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }
}

class Weather{
    private int id;
    private String main;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}