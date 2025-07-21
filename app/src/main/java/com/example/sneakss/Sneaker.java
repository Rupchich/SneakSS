package com.example.sneakss;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Sneaker {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "imageResId")
    public int imageResId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "brand")
    public String brand;

    @ColumnInfo(name = "color")
    public String color;

    @ColumnInfo(name = "size")
    public String size;

    @ColumnInfo(name = "purpose")
    public String purpose;

    @ColumnInfo(name = "price")
    public double price;

    @ColumnInfo(name = "note")
    public String note;

    @ColumnInfo(name = "isFavorite")
    public boolean isFavorite;
}
