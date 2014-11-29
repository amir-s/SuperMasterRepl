import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.jws.*;
import javax.jws.*;
import javax.xml.ws.Endpoint;
// this class boots up all 3 libraries
@WebService(targetNamespace="lib")
public class LibraryServerDispathcer implements ILibrary{
	HashMap<String, LibraryImpl> libs = new HashMap<String, LibraryImpl>();
	public LibraryServerDispathcer() throws FileNotFoundException, UnsupportedEncodingException {
	
		// write udp port numbers in config file
		Config cnf = new Config()
				.set("concordia:udp:port", 5001)
				.set("mcgill:udp:port", 5002)
				.set("polytechnique:udp:port", 5003)
			.write();
		
		String[] univs = {"concordia", "mcgill", "polytechnique"};
		// loop over schools and register them into POA
		for (String u: univs) {
			libs.put(u,new LibraryImpl(u, cnf.getInt(u+":udp:port")).loadBooks(u+".books"));
		}
		
		// write all the key values into the config file
		cnf.write();
		
	}

	
	public String registerUser(String InstName, String firstName,
			String lastName, String emailAddress, String phoneNumber,
			String username, String password) {
		return libs.get(InstName).registerUser(firstName, lastName, emailAddress, phoneNumber, username, password)+"@";
	
	}

	public String reserveBook(String InstName, String username,
			String password, String bookName, String authorName) {
		return libs.get(InstName).reserveBook(username, password, bookName, authorName)+"@";
		
	}

	
	public String setDuration(String InstName, String adminUsername,
			String adminPassword, String username, String bookName,
			String authorName, int days) {
		return libs.get(InstName).setDuration(adminUsername, adminPassword, username, bookName, authorName, days)+"@";
		
	}

	public String getNonRetuners(String InstName, String adminUsername,
			String adminPassword, int days) {
		String res = libs.get(InstName).getNonRetuners(adminUsername, adminPassword, days);
		if (res.equals("-1")) return "2@";
		return "0@"+res;
	}


	public String reserveInterLibrary(String InstName, String username, String password,
			String bookName, String authorName) {
		return libs.get(InstName).reserveInterLibrary(username, password, bookName, authorName)+"@";
	}
}
