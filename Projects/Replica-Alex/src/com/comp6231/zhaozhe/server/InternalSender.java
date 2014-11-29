package com.comp6231.zhaozhe.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class InternalSender {
	
	/*
	 * Mark - Context - Properties
	 */
	
	private ServerInfo serverInfo;
	 
	/*
	 * Mark - Context - Getters & Setters
	 */

	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

	/*
	 * Mark - Delegate - Properties
	 */
	 
	private InternalSenderDelegate delegate;
	
	/*
	 * Mark - Delegate - Getters & Setters
	 */


	public InternalSenderDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(InternalSenderDelegate delegate) {
		this.delegate = delegate;
	}
	
	/*
	 * Mark - Basic - Properties
	 */
	 
	private boolean shouldStop;
	
	/*
	 * Mark - Basic - Methods
	 */
	 
	public void stop(){
		shouldStop = true;
	}
	

	public void sendMessage(InternalMessage sendMessage) {
		shouldStop = false;
		ServerInfoManager serverInfoManager = ServerInfoManager.defaultManager();
		for(ServerInfo otherServer:serverInfoManager.getServers()){
			if (otherServer != serverInfo && !shouldStop){
				System.out.println(serverInfo.getName() + " is UDP to " + otherServer.getName());
				DatagramSocket socket = null;
				 
				try {
					socket = new DatagramSocket();
					InetAddress host = InetAddress.getByName("localhost");
					int serverPort = otherServer.getPortForUDP();

					// sending 
					byte[] sendBytes = sendMessage.encode();
					DatagramPacket sendPacket = new DatagramPacket(sendBytes, sendBytes.length, host, serverPort);
					socket.send(sendPacket);
					
					// receiving
					byte[] receiveBytes = new byte[1000];
					DatagramPacket receivePacket = new DatagramPacket(receiveBytes, receiveBytes.length);
					socket.receive(receivePacket);
					
					// build the returning message
					InternalMessage receiveMessage = new InternalMessage();
					receiveMessage.decode(receiveBytes);
					
					this.delegate.internalSenderDidReceiveMessage(serverInfo, otherServer, receiveMessage);
					
				} catch (SocketException e) {
					System.out.println("Socket: " + e.getMessage());
				} catch (IOException e) {
					System.out.println("IO: " + e.getMessage());
				} finally {
					if (socket != null) socket.close();
				}
			}
		}
		this.delegate.internalSenderDidReceiveAllMessages(serverInfo);
	}
}
