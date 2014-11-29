public interface ILibrary{
	// 0 : OK
	// 1 : Server failure 
	// 5 : Username exists 
	public String registerUser(String instName, String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password);
	// output: 2@


	// 0 : OK
	// 1 : Server failure
	// 2 : Authentication failed
	// 3 : Book does not exists or no copies left, or the user already has the book
	public String reserveBook(String instName, String username, String password, String bookName, String authorName);
	// output: 2@


	// 0 : OK
	// 1 : Server failure
	// 2 : Authentication failed
	// 4 : Username or Book doesn't exists, or user doesn't have this book
	public String setDuration(String instName, String adminUsername, String adminPassword, String username, String bookName, String authorName, int days);
	// output: 2@


	// 0 : OK
	// 1 : Server failure
	// 2 : Authentication failed
	// 3 : Book does not exists or no copies left
	public String reserveInterLibrary(String instName, String username, String password, String bookName, String authorName);
	// output: 2@


	// 0 : OK
	// 1 : Server failure
	// 2 : Authentication failed
	public String getNonRetuners(String instName, String adminUsername, String adminPassword, int days);
	// output: 2@InsName,FirstName,LastName,PhoneNumber@InsName,FirstName,LastName,PhoneNumber@InsName,FirstName,LastName,PhoneNumber
}
