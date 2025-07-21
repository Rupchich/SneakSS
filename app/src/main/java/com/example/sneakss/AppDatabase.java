package com.example.sneakss;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Sneaker.class, User.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SneakerDao sneakerDao();
    public abstract UserDao userDao();
}
