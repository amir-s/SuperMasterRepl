package com.replicaManager;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.Scanner;

import com.UDPTransport.UDPTransporter;
import com.heartbeat.HeartBeatCallBack;
import com.heartbeat.HeartBeatListener;

public class ReplicaManager {
	private String replica = null;
	private Process process = null;
	private int heartBeatPort = -1;
	
	private String sequencerHost = "localhost";
	private int sequencerPort = 4050;
	
	Process startProcess () throws IOException {
		System.out.println("STARTING REPLICA " + replica);
		ProcessBuilder pb = new ProcessBuilder();
		String path = "./" + replica + ".jar";
		pb.command("java", "-jar",  path);
		pb.redirectError(Redirect.INHERIT);
		pb.redirectOutput(Redirect.INHERIT);
		pb.directory(new File("./replicas/" + replica + "/"));
		process = pb.start();
		System.out.println("Sending START to Sequencer");
		UDPTransporter.send(sequencerHost, sequencerPort, "START");
		return process;
	}
	Process restartProcess() throws Exception {
		System.out.println("Sending STOP to Sequencer");
		UDPTransporter.send(sequencerHost, sequencerPort, "STOP");
		System.out.println("RESTARTING REPLICA " + replica);
		
		System.out.println("KILLING REPLICA " + replica);
		if (process != null) process.destroy();
		Thread.sleep(1000);
		return startProcess();
	}
	
	public ReplicaManager(final String replica, int heartBeatPort) throws Exception {
		this.replica = replica;
		this.heartBeatPort = heartBeatPort;
		
		new HeartBeatListener(this.heartBeatPort, new HeartBeatCallBack() {
			
			public void up() {
				System.out.println("REPLICA " + replica + " IS UP");
			}
			
			public void down() {
				System.out.println("REPLICA " + replica + " IS DOWN");
				System.out.println("STARTING " + replica + " AGAIN");
				try {
					restartProcess();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		startProcess();
		
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("You should provide the replica name and heartbeat port");
			return;
		}
		System.out.println("Starting " + args[0] + " with heart beat port=" + args[1]);
		ReplicaManager rm = new ReplicaManager(args[0], Integer.parseInt(args[1]));
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			if (sc.next().equals("r")) {
				rm.restartProcess();
			}
		}
		sc.close();
	}
}
