package com.comp6231.common;

import java.util.HashMap;
import java.util.Map;

public class ServerError {
	public static final String SUCCESS 						= "0";
	public static final String SERVER_FAILED 				= "1";
	public static final String AUTENTCIATION_FAILED 		= "2";
	public static final String BOOK_NOT_EXISTED 			= "3";
	public static final String ACCOUNT_EXISTED 				= "5";
	
	
	
	private static Map<String, String> messages;
	
	static {
		messages = new HashMap<String, String>();
		messages.put(SERVER_FAILED, "Server Failed");
		messages.put(AUTENTCIATION_FAILED, "Autentciation Failed");
		messages.put(SERVER_FAILED, "Book not existed");
		messages.put(SERVER_FAILED, "Account existed");
	}

	public static String getMessage(String errorCode) {
		return messages.get(errorCode);
	}
}
