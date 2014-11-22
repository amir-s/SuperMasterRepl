package com.zhaozhe.server;

import corba.CORBAResponse;


public class Response{
	
	/*
	 * Mark - Constructors
	 */
	
	public Response() {
		this.errorCode = "";
		this.data = "";
	}

	public Response(CORBAResponse response) {
		setFromCorba(response);
	}
	
	/*
	 * Mark - Basic - Properties
	 */

	private String errorCode;
	private String data;
	
	/*
	 * Mark - Basic - Methods
	 */
	
	public boolean isSuccess()
	{
		return errorCode.equals(ServerError.SUCCESS);
	}
	
	/*
	 * Mark - Basic - Getters & Setters
	 */
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	/*
	 * Mark - CORBA Coversion - Methods
	 */
	 
	public CORBAResponse toCorba() {
		CORBAResponse response = new CORBAResponse();
		response.errorCode = errorCode;
		response.data = data;
		return response;
	}
	
	public void setFromCorba(CORBAResponse response) {
		errorCode = response.errorCode;
		data = response.data;
	}
}
