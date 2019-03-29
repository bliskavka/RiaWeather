package com.example.riaweather.repository.room_stuff;

import android.arch.persistence.room.TypeConverter;

import com.example.riaweather.repository.retrofit_stuff.response_weather.BaseWeatherEntity;
import com.google.gson.Gson;

public class Converters {

    @TypeConverter
    public static BaseWeatherEntity toWeatherEntity(String gsonString){
        Gson gson = new Gson();
        return gson.fromJson(gsonString, BaseWeatherEntity.class);
    }

    @TypeConverter
    public static String fromWeatherEntity(BaseWeatherEntity weatherEntity){
        Gson gson = new Gson();
        return gson.toJson(weatherEntity);
    }
}
