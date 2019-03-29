package com.example.riaweather.repository.retrofit_stuff;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GooglePlacesClient {
    static GooglePlacesQuery instance = null;

    private static GooglePlacesQuery init(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GooglePlacesQuery.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(GooglePlacesQuery.class);
    }

    public static GooglePlacesQuery getInstance(){
        if (instance == null) {
            instance = init();
        }
        return instance;
    }
}
