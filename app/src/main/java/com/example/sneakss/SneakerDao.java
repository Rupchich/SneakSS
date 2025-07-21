package com.example.sneakss;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SneakerDao {

    @Insert
    void insert(Sneaker sneaker);

    @Update
    void update(Sneaker sneaker);

    @Delete
    void delete(Sneaker sneaker);

    @Query("SELECT * FROM Sneaker")
    List<Sneaker> getAll();

    @Query("SELECT * FROM Sneaker WHERE isFavorite = 1")
    List<Sneaker> getFavorites();
}
