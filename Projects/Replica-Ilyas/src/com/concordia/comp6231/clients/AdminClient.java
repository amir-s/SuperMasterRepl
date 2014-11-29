package com.concordia.comp6231.clients;

import java.util.Scanner;
import com.concordia.comp6231.client.concordia.DrmsService;
import com.concordia.comp6231.client.concordia.Drms;

/**
 * @author Ilyas Rashid - Student Id 4819608
 * @date 19/11/2014
 * Assignment # 3 - Option 1
 */

public class AdminClient {

	//Return basic menu.
	public static void showMenu()
	{
		System.out.println("\n****Welcome to the DRMS Administrator Client****\n");
		System.out.println("Please select an option (1-3)");
		System.out.println("1. List users with non returned items.");
		System.out.println("2. Debug Tool: Set Duration.");
		System.out.println("3. Exit");
	}
	
	public static void main(String[] args) {
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
					String _adminUsername, _adminPassword, _educationalInstitution;
					int _numberOfDays;
					
					System.out.println("Please enter the following Information using a space in between each field:");
					System.out.println("Admin Username, Admin Password, Educational Institution, Number of Days");

					_keyboard.useDelimiter(System.getProperty("line.separator"));
					_userInput = _keyboard.next();
					 
					 String[] _splited = _userInput.split("\\s+");
					 if (_splited.length != 4){
						 System.out.println("Invalid number of Fields");
						 showMenu();
						 break;
					 }
					 
					 _adminUsername = _splited[0];
					 _adminPassword = _splited[1];
					 _educationalInstitution = _splited[2];
					 _numberOfDays = Integer.parseInt(_splited[3]);
					 
					 if(!_adminUsername.toLowerCase().equals("admin") || !_adminPassword.equals("Admin")){
						 System.out.println("The Admin username or password is Invalid.");
						 showMenu();
						 break;
					 }
					 else if(_educationalInstitution.toLowerCase().equals("concordia")){
						 System.out.println("Sending to Concordia");
						 String _result = _concordiaServer.getNonReturners(_adminUsername, _adminPassword, _educationalInstitution, _numberOfDays);
						 
						 System.out.println(_result);
							 
					 }
					 else if(_educationalInstitution.toLowerCase().equals("mcgill")){
						 System.out.println("Sending to McGill");
						 String _result = _mcgillServer.getNonReturners(_adminUsername, _adminPassword, _educationalInstitution, _numberOfDays);

						 System.out.println(_result);
					 }
					 else if(_educationalInstitution.toLowerCase().equals("uqam")){
						 System.out.println("Sending to UQAM");
						 String _result = _uqamServer.getNonReturners(_adminUsername, _adminPassword, _educationalInstitution, _numberOfDays);
						 
						 System.out.println(_result);
					 }
					showMenu();
					break;
				case 2:
					String _db_username, _db_bookName;
					int _Num_of_days;
					
					System.out.println("Please enter the Username");
					_db_username = _keyboard.next();
					
					System.out.println("Please enter the Book Name");
					_keyboard.useDelimiter(System.getProperty("line.separator"));
					_db_bookName = _keyboard.next();
					
					System.out.println("Please enter the Number of Days");
					_Num_of_days = _keyboard.nextInt();
					

					 System.out.println("Sending to Concordia: " + _db_username + " " + _db_bookName + " " + _Num_of_days);
					 String _concordia_result = _concordiaServer.setDuration(_db_username, _db_bookName, _Num_of_days);						 
					 System.out.println(_concordia_result);
							 
					 System.out.println("Sending to McGill");
					 String _mcgill_result = _mcgillServer.setDuration(_db_username, _db_bookName, _Num_of_days);
					 System.out.println(_mcgill_result);
					 
					 System.out.println("Sending to UQAM");
					 String _uqam_result = _uqamServer.setDuration(_db_username, _db_bookName, _Num_of_days);
					 System.out.println(_uqam_result);

					showMenu();
					break;
				case 3:
					System.out.println("Thank you for using the DRMS Administrator Client, have a nice day!");
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