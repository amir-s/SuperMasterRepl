package com.comp6231.common;



public class Response{
	
	/*
	 * Mark - Constructors
	 */
	
	public Response() {
		this.errorCode = "";
		this.data = "";
	}

	public Response(String responseString) {
		setFromString(responseString);
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
	 * Mark - String Coversion - Methods
	 */
	 
	public String toString() {
		return errorCode + "@" + data;
	}
	
	public void setFromString(String responseValue) {
		String[] parts = responseValue.split("\\@");
		errorCode = parts[0];
		if (parts.length > 1) {
			data = parts[1];
		} else {
			data = "";
		}
	}
	
}