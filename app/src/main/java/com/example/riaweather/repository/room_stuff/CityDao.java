package com.example.riaweather.repository.room_stuff;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.riaweather.repository.retrofit_stuff.response_weather.BaseWeatherEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CityDao {

    @Query("SELECT * FROM city_table")
    LiveData<List<City>> getAllCities();

    @Query("SELECT * FROM city_table WHERE id = :id")
    City getCityById(int id);

    @Insert (onConflict = REPLACE)
    void addCity(City city);

    @Update
    void editCity(City city);

    @Delete
    void deleteCity(City city);

    @Query("DELETE FROM city_table")
    void deleteAllCities();

}
