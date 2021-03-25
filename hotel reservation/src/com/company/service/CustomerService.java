package com.company.service;

import com.company.model.Customer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class CustomerService {

    // static reference
    private static final CustomerService customerService = new CustomerService();

    // collection of customers
    Collection<Customer> customers = new HashSet<Customer>();

    public static CustomerService getInstance(){
        return customerService;
    }

    public void addCustomer(String email, String firstName, String lastName){
            customers.add(new Customer(firstName, lastName, email));
            System.out.println("Your account has been validated and added");
    }

    public Customer getCustomer(String customerEmail){
        Optional<Customer> customer = customers.stream().filter(customer1 ->
                customer1.getEmail().equals(customerEmail)).findFirst();
        return customer.orElse(null);
    }

    public Collection<Customer> getAllCustomers(){
        return customers;
    }
}
