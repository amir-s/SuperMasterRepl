

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comp6231.common.ILibrary;
import com.comp6231.common.UDPServer;

public class LibraryServerDispatcher implements ILibrary{
	
	/*
	 * Mark - Driver - Methods
	 */
	 
	public static void main(String[] args) {
		new LibraryServerDispatcher();
	}
	
	/*
	 * Mark - Constructors
	 */
	
	public LibraryServerDispatcher() {
		initPortal();
		initServers();
	}
	
	/*
	 * Mark - Portal - Properties
	 */
	
	private UDPServer portal;
	 
	/*
	 * Mark - Portal - Methods
	 */
	
	private void initPortal() {
		portal = new UDPServer();
		portal.setHeartBeatsPortNumber(4023);
		portal.setPortNumber(7000);
		portal.setLibrary(this);
		portal.start();
	}
	
	/*
	 * Mark - Servers - Properties
	 */
	
	private Map<String, LibraryServer> libraries; 
	
	/*
	 * Mark - Servers - Methods
	 */
	
	private void initServers() {
		System.out.println("Alex Replica");
		libraries = new HashMap<String, LibraryServer>();
		
		List<ServerInfo> infos = ServerInfoManager.defaultManager().getServers();
		for (ServerInfo info : infos) {
			LibraryServer libraryServer = new LibraryServer(info);
			libraries.put(info.getName(), libraryServer);
		}
	}
	
	/*
	 * Mark - Proxy - Methods
	 */
	 
	 
	
	@Override
	public String registerUser(String instName, String firstName,
			String lastName, String emailAddress, String phoneNumber,
			String username, String password) {
		
		System.out.println("LibraryServerDispatcher : Function Called : registerUser");
		
		LibraryServer libraryServer = libraries.get(instName);
		return libraryServer.registerUser(instName, firstName, lastName, emailAddress, phoneNumber, username, password);
	}

	@Override
	public String reserveBook(String instName, String username,
			String password, String bookName, String authorName) {
		
		LibraryServer libraryServer = libraries.get(instName);
		return libraryServer.reserveBook(instName, username, password, bookName, authorName);
	}

	@Override
	public String setDuration(String instName, String adminUsername,
			String adminPassword, String username, String bookName,
			String authorName, int days) {
		
		LibraryServer libraryServer = libraries.get(instName);
		return libraryServer.setDuration(instName, adminUsername, adminPassword, username, bookName, authorName, days);
	}

	@Override
	public String reserveInterLibrary(String instName, String username,
			String password, String bookName, String authorName) {
		
		LibraryServer libraryServer = libraries.get(instName);
		return libraryServer.reserveInterLibrary(instName, username, password, bookName, authorName);
	}

	@Override
	public String getNonRetuners(String instName, String adminUsername,
			String adminPassword, int days) {
		
		LibraryServer libraryServer = libraries.get(instName);
		return libraryServer.getNonRetuners(instName, adminUsername, adminPassword, days);
	}
	
	
}