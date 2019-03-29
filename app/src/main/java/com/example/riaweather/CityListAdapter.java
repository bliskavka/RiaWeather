package com.example.riaweather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.riaweather.repository.room_stuff.City;
import com.example.riaweather.util.NumbersService;

import java.util.ArrayList;
import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.Holder> {

    private List<City> cities = new ArrayList<>();
    private Context context;
    private final OnItemClickListener clickListener;

    public CityListAdapter(Context context, OnItemClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(City city);
        void onItemLongClick(City city);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_city, viewGroup, false);
        return new Holder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        final City currentCity = cities.get(i);
        holder.cityName.setText(currentCity.getName());
        if (currentCity.getWeather() != null) {
            holder.summary.setText(currentCity.getWeather().getCurrently().getSummary());
            holder.temp.setText(NumbersService.getTempInFormat(
                    currentCity.getWeather().getCurrently().getTemperature()));


            Float temp = currentCity.getWeather().getCurrently().getTemperature();
            if (temp > 25)
                holder.temp.setTextColor(context.getResources().getColor(R.color.colorHot));
            if (temp <= 25 && temp > 15)
                holder.temp.setTextColor(context.getResources().getColor(R.color.colorWarm));
            if (temp <= 15 && temp > 5)
                holder.temp.setTextColor(context.getResources().getColor(R.color.colorNormal));
            if (temp <= 5 && temp > -5)
                holder.temp.setTextColor(context.getResources().getColor(R.color.colorCold));
            if (temp < -5)
                holder.temp.setTextColor(context.getResources().getColor(R.color.colorSuperCold));

        }
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(currentCity);
            }
        });

        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clickListener.onItemLongClick(currentCity);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void setData(List<City> cities){
        this.cities = cities;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView cityName;
        TextView summary;
        TextView temp;
        LinearLayout parent;

        public Holder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.city_name);
            summary = itemView.findViewById(R.id.summary);
            temp = itemView.findViewById(R.id.temp);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
