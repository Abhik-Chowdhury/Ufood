package com.example.ufood.Model;

import java.util.ArrayList;

public class food {
    public String foodType;
    public String servingPeople;
    public String description;
    public String country;
    public String city;
    public String street;
    public String houseNo;
    public String landmark;
    public String pin;
    public ArrayList<String> item;

    public food(String foodType, String servingPeople, String description, String country, String city, String street, String houseNo,String landmark,String pin, ArrayList<String> item) {
        this.foodType = foodType;
        this.servingPeople = servingPeople;
        this.description = description;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNo = houseNo;
        this.landmark = landmark;
        this.pin = pin;
        this.item = item;
    }
}
