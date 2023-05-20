package com.java.eventsystem.tddeventsystem;

import java.time.LocalDateTime;

public class Event {
	private String identifier;
    private String title;
    private LocalDateTime dateTime;
    private double ticketPrice;
    private int totalSeats;
    
	public Event(String identifier, String title, LocalDateTime dateTime, double ticketPrice, int totalSeats) {
		super();
		this.identifier = identifier;
		this.title = title;
		this.dateTime = dateTime;
		this.ticketPrice = ticketPrice;
		this.totalSeats = totalSeats;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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
}
