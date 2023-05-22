package com.java.eventsystem.tddeventsystem;

import java.io.Serializable;

public class Booking implements Serializable {
	private int id;
    private Customer customer;
    private int bookedSeats;
    private double totalAmount;
    private static int nextId = 0;
    
	public Booking(Customer customer, int bookedSeats, double totalAmount) {
		super();
		this.id = getNextId();
		this.customer = customer;
		this.bookedSeats = bookedSeats;
		this.totalAmount = totalAmount;
		nextId++;
	}
	
	public static int getNextId() {
		return nextId;
	}
	public int getId() {
		return id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
