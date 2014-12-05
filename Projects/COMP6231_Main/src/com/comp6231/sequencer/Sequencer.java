package com.comp6231.sequencer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.UDPTransport.UDPTransporter;
import com.comp6231.common.IConsumer;
import com.comp6231.common.InterMessage;
import com.comp6231.common.InterReceiver;
import com.comp6231.common.InterReceiverHandler;
import com.comp6231.common.InterSender;
import com.comp6231.common.SocketProducerConsumer;

public class Sequencer {
	private Queue<DatagramPacket> seqQueue = new LinkedList<DatagramPacket>();
	private HashMap<Long,DatagramPacket> seqMap = new HashMap<Long,DatagramPacket>();
	private boolean holdRequests = false;
	private int r1ServerPort = 5000;
	private int r2ServerPort = 6000;
	private int r3ServerPort = 7000;
	private long sequenceNumber = 0;
	
	public Sequencer() {
		
		try {
			Thread feThread = new Thread(new Runnable() {
		         public void run() {
		        	 initFrontEndListener();
		         }
			});
			feThread.start();
			Thread rmThread = new Thread(new Runnable() {
				 public void run() {
					 startReplicaManagerListener();
				 }
			});
			rmThread.start();
			Thread prThread = new Thread(new Runnable() {
				public void run() {
					consumer();
				}
			});
			prThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	Desc : 
	   
	@return : Nothing
	*/
	/*
	private void startFrontEndListener(){
		DatagramSocket _aSocket = null;

		try{
			int _port = 4001;
			_aSocket = new DatagramSocket(_port);
			System.out.println("Front End Listener started and binding to port: " + _port);
			//byte [] _buffer = new byte[1000];
			
			while(true){
				byte [] _buffer = new byte[1000];
	
				DatagramPacket _request = new DatagramPacket(_buffer, _buffer.length);
				
				_aSocket.receive(_request);
				
				seqQueue.add(_request);
				seqMap.put(new Long(sequenceNumber), _request);
				
				InterMessage message = new InterMessage();
				message.decode(_buffer);
				message.addParameter(InterMessage.KEY_SEQUENCE_NUMBER, Long.toString(sequenceNumber));
				sequenceNumber++;
				_buffer = message.encode();
				
				//TO DO: Adding to the queue

				InetAddress _aHost = InetAddress.getByName("localhost");
				System.out.println("Sequencer: Got a message on port 4001");
				System.out.println("Sequencer: Message Content: " + new String(_request.getData()));
				
//				//TO DO:Add hold requests check
//				DatagramPacket _sendToR1 = new DatagramPacket(_buffer, _buffer.length, _aHost, r1ServerPort);
//				DatagramPacket _sendToR2 = new DatagramPacket(_buffer, _buffer.length, _aHost, r2ServerPort);
//				DatagramPacket _sendToR3 = new DatagramPacket(_buffer, _buffer.length, _aHost, r3ServerPort);
//				
//				_aSocket.send(_sendToR1);
//				_aSocket.send(_sendToR2);
//				_aSocket.send(_sendToR3);
				
//				_buffer = returnedMessage1.encode();
				
				//TO DO: Remove from queue when done
				//seqQueue.remove();
			
			
			}
		} catch (SocketException e){ System.out.println("Socket: " + e.getMessage());
		} catch (IOException e){ System.out.println("IO: " + e.getMessage());
		}finally {if(_aSocket != null) _aSocket.close();}
		
	}
	*/
	/**
	Desc :    
	@return : Nothing
	*/
	private void startReplicaManagerListener(){
		DatagramSocket _aSocket = null;

		try{
			int _port = 4050;

			_aSocket = new DatagramSocket(_port);
			System.out.println("Replica Manager Listener started and binding to port: " + _port);
			byte [] _buffer = new byte[1000];
			
			
			while(true){
			
				DatagramPacket _request = new DatagramPacket(_buffer, _buffer.length);
				_aSocket.receive(_request);
				System.out.println("Sequencer: Got a message on port 4050");
				System.out.println("Sequencer: Message Content: " + new String(_request.getData()));
				
				
				int _i;
				String _udpRequest="";
				
				for (_i = 0; _i < _buffer.length && _buffer[_i] != 0; _i++) {
					_udpRequest += (char)_buffer[_i];
				}
					System.out.println("!! '" + _udpRequest+"'");
				if( _udpRequest.contains("STOP")){
					System.out.println("START QUEUEING");
					receiver.pauseConsumer();
					holdRequests = true;				
				}
				else if(_udpRequest.contains("START")){
					System.out.println("STOP QUEUEING");
					receiver.resumeConsumer();
					holdRequests = false;				
				}
			}
		} catch (SocketException e){ System.out.println("Socket: " + e.getMessage());
		} catch (IOException e){ System.out.println("IO: " + e.getMessage());
		}finally {if(_aSocket != null) _aSocket.close();}
		
	}
	
	private SocketProducerConsumer receiver;

	private void consumer() {
		while (true) {
			
		}
	}
	private void initFrontEndListener() {

		receiver = new SocketProducerConsumer();
		receiver.setPortNumber(4001);
		receiver.setConsumer(new IConsumer() {
			
			public void consume(InterMessage receiveMessage, DatagramPacket packet) throws IOException {
				System.out.println("Consuming");
				InterMessage message = receiveMessage;
				
				message.addParameter(InterMessage.KEY_SEQUENCE_NUMBER, Long.toString(sequenceNumber));
				sequenceNumber++;
				
				InterSender sender;
				sender = new InterSender();
				sender.setToPortNumber(r1ServerPort);
				InterMessage returnedMessage1 = sender.sendMessage(message);
				returnedMessage1.removeParameter(InterMessage.KEY_SEQUENCE_NUMBER);
				returnedMessage1.removeParameter(InterMessage.KEY_REPLICA_MANAGER_PORT_NUMBER);

				sender = new InterSender();
				sender.setToPortNumber(r2ServerPort);
				InterMessage returnedMessage2 = sender.sendMessage(message);
				returnedMessage2.removeParameter(InterMessage.KEY_SEQUENCE_NUMBER);
				returnedMessage2.removeParameter(InterMessage.KEY_REPLICA_MANAGER_PORT_NUMBER);

				sender = new InterSender();
				sender.setToPortNumber(r3ServerPort);
				InterMessage returnedMessage3 = sender.sendMessage(message);
				returnedMessage3.removeParameter(InterMessage.KEY_SEQUENCE_NUMBER);
				returnedMessage3.removeParameter(InterMessage.KEY_REPLICA_MANAGER_PORT_NUMBER);
				
				String[] r = new String[3];
				r[0] = new String(returnedMessage1.encode());
				r[1] = new String(returnedMessage2.encode());
				r[2] = new String(returnedMessage3.encode());
				
				for (int i=0;i<3;i++) {
					System.out.println("~~ " + i + " " + r[i]);
				}
				int wrong = -1;
				
				int[] replicaManagers = {4011, 4012, 4013};
				
				if (r[1].equals(r[2]) && !r[1].equals(r[0])) wrong = 0;
				if (r[0].equals(r[2]) && !r[0].equals(r[1])) wrong = 1;
				if (r[0].equals(r[1]) && !r[0].equals(r[2])) wrong = 2;
				
				
				if (wrong != -1) {
					UDPTransporter.send("localhost", replicaManagers[wrong], "WRONG");
				}
				InterMessage toSend = returnedMessage1;
			
				if (wrong == 0) toSend = returnedMessage2;
				if (wrong == 1) toSend = returnedMessage3;
				if (wrong == 2) toSend = returnedMessage1;
				
				toSend.setType(InterMessage.TYPE_RETURN);
				
				
				// send back
				byte[] sendBytes = toSend.encode();
				DatagramPacket sendPacket = new DatagramPacket(sendBytes, sendBytes.length, packet.getAddress(), packet.getPort());
				receiver.send(sendPacket);
				System.out.println("Wrong = " + wrong);

			}

		});

		receiver.start();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Sequencer();

	}
	
	

}
