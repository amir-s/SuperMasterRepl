package com.zhaozhe.client;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.Scanner;

import com.zhaozhe.server.LibraryServerInterface;
import com.zhaozhe.server.Response;
import com.zhaozhe.server.ServerError;
import com.zhaozhe.server.ServerInfo;

import corba.LibraryServer;

public class AdminClient extends Client {
	
	/*
	 * Mark - Driver
	 */

	public static void main(String[] args) throws Exception{
		ServerConnector serverConnector = new ServerConnector();
		ServerInfo serverInfo = serverConnector.chooseServer();
		LibraryServer libraryServer = serverConnector.connect(serverInfo);
		
		AdminClient client = new AdminClient();
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
				// get non returnes 
				System.out.println("username password numDays");
				String username = keyboard.next(); 
				String password = keyboard.next(); 
				String numDays = keyboard.next(); 

				response = new Response(libraryServer.getNonRetuners(username, password, serverInfo.getName(), numDays));
				showResponse(response);
				
				break;
			}
			
			case 2: 
			{
				// set duration
				System.out.println("username bookName numDays");
				String username = keyboard.next(); 
				String bookName = keyboard.next(); 
				String numDays = keyboard.next(); 

				response = new Response(libraryServer.setDuration(username, bookName, numDays));
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
