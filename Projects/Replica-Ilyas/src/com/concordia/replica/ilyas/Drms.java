package com.concordia.replica.ilyas;

import com.comp6231.common.ILibrary;

/**
 * @author Ilyas Rashid - Student Id 4819608
 * @date 19/11/2014
 * Assignment # 3 - Option 1
 */


public class Drms implements ILibrary{
	
	private LibraryServer m_concordiaServer = new LibraryServer(2020);
	private LibraryServer m_mcgillServer = new LibraryServer(2021);
	private LibraryServer m_polytechniqueServer = new LibraryServer(2022);
	
	// 0 : OK
	// 1 : Server failure 
	// 5 : Username exists 
	public String registerUser(String instName, String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password){
		long longPhoneNumber=0L;  
		
		try {
			 longPhoneNumber = Long.parseLong(phoneNumber);
			 //System.out.println("longPhoneNumber = " + longPhoneNumber);
		  } catch (NumberFormatException nfe) {
		     System.out.println("NumberFormatException: " + nfe.getMessage());
		  }
		
		if (instName.toLowerCase().equals("concordia")){
			 return m_concordiaServer.createAccount(firstName, lastName, emailAddress, longPhoneNumber, username, password, instName);			
		}
		else if (instName.toLowerCase().equals("mcgill")){
			return m_mcgillServer.createAccount(firstName, lastName, emailAddress, longPhoneNumber, username, password, instName);
		}
		else if (instName.toLowerCase().equals("polytechnique")){
			return m_polytechniqueServer.createAccount(firstName, lastName, emailAddress, longPhoneNumber, username, password, instName);
		}
		
		return "";
	}
	

	// 0 : OK
	// 1 : Server failure
	// 2 : Authentication failed
	// 3 : Book does not exists or no copies left, or the user already has the book
	public String reserveBook(String instName, String username, String password, String bookName, String authorName){
		if (instName.toLowerCase().equals("concordia")){
			return m_concordiaServer.reserveBook(username, password, bookName, authorName);
		}
		else if (instName.toLowerCase().equals("mcgill")){
			return m_mcgillServer.reserveBook(username, password, bookName, authorName);
		}
		else if (instName.toLowerCase().equals("polytechnique")){
			return m_polytechniqueServer.reserveBook(username, password, bookName, authorName);
		}
		
		return "";
	}


	// 0 : OK
	// 1 : Server failure
	// 2 : Authentication failed
	// 4 : Username or Book doesn't exists, or user doesn't have this book
	public String setDuration(String instName, String adminUsername, String adminPassword, String username, String bookName, String authorName, int days){
		if (instName.toLowerCase().equals("concordia")){
			return m_concordiaServer.setDuration(username, bookName, days);
		}
		else if (instName.toLowerCase().equals("mcgill")){
			return m_mcgillServer.setDuration(username, bookName, days);
		}
		else if (instName.toLowerCase().equals("polytechnique")){
			return m_polytechniqueServer.setDuration(username, bookName, days);
		}
		
		return "";
	}
	


	// 0 : OK
	// 1 : Server failure
	// 2 : Authentication failed
	// 3 : Book does not exists or no copies left
	public String reserveInterLibrary(String instName, String username, String password, String bookName, String authorName){
		if (instName.toLowerCase().equals("concordia")){
			return m_concordiaServer.reserveInterLibrary(username, password, bookName, authorName);
		}
		else if (instName.toLowerCase().equals("mcgill")){
			return m_mcgillServer.reserveInterLibrary(username, password, bookName, authorName);
		}
		else if (instName.toLowerCase().equals("polytechnique")){
			return m_polytechniqueServer.reserveInterLibrary(username, password, bookName, authorName);
		}
		
		return "";
	}
	


	// 0 : OK
	// 1 : Server failure
	// 2 : Authentication failed
	public String getNonRetuners(String instName, String adminUsername, String adminPassword, int days){
		if (instName.toLowerCase().equals("concordia")){
			return m_concordiaServer.getNonReturners(adminUsername, adminPassword, instName, days);
		}
		else if (instName.toLowerCase().equals("mcgill")){
			return m_mcgillServer.getNonReturners(adminUsername, adminPassword, instName, days);
		}
		else if (instName.toLowerCase().equals("polytechnique")){
			return m_polytechniqueServer.getNonReturners(adminUsername, adminPassword, instName, days);
		}
		return "";
	}
	//2@InsName,FirstName,LastName,PhoneNumber@InsName,FirstName,LastName,PhoneNumber@InsName,FirstName,LastName,PhoneNumber


	public static void main(String[] args){
		Drms drms = new Drms();
		//System.out.println(drms.getNonRetuners("Polytechnique", "Admin", "Admin", 5));
		System.out.println("");
		System.out.println("");
		System.out.println(drms.registerUser("McGill", "John", "Smith", "john@smith.com", "514123456", "johnsmith", "johnsmith"));
		System.out.println(drms.registerUser("McGill", "John", "Smith", "john@smith.com", "514123456", "johnsmith", "johnsmith"));
		System.out.println(drms.reserveBook("Concordia", "paulka", "paulka", "Absolute C++", "Walter"));
		System.out.println(drms.reserveInterLibrary("McGill", "paulka", "paulka", "Absolute C++", "Walter"));
		System.out.println(drms.reserveInterLibrary("McGill", "johnsmith", "johnsmith", "Absolute C++", "Walter"));
		System.out.println(drms.setDuration("Concordia", "Admin", "Admin", "paulka", "Absolute C++", "Walter", 10));
		System.out.println(drms.getNonRetuners("Polytechnique", "Admin", "Admin", 30));
	}
	
}