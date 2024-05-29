package com.groceryapp;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<String> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(String item) {
        items.add(item);
    }

    public void removeItem(String item) {
        items.remove(item);
    }

    public List<String> getItems() {
        return items;
    }

    public int getSize() {
        return items.size();
    }

    public double calculateTotal() {
       
        return items.size() * getItemPrice();
    }

   
    private double getItemPrice() {
        return 10.0; 
    }
}
