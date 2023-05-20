package com.java.eventsystem.tddeventsystem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

public class EventServiceTest {
	@Test
    void createEvent() {
        EventService service = new EventService();
        int eventId = service.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50);
        assertEquals("Sample Event", service.getEventById(eventId).getTitle());
    }
	
	@Test
    void getAllEvents() {
		EventService service = new EventService();
		service.createEvent("Sample Event 1", LocalDateTime.now(), 100.0, 50);
        service.createEvent("Sample Event 2", LocalDateTime.now(), 200.0, 25);
        
        assertEquals(2, service.getAllEvents().size());
    }
	
	@Test
    void getAvailableSeats() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50);
		assertEquals(50, eventService.getAvailableSeatsById(eventId));
		eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 10);
        assertEquals(40, eventService.getAvailableSeatsById(eventId));
    }
	
	@Test
    void createBooking() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50);
		
		int bookingId = eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 1);
		
        assertEquals(0, bookingId);
    }
	
	@Test
    void getBooking() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50);
		
		int bookingId = eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 1);
		
		Booking booking = eventService.getBooking(customerService.getCustomer("Max Mustermann"), eventService.getEventById(eventId));
		
        assertEquals(bookingId, booking.getId());
    }
	
	@Test
    void createBookingTestAmount() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50);
		
		int bookingId = eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 2);
		
		Booking booking = eventService.getBooking(bookingId, eventService.getEventById(eventId));
		
        assertEquals(200.0, booking.getTotalAmount());
    }
	
	@Test
    void getWrongBooking() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50);
		
		int bookingId = 999;

		assertThrows(IllegalArgumentException.class, () -> eventService.getBooking(customerService.getCustomer("Max Mustermann"), eventService.getEventById(eventId)));		
		assertThrows(IllegalArgumentException.class, () -> eventService.getBooking(bookingId, eventService.getEventById(eventId)));
    }
	
	@Test
    void createBookingWithInsufficientSeats() {
        EventService eventService = new EventService();
        int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50);
        CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
        assertThrows(IllegalArgumentException.class, () -> {
        	eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 51);
        });
    }
}
