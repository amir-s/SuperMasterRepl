package com.zhaozhe.server;

import java.util.HashMap;
import java.util.Map;

public class ServerError {
	public static final String SUCCESS 						= "100";
	
	public static final String ACCOUNT_NOT_EXISTED 			= "211";
	public static final String ACCOUNT_EXISTED 				= "212";
	public static final String ACCOUNT_WRONG_PASSWORD 		= "213";
	public static final String ACCOUNT_IS_NOT_ADMIN 		= "214";
	
	public static final String BOOK_NOT_EXISTED 			= "311";
	public static final String BOOK_NOT_ENOUGH 				= "313";
	public static final String BOOK_NOT_ENOUGH_EVEN_REMOTE 	= "314";
	
	private static Map<String, String> messages;
	
	static {
		messages = new HashMap<String, String>();
		messages.put(
				ACCOUNT_NOT_EXISTED, 
				"This account does't exist");
		messages.put(
				ACCOUNT_EXISTED, 
				"This account name has already existed");
		messages.put(
				ACCOUNT_WRONG_PASSWORD, 
				"The password is wrong");
		messages.put(
				ACCOUNT_IS_NOT_ADMIN, 
				"This account is not an admin");
		
		messages.put(
				BOOK_NOT_EXISTED, 
				"This book doesn't exist");
		messages.put(
				BOOK_NOT_ENOUGH, 
				"all books has been rented");
		messages.put(
				BOOK_NOT_ENOUGH_EVEN_REMOTE, 
				"not even a book in other libraries");
	}

	public static String getMessage(String errorCode) {
		return messages.get(errorCode);
		
	}
}
