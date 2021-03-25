package com.company.menu;

import com.company.api.AdminResource;
import com.company.model.FreeRoom;
import com.company.model.Room;
import com.company.model.RoomType;
import com.company.service.ReservationService;

import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;

public class AdminMenu {
    Boolean exitAdminMenu;
    Scanner scanner;
    public AdminMenu(Scanner scannerPara) {
        this.scanner = scannerPara;
    }

    private void seeAllCustomers(){
        AdminResource.getInstance().getAllCustomers().forEach(System.out::println);
    }

    private void seeAllRooms(){
        ReservationService.getInstance().printAllRooms();
    }

    private void seeAllReservations(){
        ReservationService.getInstance().printAllReservations();
    }

    private void addARoom(){
        System.out.println("Enter a room number");
        String roomNumber = scanner.nextLine();
        while (ReservationService.getInstance().getRoomNumbers().contains(roomNumber)){
            System.out.println("There is already a room with that number, enter a different one please");
            roomNumber = scanner.nextLine();
        }
        System.out.println("Enter your room price as a double");
        String roomPrice = scanner.nextLine();
        System.out.println("Enter your room type");
        String roomType = scanner.nextLine();
        while(!roomType.toLowerCase(Locale.ROOT).equals("single") && !roomType.toLowerCase(Locale.ROOT).equals("double")){
            System.out.println("Please chose either single or double");
            roomType = scanner.nextLine();
        }
        if(roomPrice.equals("0.0") || roomPrice.equals("free")){
            ReservationService.getInstance().addRoom(new FreeRoom(roomNumber, RoomType.valueOf(roomType.toUpperCase(Locale.ROOT))));
        }
        else {
            ReservationService.getInstance().addRoom(new Room(roomNumber, Double.valueOf(roomPrice),RoomType.valueOf(roomType.toUpperCase(Locale.ROOT))));
        }
        System.out.println("Your room has been added");

    }

    public void start() throws ParseException {
        exitAdminMenu = false;
        while(!exitAdminMenu) {
            System.out.println("Admin Menu");
            System.out.println("--------------------------------");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            System.out.println("--------------------------------");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    seeAllCustomers();
                    break;
                case "2":
                    seeAllRooms();
                    break;
                case "3":
                    seeAllReservations();
                    break;
                case "4":
                    addARoom();
                    break;
                case "5":
                    exitAdminMenu = true;
                    MainMenu mainMenu = new MainMenu(scanner);
                    mainMenu.start();
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }
}
