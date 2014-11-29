package com.concordia.comp6231.clients;

import java.util.HashMap;
import java.util.Scanner;
import com.concordia.comp6231.client.concordia.DrmsService;
import com.concordia.comp6231.client.concordia.Drms;

/**
 * @author Ilyas Rashid - Student Id 4819608
 * @date 19/11/2014
 * Assignment # 3 - Option 1
 */

public class StudentClient implements Runnable{
	public HashMap<String, String> m_userInServer = new HashMap<String, String>();

	//Return basic menu.
	public static void showMenu()
	{
		System.out.println("\n****Welcome to Student Client****\n");
		System.out.println("Please select an option (1-3)");
		System.out.println("1. Create an Account.");
		System.out.println("2. Reserve a Book.");
		System.out.println("3. Reserve Inter Library Book");
		System.out.println("4. Create 5 threads that try to create the same account.");
		System.out.println("5. Exit");
	}
	
	public synchronized void createAccount(){
		/*System.setSecurityManager(new RMISecurityManager());
		DrmsInterface _concordiaServer;
		try {
			_concordiaServer = (DrmsInterface) Naming.lookup("rmi://localhost:2020/DRMS");
			String _result = _concordiaServer.createAccount("Paul", "Karia", "paul.karia@karia.com", 5141111111L, "threadUser", "paulka", "Concordia");
			System.out.println("Result from Thread ID " + Thread.currentThread().getId() + ": " + _result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	}
	
	@Override
	public void run(){
		System.out.println("Thread ID: " + Thread.currentThread().getId());
		createAccount();		
	}
	
	public static void main(String[] args) {
		StudentClient _sc = new StudentClient();
		_sc.m_userInServer.put("paulka","concordia");
		_sc.m_userInServer.put("boromirking","concordia");		
		_sc.m_userInServer.put("paulco", "mcgill");
		_sc.m_userInServer.put("wayneg", "uqam");
		
		try {
		
			
			DrmsService drmsService = new DrmsService();
			Drms _concordiaServer = drmsService.getDrmsPort();
			
			com.concordia.comp6231.client.mcgill.DrmsService _mcgillDrmsService = new com.concordia.comp6231.client.mcgill.DrmsService();
			com.concordia.comp6231.client.mcgill.Drms _mcgillServer = _mcgillDrmsService.getDrmsPort(); 
			
			com.concordia.comp6231.client.uqam.DrmsService _uqamDrmsService = new com.concordia.comp6231.client.uqam.DrmsService();
			com.concordia.comp6231.client.uqam.Drms _uqamServer = _uqamDrmsService.getDrmsPort();
			

				
			int _userChoice=0;
			String _userInput="";
			Scanner _keyboard = new Scanner(System.in);
			
			showMenu();
			
			while(true)
			{
				Boolean _valid = false;
				
				// Enforces a valid integer input.
				while(!_valid)
				{
					try{
						_userChoice=_keyboard.nextInt();
						_valid=true;
					}
					catch(Exception e)
					{
						System.out.println("Invalid Input, please enter an Integer");
						_valid=false;
						_keyboard.nextLine();
					}
				}
				
				// Manage user selection.
				switch(_userChoice)
				{
				case 1:
					String _firstName, _lastName, _emailAddress, _username, _password, _educationalInstitution;
					long _phoneNumber;
					
					System.out.println("Please enter the following Information using a space in between each field:");
					System.out.println("First Name, Last Name, Email Address, 10 digit Phone Number, Username, Password, Educational Institution");

					_keyboard.useDelimiter(System.getProperty("line.separator"));
					_userInput = _keyboard.next();
					 
					 String[] _splited = _userInput.split("\\s+");
					 if (_splited.length != 7){
						 System.out.println("Invalid number of Fields");
						 showMenu();
						 break;
					 }
					 
					 _firstName = _splited[0];
					 _lastName = _splited[1];
					 _emailAddress = _splited[2];
					 _phoneNumber = Long.parseLong(_splited[3]);
					 _username = _splited[4];
					 _password = _splited[5];
					 _educationalInstitution = _splited[6];
					 
					 if(!_emailAddress.contains("@")){
						 System.out.println("The Email Address " +  _emailAddress + " is Invalid.");
						 showMenu();
						 break;
					 }
					 else if(_username.length() < 6 || _username.length() > 15){
						 System.out.println("The username " +  _username + " is Invalid.");
						 System.out.println("The username must be at least 6 characters but no longer than 15 characters.");
						 showMenu();
						 break;
					 }
					 else if(_password.length() < 6){
						 System.out.println("The password is Invalid.");
						 System.out.println("The password must be at least 6 characters long.");
						 showMenu();
						 break;
					 }
					/* Let the server check if the user exists
					 * else if(_sc.m_userInServer.containsKey(_username)){
						 System.out.println("The _username " +  _username + " already exists in " + _sc.m_userInServer.get(username));
						 break;
					 }*/
					 else if(_educationalInstitution.toLowerCase().equals("concordia")){
						 System.out.println("Sending to Concordia");
						 String _result = _concordiaServer.createAccount(_firstName, _lastName, _emailAddress, _phoneNumber, _username, _password, _educationalInstitution);
						 if (_result.contains("Success")){
							 _sc.m_userInServer.put(_username,"concordia");
						 }
						 
						 System.out.println(_result);
							 
					 }
					 else if(_educationalInstitution.toLowerCase().equals("mcgill")){
						 System.out.println("Sending to McGill");
						 String _result = _mcgillServer.createAccount(_firstName, _lastName, _emailAddress, _phoneNumber, _username, _password, _educationalInstitution);
						 if (_result.contains("Success")){
							 _sc.m_userInServer.put(_username,"mcgill");
						 }
						 System.out.println(_result);
					 }
					 else if(_educationalInstitution.toLowerCase().equals("uqam")){
						 System.out.println("Sending to UQAM");
						 String _result = _uqamServer.createAccount(_firstName, _lastName, _emailAddress, _phoneNumber, _username, _password, _educationalInstitution);
						 if (_result.contains("Success")){
							 _sc.m_userInServer.put(_username,"uqam");
						 }
						 System.out.println(_result);
					 }
					showMenu();
					break;
				case 2:
					String _rb_username, _rb_password, _bookName, _authorName, _serverToSendRequest;
					
					System.out.println("Please enter your Username");
					_rb_username = _keyboard.next();
					
					//Check the map to see which server the users account was created in
					_serverToSendRequest = _sc.m_userInServer.get(_rb_username);
					if(_serverToSendRequest == null){
						System.out.println("This username has not been registered in the system.");
						showMenu();
						break;
						
					}
					
					System.out.println("Please enter your Password");
					_rb_password = _keyboard.next();
					System.out.println("Please enter the Book Name");
					_keyboard.useDelimiter(System.getProperty("line.separator"));
					_bookName = _keyboard.next();
					System.out.println("Please enter the Author");
					_authorName = _keyboard.next();
					
					if(_serverToSendRequest.equals("concordia")){
						 System.out.println("Sending to Concordia");
						 String _result = _concordiaServer.reserveBook(_rb_username, _rb_password, _bookName, _authorName);						 
						 System.out.println(_result);
							 
					 }
					 else if(_serverToSendRequest.equals("mcgill")){
						 System.out.println("Sending to McGill");
						 String _result = _mcgillServer.reserveBook(_rb_username, _rb_password, _bookName, _authorName);
						 System.out.println(_result);
					 }
					 else if(_serverToSendRequest.equals("uqam")){
						 System.out.println("Sending to UQAM");
						 String _result = _uqamServer.reserveBook(_rb_username, _rb_password, _bookName, _authorName);
						 System.out.println(_result);
					 }
					showMenu();
					break;
				case 3:
					String _inter_username, _inter_password, _inter_bookName, _inter_authorName, _inter_serverToSendRequest;
					
					System.out.println("Please enter your Username");
					_inter_username = _keyboard.next();
					
					//Check the map to see which server the users account was created in
					_inter_serverToSendRequest = _sc.m_userInServer.get(_inter_username);
					if(_inter_serverToSendRequest == null){
						System.out.println("This username has not been registered in the system.");
						showMenu();
						break;
						
					}
					
					System.out.println("Please enter your Password");
					_inter_password = _keyboard.next();
					System.out.println("Please enter the Book Name");
					_keyboard.useDelimiter(System.getProperty("line.separator"));
					_inter_bookName = _keyboard.next();
					System.out.println("Please enter the Author");
					_inter_authorName = _keyboard.next();
					
					if(_inter_serverToSendRequest.equals("concordia")){
						 System.out.println("Sending to Concordia");
						 String _result = _concordiaServer.reserveInterLibrary(_inter_username, _inter_password, _inter_bookName, _inter_authorName);						 
						 System.out.println(_result);
							 
					 }
					 else if(_inter_serverToSendRequest.equals("mcgill")){
						 System.out.println("Sending to McGill");
						 String _result = _mcgillServer.reserveInterLibrary(_inter_username, _inter_password, _inter_bookName, _inter_authorName);
						 System.out.println(_result);
					 }
					 else if(_inter_serverToSendRequest.equals("uqam")){
						 System.out.println("Sending to UQAM");
						 String _result = _uqamServer.reserveInterLibrary(_inter_username, _inter_password, _inter_bookName, _inter_authorName);
						 System.out.println(_result);
					 }
					showMenu();
					break;
				case 4:
					System.out.println("Starting 5 Threads");
					StudentClient _studentClient = new StudentClient();
					Thread _t1 = new Thread(_studentClient);
					Thread _t2 = new Thread(_studentClient);
					Thread _t3 = new Thread(_studentClient);
					Thread _t4 = new Thread(_studentClient);
					Thread _t5 = new Thread(_studentClient);
					
					_t1.start();
					_t2.start();
					_t3.start();
					_t4.start();
					_t5.start();
					System.out.println("Threads started, main menu sleeping for 5 seconds");
					showMenu();
					break;
				case 5:
					//System.out.println("Size of Map:" + _sc.m_userInServer.size());
					//System.out.println("toString of Map:" + _sc.m_userInServer.toString());
					System.out.println("Thank you for using the DRMS Student Client, have a nice day!");
					_keyboard.close();
					System.exit(0);
				default:
					System.out.println("Invalid Input, please try again.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}