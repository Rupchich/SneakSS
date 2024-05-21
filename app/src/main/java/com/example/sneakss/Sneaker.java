package com.example.sneakss;

public class Sneaker {
    private String imageUrl;
    private double size;
    private String company;
    private String model;
    private double price;

    public Sneaker(String imageUrl, double size, String company, String model, double price) {
        this.imageUrl = imageUrl;
        this.size = size;
        this.company = company;
        this.model = model;
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getSize() {
        return size;
    }

    public String getCompany() {
        return company;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }
}