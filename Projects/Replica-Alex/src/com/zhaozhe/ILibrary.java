package com.zhaozhe;
public interface ILibrary{
	// 0 : OK
	// 1 : Server failiure 
	// 5 : Username exists 
	public String registerUser(String instName, String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password);
	// output: 2@


	// 0 : OK
	// 1 : Server faillure
	// 2 : Autentciation failed
	// 3 : Book does not exists or no copies left
	public String reserveBook(String instName, String username, String password, String bookName, String authorName);
	// output: 2@


	// 0 : OK
	// 1 : Server faillure
	// 2 : Autentciation failed
	// 4 : Username or Book doesnt exists, or user doesnt have this book
	public String setDuration(String instName, String adminUsername, String adminPassword, String username, String bookName, String authorName, int days);
	// output: 2@


	// 0 : OK
	// 1 : Server faillure
	// 2 : Autentciation failed
	// 3 : Book does not exists or no copies left
	public String reserveInterLibrary(String instName, String username, String password, String bookName, String authorName);
	// output: 2@


	// 0 : OK
	// 1 : Server faillure
	// 2 : Autentciation failed
	public String getNonRetuners(String instName, String adminUsername, String adminPassword, int days);
	// output: 2@InsName,FirstName,LastName,PhoneNumber@InsName,FirstName,LastName,PhoneNumber@InsName,FirstName,LastName,PhoneNumber
}
