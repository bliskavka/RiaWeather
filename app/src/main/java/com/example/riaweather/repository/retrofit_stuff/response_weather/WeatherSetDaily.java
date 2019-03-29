package com.example.riaweather.repository.retrofit_stuff.response_weather;

import java.io.Serializable;

public class WeatherSetDaily extends WeatherSet implements Serializable {

    float temperatureHigh;

    float temperatureLow;

    public float getTemperatureHigh() {
        return temperatureHigh;
    }

    public float getTemperatureLow() {
        return temperatureLow;
    }
}
