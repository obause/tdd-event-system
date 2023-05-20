package com.java.eventsystem.tddeventsystem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerServiceTest {
    @Test
    void createCustomer() {
        CustomerService service = new CustomerService();
        Customer customer = new Customer("Max Mustermann", "Addresse 1");
        service.createCustomer(customer);
        assertEquals(1, service.getAllCustomers().size());
    }
}