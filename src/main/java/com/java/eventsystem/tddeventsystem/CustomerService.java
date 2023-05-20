package com.java.eventsystem.tddeventsystem;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
	private Map<String, Customer> customers = new HashMap<>();

    public void createCustomer(String name, String address) {
    	Customer customer = new Customer(name, address);
    	customers.put(customer.getName(), customer);
    }
    
    public Customer getCustomer(String name) {
    	Customer customer = customers.get(name);
		return customer;
    	
    }

    public List<Customer> getAllCustomers() {
    	List<Customer> customerList = new ArrayList<Customer>(customers.values());
        return customerList;
    }
}
