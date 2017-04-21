package com.example.ruby.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruby.weatherapp.Model.WeatherBundle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link WeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekFragment extends Fragment{

    private Gson gson;
    private Retrofit retrofit;
    private WeatherInterface weatherInterface;
    private SharedPreferences prefs;
    private WeatherBundle weatherBundle;
    private ArrayList<String> dates;
    public static String PREV_DATE = "";

    public WeekFragment() {
        // Required empty public constructor
    }

    public static WeekFragment newInstance() {
        WeekFragment fragment = new WeekFragment();
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

        return inflater.inflate(R.layout.fragment_week, container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            doCall();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //doCall();
    }

    public void doCall(){
        Call<WeatherBundle> weatherBundleCall = weatherInterface.getForecast(
                getResources().getString(R.string.weather_api_key),
                Integer.toString(prefs.getInt("locId", 0)));

        weatherBundleCall.enqueue(new Callback<WeatherBundle>() {
            @Override
            public void onResponse(Call<WeatherBundle> call, Response<WeatherBundle> response) {
                if(response.isSuccessful()) {
                    weatherBundle = response.body();
                    dates = new ArrayList<>();
                    for(WeatherBundle.WeatherList wl : weatherBundle.getList()){
                        dates.add(wl.getDateText().split(" ")[0]);
                    }
                    CustomAdapter customAdapter = new CustomAdapter(
                            WeekFragment.this.getContext(),
                            weatherBundle.getList().toArray(new WeatherBundle.WeatherList[weatherBundle.getCount()]));
                    ListView customListView = (ListView) WeekFragment.this.getView().findViewById(R.id.weather_list);
                    customListView.setAdapter(customAdapter);
                }
                else{
                    Toast.makeText(WeekFragment.this.getActivity(), "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherBundle> call, Throwable t) {
                Toast.makeText(WeekFragment.this.getActivity(), "Fail " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class CustomAdapter extends ArrayAdapter<WeatherBundle.WeatherList>{

        public CustomAdapter(@NonNull Context context, WeatherBundle.WeatherList[] resource) {
            super(context, R.layout.fragment_week, resource);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater customInflater = LayoutInflater.from(getContext());
            View customView = customInflater.inflate(R.layout.list_view_item, parent, false);

            TextView weatherText = (TextView) customView.findViewById(R.id.weather_txt);
            TextView descriptionText = (TextView) customView.findViewById(R.id.desc_txt);
            TextView tempText = (TextView) customView.findViewById(R.id.temp_txt);
            TextView minMaxText = (TextView) customView.findViewById(R.id.temp_min_max_txt);
            TextView headerText = (TextView) customView.findViewById(R.id.header_txt);
            TextView timeText = (TextView) customView.findViewById(R.id.time_txt);

            WeatherBundle.WeatherList wl = getItem(position);

            String[] splitDt = wl.getDateText().split(" ");

            if(splitDt[0].equals(PREV_DATE)) headerText.setVisibility(View.GONE);
            else {
                headerText.setText(splitDt[0]);
                PREV_DATE = splitDt[0];
            }
            timeText.setText(splitDt[1]);

            weatherText.setText(wl.getWeather().get(0).getMain());
            descriptionText.setText(wl.getWeather().get(0).getDescription());

            tempText.setText(wl.getMain().getTemp() + "°");
            minMaxText.setText(wl.getMain().getMinTemp() + "°/" + wl.getMain().getMaxTemp() + "°");

            return customView;
        }
    }
}
