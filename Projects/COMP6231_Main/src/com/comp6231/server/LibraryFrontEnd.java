package com.comp6231.server;

import java.io.PrintWriter;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import corba.LibraryServerPOA;

public class LibraryFrontEnd extends LibraryServerPOA {
	

	/*
	 * Mark - Driver
	 */
	
	public static void main(String[] args) {
		try {
			
			ORB orb = ORB.init(args, null);
			POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			
			LibraryFrontEnd frontEnd = new LibraryFrontEnd();
			
			byte[] id = rootPOA.activate_object(frontEnd);
			org.omg.CORBA.Object ref = rootPOA.id_to_reference(id);
			String ior = orb.object_to_string(ref);
			
			PrintWriter fw = new PrintWriter("corba/ior.txt");
			fw.println(ior);
			fw.close();
		
			rootPOA.the_POAManager().activate();
			orb.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String registerUser(String instName, String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password) {
		System.out.println(instName + " " + firstName);
		
		return "1@haha";
	}

	@Override
	public String reserveBook(String instName, String username, String password, String bookName, String authorName) {
		return null;
	}

	@Override
	public String setDuration(String instName, String adminUsername, String adminPassword, String username, String bookName, String authorName, String days) {
		return null;
	}

	@Override
	public String reserveInterLibrary(String instName, String username, String password, String bookName, String authorName) {
		return null;
	}

	@Override
	public String getNonRetuners(String instName, String adminUsername, String adminPassword, String days) {
		return null;
	}

}
