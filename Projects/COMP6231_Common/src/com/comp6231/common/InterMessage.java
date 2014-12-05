package com.comp6231.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class InterMessage {
	/*
	 * Mark - Constructors
	 */
	
	public InterMessage() {
		parameters = new HashMap<String, String>();
	}

	/*
	 * Mark - Basic - Properties
	 */
	 
	private String type;
	private Map<String, String> parameters;
	
	/*
	 * Mark - Basic - Types
	 */
	
	public static final String TYPE_REGISTE_USER 			= "registerUser";
	public static final String TYPE_RESERVE_BOOK 			= "reserveBook";
	public static final String TYPE_SET_DURATION 			= "setDuration";
	public static final String TYPE_RESERVE_INTER_LIBRARY 	= "reserveInterLibrary";
	public static final String TYPE_GET_NON_RETURNERS 		= "getNonRetuners";
	
	public static final String TYPE_RETURN 					= "RETURN";
	
	/*
	 * Mark - Basic - Keys
	 */
	 
	public static final String KEY_RETURN_VALUE 				= "value";
	public static final String KEY_SEQUENCE_NUMBER 				= "sequenceNumber";
	public static final String KEY_REPLICA_MANAGER_PORT_NUMBER 	= "replicaManagerPortNumber";
	
	/*
	 * Mark - Basic - Methods
	 */

	public void addParameter(String name, String value){
		parameters.put(name, value);
	}
	
	public String getParameter(String name){
		return parameters.get(name);
	}

	public void removeParameter(String name) {
		parameters.remove(name);
	}
	
	/*
	 * Mark - Basic - Getters & Setters
	 */
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	
	/*
	 * Mark - Transform - Methods
	 */
	
	public byte[] encode(){
		StringBuilder sb = new StringBuilder();
		sb.append(type);
		sb.append("|");
		
		Set<Map.Entry<String, String>> entrySet = parameters.entrySet();
		for(Map.Entry<String, String> entry : entrySet){
			sb.append(entry.getKey() + ":" + entry.getValue() + ",");
		}
		if(entrySet.size() > 0){
			sb.deleteCharAt(sb.length() - 1);
		}
		
		sb.append("\n");
		System.out.println("Encoded Message: '" + sb.toString() + "'");
		return sb.toString().getBytes();
	}
	
	public void decode(byte[] messageBytes){
		String message = new String(messageBytes);
		int endIndex = message.indexOf('\n');
		message = message.substring(0, endIndex);
		String[] parts = message.split("\\|");
		this.type = parts[0];
		String[] parameterStrings = parts[1].split(",");
		System.out.println("---");
		System.out.println(this.type);
		System.out.println(message);
		for (String parameterString : parameterStrings){
			System.out.println(parameterString);
			String[] paraParts = parameterString.split(":");
			String name = paraParts[0];
			String value = paraParts[1];
			this.addParameter(name, value);
		}
	}
}
