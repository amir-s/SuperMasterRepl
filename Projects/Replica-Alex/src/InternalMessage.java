

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InternalMessage {

	/*
	 * Mark - Constructors
	 */
	
	public InternalMessage() {
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

	public static final String TYPE_GET_NON_RETURNS = "GET_NON_RETURNS";
	public static final String TYPE_HAS_BOOK = "HAS_BOOK";
	public static final String TYPE_RESERVE_BOOK = "RESERVE_BOOK";
	public static final String TYPE_RETURN = "RETURN";
	
	/*
	 * Mark - Basic - Methods
	 */

	public void addParameter(String name, String value){
		parameters.put(name, value);
	}
	
	public String getParameter(String name){
		return parameters.get(name);
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
		
		return sb.toString().getBytes();
	}
	
	public void decode(byte[] messageBytes){
		String message = new String(messageBytes);
		int endIndex = message.lastIndexOf('\n');
		message = message.substring(0, endIndex);
		
		String[] parts = message.split("\\|");
		this.type = parts[0];
		
		String[] parameterStrings = parts[1].split(",");
		for (String parameterString : parameterStrings){
			String[] paraParts = parameterString.split(":");
			String name = paraParts[0];
			String value = paraParts[1];
			this.addParameter(name, value);
		}
	}
}
