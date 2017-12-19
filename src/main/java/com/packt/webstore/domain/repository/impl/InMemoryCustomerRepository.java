package com.packt.webstore.domain.repository.impl;

import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

    List<Customer> listOfCustomers = new ArrayList<>();

    public InMemoryCustomerRepository() {
        Customer customerAndrzej = new Customer("C1234", "Andrzej Piasecki", "Popieluszki 51, 97-200 Tomaszow Mazowiecki");
        Customer customerMikolaj = new Customer("C1235", "Mikolaj Zegar", "Zakatna 48, 97-200 Tomaszow Mazowiecki");
        Customer customerTymon = new Customer("C1236", "Tymon Nowak", "Dabrowskiego 24, 50-451 Wroclaw");

        listOfCustomers.add(customerAndrzej);
        listOfCustomers.add(customerMikolaj);
        listOfCustomers.add(customerTymon);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return listOfCustomers;
    }
}
