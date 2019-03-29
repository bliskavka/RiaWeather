package com.example.riaweather.repository.retrofit_stuff;

import com.example.riaweather.repository.retrofit_stuff.response_autocomplete.AutocompleteEntity;
import com.example.riaweather.repository.retrofit_stuff.response_details.DetailsEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlacesQuery {
    String BASE_URL = "https://maps.googleapis.com/maps/api/place/";

    @GET("autocomplete/json?types=(cities)&language=en")
    Call<AutocompleteEntity> getAutocomplete(@Query("key") String key,
                                             @Query("input") String input);

    @GET("details/json?fields=address_component,geometry")
    Call<DetailsEntity> getDetails(@Query("key") String key,
                                   @Query("placeid") String placeId);
}
