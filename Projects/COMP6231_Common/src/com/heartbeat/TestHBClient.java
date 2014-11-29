package com.heartbeat;

public class TestHBClient {
	public static void main(String[] args) {
		Heart h = new Heart("localhost", 5003);
		h.beat();
	}
}
