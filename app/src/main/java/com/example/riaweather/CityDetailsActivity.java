package com.example.riaweather;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.riaweather.databinding.ActivityCityDetailsBinding;
import com.example.riaweather.repository.retrofit_stuff.response_weather.WeatherSet;
import com.example.riaweather.repository.room_stuff.City;
import com.example.riaweather.util.NumbersService;

public class CityDetailsActivity extends AppCompatActivity {

    ActivityCityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_city_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent().getExtras() == null)
            throw new AssertionError("Object cannot be null");
        City city = (City)getIntent().getSerializableExtra("city_obj");
        WeatherSet currentWeather = city.getWeather().getCurrently();

        setTitle(city.getName());
        binding.setCity(city);
        String st = NumbersService.getSimpleDateFromTimestamp(city.getWeather().getDaily().getData().get(0).getTime());

    }
}
