package com.replicaManager;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.Scanner;

import com.UDPTransport.PacketServer;
import com.UDPTransport.UDPTransporter;
import com.heartbeat.HeartBeatCallBack;
import com.heartbeat.HeartBeatListener;

public class ReplicaManager {
	private String replica = null;
	private Process process = null;
	private int heartBeatPort = -1;
	private int commandPort = -1;
	int wrongCounter = 0;
	private String sequencerHost = "localhost";
	private int sequencerPort = 4050;
	
	Process startProcess () throws IOException {
		wrongCounter = 0;
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
	
	public ReplicaManager(final String replica, int heartBeatPort, int commandPort) throws Exception {
		this.replica = replica;
		this.heartBeatPort = heartBeatPort;
		this.commandPort = commandPort;
		
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
		
		final ReplicaManager self = this;
		UDPTransporter.server(commandPort, new PacketServer() {
			public String serve(String in) {
				if (in.toUpperCase().contains("WRONG")) {
					System.out.println("GOT WRONG COMMAND");
					self.wrongCounter++;
					System.out.println("Counter = " + self.wrongCounter);
					if (self.wrongCounter >= 3) {
						try {
							self.restartProcess();
						} catch (Exception e) {
							System.out.println("Can not restart the process!");
						}
					}
				}
				return "OK";
			}
		});
		
		startProcess();
		
	}
	
	
	// USAGE:
	// java -cp bin/:../COMP6231_Common/bin com.replicaManager.ReplicaManager amir 4021 4011
	// java -cp bin/:../COMP6231_Common/bin com.replicaManager.ReplicaManager ilyas 4022 4012
	// java -cp bin/:../COMP6231_Common/bin com.replicaManager.ReplicaManager alex 4023 4013
	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("You should provide the replica name and heartbeat port");
			return;
		}
		System.out.println("Starting " + args[0] + " with heart beat port=" + args[1]);
		ReplicaManager rm = new ReplicaManager(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			if (sc.next().equals("r")) {
				rm.restartProcess();
			}
		}
		sc.close();
	}
}
