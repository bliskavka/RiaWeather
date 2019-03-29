package com.example.riaweather;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.riaweather.repository.Repository;
import com.example.riaweather.repository.room_stuff.City;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private Repository repository;

    private LiveData<List<City>> cities;

    public MutableLiveData<String> requestStatus;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        requestStatus = repository.requestStatus;
        cities = repository.getAllCities();
    }

    public LiveData<List<City>> getAllCities() {
        return cities;
    }

    public void addCity(City city){
        repository.addCity(city);
    }

    public void editCity(City city){
        repository.editCity(city);
    }

    public void deleteCity(City city){
        repository.deleteCity(city);
    }

    public void updateWeather(City city){
        repository.updateWeather(city);
    }
}
