package com.comp6231.zhaozhe.server;

import com.comp6231.common.ILibrary;
import com.comp6231.common.UDPServer;

public class LibraryServerDispatcher implements ILibrary{
	
	/*
	 * Mark - Driver - Methods
	 */
	 
	public static void main(String[] args) {
		new LibraryServerDispatcher();
	}
	
	/*
	 * Mark - Constructors
	 */
	
	public LibraryServerDispatcher() {
		initPortal();
	}
	
	/*
	 * Mark - Portal - Properties
	 */
	
	private UDPServer portal;
	 
	/*
	 * Mark - Portal - Methods
	 */
	
	private void initPortal() {
		portal = new UDPServer();
		portal.setPortNumber(7000);
		portal.setLibrary(this);
		portal.start();
	}
	
	
	@Override
	public String registerUser(String instName, String firstName,
			String lastName, String emailAddress, String phoneNumber,
			String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String reserveBook(String instName, String username,
			String password, String bookName, String authorName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setDuration(String instName, String adminUsername,
			String adminPassword, String username, String bookName,
			String authorName, int days) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String reserveInterLibrary(String instName, String username,
			String password, String bookName, String authorName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNonRetuners(String instName, String adminUsername,
			String adminPassword, int days) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
