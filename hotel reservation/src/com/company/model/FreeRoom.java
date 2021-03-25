package com.company.model;

public class FreeRoom extends Room{
    public FreeRoom(String roomNumberInput, RoomType roomTypeInput) {
        super(roomNumberInput, 0.0, roomTypeInput);
    }

    // overriding the toString() method for a better description
    @Override
    public String toString() {
        return "Free room, costs no money, room number: " + getRoomNumber() + " room type: " + getRoomType();
    }

}
