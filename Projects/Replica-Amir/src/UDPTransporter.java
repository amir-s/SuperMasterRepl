import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

// This class has all the functions to do
// UDP communication in the libraries
public class UDPTransporter {
	
	// this function sends a message to a port on localhost
	public static String transport(int port, String msg) {
		try {
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("localhost");
			byte[] sendData = msg.getBytes();
			byte[] receiveData = new byte[4096];
			
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			clientSocket.send(sendPacket);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			String out = new String(receivePacket.getData());
			clientSocket.close();
			return out;
		}catch (Exception e) {
			Logger.log("Transporter", "N/A", "Transporter Error " + e.getMessage());
			return null;
		}
		
	}
	
	/// this create a server that listenes
	// on the specified port and runs the function
	// whenever any message come.
	public static void server(int port, final PacketServer cb) throws Exception{
		final DatagramSocket server = new DatagramSocket(port);
		Thread listener = new Thread(new Runnable() {
			
			public void run() {
				while (true) {
					byte[] recData = new byte[1024];
					DatagramPacket receivePacket = new DatagramPacket(recData, recData.length);
					try {
						server.receive(receivePacket);
					} catch (IOException e) {
						Logger.log("TransporterServer", "N/A", "Error creating the server");
						e.printStackTrace();
						return;
					}
					byte[] send = cb.serve(new String(receivePacket.getData())).getBytes();
					int prt = receivePacket.getPort();
					InetAddress IPAddress = receivePacket.getAddress();
					DatagramPacket sendPacket = new DatagramPacket(send, send.length, IPAddress, prt);
					try {
						server.send(sendPacket);
					} catch (IOException e) {
						Logger.log("TransporterServer", "N/A", "Error sending the data");
						e.printStackTrace();
					}
				}	
			}
		});
		listener.start();
	}
	
}
