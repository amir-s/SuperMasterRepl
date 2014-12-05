package com.comp6231.zhaozhe.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerInfoManager {
	
	/*
	 * Mark - Singleton - Properties
	 */
	 
	private static ServerInfoManager defaultManager;
	
	/*
	 * Mark - Singleton - Methods
	 */
	 
	public static ServerInfoManager defaultManager(){
		if (defaultManager == null){
			defaultManager = new ServerInfoManager();
		}
		return defaultManager;
	}
	
	/*
	 * Mark - Constructors
	 */
	
	private ServerInfoManager(){
		servers = new ArrayList<ServerInfo>();
		serversByName = new HashMap<String, ServerInfo>();
		
		addServerInfo(new ServerInfo("Concordia", 1, 7001));
		addServerInfo(new ServerInfo("McGill", 2, 7002));
		addServerInfo(new ServerInfo("Polytechnique", 3, 7003));
	}
	
	/*
	 * Mark - Basic - Properties
	 */
	 
	private List<ServerInfo> servers;
	private Map<String, ServerInfo> serversByName;
	
	/*
	 * Mark - Basic - Methods
	 */
	 
	public void addServerInfo(ServerInfo serverInfo){
		servers.add(serverInfo);
		serversByName.put(serverInfo.getName(), serverInfo);
	}

	public List<ServerInfo> getServers() {
		return servers;
	}

}
