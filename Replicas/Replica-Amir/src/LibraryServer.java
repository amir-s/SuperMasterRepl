import javax.jws.*;

@WebService
public interface LibraryServer{
	@WebMethod
	public boolean registerUser(String InstName, String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password);
	@WebMethod
	public boolean reserveBook(String InstName, String username, String password, String bookName, String authorName);
	@WebMethod
	public void setDuration(String InstName, String adminUsername, String adminPassword, String username, String bookName, String authorName, int days);
	@WebMethod
	public String getNonRetuners(String InstName, String adminUsername, String adminPassword, int days);
	@WebMethod
	public boolean reserveInterLibrary(String InstName, String username, String password, String bookName, String authorName);
}
