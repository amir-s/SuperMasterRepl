package com.heartbeat;

public class TestHBServer {
	public static void main(String[] args) throws Exception {
		new HeartBeatListener(5003, new HeartBeatCallBack() {
			
			@Override
			public void up() {
			 System.out.println("UP");
				
			}
			
			@Override
			public void down() {
				System.out.println("DOWN");
				
			}
		});
	}
}
