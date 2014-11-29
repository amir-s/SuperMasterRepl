import java.io.FileNotFoundException;
import java.util.Scanner;
import client.LibraryServerMain;
import client.LibraryServerMainService;



// The Client class will be used in the client application
// which has all the functionality that a client should have
public class Client {
	// variables
	
	// config file
	private Config cnf = new Config();
	// orb
	
	// Returns the remote object of the library based on the institution name
	public LibraryServerMain getLibrary() {
		// look by the IOR that has been written into the config file.
		// get the remote object and return it to the caller
		LibraryServerMainService service = new LibraryServerMainService();
		LibraryServerMain srv = service.getLibraryServerMainPort();
		return srv;
	}
	
	
	// create an account based on all the values that a library needs
	// to create a user. the function returns true when the registration is completed
	// and returns false when there is another user with the same user name or an exception happens
	
	public boolean createAccount(String educationalInstitute, String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password) {
		Logger.log("Client", username, "Trying to be registered");
		try {
			// try to register the user
			// into the desited library
			
			// if registration was successful
			if (getLibrary().registerUser(educationalInstitute,firstName, lastName, emailAddress, phoneNumber, username, password)) {
				Logger.log("Client", username, "User has been registered");
				return true;
			}else {
				// username is already exists
				Logger.log("Client", username, "Username already exists");
				return false;
			}
		// catch all the execptions
		// log it and return false
		}catch (Exception e) {
			Logger.log("Client", username, "There was a problem: " + e.getMessage());
			return false;
		}
		
	}
	
	
	// this function tries to reserve the book with bookName and authorName values
	// from the educational institute, if the book is finished or an exception happens
	// this function returns false, other wise it will return true.
	public boolean reserveBook(String educationalInstitute,String username, String password, String bookName, String authorName) {
		Logger.log("Client", username, "Trying to reserve " + bookName + ":" + authorName);
		try {
			// get the library from remote repo and call the reserve function on that
			// the returns value of the function says if the process was successful or not
			if (getLibrary().reserveBook(educationalInstitute,username, password, bookName, authorName)) {
				Logger.log("Client", username, " Reserved " + bookName + ":" + authorName);
				return true;
			}
			// the operation was not successful so we print the 
			// appropriate message and return false
			Logger.log("Client", username, " could NOT Reserved " + bookName + ":" + authorName);
			return false;
			
		// catch exceptions and return false
		}catch (Exception e) {
			System.out.println("There was a problem with " + educationalInstitute + " " + e.getMessage());
			return false;
		}
	}
	
	// This function is like the above function
	// but instead of 'reserve', it will call 'reserveInterLibrary' on the remote
	// library which will reserve the book from another school if the book is not
	// available in the requested library.
	public boolean interReserveBook(String educationalInstitute,String username, String password, String bookName, String authorName) {
		Logger.log("Client", username, "Trying to Inter Reserve " + bookName + ":" + authorName);
		try {
			// get the library from remote repo and call the reserve function on that
			// the returns value of the function says if the process was successful or not
			if (getLibrary().reserveInterLibrary(educationalInstitute, username, password, bookName, authorName)) {
				Logger.log("Client", username, " Reserved " + bookName + ":" + authorName);
				return true;
			}
			// the operation was not successful so we print the 
			// appropriate message and return false
			Logger.log("Client", username, " could NOT Reserved " + bookName + ":" + authorName);
			return false;
		
		// catch exceptions and return false
		}catch (Exception e) {
			System.out.println("There was a problem with " + educationalInstitute + " " + e.getMessage());
			return false;
		}
	}
	
	// this function creates bunch of users on different
	// schools. for the sake of test, usernames will start
	// with different characters, 'a', 'b', 'c', ...
	// like this: d.user.19, and the password will be pass19
	
	public static void test() {
		Client c = new Client();
		String[] univs = {"concordia","mcgill","polymtl"};
		for (int i=0;i<20;i++) {
			c.createAccount(univs[i%3], "Name"+i, "Family"+i, "email" + i + "@gmail.com", "+111." + i, ""+(char) ('a'+(i/5)) +".user." + i, "pass"+i);
			Logger.log("TEST", "N/A", "Adding user to " + univs[i%3] + " : " + "Name"+i + " Family"+i + " email" + i + "@gmail.com" + " +111." + i + " "+(char) ('a'+(i/5)) +".user." + i + " pass"+i);
			
//			if (i%2 == 0) {
//				c.reserveBook(univs[i%3], ""+(char) ('a'+(i/5)) +".user." + i, "pass"+i, "book"+(i/4), "author"+(i/4));
//			}
		}
		
	}
	public static void main(String[] args) throws FileNotFoundException {
		// init the ORB
		
		// generate test usernames
		//test();
		
		// ready for input
		Client c = new Client();
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			// print the menu
			System.out.println("** Menu:");
			System.out.println("   1. Register new user.");
			System.out.println("   2. Reserve a book");
			System.out.println("   3. Reserve a book InterLib");
			
			// detect EOF
			if (!sc.hasNext()) break;
			
			// read option
			int opt = sc.nextInt();
			
			// 1. Register new user.
			if (opt == 1) {
				
				// reading the needed inputs to comminicate
				// to the desired server
				String[] properties = new String[7];
				String[] labels = {"First Name","Last Name","Email","Phone","Username","Password","Institute"};
				for (int i=0;i<properties.length;i++) {
					System.out.println("Enter " + labels[i]);
					properties[i] = sc.next();
				}
				// actually call the function on the client and get the result
				if (c.createAccount(properties[6], properties[0], properties[1], properties[2], properties[3], properties[4], properties[5])) {
					System.out.println("Done");
				}else {
					System.out.println("There was a problem");
				}
			// 2. Reserve a book
			}else if (opt == 2) {
				// reading the needed inputs to comminucate
				// to the desired server
				String[] properties = new String[5];
				String[] labels = {"Username","Password","Book Name","Book Author","Institute"};
				for (int i=0;i<properties.length;i++) {
					System.out.println("Enter " + labels[i]);
					properties[i] = sc.next();
				}
				// actually call the function on the client and get the result
				if (c.reserveBook(properties[4], properties[0], properties[1], properties[2], properties[3])) {
					System.out.println("Done");
				}else {
					System.out.println("There was a problem");
				}
			// 3. Reserve a book InterLib
			}else if (opt == 3) {
				// reading the needed inputs to comminucate
				// to the desired server
				String[] properties = new String[5];
				String[] labels = {"Username","Password","Book Name","Book Author","Institute"};
				for (int i=0;i<properties.length;i++) {
					System.out.println("Enter " + labels[i]);
					properties[i] = sc.next();
				}
				// actually call the function on the client and get the result
				if (c.interReserveBook(properties[4], properties[0], properties[1], properties[2], properties[3])) {
					System.out.println("Done");
				}else {
					System.out.println("There was a problem");
				}
			}else {
				System.out.println("Enter a valid option!");
			}
		}
	}
}
