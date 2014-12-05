package com.comp6231.common;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

public class InterReceiver extends Thread {
	
	/*
	 * Mark - Constructors
	 */
	
	public InterReceiver() {
		handlers = new HashMap<String, InterReceiverHandler>();
	}
	
	/*
	 * Mark - Context - Properties
	 */
	 
	private int portNumber;
	
	/*
	 * Mark - Context - Getters & Setters
	 */

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	
	/*
	 * Mark - Handlers - Properties
	 */
	 

	private Map<String, InterReceiverHandler> handlers;
	
	/*
	 * Mark - Handlers - Methods
	 */
	 
	public void addHandler(InterReceiverHandler handler, String messageType) {
		handlers.put(messageType, handler);
	}
	
	/*
	 * Mark - Basic - Methods
	 */
	
	public void run() {
		DatagramSocket socket = null;
		
		try {
			socket = new DatagramSocket(portNumber);
			byte[] receiveBytes = new byte[1000];
			
			while(true){
				
				// get receive message
				DatagramPacket receivePacket = new DatagramPacket(receiveBytes, receiveBytes.length);
				socket.receive(receivePacket);
				
				InterMessage receiveMessage = new InterMessage();
				receiveMessage.decode(receiveBytes);
				
				System.out.println("InterRecever : Event : Received message at port " + portNumber + " from port " + receivePacket.getPort());
				
				// build send back message
				InterMessage sendMessage = new InterMessage();
				sendMessage.setType(InterMessage.TYPE_RETURN);
				
				// handle
				String messageType = receiveMessage.getType();
				InterReceiverHandler handler = handlers.get(messageType);
				handler.handle(receiveMessage, sendMessage);

				// send back
				byte[] sendBytes = sendMessage.encode();
				DatagramPacket sendPacket = new DatagramPacket(sendBytes, sendBytes.length, receivePacket.getAddress(), receivePacket.getPort());
				socket.send(sendPacket);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage() + " catch something");
		} finally {
			if (socket != null) socket.close();
		}
	}
}
