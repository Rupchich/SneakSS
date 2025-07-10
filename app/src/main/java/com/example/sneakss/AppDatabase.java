package com.example.sneakss;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Sneaker.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SneakerDao sneakerDao();
}
