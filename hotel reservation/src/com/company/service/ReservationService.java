package com.company.service;

import com.company.menu.MainMenu;
import com.company.model.Customer;
import com.company.model.IRoom;
import com.company.model.Reservation;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.company.menu.MainMenu.getDatesBetweenUsingJava8;

public class ReservationService {
    // static reference
    private static final ReservationService reservationService = new ReservationService();

    public static ReservationService getInstance(){
        return reservationService;
    }

    public Collection<String> getRoomNumbers() {
        return roomNumbers;
    }

    // constructor
    public ReservationService(){

    }
    // default modifier
     Collection<String> roomNumbers = new HashSet<>();
    // rooms collections
    public ArrayList<IRoom> roomsAvailable = new ArrayList<>();
    // reservations collections
    private Collection<Reservation> reservations = new HashSet<>();

    public Collection<Reservation> getReservations(){
        return reservations;
    }

    public void addRoom(IRoom iRoom){
        roomsAvailable.add(iRoom);
        roomNumbers.add(iRoom.getRoomNumber());
    }

    public IRoom getRoom(String roomId){
        Optional<IRoom> room = roomsAvailable.stream().filter(r ->
                roomId.equals(r.getRoomNumber())).findFirst();
        return room.orElse(null);
    }

    public Reservation reserveARoom(Reservation reservationToReserve){
        reservations.add(reservationToReserve);
//        roomsAvailable.remove(reservationToReserve.getiRoom());
        for (LocalDate date: getDatesBetweenUsingJava8(
                Instant.ofEpochMilli(reservationToReserve.getCheckInDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate(),
                Instant.ofEpochMilli(reservationToReserve.getCheckOutDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate())){
            ReservationService.getInstance().getRoom(reservationToReserve.getiRoom().getRoomNumber()).getTakenDates().add(
                    Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
        }
        return reservationToReserve;
    }

    public Collection<IRoom> getAllRooms(){
        return roomsAvailable;
    }

    public ArrayList<Reservation> getCustomersReservations(Customer customer){
        ArrayList<Reservation> reservationsToReturn = new ArrayList<>();
        for(Reservation reservation: reservations){
            if(reservation.getCustomer().getEmail() == customer.getEmail()){
                reservationsToReturn.add(reservation);
            }
        }
        return reservationsToReturn;
    }

    public void printAllReservations(){
        if(reservations.size() == 0){
            System.out.println("No reservations");
        }
        else{
            reservations.forEach( reservation -> {
                System.out.println(reservation.getiRoom().getRoomNumber() + ": " + reservation);
            });
        }
    }

    public void printAllRooms(){
        if(roomsAvailable.size() == 0){
            System.out.println("No rooms available, try a different option :(");
        }
        else {
            for(int i = 0; i < roomsAvailable.size(); i++){
                System.out.println(roomsAvailable.get(i));
            }
        }
    }

    public int roomsAvailableSize(){
        return roomsAvailable.size();
    }
}
