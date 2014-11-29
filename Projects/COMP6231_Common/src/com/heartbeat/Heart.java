package com.heartbeat;

import com.UDPTransport.UDPTransporter;


public class Heart extends Thread{
	private final long PERIOD = 5000;
	private String host = null;
	private int port = -1;
	private final String msg = "HELLO";
	private boolean stop = false;
	public Heart(String host, int port) {
		this.host = host;
		this.port = port;
	}
	public void run() {
		while (!stop) {
			System.out.println("Sending HeartBeat");
			UDPTransporter.transport(host, port, msg);
			try {
				Thread.sleep(PERIOD);
			} catch (InterruptedException e) {
				stop = false;
				e.printStackTrace();
			}
		}
	}
	public void beat() {
		start();
	}
}
