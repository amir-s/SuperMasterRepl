package com.zhaozhe.client;

import java.util.Scanner;

import com.zhaozhe.server.Response;
import com.zhaozhe.server.ServerInfo;

import corba.LibraryServer;

public class DebugClient extends Client {
	
	/*
	 * Mark - Driver
	 */

	public static void main(String[] args) throws Exception{
		
		ServerConnector serverConnector = new ServerConnector();
		ServerInfo serverInfo = serverConnector.chooseServer();
		LibraryServer libraryServer = serverConnector.connect(serverInfo);
		
		DebugClient client = new DebugClient();
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
		System.out.println("1. Get Info");
		System.out.println("2. Exit");
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

				// show
				response = new Response(libraryServer.show());
				showResponse(response);
				
				break;
			}
			case 2:
			{
				
				// quit
				System.out.println("Have a nice day!");
				keyboard.close();
				System.exit(0);
			}
			
			default:
				System.out.println("Invalid Input, please try again.");
			}
		}
	}
}
