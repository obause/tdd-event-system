package com.java.eventsystem.tddeventsystem;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventService {
	private Map<Integer, Event> events = new HashMap<>();
	
	public int createEvent(String title, LocalDateTime dateTime, double ticketPrice, int totalSeats) {
		Event event = new Event(title, dateTime, ticketPrice, totalSeats);
        events.put(event.getId(), event);
        return event.getId();
    }
	
	public Event getEventById(int id) {
		Event event = events.get(id);
		return event;
	}
	
	public List<Event> getAllEvents() {
		List<Event> eventsList = new ArrayList<Event>(events.values());
        return eventsList;
	}
	
	public int getTotalSeatsById(int id) {
		Event event = events.get(id);
		return event.getTotalSeats();
	}
	
	public int getAvailableSeatsById(int id) {
		Event event = events.get(id);
		return event.getAvailableSeats();
	}
	
	public int createBooking(Event event, Customer customer, int bookedSeats) {
		double totalAmount = event.getTicketPrice() * bookedSeats;
		Booking booking = new Booking(customer, bookedSeats, totalAmount);
        List<Booking> bookings = event.getBookings();
        bookings.add(booking);
        if (booking.getBookedSeats() > event.getAvailableSeats()) {
        	throw new IllegalArgumentException("Not enough seats available.");
        } else {
        	event.setAvailableSeats(event.getAvailableSeats() - booking.getBookedSeats());
        	event.setBookings(bookings);
            return booking.getId();
        }
    }
	
	public Booking getBooking(Customer customer, Event event) throws IllegalArgumentException{
		List<Booking> bookings = event.getBookings();
		for (Booking booking : bookings) {
			if(booking.getCustomer().equals(customer)) {
				return booking;
			}
		}
		throw new IllegalArgumentException("Booking with this ID does not exist.");
	}
	
	public Booking getBooking(int id, Event event) {
		List<Booking> bookings = event.getBookings();
		for (Booking booking : bookings) {
			if(booking.getId() == id) {
				return booking;
			}
		}
		throw new IllegalArgumentException("Booking with this ID does not exist.");
	}
}
