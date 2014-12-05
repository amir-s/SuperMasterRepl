package com.comp6231.server;

import java.io.PrintWriter;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import com.comp6231.common.InterMessage;
import com.comp6231.common.InterSender;

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
	
	/*
	 * Mark - Constructors
	 */
	
	public LibraryFrontEnd() {
		initInterSender();
	}
	
	/*
	 * Mark - Basic - Properties
	 */
	
	private InterSender interSender;
	
	/*
	 * Mark - Basic - Methods
	 */
	
	private void initInterSender() {
		interSender = new InterSender();
		interSender.setToPortNumber(4001);
	}
	 
	 

	@Override
	public String registerUser(String instName, String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password) {
		InterMessage message = new InterMessage();
		message.setType(InterMessage.TYPE_REGISTE_USER);
		
		System.out.println("LibraryFrontEnd : Function Called : registerUser");
		
		message.addParameter("instName", instName);
		message.addParameter("firstName", firstName);
		message.addParameter("lastName", lastName);
		message.addParameter("emailAddress", emailAddress);
		message.addParameter("phoneNumber", phoneNumber);
		message.addParameter("username", username);
		message.addParameter("password", password);
		
		InterMessage returnMessage = interSender.sendMessage(message);
		return returnMessage.getParameter(InterMessage.KEY_RETURN_VALUE);
	}

	@Override
	public String reserveBook(String instName, String username, String password, String bookName, String authorName) {
		InterMessage message = new InterMessage();
		message.setType(InterMessage.TYPE_RESERVE_BOOK);
		
		message.addParameter("instName", instName);
		message.addParameter("username", username);
		message.addParameter("password", password);
		message.addParameter("bookName", bookName);
		message.addParameter("authorName", authorName);

		InterMessage returnMessage = interSender.sendMessage(message);
		return returnMessage.getParameter(InterMessage.KEY_RETURN_VALUE);
	}

	@Override
	public String setDuration(String instName, String adminUsername, String adminPassword, String username, String bookName, String authorName, String days) {
		InterMessage message = new InterMessage();
		message.setType(InterMessage.TYPE_SET_DURATION);
		
		message.addParameter("instName", instName);
		message.addParameter("adminUsername", adminUsername);
		message.addParameter("adminPassword", adminPassword);
		message.addParameter("username", username);
		message.addParameter("bookName", bookName);
		message.addParameter("authorName", authorName);
		message.addParameter("days", days);

		InterMessage returnMessage = interSender.sendMessage(message);
		return returnMessage.getParameter(InterMessage.KEY_RETURN_VALUE);
	}

	@Override
	public String reserveInterLibrary(String instName, String username, String password, String bookName, String authorName) {
		InterMessage message = new InterMessage();
		message.setType(InterMessage.TYPE_RESERVE_INTER_LIBRARY);
		
		message.addParameter("instName", instName);
		message.addParameter("username", username);
		message.addParameter("password", password);
		message.addParameter("bookName", bookName);
		message.addParameter("authorName", authorName);

		InterMessage returnMessage = interSender.sendMessage(message);
		return returnMessage.getParameter(InterMessage.KEY_RETURN_VALUE);
	}

	@Override
	public String getNonRetuners(String instName, String adminUsername, String adminPassword, String days) {
		InterMessage message = new InterMessage();
		message.setType(InterMessage.TYPE_GET_NON_RETURNERS);
		
		message.addParameter("instName", instName);
		message.addParameter("adminUsername", adminUsername);
		message.addParameter("adminPassword", adminPassword);
		message.addParameter("days", days);

		InterMessage returnMessage = interSender.sendMessage(message);
		return returnMessage.getParameter(InterMessage.KEY_RETURN_VALUE);
	}

}
