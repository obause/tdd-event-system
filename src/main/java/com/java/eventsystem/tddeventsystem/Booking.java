package com.java.eventsystem.tddeventsystem;

public class Booking {
	private String identifier;
    private Customer customer;
    private Event event;
    private int bookedSeats;
    private double totalAmount;
    
	public Booking(String identifier, Customer customer, Event event, int bookedSeats, double totalAmount) {
		super();
		this.identifier = identifier;
		this.customer = customer;
		this.event = event;
		this.bookedSeats = bookedSeats;
		this.totalAmount = totalAmount;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public int getBookedSeats() {
		return bookedSeats;
	}
	public void setBookedSeats(int bookedSeats) {
		this.bookedSeats = bookedSeats;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
}
