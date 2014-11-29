package com.comp6231.common;

public class UDPServer{
	
	/*
	 * Mark - Constructors
	 */
	
	public UDPServer() {
		initInterReceiver();
	}
	
	/*
	 * Mark - Context - Properties
	 */
	 
	private ILibrary library;
	
	/*
	 * Mark - Context - Getters & Setters
	 */
	 
	public ILibrary getLibrary() {
		return library;
	}

	public void setLibrary(ILibrary library) {
		this.library = library;
	}
	
	/*
	 * Mark - Basic - Properties
	 */
	 

	private InterReceiver interReceiver;
	
	/*
	 * Mark - Basic - Methods
	 */
	 
	public void initInterReceiver() {
		interReceiver = new InterReceiver();
		
		// registerUser
		interReceiver.addHandler(new InterReceiverHandler() {
			
			@Override
			public void handle(InterMessage receiveMessage, InterMessage returnMessage) {
				String instName = receiveMessage.getParameter("instName");
				String firstName = receiveMessage.getParameter("firstName");
				String lastName = receiveMessage.getParameter("lastName");
				String emailAddress = receiveMessage.getParameter("emailAddress");
				String phoneNumber = receiveMessage.getParameter("phoneNumber");
				String username = receiveMessage.getParameter("username");
				String password = receiveMessage.getParameter("password");
				
				String value = library.registerUser(instName, firstName, lastName, emailAddress, phoneNumber, username, password);
				
				returnMessage.addParameter(InterMessage.KEY_RETURN_VALUE, value);
			}
		}, InterMessage.TYPE_REGISTE_USER);
		
		// reserveBook
		interReceiver.addHandler(new InterReceiverHandler() {
			
			@Override
			public void handle(InterMessage receiveMessage, InterMessage returnMessage) {
				String instName = receiveMessage.getParameter("instName");
				String username = receiveMessage.getParameter("username");
				String password = receiveMessage.getParameter("password");
				String bookName = receiveMessage.getParameter("bookName");
				String authorName = receiveMessage.getParameter("authorName");
				
				String value = library.reserveBook(instName, username, password, bookName, authorName);
				
				returnMessage.addParameter(InterMessage.KEY_RETURN_VALUE, value);
			}
		}, InterMessage.TYPE_RESERVE_BOOK);
		
		// setDuration
		interReceiver.addHandler(new InterReceiverHandler() {
			
			@Override
			public void handle(InterMessage receiveMessage, InterMessage returnMessage) {
				String instName = receiveMessage.getParameter("instName");
				String username = receiveMessage.getParameter("username");
				String password = receiveMessage.getParameter("password");
				String bookName = receiveMessage.getParameter("bookName");
				String authorName = receiveMessage.getParameter("authorName");
				
				String value = library.reserveBook(instName, username, password, bookName, authorName);
				
				returnMessage.addParameter(InterMessage.KEY_RETURN_VALUE, value);
			}
		}, InterMessage.TYPE_RESERVE_BOOK);
		
		// reserveInterLibrary
		interReceiver.addHandler(new InterReceiverHandler() {
			
			@Override
			public void handle(InterMessage receiveMessage, InterMessage returnMessage) {
				String instName = receiveMessage.getParameter("instName");
				String username = receiveMessage.getParameter("username");
				String password = receiveMessage.getParameter("password");
				String bookName = receiveMessage.getParameter("bookName");
				String authorName = receiveMessage.getParameter("authorName");
				
				String value = library.reserveInterLibrary(instName, username, password, bookName, authorName);
				
				returnMessage.addParameter(InterMessage.KEY_RETURN_VALUE, value);
			}
		}, InterMessage.TYPE_RESERVE_BOOK);
		
		// getNonRetuners
		interReceiver.addHandler(new InterReceiverHandler() {
			
			@Override
			public void handle(InterMessage receiveMessage, InterMessage returnMessage) {
				String instName = receiveMessage.getParameter("instName");
				String adminUsername = receiveMessage.getParameter("adminUsername");
				String adminPassword = receiveMessage.getParameter("adminPassword");
				String days = receiveMessage.getParameter("days");
				
				String value = library.getNonRetuners(instName, adminUsername, adminPassword, Integer.valueOf(days));
				
				returnMessage.addParameter(InterMessage.KEY_RETURN_VALUE, value);
			}
		}, InterMessage.TYPE_RESERVE_BOOK);
				
			
		
	}
	
	public void start() {
		interReceiver.start();
	}
	
	/*
	 * Mark - Basic - Getters & Setters
	 */

	public void setPortNumber(int portNumber) {
		interReceiver.setPortNumber(portNumber);
	}
	
	
	
	
	
}
