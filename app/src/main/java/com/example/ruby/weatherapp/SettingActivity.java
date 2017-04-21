package com.example.ruby.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MultiAutoCompleteTextView;

public class SettingActivity extends AppCompatActivity {

    MultiAutoCompleteTextView countryInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        countryInput = (MultiAutoCompleteTextView)findViewById(R.id.country_input);
    }
}
