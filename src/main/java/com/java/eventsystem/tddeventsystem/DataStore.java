package com.java.eventsystem.tddeventsystem;

import java.io.*;
import java.util.List;

public class DataStore {
    public void saveData(CustomerService customerService, EventService eventService) {
        try {
            FileOutputStream fileOut = new FileOutputStream("data.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(customerService.getAllCustomers());
            out.writeObject(eventService.getAllEvents());
            //out.writeObject(bookingService.getAllBookings());
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void loadData(CustomerService customerService, EventService eventService) {
        try {
            FileInputStream fileIn = new FileInputStream("data.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            List<Customer> customers = (List<Customer>) in.readObject();
            List<Event> events = (List<Event>) in.readObject();
            //List<Booking> bookings = (List<Booking>) in.readObject();
            customerService.setCustomers(customers);
            eventService.setEvents(events);
            //bookingService.setBookings(bookings);
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
    }
}
