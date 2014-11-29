package com.comp6231.zhaozhe.server;

import java.io.File;


public class ServerInfo {
	
	/*
	 * Mark - Constructors
	 */

	public ServerInfo() {
		super();
	}
	
	public ServerInfo(String name, int portForRMI, int portForUDP) {
		super();
		this.name = name;
		this.identity = portForRMI;
		this.portForUDP = portForUDP;
		
		String path = "/Users/Zhaozhe/Documents/study/Concorida/Courses/2014_09_COMP 6231_Distributed_System_Design/Workspace/Assignment_1";
		path = path + "/" + "data";
		path = path + "/" + name;
		path = path + "/" + "log";
		path = path + "/" + "users";
		
		new File(path).mkdirs();
		
	}
	
	/*
	 * Mark - Basic - Properties
	 */
	 
	private String name;
	private int identity;
	private int portForUDP;
	
	/*
	 * Mark - Basic - Getters & Setters
	 */
	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdentity() {
		return identity;
	}
	public void setIdentity(int identity) {
		this.identity = identity;
	}
	public int getPortForUDP() {
		return portForUDP;
	}
	public void setPortForUDP(int portForUDP) {
		this.portForUDP = portForUDP;
	}
}
