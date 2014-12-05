package com.comp6231.common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SocketProducerConsumer extends Thread {
	
	/*
	 * Mark - Constructors
	 */
	
	public SocketProducerConsumer() {
		
	}
	
	/*
	 * Mark - Context - Properties
	 */
	 
	private int portNumber;
	private Queue<DatagramPacket> sockets = new LinkedList<>();
	private Queue<InterMessage> messages = new LinkedList<>();
	private Object lock = new Object();
	private IConsumer consumer;
	
	/*
	 * Mark - Context - Getters & Setters
	 */

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	
	public void produce(InterMessage receiveMessage, DatagramPacket socket) {
		synchronized (lock) {
			sockets.add(socket);
			messages.add(receiveMessage);
		}
	}
	public void setConsumer(IConsumer c) {
		this.consumer = c;
	}
	/*
	 * Mark - Basic - Methods
	 */
	DatagramSocket socket = null;
	public void run() {
		
		
		try {
			socket = new DatagramSocket(portNumber);
			byte[] receiveBytes = new byte[4096];
			
			while(true){
				
				// get receive message
				DatagramPacket receivePacket = new DatagramPacket(receiveBytes, receiveBytes.length);
				socket.receive(receivePacket);
				
				InterMessage receiveMessage = new InterMessage();
				receiveMessage.decode(receiveBytes);
				
				System.out.println("InterRecever : Event : Received message at port " + portNumber + " from port " + receivePacket.getPort());
				
				produce(receiveMessage, receivePacket);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null) socket.close();
		}
	}
	public void pauseConsumer() {
		stop = true;
	}
	public void resumeConsumer() {
		stop = false;
	}
	private boolean stop = false;
	public synchronized void start() {
		new Thread(new Runnable() {
			
			public void run() {
				try {
					while (true) {
						System.out.println("Checking Queue");
						if (stop == true) {
							System.out.println("Consuming is paused!");
							Thread.sleep(10000);
							continue;
						}
						synchronized (lock) {
							if (sockets.size() > 0) consumer.consume(messages.poll(), sockets.poll());
						}
						Thread.sleep(2000);
					}
				}catch (Exception e) {
					e.printStackTrace();
					System.out.println("Exception in Consumer Thread");
				}
			}
		}).start();
		super.start();
	}

	public void send(DatagramPacket sendPacket) throws IOException {
		socket.send(sendPacket);
	}
}
