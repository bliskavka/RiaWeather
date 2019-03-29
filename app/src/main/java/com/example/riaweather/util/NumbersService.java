package com.example.riaweather.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NumbersService {

    public static String getTempInFormat(float temp){
        String result = String.valueOf(Math.round(temp));
        if(temp > 0)
            return "+" + result;
        return result;
    }

    public static String getDateFromTimestamp(long timestamp){
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM, hh:mm");
        String dateString = formatter.format(new Date(timestamp));
        return dateString;
    }
    public static String getSimpleDateFromTimestamp(long timestamp){
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd");
        String dateString = formatter.format(new Date(timestamp*1000));
        return dateString;
    }

    public static String getWindDirection(float bearing){
        if (bearing >= 0 && bearing <= 45) return "ПН";
        if (bearing >= 45 && bearing <= 135) return "СХ";
        if (bearing >= 135 && bearing <= 225) return "ПД";
        if (bearing >= 225 && bearing <= 315) return "ЗХ";
        return "ПН";
    }
}
