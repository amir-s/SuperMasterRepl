package com.comp6231.client;

import java.util.Scanner;

import com.comp6231.common.PrettyPrinter;
import com.comp6231.common.Response;

import corba.LibraryServer;

public class AdminClient extends Client {
	
	/*
	 * Mark - Driver
	 */

	public static void main(String[] args) throws Exception{
		ServerConnector serverConnector = new ServerConnector();
		String instName = serverConnector.chooseServer();
		LibraryServer libraryServer = serverConnector.connect();
		
		AdminClient client = new AdminClient();
		client.setInstName(instName);
		client.setLibraryServer(libraryServer);
		
		client.run();
	}
	
	
	/*
	 * Mark - Interaction
	 */
	
	public void showMenu(){
		System.out.println("-------- Menu --------");
		System.out.println("Please select an option (1-4)");
		System.out.println("1. Get Non Returns");
		System.out.println("2. Set Duration");
		System.out.println("3. Exit");
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
				// get non returns 
				System.out.println("username password days");
				String username = keyboard.next(); 
				String password = keyboard.next(); 
				String days = keyboard.next(); 

				response = new Response(libraryServer.getNonRetuners(instName, username, password, days));
				//showResponse(response);
				if (response.isSuccess()) {
					System.out.println("Good!");
					
					final PrettyPrinter printer = new PrettyPrinter(System.out);
					
					if  (response.getData().indexOf("$") == -1) {
						System.out.println("The list is empty!");
						break;
					}
					String[] list = response.getData().substring(1).split("\\$");
					String[][] table = new String[list.length+1][];
					table[0] = new String[4];
					table[0][0] = " Institution Name ";
					table[0][1] = " First Name ";
					table[0][2] = " Last Name ";
					table[0][3] = " Phone Number ";
					
					for (int i=0;i<list.length;i++) {
						table[i+1] = list[i].split("\\^");
					}
					
					printer.print(table);
				}
				break;
			}
			
			case 2: 
			{
				// set duration
				System.out.println("adminUsername adminPassword username bookName authorName days");
				String adminUsername = keyboard.next(); 
				String adminPassword = keyboard.next(); 
				String username = keyboard.next(); 
				String bookName = keyboard.next(); 
				String authorName = keyboard.next(); 
				String days = keyboard.next(); 

				response = new Response(libraryServer.setDuration(instName, adminUsername, adminPassword, username, bookName, authorName, days));
				showResponse(response);
				
				break;
			}
			
			case 3:
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
