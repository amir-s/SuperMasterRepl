
// Datastructure for keeping information
// of each student, the field names speak for themselfs.
public class Student {
	public String username;
	public String password;
	public String firstName;
	public String lastName;
	public String phoneNumber;
	public String emailAddress;
	// constructor
	public Student(String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}
	// check if the provided user name and password belongs to this user
	public boolean auth(String username, String password) {
		return this.username.equals(username) && this.password.equals(password);
	}
}
