package com.comp6231.client;

import java.util.Scanner;

import com.zhaozhe.server.Response;
import com.zhaozhe.server.ServerError;
import com.zhaozhe.server.ServerInfo;

import corba.LibraryServer;

public class StudentClient extends Client {
	
	/*
	 * Mark - Driver
	 */

	public static void main(String[] args) throws Exception{
		
		ServerConnector serverConnector = new ServerConnector();
		ServerInfo serverInfo = serverConnector.chooseServer();
		LibraryServer libraryServer = serverConnector.connect(serverInfo);
		
		StudentClient client = new StudentClient();
		client.setServerInfo(serverInfo);
		client.setLibraryServer(libraryServer);
		
		client.run();
	}
	
	/*
	 * Mark - Interaction
	 */
	
	public void showMenu(){
		System.out.println("-------- Menu --------");
		System.out.println("Please select an option (1-4)");
		System.out.println("1. Create account");
		System.out.println("2. Rent a book in local library");
		System.out.println("3. Rent a book in libraries");
		System.out.println("4. Exit");
	}
	
	public void run() throws Exception{

		int index = 0;
		Scanner keyboard = new Scanner(System.in);
		
		
		while(true)
		{
			showMenu();
			
			index = keyboard.nextInt();
			keyboard.nextLine();
			Response response;
			
			
			switch(index) {

			case 1: 
			{
				
				// create account
				System.out.println("firstName lastName emailAddress phoneNumber username password");
				
				String firstName = keyboard.next(); 
				String lastName = keyboard.next(); 
				String emailAddress = keyboard.next(); 
				String phoneNumber = keyboard.next(); 
				String username = keyboard.next(); 
				String password =  keyboard.next(); 
				
				if (username.length() < 6 || username.length() > 15) {
					System.out.println("Username 6 - 15");
					break;
				}
				
				if (password.length() < 6) {
					System.out.println("Password > 6");
					break;
				}

				response = new Response(libraryServer.createAccount(firstName, lastName, emailAddress, phoneNumber, username, password, serverInfo.getName()));
				showResponse(response);
				
				break;
			}
			
			case 2: 
			{
				// reserve book
				System.out.println("username password bookName authorName");
				String username = keyboard.next(); 
				String password = keyboard.next(); 
				String bookName = keyboard.next(); 
				String authorName = keyboard.next(); 

				response = new Response(libraryServer.reserveBook(username, password, bookName, authorName));
				showResponse(response);
				
				break;
			}
			
			case 3: 
			{
				// reserver inter library
				System.out.println("username password bookName authorName");
				String username = keyboard.next(); 
				String password = keyboard.next(); 
				String bookName = keyboard.next(); 
				String authorName = keyboard.next(); 

				response = new Response(libraryServer.reserveInterLibrary(username, password, bookName, authorName));
				showResponse(response);
				
				break;
			}
			
			case 4:
			{
				// quit
				System.out.println("Have a nice day!");
				keyboard.close();
				System.exit(0);
			}
			
			default:
				System.out.println("Invalid Input, please try again.");
			}
			keyboard.nextLine();
		}
	}
}
