package com.comp6231.zhaozhe.server;



public class Response{
	
	/*
	 * Mark - Constructors
	 */
	
	public Response() {
		this.errorCode = "";
		this.data = "";
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
	
	public String toString(){
		return this.errorCode + "@" + this.data;
	}
	
}
