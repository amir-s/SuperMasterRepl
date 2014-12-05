package com.comp6231.common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class InterSender {
	
	/*
	 * Mark - Context - Properties
	 */
	
	private int toPortNumber;
	 
	/*
	 * Mark - Context - Getters & Setters
	 */

	public int getToPortNumber() {
		return toPortNumber;
	}

	public void setToPortNumber(int toPortNumber) {
		this.toPortNumber = toPortNumber;
	}
	
	/*
	 * Mark - Control - Properties
	 */
	
	private boolean oneWay;
	
	/*
	 * Mark - Control - Getters & Setters
	 */

	public boolean isOneWay() {
		return oneWay;
	}

	public void setOneWay(boolean oneWay) {
		this.oneWay = oneWay;
	}

	/*
	 * Mark - Basic - Methods
	 */
	
	public InterMessage sendMessage(InterMessage sendMessage) {
		DatagramSocket socket = null;
		 
		try {
			socket = new DatagramSocket();
			InetAddress host = InetAddress.getByName("localhost");
			int serverPort = toPortNumber;

			System.out.println("InterSender : Event : Going to send message to port " + toPortNumber);
			// sending 
			byte[] sendBytes = sendMessage.encode();
			DatagramPacket sendPacket = new DatagramPacket(sendBytes, sendBytes.length, host, serverPort);
			socket.send(sendPacket);
			
			if (!isOneWay()) {
				// receiving
				byte[] receiveBytes = new byte[1000];
				DatagramPacket receivePacket = new DatagramPacket(receiveBytes, receiveBytes.length);
				socket.receive(receivePacket);
				
				// build the returning message
				InterMessage receiveMessage = new InterMessage();
				receiveMessage.decode(receiveBytes);
				return receiveMessage;
			}
			
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (socket != null) socket.close();
		}
		return null;
	}
}
