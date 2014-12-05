package com.comp6231.common;

import com.heartbeat.Heart;

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
	 * Mark - Basic - Methods
	 */

	public void start() {
		interReceiver.start();
		startHeartBeats();
	}
	
	/*
	 * Mark - Heart Beats - Properties
	 */
	 
	private Heart heartBeats;
	private int heartBeatsPortNumber;
	
	/*
	 * Mark - Heart Beats - Methods
	 */
	
	
	public void startHeartBeats () {
		heartBeats = new Heart("localhost", heartBeatsPortNumber);
		heartBeats.beat();
	}
	 
	/*
	 * Mark - Heart Beats - Getters & Setters
	 */
	
	public int getHeartBeatsPortNumber() {
		return heartBeatsPortNumber;
	}

	public void setHeartBeatsPortNumber(int heartBeatsPortNumber) {
		this.heartBeatsPortNumber = heartBeatsPortNumber;
	}

	 
	
	/*
	 * Mark - Request Handle  - Properties
	 */
	 

	private InterReceiver interReceiver;
	
	/*
	 * Mark - Request Handle - Methods
	 */
	 
	public void initInterReceiver() {
		interReceiver = new InterReceiver();
		
		// registerUser
		interReceiver.addHandler(new InterReceiverHandler() {
			
			@Override
			public InterMessage handle(InterMessage receiveMessage) {
				InterMessage returnMessage = new InterMessage();
				
				System.out.println("UDPServer : Event : Did receive handle request");
				
				String instName = receiveMessage.getParameter("instName");
				String firstName = receiveMessage.getParameter("firstName");
				String lastName = receiveMessage.getParameter("lastName");
				String emailAddress = receiveMessage.getParameter("emailAddress");
				String phoneNumber = receiveMessage.getParameter("phoneNumber");
				String username = receiveMessage.getParameter("username");
				String password = receiveMessage.getParameter("password");
				
				String value = library.registerUser(instName, firstName, lastName, emailAddress, phoneNumber, username, password);
				
				returnMessage.addParameter(InterMessage.KEY_RETURN_VALUE, value);

				return returnMessage;
			}
		}, InterMessage.TYPE_REGISTE_USER);
		
		// reserveBook
		interReceiver.addHandler(new InterReceiverHandler() {
			
			@Override
			public InterMessage handle(InterMessage receiveMessage) {
				InterMessage returnMessage = new InterMessage();
				
				String instName = receiveMessage.getParameter("instName");
				String username = receiveMessage.getParameter("username");
				String password = receiveMessage.getParameter("password");
				String bookName = receiveMessage.getParameter("bookName");
				String authorName = receiveMessage.getParameter("authorName");
				
				String value = library.reserveBook(instName, username, password, bookName, authorName);
				
				returnMessage.addParameter(InterMessage.KEY_RETURN_VALUE, value);
				
				return returnMessage;
			}
		}, InterMessage.TYPE_RESERVE_BOOK);
		
		// setDuration
		interReceiver.addHandler(new InterReceiverHandler() {
			
			@Override
			public InterMessage handle(InterMessage receiveMessage) {
				InterMessage returnMessage = new InterMessage();
				
				String instName = receiveMessage.getParameter("instName");
				String adminUsername = receiveMessage.getParameter("adminUsername");
				String adminPassword = receiveMessage.getParameter("adminPassword");
				String username = receiveMessage.getParameter("username");
				String bookName = receiveMessage.getParameter("bookName");
				String authorName = receiveMessage.getParameter("authorName");
				String days = receiveMessage.getParameter("days");
				
				String value = library.setDuration(instName, adminUsername, adminPassword, username, bookName, authorName, Integer.valueOf(days));
				
				returnMessage.addParameter(InterMessage.KEY_RETURN_VALUE, value);

				return returnMessage;
			}
		}, InterMessage.TYPE_SET_DURATION);
		
		// reserveInterLibrary
		interReceiver.addHandler(new InterReceiverHandler() {
			
			@Override
			public InterMessage handle(InterMessage receiveMessage) {
				InterMessage returnMessage = new InterMessage();
				
				String instName = receiveMessage.getParameter("instName");
				String username = receiveMessage.getParameter("username");
				String password = receiveMessage.getParameter("password");
				String bookName = receiveMessage.getParameter("bookName");
				String authorName = receiveMessage.getParameter("authorName");
				
				String value = library.reserveInterLibrary(instName, username, password, bookName, authorName);
				
				returnMessage.addParameter(InterMessage.KEY_RETURN_VALUE, value);

				return returnMessage;
			}
		}, InterMessage.TYPE_RESERVE_INTER_LIBRARY);
		
		// getNonRetuners
		interReceiver.addHandler(new InterReceiverHandler() {
			
			@Override
			public InterMessage handle(InterMessage receiveMessage) {
				InterMessage returnMessage = new InterMessage();
				
				String instName = receiveMessage.getParameter("instName");
				String adminUsername = receiveMessage.getParameter("adminUsername");
				String adminPassword = receiveMessage.getParameter("adminPassword");
				String days = receiveMessage.getParameter("days");
				
				String value = library.getNonRetuners(instName, adminUsername, adminPassword, Integer.valueOf(days));
				
				returnMessage.addParameter(InterMessage.KEY_RETURN_VALUE, value);

				return returnMessage;
			}
		}, InterMessage.TYPE_GET_NON_RETURNERS);
	}

	public void setPortNumber(int portNumber) {
		interReceiver.setPortNumber(portNumber);
	}
	
}
