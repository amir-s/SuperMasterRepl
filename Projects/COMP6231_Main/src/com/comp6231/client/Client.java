package com.comp6231.client;

import com.comp6231.common.Response;
import com.comp6231.common.ServerError;

import corba.LibraryServer;

public class Client {

	/*
	 * Mark - Context
	 */

	protected String instName;
	protected LibraryServer libraryServer;

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
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
