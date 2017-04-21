package com.example.ruby.weatherapp;

import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruby.weatherapp.Model.CurrentWeather;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link TodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFragment extends Fragment {

    private Gson gson;
    private Retrofit retrofit;
    private WeatherInterface weatherInterface;
    private SharedPreferences prefs;
    private CurrentWeather currentWeather;

    public TodayFragment() {
        // Required empty public constructor
    }

    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        weatherInterface = retrofit.create(WeatherInterface.class);

        prefs = getActivity().getSharedPreferences("pref", 0);

        return inflater.inflate(R.layout.fragment_today, container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isAdded() && isVisibleToUser){
            doCall();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        doCall();
    }

    public void doCall(){
        final Call<CurrentWeather> currentWeatherCall = weatherInterface.getWeather(
                getResources().getString(R.string.weather_api_key),
                Integer.toString(prefs.getInt("locId", 0)));

        currentWeatherCall.enqueue(new Callback<CurrentWeather>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                if(response.isSuccessful()) {
                    currentWeather = response.body();

                    TextView locationText = (TextView)TodayFragment.this.getActivity().findViewById(R.id.location_txt);
                    TextView dateText = (TextView)TodayFragment.this.getActivity().findViewById(R.id.date_txt);
                    TextView weatherText = (TextView)TodayFragment.this.getActivity().findViewById(R.id.weather_txt);
                    TextView descriptionText = (TextView)TodayFragment.this.getActivity().findViewById(R.id.desc_txt);
                    TextView tempText = (TextView)TodayFragment.this.getActivity().findViewById(R.id.temp_txt);
                    TextView minTempText = (TextView)TodayFragment.this.getActivity().findViewById(R.id.min_temp_txt);
                    TextView maxTempText = (TextView)TodayFragment.this.getActivity().findViewById(R.id.max_temp_txt);
                    TextView speedText = (TextView)TodayFragment.this.getActivity().findViewById(R.id.speed_txt);
                    TextView degText = (TextView)TodayFragment.this.getActivity().findViewById(R.id.deg_txt);
                    TextView cloudinessText = (TextView)TodayFragment.this.getActivity().findViewById(R.id.cloudiness_txt);

                    locationText.setText(currentWeather.getName());
                    dateText.setText(new SimpleDateFormat("EEE, MMMM dd").format(new Date(Long.parseLong(currentWeather.getDt())*1000L)));
                    weatherText.setText(currentWeather.getWeather().get(0).getMain());
                    descriptionText.setText(currentWeather.getWeather().get(0).getDescription());
                    tempText.setText(currentWeather.getMain().getTemp() + "°");
                    minTempText.setText(currentWeather.getMain().getMinTemp() + "°");
                    maxTempText.setText(currentWeather.getMain().getMaxTemp() + "°");
                    speedText.setText(currentWeather.getWind().getSpeed() + "m/s");
                    degText.setText(currentWeather.getWind().getDeg() + "°");
                    cloudinessText.setText(currentWeather.getClouds().getAll() + "%");
                }
                else{
                    Toast.makeText(TodayFragment.this.getActivity(), "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                Toast.makeText(TodayFragment.this.getActivity(), "Fail " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
