package com.example.riaweather.repository.retrofit_stuff.response_weather;

import java.io.Serializable;

public class WeatherSet implements Serializable {

    long time;

    String summary;

    float temperature;

    float humidity;

    float pressure;

    float windSpeed;

    float windBearing;

    public long getTime() {
        return time;
    }

    public String getSummary() {
        return summary;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public float getWindBearing() {
        return windBearing;
    }
}
