package com.java.eventsystem.tddeventsystem;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
	private List<Customer> customers = new ArrayList<>();

    public void createCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }
}
