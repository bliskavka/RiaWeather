package com.example.riaweather;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.riaweather.repository.room_stuff.City;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        CitySearchActivity.class));
            }
        });

        RecyclerView rv = findViewById(R.id.rv);
        final CityListAdapter adapter = new CityListAdapter(this, new CityListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(City city) {
                Intent intent = new Intent(MainActivity.this, CityDetailsActivity.class);
                intent.putExtra("city_obj", city);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(City city) {
                mainViewModel.deleteCity(city);
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);


        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getAllCities().observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(@Nullable List<City> cities) {
                adapter.setData(cities);
                Log.d("database", "data changed" + cities.size());
            }
        });

        mainViewModel.requestStatus.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*
    This needed for 'Update' button in the toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        updateAllCities();
        return super.onOptionsItemSelected(item);
    }

    public void updateAllCities(){
        List<City> cities = mainViewModel.getAllCities().getValue();
        for (City city: cities) {
            mainViewModel.updateWeather(city);
        }
    }
}
