package com.comp6231.client;

import com.zhaozhe.server.Response;
import com.zhaozhe.server.ServerError;
import com.zhaozhe.server.ServerInfo;

import corba.LibraryServer;

public class Client {

	/*
	 * Mark - Context
	 */

	protected ServerInfo serverInfo;
	protected LibraryServer libraryServer;
	
	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

	public LibraryServer getLibraryServer() {
		return libraryServer;
	}

	public void setLibraryServer(LibraryServer libraryServer) {
		this.libraryServer = libraryServer;
	}
	
	/*
	 * Mark - Basic - Methods
	 */
	 
	public void showResponse(Response response) {
		System.out.println("------- Result -------");
		if (response.isSuccess()) {
			System.out.println("Success: " + response.getData());
		} else {
			System.out.println("Attention: " + ServerError.getMessage(response.getErrorCode()));
		}
	}
}
