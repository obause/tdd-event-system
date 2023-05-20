package com.java.eventsystem.tddeventsystem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerServiceTest {
    @Test
    void createCustomer() {
        CustomerService service = new CustomerService();
        String customerName = "Max Mustermann";
        String customerAddress = "Addresse 1";
        service.createCustomer(customerName, customerAddress);
        assertEquals(customerAddress, service.getCustomer(customerName).getAddress());
    }
    
    @Test
    void getAllCustomers() {
        CustomerService service = new CustomerService();
        service.createCustomer("Max Mustermann", "Addresse 1");
        service.createCustomer("Maxi Mustermann", "Addresse 2");
        
        assertEquals(2, service.getAllCustomers().size());
    }
    
    
}