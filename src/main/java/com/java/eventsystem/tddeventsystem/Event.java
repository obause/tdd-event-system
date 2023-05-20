package com.java.eventsystem.tddeventsystem;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Event {
	private static int nextId = 0;
	private int id;
    private String title;
    private LocalDateTime dateTime;
    private double ticketPrice;
    private int totalSeats;
    private int availableSeats;
    private List<Booking> bookings = new ArrayList<>();
    
	public Event(String title, LocalDateTime dateTime, double ticketPrice, int totalSeats) {
		super();
		this.id = nextId;
		this.title = title;
		this.dateTime = dateTime;
		this.ticketPrice = ticketPrice;
		this.totalSeats = totalSeats;
		this.availableSeats = totalSeats;
		nextId++;
	}
	
	public static int getNextId() {
		return nextId;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public double getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public int getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
	public int getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
}
