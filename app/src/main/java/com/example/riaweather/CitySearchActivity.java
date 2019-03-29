package com.example.riaweather;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.riaweather.repository.retrofit_stuff.GooglePlacesClient;
import com.example.riaweather.repository.retrofit_stuff.GooglePlacesQuery;
import com.example.riaweather.repository.retrofit_stuff.response_autocomplete.AutocompleteCity;
import com.example.riaweather.repository.retrofit_stuff.response_autocomplete.AutocompleteEntity;
import com.example.riaweather.repository.retrofit_stuff.response_details.DetailsEntity;
import com.example.riaweather.repository.room_stuff.City;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitySearchActivity extends AppCompatActivity {

    private final String key = "AIzaSyBrRBy-9O0hJG2a6sJo3AoFKuh6fenbZTM";

    GooglePlacesQuery googlePlacesQuery = GooglePlacesClient.getInstance();
    Call<AutocompleteEntity> call;
    Call<DetailsEntity> call2;
    ArrayList<String> citiesSuggest = new ArrayList<>();
    ArrayAdapter<String> adapter;
    AutocompleteEntity autocompleteEntity;
    DetailsEntity detailsEntity;

    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        EditText editText = findViewById(R.id.editText);
        if (editText.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);


        adapter = new  ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, citiesSuggest);
        ListView rv = findViewById(R.id.lv);
        rv.setAdapter(adapter);
        rv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String placeId = autocompleteEntity.predictions.get(position).place_id;
                final String name = autocompleteEntity.predictions.get(position).description;
                Log.d("Place selected: ", "place ID " + placeId);

                call2 = googlePlacesQuery.getDetails(key, placeId);
                call2.enqueue(new Callback<DetailsEntity>() {
                    @Override
                    public void onResponse(Call<DetailsEntity> call, Response<DetailsEntity> response) {
                        if (!response.isSuccessful()) {
                            Log.d("PlaceDetails response: ", "unsuccessful" + response.code());
                            return;
                        }

                        Log.d("PlaceDetails response: ", "successful" + response.code());
                        detailsEntity = response.body();
                        City city1 = new City(name,
                                detailsEntity.result.geometry.location.lat,
                                detailsEntity.result.geometry.location.lng,
                                null);
                        mainViewModel.addCity(city1);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<DetailsEntity> call, Throwable t) {
                        Log.d("PlaceDetails response: ", "error", t);
                    }
                });
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                citiesSuggest.clear();
                if(s.length()!=0) {
                    Log.d("Autocomplete Response: ", "text changed: " + s);
                    call = googlePlacesQuery.getAutocomplete(key, s.toString());
                    call.enqueue(new Callback<AutocompleteEntity>() {
                        @Override
                        public void onResponse(Call<AutocompleteEntity> call, Response<AutocompleteEntity> response) {
                            if (!response.isSuccessful()) {
                                Log.d("Autocomplete Response: ", "successful" + response.code());
                                return;
                            }

                            Log.d("Autocomplete Response: ", "response recieved " + response.code());
                            autocompleteEntity = response.body();

                            Log.d("Autocomplete Response: ", "array recieved " + autocompleteEntity.predictions.size());

                            for (AutocompleteCity city : autocompleteEntity.predictions) {
                                citiesSuggest.add(city.description);
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<AutocompleteEntity> call, Throwable t) {
                            Log.d("Retrofit Response", "error", t);
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
