package com.example.riaweather.repository.retrofit_stuff.response_weather;

import java.io.Serializable;
import java.util.List;

public class Hourly implements Serializable {

    String summary;

    List<WeatherSet> data;

    public String getSummary() {
        return summary;
    }

    public List<WeatherSet> getData() {
        return data;
    }
}
