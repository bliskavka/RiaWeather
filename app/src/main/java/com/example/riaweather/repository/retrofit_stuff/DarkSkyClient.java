package com.example.riaweather.repository.retrofit_stuff;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DarkSkyClient {
    static DarkSkyQuery instance = null;

    static DarkSkyQuery init(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DarkSkyQuery.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(DarkSkyQuery.class);
    }

    public static DarkSkyQuery getInstance(){
        if (instance == null) {
            instance = init();
        }
        return instance;
    }
}
