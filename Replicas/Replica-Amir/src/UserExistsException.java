
public class UserExistsException extends Exception {
	private String username;
	public UserExistsException(String username) {
		this.username = username;
	}
	public String what() {
		return "The username " + this.username + " is already exists!";
	}
}
