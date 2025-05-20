package com.example.maendderslaes;

public class Item {

    // Attributes
    private String name;
    private int price;

    // ________________________________________

    public Item(String name, int price) {

        this.name = name;
        this.price = price;
    }

    // ________________________________________

    public String getName() {
        return name;
    }

    // ________________________________________

    public int getPrice() {
        return price;
    }

} // Class end
