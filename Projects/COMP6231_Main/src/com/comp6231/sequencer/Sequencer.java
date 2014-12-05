package com.comp6231.sequencer;

import java.util.LinkedList;
import java.util.Queue;
import java.net.*;
import java.io.*;
import java.nio.charset.Charset;

public class Sequencer {
	private Queue<byte[]> seqQueue = new LinkedList<byte[]>();
	private boolean holdRequests = false;
	private int r1ServerPort = 5000;
	private int r2ServerPort = 6000;
	private int r3ServerPort = 7000;
	
	public Sequencer() {
		try {
			Thread t = new Thread(new Runnable() {
		         public void run()
		         {
		        	 startFrontEndListener();
		         }
			});
			t.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Thread t1 = new Thread(new Runnable() {
		         public void run()
		         {
		        	 startReplicaManagerListener();
		         }
			});
			t1.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	Desc : 
	   
	@return : Nothing
	*/
	private void startFrontEndListener(){
		DatagramSocket _aSocket = null;

		try{
			int _port = 4001;
			_aSocket = new DatagramSocket(_port);
			System.out.println("Client Listener started and binding to port: " + _port);
			byte [] _buffer = new byte[1000];
			
			while(true){
			
			DatagramPacket _request = new DatagramPacket(_buffer, _buffer.length);
			
			_aSocket.receive(_request);

			//TODO: Adding to the queue
			//seqQueue.add(_buffer);
			InetAddress _aHost = InetAddress.getByName("localhost");
			
			//TODO:Add hold requests check
			DatagramPacket _sendToR1 = new DatagramPacket(_request.getData(), _request.getLength(), _aHost, r1ServerPort);
			DatagramPacket _sendToR2 = new DatagramPacket(_request.getData(), _request.getLength(), _aHost, r2ServerPort);
			DatagramPacket _sendToR3 = new DatagramPacket(_request.getData(), _request.getLength(), _aHost, r3ServerPort);
			
			_aSocket.send(_sendToR1);
			_aSocket.send(_sendToR2);
			_aSocket.send(_sendToR3);
			
			//TODO: Remove from queue when done
			//seqQueue.remove();
			
			
			}
		} catch (SocketException e){ System.out.println("Socket: " + e.getMessage());
		} catch (IOException e){ System.out.println("IO: " + e.getMessage());
		}finally {if(_aSocket != null) _aSocket.close();}
		
	}
	
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
				
				Charset _charset = Charset.forName("UTF-8");
				
				int _i;
				String _udpRequest="";
				
				for (_i = 0; _i < _buffer.length && _buffer[_i] != 0; _i++) {
					_udpRequest = new String(_buffer, 0, _i, _charset);
				}
					
				if( _udpRequest.contains("HoldRequests")){
					holdRequests = true;				
				}
				else if( _udpRequest.contains("ResumeRequests")){
					holdRequests = false;				
				}
			}
		} catch (SocketException e){ System.out.println("Socket: " + e.getMessage());
		} catch (IOException e){ System.out.println("IO: " + e.getMessage());
		}finally {if(_aSocket != null) _aSocket.close();}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Sequencer sq = new Sequencer();

	}

}
