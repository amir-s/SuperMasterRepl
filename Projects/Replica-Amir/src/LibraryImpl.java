import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.UDPTransport.PacketServer;
import com.UDPTransport.UDPTransporter;





// The implementation of the Library of school
// This class has all the methods that the school
// needed, this is a CORBA object that will be accessible remotely

public class LibraryImpl {
	
	// institudeName
	private String institudeName;
	
	// The UDP port that this lib is listening to
	private int UDPPort;
	
	// list of studends. This map, maps the usernames to student objects
	// and there is a map for each starting letter of the usernames
	// so we can lock them separately
	private HashMap<String, Student>[] students = new HashMap[26];
	
	// This map is being used to map the name of the books
	// to its corresponding book object
	private HashMap<String, Book> books = new HashMap<String, Book>();
	
	// this is the lock manager for doing
	// some stuff synchronized. it will return and object
	// that we can lock, based on the key that we
	// provide it.
	private LockManager lm = new LockManager();
	
	// constructor
	public LibraryImpl(String name, int UDPPort) {
		this.institudeName = name;
		this.UDPPort = UDPPort;
		
		// create all the hashmaps for storing usernames
		for (char c='a'; c<='z';c++) {
			students[c-'a'] = new HashMap<String, Student>();
		}
		// for using in UDP server
		final LibraryImpl self = this;
		try {
			
			// create the UDP server
			UDPTransporter.server(UDPPort, new PacketServer() {
				
				// this function will be called  when a package comes in
				public String serve(String in) {
					Logger.log(self.institudeName, "N/A", "Recieved " + in + " from UDP server");
					
					// is it a get-non-returners list from another school?
					if (in.startsWith("nonRetList-")) {
						// parse the incoming data
						// get the parameters
						String s = in.substring(11, in.indexOf('@'));
						int days = Integer.parseInt(s);
						Logger.log(self.institudeName, "N/A", "Getting NonReturnersList(" + days + ") for sending through UDP");
						// send back the list of non-returners based on the input
						return self.listNonReturners(days);
						
					// is it a inter reserve request?
					}else if (in.startsWith("InterReserve-")) {
						// parse the incoming data
						// get all the parameters
						String s = in.substring(13, in.indexOf('@'));
						String[] book = s.split("\\$");
						Logger.log(self.institudeName, "N/A", "Got request for InterReserve for (" + book[0] + ":" + book[1] + ")");
						// can we reserve this book for this user?
						if (interReserveBook(book[0], book[1]) == 0) return "TRUE";
						// no we cant!
						return "FALSE";
					}
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// for logging purposes
	private void log(String msg) {
		System.out.println("LOG [" + institudeName + "] " + msg);
	}
	
	// register the user in this local school
	// based on the values that will be feeded in
	// returns true or false based on the successfulness
	// of the operation
	public int registerUser(String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password) {
		Logger.log(institudeName, username, "Registering user");
		
		// lock the block based on the username requested
		synchronized (lm.get(getKey(username))) {
			// get the list of students starting with the same character
			// in username
			HashMap<String, Student> list = getList(getKey(username));
			// check if this username is already exist
			// and return false if it is.
			if (list.containsKey(username)) {
				Logger.log(institudeName, username, "Username exists");
				return 5;//username exists
			}
			// put this user into the list and return true.
			list.put(username, new Student(firstName, lastName, emailAddress, phoneNumber, username, password));
			Logger.log(institudeName, username, "Username registered");
		}
		return 0;
	}
	
	// check if a username and password that provided
	// is valid and assigned to a user
	public boolean check(String username, String password) {
		
		// lock the list
		synchronized (lm.get(getKey(username))) {
			Logger.log(institudeName, username, "Authenticating");
			// get the list
			HashMap<String, Student> list = getList(getKey(username));
			// check if this username exists and if its password is right.
			return list.containsKey(username) && list.get(username).auth(username, password);
		}
	}
	// get the key for locking based on the username
	private char getKey(String username) {
		// return first character, in lowercase
		return username.toLowerCase().charAt(0);
	}
	// get the list of users
	private HashMap<String, Student> getList(char k) {
		return students[k-'a'];
	}

	// reserve the needed book if it is already exists in the local repo
	// and if its quantity is more than zero. this reservatoin is for the 
	// user that provided the username and password
	public int reserveBook(String username, String password, String bookName, String authorName) {
		Logger.log(institudeName, username, "Reserving book " + bookName + ":" + authorName + " for " + username);
		// authentication
		if (!check(username, password)) {
			Logger.log(institudeName, username, "Authentication failed");
			return 2;
		}
		// lower case all in input
		bookName = bookName.toLowerCase();
		authorName = authorName.toLowerCase();
		
		// do we have this book?
		if (!books.containsKey(bookName+":"+authorName)) {
			Logger.log(institudeName, username, "Book " + bookName + ":" + authorName + " not found");
			return 3;
		}
		// get the book
		Book b = books.get(bookName+":"+authorName);
		// lock the book
		synchronized (b) {
			// we dont have any of this book right now, it is finished
			if (b.quantity == 0) {
				Logger.log(institudeName, username, "Book " + bookName + ":" + authorName + " is finished");
				return 3;
			}
			// check if this user is already in the list of borrowers of this book
			if (b.borrowers.containsKey(username)) {
				Logger.log(institudeName, username, "Book " + bookName + ":" + authorName + " is already borrowed");
				return 3;
			}
			// decrease the quantity and put the reservation
			b.quantity--;
			Logger.log(institudeName, username, "Book " + bookName + ":" + authorName + " is reserved. Quantity: " + b.quantity);
			b.borrowers.put(username, new Integer(14));
		}
		// return
		return 0;
	}
	
	// reserve the needed book if it is already exists in the local repo
	// and if its quantity is more than zero.
	// this function does not care about the person that reserve this book
	// it should be handled outside
	public int interReserveBook(String bookName, String authorName) {
		Logger.log(institudeName, "INTER", "Reserving book " + bookName + ":" + authorName);
		
		// lower case the input
		bookName = bookName.toLowerCase();
		authorName = authorName.toLowerCase();
		
		// do we have this book?
		if (!books.containsKey(bookName+":"+authorName)) {
			Logger.log(institudeName, "INTER", "Book " + bookName + ":" + authorName + " not found");
			return 3;
		}
		// get the book
		Book b = books.get(bookName+":"+authorName);
		// lock it
		synchronized (b) {
			// do we still have it?
			if (b.quantity == 0) {
				Logger.log(institudeName, "INTER", "Book " + bookName + ":" + authorName + " is finished");
				return 3;
			}
			// decrease its quantity
			b.quantity--;
			Logger.log(institudeName, "INTER", "Book " + bookName + ":" + authorName + " is reserved. Quantity: from " + (b.quantity+1) + " TO " + b.quantity);
		}
		return 0;
	}
	
	
	// load the list of the books from the provided file
	// into the books list
	public LibraryImpl loadBooks(String file) {
		Scanner sc;
		try {
			sc = new Scanner(new File(file));
			while (sc.hasNextLine()) {
				// read information for each book from the file
				String name = sc.nextLine().toLowerCase();
				String author = sc.nextLine().toLowerCase();
				int quantity = Integer.parseInt(sc.nextLine());
				// put it into list
				books.put(name+":"+author, new Book(name, author, quantity));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			log("There was a problem with Config file " + e.getMessage());
		}
		return this;
	}
	
	// authenticate admin with hard coded username and password
	private boolean authAdmin(String username, String password) {
		return username.toLowerCase().equals("admin") && password.toLowerCase().equals("admin");
	}
	
	// set reservation duration of reservation
	public int setDuration(String adminUsername, String adminPassword, String username, String bookName, String authorName, int days) {
		// lowercase the book properties
		bookName = bookName.toLowerCase();
		authorName = authorName.toLowerCase();
		Logger.log(institudeName, adminUsername, "Set Duration for " + username + " @ " + bookName + ":" + authorName);
		// authenticating
		if (!authAdmin(adminUsername, adminPassword)) {
			Logger.log(institudeName, adminUsername, "Admin authentication failed");
			return 2;
		}
		// get the book
		Book b = books.get(bookName+":"+authorName);
		// is it real?
		if (b == null) {
			Logger.log(institudeName, adminUsername, "Book not found " + bookName + ":" + authorName);
			return 4;
		}
		// lock it
		synchronized (b) {
			// it is not borrowed by the specified user
			if (!b.borrowers.containsKey(username)) {
				Logger.log(institudeName, adminUsername, "User " + username + " didn't borrowed " + bookName + ":" + authorName);
				return 4;
			}
			// get the current duration
			int oldValue = b.borrowers.get(username).intValue();
			// set the new duration
			b.borrowers.put(username, new Integer(oldValue+days));
			Logger.log(institudeName, adminUsername, "Duration for " + username + " @ " + bookName + ":" + authorName + " is now " + b.borrowers.get(username));
		}
		return 0;
	}
	
	// generate the list of users that borrowed a book
	// and their duration is less than `days`
	private String listNonReturners(int days) {
		String result = "";
		Iterator<Entry<String, Book>> it = books.entrySet().iterator();
		// for over all books
		while (it.hasNext()) {
			// get the book borrowers' map
			Map.Entry<String, Book> book = (Entry<String, Book>) it.next();
			// lock the book
			synchronized (book) {
				// loop over the borrowers
				for (Map.Entry<String, Integer> record: book.getValue().borrowers.entrySet()) {
					if (record.getValue().intValue() >= days) continue; // is it desired?
					// add this to the return list
					Student st = getList(getKey(record.getKey())).get(record.getKey());
					result += "@" + institudeName + "," + st.firstName + "," + st.lastName + "," + st.phoneNumber;
				}
			}
		}
		// return list with institute name
		return result;
	}
	
	// exposed version of the non returners
	// which will collect all the list of
	// other schools too
	public String getNonRetuners(String adminUsername, String adminPassword, int days) {
		Logger.log(institudeName, adminUsername, "Getting non-returners");
		
		// authenticating
		if (!authAdmin(adminUsername, adminPassword)) {
			Logger.log(institudeName, adminUsername, "Admin authentication failed");
			return "-1";
		}
		// list of schools
		String other = "";
		String[] univs = {"concordia", "mcgill", "polytechnique"};
		Config cnf = new Config();
		
		// loop over all the schools
		for (String u: univs) {
			if (institudeName.equals(u)) continue;
			Logger.log(institudeName, adminUsername, "Requesting to " + u + " for non returners for " + days + "days");
			// get the request
			other += UDPTransporter.transport("localhost", cnf.getInt(u+":udp:port"), "nonRetList-" + days + "@" + Math.random());
		}
		return (listNonReturners(days) + other).replaceAll("[\\000]*", "");
	}
	
	public int reserveInterLibrary(String username, String password, String bookName, String authorName) {
		Logger.log(institudeName, username, "Inter Reserving book " + bookName + ":" + authorName + " for " + username);
		if (!check(username, password)) {
			Logger.log(institudeName, username, "Authentication failed");
			return 2;
		}
		if (reserveBook(username, password, bookName, authorName) == 0) {
			Logger.log(institudeName, username, "book found in local repo. " + bookName + ":" + authorName + " for " + username);
			return 0;
		}
		String[] univs = {"concordia", "mcgill", "polytechnique"};
		Config cnf = new Config();
		for (String u: univs) {
			if (institudeName.equals(u)) continue;
			Logger.log(institudeName, username, "Requesting to " + u + " to reserve " + bookName + ":" + authorName + " for " + username);
			String result = UDPTransporter.transport("localhost", cnf.getInt(u+":udp:port"), "InterReserve-" + bookName + "$" + authorName + "@" + Math.random());
			if (result.startsWith("TRUE")) {
				Logger.log(institudeName, username, "Found the book on " + u + "! Reserving ...");
				Book b = new Book(bookName, authorName, 0);
				books.put(bookName+":"+authorName+"##"+u+"@"+username, b);
				b.borrowers.put(username, new Integer(14));
				Logger.log(institudeName, username, "Added reservation " + bookName+":"+authorName+"##"+u+"@"+username + " for user " + username);
				return 0;
			}else {
				Logger.log(institudeName, username, "NOT Found the book on " + u + "!");
			}
		}
		Logger.log(institudeName, username, "NOT Found the book");
		return 3;
	}
	
}
