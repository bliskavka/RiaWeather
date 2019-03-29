package com.example.riaweather.repository.retrofit_stuff.response_weather;


import java.io.Serializable;

public class BaseWeatherEntity implements Serializable {

    float latitude;

    float longitude;

    String timezone;

    WeatherSet currently;

    Hourly hourly;

    Daily daily;

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public WeatherSet getCurrently() {
        return currently;
    }

    public Hourly getHourly() {
        return hourly;
    }

    public Daily getDaily() {
        return daily;
    }
}

