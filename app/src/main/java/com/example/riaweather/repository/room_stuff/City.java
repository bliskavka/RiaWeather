package com.example.riaweather.repository.room_stuff;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.riaweather.repository.retrofit_stuff.response_weather.BaseWeatherEntity;
import com.example.riaweather.util.NumbersService;

import java.io.Serializable;

@Entity(tableName = "city_table")
public class City implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String lat;

    private String lng;

    private BaseWeatherEntity weather;

    private String lastUpdate;

    public City(String name, String lat, String lng, BaseWeatherEntity weather) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.weather = weather;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWeather(BaseWeatherEntity weather) {
        lastUpdate = NumbersService.getDateFromTimestamp(System.currentTimeMillis());
        this.weather = weather;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdateNow() {
        lastUpdate = NumbersService.getDateFromTimestamp(System.currentTimeMillis());
        this.lastUpdate = lastUpdate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public BaseWeatherEntity getWeather() {
        return weather;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }
}
