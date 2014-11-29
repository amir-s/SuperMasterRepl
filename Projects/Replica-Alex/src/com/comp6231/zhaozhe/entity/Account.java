package com.comp6231.zhaozhe.entity;

import java.util.ArrayList;
import java.util.List;

public class Account {
	
	/*
	 * Mark - Constructors
	 */
	
	public Account() {
		super();
		init();
	}
	
	public Account(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		init();
	}
	
	public Account(String username, String password, 
				   String firstName, String lastName, String emailAddress, String phoneNumber) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		init();
	}
	
	/*
	 * Mark - Initialization
	 */
	
	public void init() {
		reservations = new ArrayList<Reservation>();
	}
	
	/*
	 * Mark - Basic - Properties
	 */
	
	public static final String ADMIN_USERNAME = "Admin";
	
	private String username;
	private String password;	
	
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	private List<Reservation> reservations;
	
	/*
	 * Mark - Basic - Getters & Setters
	 */
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}
	
	public void addReservation(Reservation reservation) {
		reservations.add(reservation);
	}
}
