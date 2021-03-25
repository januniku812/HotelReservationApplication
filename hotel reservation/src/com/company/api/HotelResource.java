package com.company.api;

import com.company.model.Customer;
import com.company.model.IRoom;
import com.company.model.Reservation;
import com.company.model.Room;
import com.company.service.CustomerService;
import com.company.service.ReservationService;

import java.util.Collection;
import java.util.Date;



public class HotelResource {
    // static reference
    public static HotelResource hotelResource = new HotelResource();

    public Customer getCustomer(String email){
        return CustomerService.getInstance().getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName){
        try {
            CustomerService.getInstance().addCustomer(email, firstName, lastName);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid email");
        }
    }

    public IRoom getIroom(String roomId){
        return ReservationService.getInstance().getRoom(roomId);
    }

    public Reservation bookARoom(String email, IRoom iRoom, Date checkInDate, Date checkOutDate){
        Customer customer = CustomerService.getInstance().getCustomer(email);
        ReservationService.getInstance().reserveARoom(new Reservation(customer, iRoom, checkInDate, checkOutDate));
        return ReservationService.getInstance().reserveARoom(new Reservation(customer, iRoom, checkInDate, checkOutDate));
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = CustomerService.getInstance().getCustomer(customerEmail);
        return ReservationService.getInstance().getCustomersReservations(customer);
    }
}
