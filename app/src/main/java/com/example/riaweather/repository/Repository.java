package com.example.riaweather.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.riaweather.repository.retrofit_stuff.DarkSkyClient;
import com.example.riaweather.repository.retrofit_stuff.DarkSkyQuery;
import com.example.riaweather.repository.retrofit_stuff.response_weather.BaseWeatherEntity;
import com.example.riaweather.repository.room_stuff.City;
import com.example.riaweather.repository.room_stuff.CityDao;
import com.example.riaweather.repository.room_stuff.MainDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private CityDao cityDao;

    private LiveData<List<City>> cities;

    public MutableLiveData<String> requestStatus;

    final String apiKey = "66988afeb5e5fa8e36cdac28c8f83df4";

    public Repository(Application application) {
        MainDatabase mainDatabase = MainDatabase.getInstance(application);
        cityDao = mainDatabase.cityDao();
        cities = cityDao.getAllCities();
        requestStatus = new MutableLiveData<>();
    }

    public LiveData<List<City>> getAllCities() {
        return cities;
    }

    public void addCity(final City city) {

        DarkSkyQuery darkSkyQuery = DarkSkyClient.getInstance();

        Call<BaseWeatherEntity> call = darkSkyQuery.getPosts(apiKey, city.getLat(), city.getLng());
        /*
        Retrofit`s 'enqueue' method does all the operations async. so its no need to use Rx here.
         */
        call.enqueue(new Callback<BaseWeatherEntity>() {
            @Override
            public void onResponse(Call<BaseWeatherEntity> call, Response<BaseWeatherEntity> response) {
                if (!response.isSuccessful()) {
                    Log.d("Weather Update: ", "failed" + response.code());
                    requestStatus.setValue("failed");
                    return;
                }

                Log.d("Weather Update: ", "successful: " + response.code());

                city.setWeather(response.body());// getting weather object from API

                Completable.fromAction(new Action() {
                    @Override
                    public void run() {
                        cityDao.addCity(city);
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
                requestStatus.setValue("Successful"); //respond to main activity
            }

            @Override
            public void onFailure(Call<BaseWeatherEntity> call, Throwable t) {
                Log.d("Weather Update: ", "server error", t);
                requestStatus.setValue("Internet Connection failed");
            }
        });


    }

    public void editCity(final City city) {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                cityDao.editCity(city);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        requestStatus.setValue("Updated Successfully");
    }

    public void deleteCity(final City city) {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                cityDao.deleteCity(city);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void updateWeather(final City city) {

        DarkSkyQuery darkSkyQuery = DarkSkyClient.getInstance();

        Call<BaseWeatherEntity> call = darkSkyQuery.getPosts(apiKey, city.getLat(), city.getLng());
        /*
        Retrofit`s 'enqueue' method does all the operations async. so its no need to use Rx here.
         */
        call.enqueue(new Callback<BaseWeatherEntity>() {
            @Override
            public void onResponse(Call<BaseWeatherEntity> call, Response<BaseWeatherEntity> response) {
                if (!response.isSuccessful()) {
                    Log.d("Weather Update: ", "failed" + response.code());
                    requestStatus.setValue("update failed");
                    return;
                }

                Log.d("Weather Update: ", "successful: " + response.code());

                city.setWeather(response.body());// getting weather object from API

                editCity(city);

            }

            @Override
            public void onFailure(Call<BaseWeatherEntity> call, Throwable t) {
                Log.d("Weather Update: ", "server error", t);
                requestStatus.setValue("Internet Connection failed");
            }
        });
    }
}
