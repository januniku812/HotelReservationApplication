package com.company.model;

import java.util.ArrayList;
import java.util.Date;

public interface IRoom {
    public String getRoomNumber();

    public Double getRoomPrice();

    public RoomType getRoomType();

    public boolean isFree();

    public ArrayList<Date> getTakenDates();

    default void showSuccess(){
        System.out.println("Room successfully reserved");
    }
}
