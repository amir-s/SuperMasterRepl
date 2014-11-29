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
