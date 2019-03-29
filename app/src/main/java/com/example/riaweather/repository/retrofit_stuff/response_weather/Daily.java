package com.example.riaweather.repository.retrofit_stuff.response_weather;

import java.io.Serializable;
import java.util.List;

public class Daily implements Serializable {

    String summary;

    List<WeatherSetDaily> data;

    public String getSummary() {
        return summary;
    }

    public List<WeatherSetDaily> getData() {
        return data;
    }
}
