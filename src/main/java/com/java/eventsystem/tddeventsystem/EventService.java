package com.java.eventsystem.tddeventsystem;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventService {
	private Map<Integer, Event> events = new HashMap<>();
	private Blacklist blacklist;
	//private EmailService emailService;
	
//	public EventService(Blacklist blacklist, EmailService emailService) {
//		// Customer Service mit rein?
//		// Daten laden?
//		this.blacklist = blacklist;
//		this.emailService = emailService;
//	}
	
	public int createEvent(String title, LocalDateTime dateTime, double ticketPrice, int totalSeats, String organizerEmail) {
		Event event = new Event(title, dateTime, ticketPrice, totalSeats, organizerEmail);
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
	
	public int getAvailableSeatsById(int id) {
		Event event = events.get(id);
		return event.getAvailableSeats();
	}
	
	public int createBooking(Event event, Customer customer, int bookedSeats) {
		double totalAmount = event.getTicketPrice() * bookedSeats;
		Booking booking = new Booking(customer, bookedSeats, totalAmount);
        List<Booking> bookings = event.getBookings();
        
        if (blacklist != null && blacklist.isBlacklisted(booking.getCustomer().getName())) {
            throw new IllegalArgumentException("Customer is blacklisted");
        } 
        else if (booking.getBookedSeats() > event.getAvailableSeats()) {
        	throw new IllegalArgumentException("Not enough seats available.");
        } 
        else {
        	Booking existingBooking = null;
        	for (Booking bookingItem : bookings) {
        		if (bookingItem.getCustomer().equals(customer)) {
        			existingBooking = bookingItem;
        		}
        	}
        	if (existingBooking != null) {
        		booking.setBookedSeats(bookedSeats + existingBooking.getBookedSeats());
    			booking.setTotalAmount(totalAmount + existingBooking.getTotalAmount());
    			bookings.remove(existingBooking);
        	}
        	bookings.add(booking);
//        	if (booking.getBookedSeats() >= event.getTotalSeats() * 0.10) {
//                emailService.sendEmail(event.getOrganizerEmail(), "New Booking", "A new booking has been made for " + booking.getBookedSeats() + " seats.");
//            }
        	event.setAvailableSeats(event.getAvailableSeats() - bookedSeats);
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
	
	public void setEvents(List<Event> events) {
		for (Event event : events) {
    		this.events.put(event.getId(), event);
    	}
	}
	
	public void addBlacklist(Blacklist blacklist) {
		this.blacklist = blacklist;
	}
}
