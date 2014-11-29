package com.comp6231.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import org.omg.CORBA.ORB;

import com.comp6231.common.LibraryInfo;

import corba.LibraryServer;
import corba.LibraryServerHelper;

public class ServerConnector {

	public String chooseServer() {
		System.out.println("Select a server to connect");
		
		int index;
		String[] names = LibraryInfo.getNames();
		for (index = 0 ; index < names.length; index++) {
			System.out.println(index + ". " + names[index]);
		}

		Scanner keyboard = new Scanner(System.in);
		index = keyboard.nextInt();
		keyboard.nextLine();

		return names[index];
	}

	// to get the stub object
	public LibraryServer connect() throws Exception{
		
		ORB orb = ORB.init(new String[1], null);

		BufferedReader br = new BufferedReader(new FileReader("corba/ior.txt"));
		String ior = br.readLine();
		br.close();
		
		org.omg.CORBA.Object ref = orb.string_to_object(ior);

		LibraryServer server = LibraryServerHelper.narrow(ref);
		return server;
	}

}
