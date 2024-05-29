package com.groceryapp;

public class GroceryItem {
    private String name;
    private double price;
    private String imageURL;
    private int quantityValue;
    private String quantityUnit;

    public GroceryItem() {
    }

    public GroceryItem(String name, double price, String imageURL, int quantityValue, String quantityUnit) {
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
        this.quantityValue = quantityValue;
        this.quantityUnit = quantityUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getQuantityValue() {
        return quantityValue;
    }

    public void setQuantityValue(int quantityValue) {
        this.quantityValue = quantityValue;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }
}
