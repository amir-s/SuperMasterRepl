package com.replicaManager;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

import com.heartbeat.HeartBeatCallBack;
import com.heartbeat.HeartBeatListener;

public class ReplicaManager {
	static void start () throws IOException {
		ProcessBuilder pb = new ProcessBuilder();
		String path = "./amir-server.jar";
		pb.command("java", "-jar",  path);
		pb.redirectError(Redirect.INHERIT);
		pb.redirectOutput(Redirect.INHERIT);
		pb.directory(new File("./replicas/amir/"));
		pb.start();
//		
	}
	public static void main(String[] args) throws Exception {
		HeartBeatListener hbl = new HeartBeatListener(4021, new HeartBeatCallBack() {
			
			@Override
			public void up() {
				System.out.println("UP");
				
			}
			
			@Override
			public void down() {
				System.out.println("DOWN");
				System.out.println("Starting again ...");
				try {
					ReplicaManager.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		ReplicaManager.start();
		
	}
}
