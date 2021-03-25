package com.company.model;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Room implements IRoom{
    // privat variables
    String roomNumber;
    Double price;
    RoomType roomType;
    private ArrayList<Date> takenDates = new ArrayList<>();

    // constructor
    public Room(String roomNumberInput, Double priceInput, RoomType roomTypeInput){
        // assigning all of our private variables
        roomNumber = roomNumberInput;
        price = priceInput;
        roomType = roomTypeInput;
    }

    public void addTakenDates(ArrayList<Date> dates){
        takenDates.addAll(dates);
    }

    @Override
    public ArrayList<Date> getTakenDates() {
        return takenDates;
    }

    // overriding methods from IRoom interface
    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    // overriding the toString() method for better description
    @Override
    public String toString() {
        return "Room number: " + roomNumber + " Price: " + price + " Room Type: " + roomType;
    }

}
