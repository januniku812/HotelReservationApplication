package com.company.menu;

import com.company.model.*;
import com.company.service.CustomerService;
import com.company.service.ReservationService;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainMenu {
    Scanner scanner;
    public Boolean exitMainMenu;

    public MainMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    private void verifyEmail(String emailInput){
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(emailInput);
        if(!matcher.matches()){
            throw new IllegalArgumentException();
        }
    }

    public static List<LocalDate> getDatesBetweenUsingJava8(
            LocalDate startDate, LocalDate endDate) {

        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate.minusDays(-1));
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(startDate::plusDays)
                .collect(Collectors.toList());
    }

    private void reserveRoom() throws ParseException {
        System.out.println("Enter your email");
        String email = scanner.nextLine();
        try {
            verifyEmail(email);
        } catch (IllegalArgumentException illegalArgumentException){
            System.out.println("Invalid Email");
            exitMainMenu = true;
        }
        Customer customer = CustomerService.getInstance().getCustomer(email);
        // if they don't exist by that email
        if(customer == null) {
            System.out.println("You don't seem to already have an account, enter your first name");
            String firstName = scanner.nextLine();
            System.out.println("Enter your last name");
            String lastName = scanner.nextLine();
            try {
                CustomerService.getInstance().addCustomer(email, firstName, lastName);
            } catch (IllegalArgumentException e){
                System.out.println("Your email was INVALID");
                exitMainMenu = true;
            }
            // resetting the customer
            customer = CustomerService.getInstance().getCustomer(email);
        }
        ReservationService.getInstance().printAllRooms();
        if(ReservationService.getInstance().roomsAvailableSize() != 0){
            System.out.println("Enter the room number of the room you would like to reserve");
            String roomNumber = scanner.nextLine();
            IRoom iRoom = ReservationService.getInstance().getRoom(roomNumber);
            // if a room by that number doesn't exist
            while(iRoom == null){
                System.out.println("There doesn't seem to by a room by this number, enter a valid room number");
                roomNumber = scanner.nextLine();
                iRoom = ReservationService.getInstance().getRoom(roomNumber);
            }
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("Enter check-in date (DD/MM/YYYY): ");
            Date checkInDate = dateFormat.parse(scanner.nextLine());
            for (Reservation reservation : ReservationService.getInstance().getReservations()) {
                if (reservation.getiRoom().getRoomNumber().equals(roomNumber)) {
                    String roomNUmber = reservation.getiRoom().getRoomNumber();
                    Room room = (Room) ReservationService.getInstance().getRoom(roomNUmber);
                    while (room.getTakenDates().contains(checkInDate)){
                        System.out.println("The room is already booked during this time period, enter a new check in date");
                        checkInDate = dateFormat.parse(scanner.nextLine());
                    }
                }
            }
            System.out.println("Enter check-out date (DD/MM/YYYY): ");
            Date checkOutDate = dateFormat.parse(scanner.nextLine());
            for (Reservation reservation : ReservationService.getInstance().getReservations()) {
                if (reservation.getiRoom().getRoomNumber().equals(roomNumber)) {
                    String roomNUmber = reservation.getiRoom().getRoomNumber();
                    Room room = (Room) ReservationService.getInstance().getRoom(roomNUmber);
                    while (room.getTakenDates().contains(getDatesBetweenUsingJava8(Instant.ofEpochMilli(reservation.getCheckInDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate(), Instant.ofEpochMilli(reservation.getCheckOutDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate()))){
                        System.out.println("This time period is already booked, enter a new check in date");
                        checkInDate = dateFormat.parse(scanner.nextLine());
                        System.out.println("Enter a new check OUT date");
                        checkOutDate = dateFormat.parse(scanner.nextLine());
                    }
                }
            }
            while (checkOutDate.before(checkInDate)){
                System.out.println("The check out date cannot be before the check in date, enter a new check out date");
                checkOutDate = dateFormat.parse(scanner.nextLine());
            }
            ReservationService.getInstance().reserveARoom(new Reservation(customer, iRoom, checkInDate, checkOutDate));
            ReservationService.getInstance().getRoom(roomNumber).showSuccess();
        }
        else{
            System.out.println("No rooms available, try another option");
        }
    }

    private void seeMyReservations() {
        System.out.println("Enter email");
        Customer customer = CustomerService.getInstance().getCustomer(scanner.nextLine());
        if(customer == null){
            System.out.println("No account by that email exists");
            exitMainMenu = true;
        }
        else {
            Collection<Reservation> reservations = ReservationService.getInstance().getCustomersReservations(customer);
            // printing out each reservation under the customer
            if(reservations.size() == 0){
                System.out.println("No reservations under this account");
            }
            else {
                reservations.forEach(System.out::println);
            }
        }
    }

    private void createAnAccount(){
        System.out.println("Enter your email");
        String email = scanner.nextLine();
        while(CustomerService.getInstance().getCustomer(email) != null){
            System.out.println("An account with this email already exists, enter a different one");
            email = scanner.nextLine();
        }
        System.out.println("Enter your first name");
        String firstName = scanner.nextLine();
        System.out.println("Enter your last name");
        String lastName = scanner.nextLine();
        try{
            CustomerService.getInstance().addCustomer(email, firstName, lastName);
        } catch (IllegalArgumentException e){
            System.out.println("Your email was INVALID");
            exitMainMenu = true;
        }
    }

    public void start() throws ParseException {
        exitMainMenu = false;
        while (!exitMainMenu) {
            System.out.println("Main Menu");
            System.out.println("--------------------------------");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.println("--------------------------------");
            System.out.println("Please select a number for the menu option");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    reserveRoom();
                    break;
                case "2":
                    seeMyReservations();
                    break;
                case "3":
                    createAnAccount();
                    break;
                case "4":
                    AdminMenu adminMenu = new AdminMenu(scanner);
                    adminMenu.start();
                case "5":
                    exitMainMenu = true;
                    break;
                default:
                    System.out.println("Please enter one of the provided numbers above");
                    // prompt them with the questions again
                    this.start();
            }
        }
    }

}
