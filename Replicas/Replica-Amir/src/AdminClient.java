import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Scanner;

import client.LibraryServerMain;
import client.LibraryServerMainService;

public class AdminClient {
	// config holder
	private Config cnf = new Config();
	// orb
	
	public AdminClient() {
	
	}
	
	// Returns the remote object based on the given name and given port.
	public LibraryServerMain getLibrary() {
		// look by the IOR that has been written into the config file.
		// get the remote object and return it to the caller
		LibraryServerMainService service = new LibraryServerMainService();
		LibraryServerMain srv = service.getLibraryServerMainPort();
		return srv;
	}
	// Sets the duration of the user with user name that borrowed the book bookName:authorName += days
	// Based on the successfulness, returns true or false
	public boolean setDuration(String educationalInstitute, String adminUsername, String adminPassword, String username, String bookName, String authorName, int days) {
		Logger.log("Admin", adminUsername, "Setting duration for " + username + " @ " + bookName + ":" + authorName + "+=" + days);
		try {
			// passing the arguments to the remote object
			getLibrary().setDuration(educationalInstitute, adminUsername, adminPassword, username, bookName, authorName, days);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("There was a problem with " + educationalInstitute + " " + e.getMessage());
			return false;
		}
	}
	
	// Gets the non-returners list of all schools that their days is less than days
	public String getNonRetuners(String educationalInstitute, String adminUsername, String adminPassword, int days) {
		Logger.log("Admin", adminUsername, "Getting non returners list for " + days + " days");
		try {
			return getLibrary().getNonRetuners(educationalInstitute, adminUsername, adminPassword, days);	
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("There was a problem with " + educationalInstitute + " " + e.getMessage());
			return "";
		}
	}
	
	// Main function which creates the Terminal User Interface
	public static void main(String[] args) throws MalformedURLException, FileNotFoundException {
		AdminClient c = new AdminClient();
		//System.out.println(c.getNonRetuners("polymtl", "admin", "admin", 15));
		
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			// Print the menu
			System.out.println("** Menu:");
			System.out.println("   1. Get Non-returners.");
			System.out.println("   2. Set Duration.");
			
			// detect end of file
			if (!sc.hasNext()) break;
			
			
			int opt = sc.nextInt();
			
			// Get non-returners
			if (opt == 1) {
				
				// input needed values for doing a get non-returners call
				System.out.println("Enter days:");
				int days = sc.nextInt();
				System.out.println("Which institute?");
				String ins = sc.next();
				
				// using admin account to initiate the request
				System.out.println(c.getNonRetuners(ins, "admin", "admin", days));
				
			// Set Duration.
			}else if (opt == 2) {
				
				// input needed values for doing a set duration call
				String[] properties = new String[5];
				String[] labels = {"Username","Institute","difference", "Book name", "Book author"};
				for (int i=0;i<properties.length;i++) {
					System.out.println("Enter " + labels[i]);
					properties[i] = sc.next();
				}
				// set duration for the specific user
				// from admin account and check if it
				// is successful or not and print the
				// appropriate message.
				if (c.setDuration(properties[1], "admin", "admin", properties[0], properties[3], properties[4], Integer.parseInt(properties[2]))) {
					System.out.println("Done");
				}else {
					System.out.println("There was a problem");
				}
				
			// if the entered option on the menu was not a valid option
			}else {
				System.out.println("Enter a valid option!");
			}
		}
		
	}
}
