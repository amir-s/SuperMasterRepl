package com.concordia.comp6231;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author Ilyas Rashid - Student Id 4819608
 * @date 19/11/2014
 * Assignment # 3 - Option 1
 */


@WebService
public interface Drms{
	@WebMethod
	public String createAccount(String FirstName, String LastName, String EmailAddress, long PhoneNumber, String Username, String Password, String EducationalInstitution);
	@WebMethod
	public String reserveBook(String Username, String Password, String BookName, String AuthorName);
	@WebMethod
	String reserveInterLibrary (String Username, String Password, String BookName, String AuthorName);
	@WebMethod
	public String getNonReturners(String AdminUsername, String AdminPassword, String EducationalInstitution, int NumDays);
	@WebMethod
	public String setDuration(String Username, String BookName, int NumDays);
	
	
}
