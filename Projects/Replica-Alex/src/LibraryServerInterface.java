


public interface LibraryServerInterface{
	
	public Response createAccount(String firstName, String lastName, String emailAddress, String phoneNumber, 
						      String username, String password, String educationalInstitution);
	
	public Response reserveBook (String username, String password, String bookName, String authorName);
	
	public Response reserveInterLibrary (String username, String password, String bookName, String authorName);
	
	public Response getNonRetuners (String adminUsername, String adminPassword, 
							    String educationalInstitution, String numDays);
	
	public Response setDuration (String username, String bookName, String numDays);

}
