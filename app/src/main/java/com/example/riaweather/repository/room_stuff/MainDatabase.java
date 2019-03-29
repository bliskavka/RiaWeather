package com.example.riaweather.repository.room_stuff;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = City.class, version = 3)
@TypeConverters({Converters.class})
public abstract class MainDatabase extends RoomDatabase {

    private static MainDatabase INSTANCE;

    public abstract CityDao cityDao();

    public static synchronized MainDatabase getInstance(Context context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MainDatabase.class, "main_database")
                    .fallbackToDestructiveMigration()
                    //.allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
