package com.company.api;

import com.company.model.Customer;
import com.company.model.IRoom;
import com.company.service.CustomerService;
import com.company.service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    // static reference
    private static final AdminResource adminResource = new AdminResource();

    public static AdminResource getInstance(){
        return adminResource;
    }

    public Customer getCustomer(String email){
        return CustomerService.getInstance().getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms){
        rooms.forEach(iRoom -> {
            ReservationService.getInstance().addRoom(iRoom);
         }
        );
    }

    public Collection<IRoom> getAllRooms(){
        return ReservationService.getInstance().getAllRooms();
    }

    public Collection<Customer> getAllCustomers(){
        return CustomerService.getInstance().getAllCustomers();
    }

    public void displayAllReservations(){
        ReservationService.getInstance().printAllReservations();
    }
}
