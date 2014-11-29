package com.heartbeat;

import com.UDPTransport.UDPTransporter;


public class Heart extends Thread{
	private final long PERIOD = 2000;
	private String host = null;
	private int port = -1;
	private final String msg = "HELLO";
	private boolean running = false;
	public Heart(String host, int port) {
		this.host = host;
		this.port = port;
	}
	public void run() {
		while (running) {
			System.out.println("Sending HeartBeat");
			UDPTransporter.send(host, port, msg);
			try {
				Thread.sleep(PERIOD);
			} catch (InterruptedException e) {
				running = true;
				e.printStackTrace();
			}
		}
	}
	public void beat() {
		running = true;
		super.start();
	}
	public void stopRunning() {
		running = false;
	}
	public void start() {
		running = true;
		super.start();
	}
}
