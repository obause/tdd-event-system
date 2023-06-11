package com.java.eventsystem.tddeventsystem;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;

public class EventServiceTest {
	@Test
	void customerModel() {
		Customer customer = new Customer("Max Mustermann", "Addresse 1");
		assertEquals("Max Mustermann", customer.getName());
		assertEquals("Addresse 1", customer.getAddress());
	}
	
	@Test
	void eventModel() {
		LocalDateTime dateTime = LocalDateTime.now();
		Event event = new Event("Sample Event", dateTime, 100.0, 50, "organizer@mail.de");
		assertEquals("Sample Event", event.getTitle());
		assertEquals(dateTime, event.getDateTime());
		assertEquals(100.0, event.getTicketPrice());
		assertEquals(50, event.getTotalSeats());
		assertEquals(50, event.getAvailableSeats());
	}
	
	@Test
	void bookingModel() {
		Customer customer = new Customer("Max Mustermann", "Addresse 1");
		Booking booking = new Booking(customer, 2, 5);
		assertEquals(customer, booking.getCustomer());
		assertEquals(2, booking.getBookedSeats());
		assertEquals(5, booking.getTotalAmount());
	}
	
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
	
	@Test
    void createEvent() {
        EventService service = new EventService();
        int eventId = service.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
        assertEquals("Sample Event", service.getEventById(eventId).getTitle());
    }
	
	@Test
    void getAllEvents() {
		EventService service = new EventService();
		int event1Id = service.createEvent("Sample Event 1", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
        int event2Id = service.createEvent("Sample Event 2", LocalDateTime.now(), 200.0, 25, "organizer@mail.de");
        
        List<Event> events = service.getAllEvents();
        assertEquals(2, events.size());
        assertEquals(event1Id, events.get(0).getId());
        assertEquals(event2Id, events.get(1).getId());
    }
	
	@Test
    void getAvailableSeats() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
		assertEquals(50, eventService.getAvailableSeatsById(eventId));
		eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 10);
        assertEquals(40, eventService.getAvailableSeatsById(eventId));
    }
	
	@Test
    void createBooking() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
		
		int bookingId = eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 1);
		
        assertEquals(0, bookingId);
    }
	
	@Test
    void getBooking() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
		
		int bookingId = eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 1);
		
		Booking booking = eventService.getBooking(customerService.getCustomer("Max Mustermann"), eventService.getEventById(eventId));
        assertEquals(bookingId, booking.getId());
    }
	
	@Test
    void createBookingTestAmount() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
		
		int bookingId = eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 2);
		
		Booking booking = eventService.getBooking(bookingId, eventService.getEventById(eventId));
		
        assertEquals(200.0, booking.getTotalAmount());
    }
	
	@Test
    void getWrongBooking() {
		CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        
		EventService eventService = new EventService();
		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
		
		int bookingId = 999;

		assertThrows(IllegalArgumentException.class, () -> eventService.getBooking(customerService.getCustomer("Max Mustermann"), eventService.getEventById(eventId)));		
		assertThrows(IllegalArgumentException.class, () -> eventService.getBooking(bookingId, eventService.getEventById(eventId)));
    }
	
	@Test
    void createBookingWithInsufficientSeats() {
        EventService eventService = new EventService();
        int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
        CustomerService customerService = new CustomerService();
        customerService.createCustomer("Max Mustermann", "Addresse 1"); 
        
        assertThrows(IllegalArgumentException.class, () -> eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 51));
    }
	
	@Test
    void createmultipleBookingsWithSameCustomer() {
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
    void saveAndLoadData() {
        DataStore dataStore = new DataStore();
        CustomerService customerService = new CustomerService();
        EventService eventService = new EventService();
        customerService.createCustomer("Max Mustermann", "Addresse 1");
        int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
        int bookingId = eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 10);
        
        dataStore.saveData(customerService, eventService);
        CustomerService loadedCustomerService = new CustomerService();
        EventService loadedEventService = new EventService();
        dataStore.loadData(loadedCustomerService, loadedEventService);
        
        assertEquals(1, loadedCustomerService.getAllCustomers().size());
        assertEquals(1, loadedEventService.getAllEvents().size());
    }
	
	@Test
    void rejectBookingIfCustomerIsBlacklisted() {
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
	
//	@Test
//    void sendEmailIfBookingIsAtLeast10PercentOfTotalSeats() {
//		CustomerService customerService = new CustomerService();
//        customerService.createCustomer("Max Mustermann", "Addresse 1");
//		EventService eventService = new EventService();
//		int eventId = eventService.createEvent("Sample Event", LocalDateTime.now(), 100.0, 50, "organizer@mail.de");
//		
//		EmailService emailService = Mockito.mock(EmailService.class);
//		
//		int bookingId = eventService.createBooking(eventService.getEventById(eventId), customerService.getCustomer("Max Mustermann"), 20);
//		Booking booking = eventService.getBooking(bookingId, eventService.getEventById(eventId));
//
//        Mockito.verify(emailService, Mockito.times(1)).sendEmail("organizer@example.com", "New Booking", "A new booking has been made for 20 seats.");
//    }
}
