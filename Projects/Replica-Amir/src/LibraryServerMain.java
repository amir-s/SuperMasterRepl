import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.jws.*;
import javax.jws.*;
import javax.xml.ws.Endpoint;
// this class boots up all 3 libraries
@WebService(targetNamespace="lib")
public class LibraryServerMain implements LibraryServer{
	HashMap<String, LibraryImpl> libs = new HashMap<String, LibraryImpl>();
	public LibraryServerMain() throws FileNotFoundException, UnsupportedEncodingException {
	
		// write udp port numbers in config file
		Config cnf = new Config()
				.set("concordia:udp:port", 3020)
				.set("mcgill:udp:port", 3021)
				.set("polymtl:udp:port", 3022)
			.write();
		
		String[] univs = {"concordia", "mcgill", "polymtl"};
		// loop over schools and register them into POA
		for (String u: univs) {
			libs.put(u,new LibraryImpl(u, cnf.getInt(u+":udp:port")).loadBooks(u+".books"));
		}
		
		// write all the key values into the config file
		cnf.write();
		
	}

	// export and expose the API
	@WebMethod
	public boolean registerUser(String InstName, String firstName,
			String lastName, String emailAddress, String phoneNumber,
			String username, String password) {
		return	libs.get(InstName).registerUser(firstName, lastName, emailAddress, phoneNumber, username, password);
	
	}
	// export and expose the API
	@WebMethod
	public boolean reserveBook(String InstName, String username,
			String password, String bookName, String authorName) {
		return libs.get(InstName).reserveBook(username, password, bookName, authorName);
		
	}
	// export and expose the API
	@WebMethod
	public void setDuration(String InstName, String adminUsername,
			String adminPassword, String username, String bookName,
			String authorName, int days) {
		libs.get(InstName).setDuration(adminUsername, adminPassword, username, bookName, authorName, days);
		
	}
	// export and expose the API
	@WebMethod
	public String getNonRetuners(String InstName, String adminUsername,
			String adminPassword, int days) {
		return libs.get(InstName).getNonRetuners(adminUsername, adminPassword, days);
		
	}
	// export and expose the API	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		Endpoint ep = Endpoint.publish("http://localhost:7777/lib", new LibraryServerMain());
		if (ep.isPublished()) {
			System.out.println("Servers are published at http://localhost:7777/lib");
		}
	}

	// export and expose the API
	@WebMethod
	public boolean reserveInterLibrary(String InstName, String username, String password,
			String bookName, String authorName) {
		return libs.get(InstName).reserveInterLibrary(username, password, bookName, authorName);
	}
}
