

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

public class InternalReceiver extends Thread {
	
	/*
	 * Mark - Constructors
	 */
	
	public InternalReceiver() {
		handlers = new HashMap<String, InternalReceiverHandler>();
	}
	
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
	 * Mark - Handlers - Properties
	 */
	 
	private Map<String, InternalReceiverHandler> handlers;
	
	/*
	 * Mark - Handlers - Methods
	 */
	 
	public void addHandler(InternalReceiverHandler handler, String messageType) {
		handlers.put(messageType, handler);
	}
	
	/*
	 * Mark - Basic - Methods
	 */
	
	public void run() {
		DatagramSocket socket = null;
		
		try {
			socket = new DatagramSocket(serverInfo.getPortForUDP());
			byte[] receiveBytes = new byte[1000];
			
			while(true){
				
				// get receive message
				DatagramPacket receivePacket = new DatagramPacket(receiveBytes, receiveBytes.length);
				socket.receive(receivePacket);
				
				InternalMessage receiveMessage = new InternalMessage();
				receiveMessage.decode(receiveBytes);
				
				// build send back message
				InternalMessage sendMessage = new InternalMessage();
				sendMessage.setType(InternalMessage.TYPE_RETURN);
				
				// handle
				String messageType = receiveMessage.getType();
				InternalReceiverHandler handler = handlers.get(messageType);
				handler.handle(receiveMessage, sendMessage);

				// send back
				byte[] sendBytes = sendMessage.encode();
				DatagramPacket sendPacket = new DatagramPacket(sendBytes, sendBytes.length, receivePacket.getAddress(), receivePacket.getPort());
				socket.send(sendPacket);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage() + " catch something");
		} finally {
			if (socket != null) socket.close();
		}
	}
}
