package com.java.eventsystem.tddeventsystem;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;
import java.util.List;

public class EventSystemTest {
	@Test
	void customerModelTest() {
		Customer customer = new Customer("Max Mustermann", "Addresse 1");
		assertEquals("Max Mustermann", customer.getName());
		assertEquals("Addresse 1", customer.getAddress());
	}
	
	@Test
	void eventModelTest() {
		LocalDateTime dateTime = LocalDateTime.now();
		Event event = new Event("Sample Event", dateTime, 100.0, 50, "organizer@mail.de");
		assertEquals("Sample Event", event.getTitle());
		assertEquals(dateTime, event.getDateTime());
		assertEquals(100.0, event.getTicketPrice());
		assertEquals(50, event.getTotalSeats());
		assertEquals(50, event.getAvailableSeats());
	}
	
	@Test
	void bookingModelTest() {
		Customer customer = new Customer("Max Mustermann", "Addresse 1");
		Booking booking = new Booking(customer, 2, 5);
		assertEquals(customer, booking.getCustomer());
		assertEquals(2, booking.getBookedSeats());
		assertEquals(5, booking.getTotalAmount());
	}
	
	@Test
    void createCustomerTest() {
        CustomerService service = new CustomerService();
        String customerName = "Max Mustermann";
        String customerAddress = "Addresse 1";
        service.createCustomer(customerName, customerAddress);
        Customer customer = service.getCustomer("Max Mustermann");
        assertSame(customer, service.getCustomer(customerName));
    }
    
    @Test
    void getAllCustomersTest() {
        CustomerService service = new CustomerService();
        service.createCustomer("Max Mustermann", "Addresse 1");
        service.createCustomer("Maxi Mustermann", "Addresse 2");
        
        assertEquals(2, service.getAllCustomers().size());
    }
	
	@Test
    void createEventTest() {
        EventService service = new EventService();
        int eventId = service.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
        assertEquals("Sample Event", service.getEventById(eventId).getTitle());
    }
	
	@Test
    void getAllEventsTest() {
		EventService service = new EventService();
		int event1Id = service.createEvent("Sample Event 1", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
        int event2Id = service.createEvent("Sample Event 2", LocalDateTime.now(), 200.0, 25, "organizer@mail.de");
        
        List<Event> events = service.getAllEvents();
        assertEquals(2, events.size());
        assertEquals(event1Id, events.get(0).getId());
        assertEquals(event2Id, events.get(1).getId());
    }
	
	@Test
    void getAvailableSeatsTest() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
		assertEquals(50, eventService.getAvailableSeatsById(eventId));
		eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 10);
        assertEquals(40, eventService.getAvailableSeatsById(eventId));
    }
	
	@Test
    void createBookingTest() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
		
		int bookingId = eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 1);
		Booking booking = eventService.getBooking(customerService.getCustomer("Max Mustermann"), eventService.getEventById(eventId));
		
        assertEquals(booking.getId(), bookingId);
    }
	
	@Test
    void createBookingAmountIsCalculatedTest() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
		
		int bookingId = eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 2);
		
		Booking booking = eventService.getBooking(bookingId, eventService.getEventById(eventId));
		
        assertEquals(200.0, booking.getTotalAmount());
    }
	
	@Test
    void getNonexistingBookingExceptionTest() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
		eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 2);
		
		int bookingId = 9999;

		assertThrows(IllegalArgumentException.class, () -> eventService.getBooking(customerService.getCustomer("Maxi Mustermann"), eventService.getEventById(eventId)));		
		assertThrows(IllegalArgumentException.class, () -> eventService.getBooking(bookingId, eventService.getEventById(eventId)));
    }
	
	@Test
    void createBookingWithInsufficientSeatsExceptionTest() {
        EventService eventService = new EventService();
        int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
        CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1"); 
        
        assertThrows(IllegalArgumentException.class, () -> eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 51));
    }
	
	@Test
    void createmultipleBookingsWithSameCustomerTest() {
        EventService eventService = new EventService();
        int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
        CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
        int bookingId = eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 10);
        int booking2Id = eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 5);
        
        assertEquals(35, eventService.getEventById(eventId).getAvailableSeats());
        assertEquals(1, eventService.getEventById(eventId).getBookings().size());
        assertEquals(15, eventService.getBooking(booking2Id, eventService.getEventById(eventId)).getBookedSeats());
        assertThrows(IllegalArgumentException.class, () -> eventService.getBooking(bookingId, eventService.getEventById(eventId)));
    }
	
	@Test
    void saveAndLoadDataTest() {
        DataStore dataStore = new DataStore();
        CustomerService customerService = new CustomerService();
        EventService eventService = new EventService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        customerService.createCustomer("Maxi Mustermann", "Addresse 2");
        int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
        int event2Id = eventService.createEvent("Second Event", LocalDateTime.now(), 50.0, 25, "organizer2@mail.de");
        eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 10);
        eventService.createBooking(eventService.getEventById(event2Id), customerService.getCustomer("Maxi Mustermann"), 10);
        
        dataStore.saveData(customerService, eventService);
        CustomerService loadedCustomerService = new CustomerService();
        EventService loadedEventService = new EventService();
        dataStore.loadData(loadedCustomerService, loadedEventService);
        
        assertEquals(2, loadedCustomerService.getAllCustomers().size());
        assertEquals(2, loadedEventService.getAllEvents().size());
    }
	
	@Test
    void rejectBookingIfCustomerIsBlacklistedTest() {
        Blacklist blacklist = Mockito.mock(Blacklist.class);
        EventService eventService = new EventService();
        CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
        
        eventService.addBlacklist(blacklist);
        
        Mockito.when(blacklist.isBlacklisted("Max Mustermann")).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 10));
        assertEquals("Customer is blacklisted", exception.getMessage());
    }
	
	@Test
    void rejectBookingIfCustomerIsNotBlacklistedTest() {
        Blacklist blacklist = Mockito.mock(Blacklist.class);
        EventService eventService = new EventService();
        CustomerService customerService = new CustomerService();
        customerService.createCustomer("Maxi Mustermann", "Addresse 2");
        int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
        
        eventService.addBlacklist(blacklist);
        Mockito.when(blacklist.isBlacklisted("Max Mustermann")).thenReturn(true);

        int bookingId = eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Maxi Mustermann"), 1);
		Booking booking = eventService.getBooking(customerService.getCustomer("Maxi Mustermann"), eventService.getEventById(eventId));
        assertEquals(bookingId, booking.getId());
    }
	
	@Test
    void sendEmailIfBookingIsAtLeast10PercentOfTotalSeatsTest() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 100, "organizer@mail.de");
		
		EmailService emailService = Mockito.mock(EmailService.class);
		eventService.addEmailService(emailService);
		
		eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 10);
		eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 50);

        Mockito.verify(emailService, Mockito.times(2)).sendEmail("organizer@mail.de", "New Booking", "A new booking has been made with more than 10% of the total seats of the event.");
    }
	
	@Test
    void sendEmailIfBookingIsLower10PercentOfTotalSeatsTest() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 100, "organizer@mail.de");
		
		EmailService emailService = Mockito.mock(EmailService.class);
		eventService.addEmailService(emailService);
		
		eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 9);
		//Booking booking = eventService.getBooking(bookingId, eventService.getEventById(eventId));

        Mockito.verify(emailService, Mockito.times(0)).sendEmail("organizer@mail.de", "New Booking", "A new booking has been made with more than 10% of the total seats of the event.");
    }
}
