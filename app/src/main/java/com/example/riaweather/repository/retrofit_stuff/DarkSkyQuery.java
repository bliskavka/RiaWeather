package com.example.riaweather.repository.retrofit_stuff;

import com.example.riaweather.repository.retrofit_stuff.response_weather.BaseWeatherEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DarkSkyQuery {
    String BASE_URL = "https://api.darksky.net/forecast/";

    @GET("{key}/{latitude},{longitude}?exclude=minutely,alerts,flags&lang=uk&units=si")
    Call<BaseWeatherEntity> getPosts(@Path("key") String key,
                                     @Path("latitude") String lat,
                                     @Path("longitude") String lng);
}
