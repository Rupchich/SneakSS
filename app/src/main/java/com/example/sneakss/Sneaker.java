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
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size){
        this.size = size;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}