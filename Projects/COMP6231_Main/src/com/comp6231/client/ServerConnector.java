package com.comp6231.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

import org.omg.CORBA.ORB;

import com.zhaozhe.server.ServerInfo;
import com.zhaozhe.server.ServerInfoManager;

import corba.LibraryServer;
import corba.LibraryServerHelper;

public class ServerConnector {

	public ServerInfo chooseServer() {
		System.out.println("Select a server to connect");

		int index = 0;
		ServerInfoManager serverInfoManager = ServerInfoManager.defaultManager();
		List<ServerInfo> serverInfos = serverInfoManager.getServers();
		for (ServerInfo serverInfo : serverInfoManager.getServers()) {
			System.out.println(index + ". " + serverInfo.getName());
			index++;
		}

		Scanner keyboard = new Scanner(System.in);
		index = keyboard.nextInt();
		keyboard.nextLine();

		return serverInfos.get(index);
	}

	// to get the stub object
	public LibraryServer connect(ServerInfo serverInfo) throws Exception{
		
		ORB orb = ORB.init(new String[1], null);

		BufferedReader br = new BufferedReader(new FileReader("corba/ior_" + serverInfo.getIdentity() + ".txt"));
		String ior = br.readLine();
		br.close();
		
		org.omg.CORBA.Object ref = orb.string_to_object(ior);

//		LibraryServer server = LibraryServerHelper.narrow(ref);
		return null;
	}

}
