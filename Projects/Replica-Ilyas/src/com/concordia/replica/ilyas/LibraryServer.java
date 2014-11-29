package com.concordia.replica.ilyas;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.nio.charset.Charset;

/**
 * @author Ilyas Rashid - Student Id 4819608
 * @date 19/11/2014
 * Assignment # 3 - Option 1
 */


public class LibraryServer {
	private List<Book> m_bookList = new ArrayList<Book>();
	private HashMap<Character, List<Student>> m_allUsers = new HashMap<Character, List<Student>>();
	String m_serverName="";
	private int m_port;
	
	//Default Constructor
	public LibraryServer(){
		System.out.println("I'm up");
	}
	
	/**
	Desc : The constructor of the LibraryServer class. It will set the RMI server port to the one passed.  
	@param1 : The port of the server
	@return : Nothing, it is a constructor
	*/
	public LibraryServer(int serverPort){
		m_port = serverPort;
		this.run();
	}	

	/**
	Desc : This method creates the account of the user on the server.
	It checks if the allusers hashmap is empty. If so, this means the user does not exist, and will create it.
	IF the hashmap is not empty, it then checks if the key (the first character of the username) exists in the map. 
	If it doesn't, this means the user does not exist, and will create it.
	If the key exists, it will get the list of students and 'synchronized' this list. It will check if the username exists.
	If not it will create the account. 
	@param1 : The users First Name
	@param2 : The users Last Name
	@param3 : The users Email Address
	@param4 : The users Phone Number
	@param5 : The users Username
	@param6 : The users password
	@param6 : The Educational Institution of the user
	@return : String that contains the Success or Failure description
	*/
	public String createAccount(String FirstName, String LastName, String EmailAddress, long PhoneNumber, String Username, String Password, String EducationalInstitution){
		String _result = "";
		boolean _userExists = false;
		
		//check if username already exists
		char _firstChar = Username.toLowerCase().charAt(0);
		
		if(m_allUsers.isEmpty()){
			synchronized (m_allUsers){
				List<Student> _studentList = new ArrayList<Student>();
				Student _tempStudent = new Student(FirstName, LastName, EmailAddress, PhoneNumber, Username, Password, EducationalInstitution);
				_studentList.add(_tempStudent);
				m_allUsers.put(_firstChar, _studentList);			
			}
			_result = "Result: Success. The Account has been successfully created in " + m_serverName + ".";
			writeToServerLogFile("The Account for user " + Username + " has been successfully created in " + m_serverName + ".");
			writeToStudentLogFile(Username.toLowerCase(), "The Account has been successfully created in " + m_serverName + ":");
			writeToStudentLogFile(Username.toLowerCase(), "FirstName: " + FirstName + ", LastName: " + LastName +  ", Email Address: " + EmailAddress);
			writeToStudentLogFile(Username.toLowerCase(), "PhoneNumber: " + PhoneNumber + ", Username: " + Username + ", Password: " + Password + ", Institution: " + EducationalInstitution);
			
		}
		else if(m_allUsers.get(_firstChar) == null){
			synchronized (m_allUsers){
				List<Student> _studentList = new ArrayList<Student>();
				Student _tempStudent = new Student(FirstName, LastName, EmailAddress, PhoneNumber, Username, Password, EducationalInstitution);
				_studentList.add(_tempStudent);			
				m_allUsers.put(_firstChar, _studentList);
			}
			_result = "Result: Success. The Account has been successfully created in " + m_serverName + ".";
			writeToServerLogFile("The Account for user " + Username + " has been successfully created in " + m_serverName + ".");
			writeToStudentLogFile(Username.toLowerCase(), "FirstName: " + FirstName + ", LastName: " + LastName +  ", Email Address: " + EmailAddress);
			writeToStudentLogFile(Username.toLowerCase(), "PhoneNumber: " + PhoneNumber + ", Username: " + Username + ", Password: " + Password + ", Institution: " + EducationalInstitution);
		}
		else{
			synchronized (m_allUsers.get(_firstChar)){
				List<Student> _studentList = m_allUsers.get(_firstChar);
				
				for(Student _s : _studentList){
					
			        if(_s.m_userName != null && _s.m_userName.toLowerCase().equals(Username.toLowerCase())){
			        	_result = "Result: Failed. The username "  + Username + " already exists in " + m_serverName + ".";
			        	writeToServerLogFile("Failed to register the username "  + Username + ", it already exists.");
			        	_userExists = true;
			        }
				}		       
			
		        if (!_userExists){
					Student _tempStudent = new Student(FirstName, LastName, EmailAddress, PhoneNumber, Username, Password, EducationalInstitution);
					_studentList.add(_tempStudent);
					
					_result = "Result: Success. The Account has been successfully created in " + m_serverName + ".";
					writeToServerLogFile("The Account for user " + Username + " has been successfully created in " + m_serverName + ".");
					writeToStudentLogFile(Username.toLowerCase(), "FirstName: " + FirstName + ", LastName: " + LastName +  ", Email Address: " + EmailAddress);
					writeToStudentLogFile(Username.toLowerCase(), "PhoneNumber: " + PhoneNumber + ", Username: " + Username + ", Password: " + Password + ", Institution: " + EducationalInstitution);
				}
			}
		}
		return _result;
	}
	
	/**
	Desc : This will reserve a book for the user who is registered on the server.
	It checks if the allusers hashmap is empty. If so, this means the user does not exist, so the book cannot be reserved.
	It also checks if the key (the first character of the username) does not exist. If so, this means the user does not exist, so the book cannot be reserved.
	If the key exists, it will get the list of students and 'synchronized' this list. It will check if the username exists. If not, the book cannot be reserved.
	If the username exists, it will first check that the password matches. If so, then it will check the library for this book with the given author. 
	If the book does not exist, then the book cannot be reserved.
	If the book exists, and there are copies available, it will reserve the book for the student for 14 days.
	It will update the students resrved book list and decrease the count of copies available of the book.	
	@param1 : The users Username
	@param2 : The users password
	@param3 : The name of the Book the user wishes to reserve
	@param4 : The name of the author of the Book the user wishes to reserve
	@return : String that contains the Success or Failure description
	*/
	public String reserveBook(String Username, String Password, String BookName, String AuthorName){
		String _result = "";
		boolean _userExists = false;
		boolean _bookExists = false;
		
		//check for user exists
		char _firstChar = Username.toLowerCase().charAt(0);		
		if(m_allUsers.isEmpty() || m_allUsers.get(_firstChar) == null){
			_result = "Result: Failed. The username "  + Username + " does not exist in " + m_serverName + ".";
			writeToServerLogFile("Failed to reserve book " + BookName + " for The username " + Username + " . The username does not exist in " + m_serverName + ".");
			
		}
		else{
			synchronized (m_allUsers.get(_firstChar)){
				List<Student> _studentList = m_allUsers.get(_firstChar);
				
				for(Student _s : _studentList){
					
			        if(_s.m_userName != null && _s.m_userName.toLowerCase().equals(Username.toLowerCase())){
			        	_userExists = true;

			        	//check password
			        	if(_s.m_password.equals(Password)){
			        		//password is good, check if the book and author exists
			        		for(Book _b : m_bookList){
			        			if(_b.m_name != null && _b.m_name.toLowerCase().equals(BookName.toLowerCase()) && _b.m_author != null && _b.m_author.toLowerCase().equals(AuthorName.toLowerCase())){
						        	_bookExists = true;
						        	synchronized (m_bookList.get(m_bookList.indexOf(_b))){
						        		if (_b.m_availableCopies == 0){
						        			_result = "Result: Failed. Sorry, all copies are already reserved.";
						        			writeToServerLogFile("Failed to reserve book " + BookName + "for The username " + Username + ". No more copies available.");
						        			writeToStudentLogFile(Username.toLowerCase(), "Failed to reserve book " + BookName + "for the username " + Username + ". No more copies available.");
						        		}
						        		else{
						        			_b.m_availableCopies--;
						        			_s.m_reservedBooks.put(BookName, 14);
						        			_result = "Result: Success. The book " + _b.m_name + " has been reserved for " + _s.m_fristName + " " + _s.m_lastName;
						        			writeToServerLogFile("Successfully reserved book " + BookName + " for the username " + Username + ".");
						        			writeToStudentLogFile(Username.toLowerCase(), "Successfully reserved book " + BookName + " for the username " + Username + ". Duration is 14 days");
						        		}
						        	}
				        		}
				        	}
			        		
			        		if(!_bookExists){
			        			_result = "Result: Failed. The book " + BookName + " with author " + AuthorName + " was not found in the library.";
			        			writeToServerLogFile("Failed to reserve book " + BookName + " with author " + AuthorName + " for The username " + Username + ". Not found in the library.");
			        			writeToStudentLogFile(Username.toLowerCase(), "Failed to reserve book " + BookName + " with author " + AuthorName + " for the username " + Username + ". Not found in the library.");
			        		}
			        			
			        	}
			        	else{
			        		//incorrect password
			        		_result = "Result: Failed. The username and password does not match.";
			        		writeToServerLogFile("Failed to reserve book " + BookName + "for the username " + Username + ". The username and password does not match.");
			        		writeToStudentLogFile(Username.toLowerCase(), "Failed to reserve book " + BookName + "for the username " + Username + ". The username and password does not match.");
			        	}
			        }
				}
				if (!_userExists){					
					_result = "Result: Failed. The username "  + Username + " does not exist in " + m_serverName + ".";
					writeToServerLogFile("Failed to reserve book " + BookName + "for the username " + Username + " . The username does not exist in " + m_serverName + ".");
				}
			}	
		}		
		
		return _result;		
	}
	
	/**
	Desc : This will reserve a book locally or at an inter library for the user who is registered on the server.
	It checks if the allusers hashmap is empty. If so, this means the user does not exist, so the book cannot be reserved.
	It also checks if the key (the first character of the username) does not exist. If so, this means the user does not exist, so the book cannot be reserved.
	If the key exists, it will get the list of students and 'synchronized' this list. It will check if the username exists. If not, the book cannot be reserved.
	If the username exists, it will first check that the password matches. If so, then it will check the library for this book with the given author.
	If the book exists, and there are copies available, it will reserve the book for the student for 14 days. 
	If the book does not exist or if there are no copies left, then it will call the method reserveBookFromARemoteLibrary which will try to reserve the 
	book from an inter library.

	@param1 : The users Username
	@param2 : The users password
	@param3 : The name of the Book the user wishes to reserve
	@param4 : The name of the author of the Book the user wishes to reserve
	@return : String that contains the Success or Failure description
	*/
	public String reserveInterLibrary(String Username, String Password, String BookName, String AuthorName){
		String _result = "";
		boolean _userExists = false;
		boolean _bookExists = false;
		
		//check for user exists
		char _firstChar = Username.toLowerCase().charAt(0);		
		if(m_allUsers.isEmpty() || m_allUsers.get(_firstChar) == null){
			_result = "Result: Failed. The username "  + Username + " does not exist in " + m_serverName + ".";
			writeToServerLogFile("reserveInterLibrary: Failed to reserve book " + BookName + " for The username " + Username + " . The username does not exist in " + m_serverName + ".");
			
		}
		else{
			synchronized (m_allUsers.get(_firstChar)){
				List<Student> _studentList = m_allUsers.get(_firstChar);
				
				for(Student _s : _studentList){
					
			        if(_s.m_userName != null && _s.m_userName.toLowerCase().equals(Username.toLowerCase())){
			        	_userExists = true;
			        	_studentList.indexOf(_s);
			        	

			        	//check password
			        	if(_s.m_password.equals(Password)){
			        		//password is good, check if the book and author exists
			        		for(Book _b : m_bookList){
			        			if(_b.m_name != null && _b.m_name.toLowerCase().equals(BookName.toLowerCase()) && _b.m_author != null && _b.m_author.toLowerCase().equals(AuthorName.toLowerCase())){
						        	_bookExists = true;
						        	synchronized (m_bookList.get(m_bookList.indexOf(_b))){
						        		if (_b.m_availableCopies == 0){
   			
						        			//No copies available locally, check other servers
						        			writeToServerLogFile("reserveInterLibrary: No copies available for book " + BookName + " for the username " + Username + ". Checking other servers");
						        			_result = reserveBookFromARemoteLibrary(Username, BookName, AuthorName);
						        		}
						        		else{
						        			_b.m_availableCopies--;
						        			_s.m_reservedBooks.put(BookName, 14);
						        			_result = "Result: Success. The book " + _b.m_name + " has been found at "+ m_serverName + " and has been reserved for " + _s.m_fristName + " " + _s.m_lastName;
						        			writeToServerLogFile("reserveInterLibrary: Successfully reserved book " + BookName + " locally for the username " + Username + ".");
						        			writeToStudentLogFile(Username.toLowerCase(), "reserveInterLibrary: Successfully reserved book " + BookName + " locally at " 
						        			+ m_serverName + " for the username " + Username + ". Duration is 14 days");
						        		}
						        	}
				        		}
				        	}
			        		
			        		if(!_bookExists){
			        			//book not available locally, check other servers
			        			writeToServerLogFile("reserveInterLibrary: book " + BookName + " not available at local library for user request " + Username + ". Checking other servers");
			        			_result = reserveBookFromARemoteLibrary(Username, BookName, AuthorName);			        			
			        		}
			        			
			        	}
			        	else{
			        		//incorrect password
			        		_result = "Result: Failed. The username and password does not match.";
			        		writeToServerLogFile("Failed to reserve book " + BookName + "for the username " + Username + ". The username and password does not match.");
			        		writeToStudentLogFile(Username.toLowerCase(), "reserveInterLibrary: Failed to reserve book " + BookName + "for the username " + Username + ". The username and password does not match.");
			        	}
			        }
				}
				if (!_userExists){					
					_result = "Result: Failed. The username "  + Username + " does not exist in " + m_serverName + ".";
					writeToServerLogFile("reserveInterLibrary: Failed to reserve book " + BookName + "for the username " + Username + " . The username does not exist in " + m_serverName + ".");
				}
			}	
		}		
		
		return _result;		
	}
	
	/**
	Desc : This method will try to reserve the book from an inter library. It will try to reserve the book from the first inter library. If the fist inter 
	library is able to reserve the book, it will try to update the students record. If the student has already reserved a copy of this book or if there is a failure 
	updating the student record,  then the first inter library will be called to unreserve the book. The method will complete with the Failed result, and the
	second inter library will not be tried. However, if the students record is updated successfuly, then the method will complete with the Success result, and
	the second inter library will not be tried.
	However, if the if the fist inter library is not able to reserve the book, then the second inter library will be tired. If the second inter 
	library is able to reserve the book, it will try to update the students record. If the student has already reserved a copy of this book or there is a failure 
	updating the student record,  then the method will complete with the Failed result. However, if the students record is updated successfuly, then the 
	method will complete with the Success result. However, if the second inter library is not able to reserve the book, the method return with a failed result, and
	the user will be notified that the book cannot be reserved in any libraries.
	If the request is coming to the Concordia library, the first inter library that it will try is McGill. The second inter library that it will try is polytechnique.
	If the request is coming to the McGill library, the first inter library that it will try is Concordia. The second inter library that it will try is polytechnique.
	If the request is coming to the polytechnique library, the first inter library that it will try is Concordia. The second inter library that it will try is McGill.
	@param1 : The users Username
	@param2 : The name of the Book the user wishes to reserve
	@param3 : The name of the author of the Book the user wishes to reserve
	@return : String that contains the Success or Failure description
	*/	
	public String reserveBookFromARemoteLibrary(String Username, String BookName, String AuthorName){
		String _result="";
		
		if (m_serverName.equals("Concordia")){

			//Check McGill first
			String _mcgillResult = reserveBookFromARemoteLibraryViaUDP(BookName, AuthorName, 6790);
			if (_mcgillResult.contains("Result: Success.")){
				
				//update student record
				String _updateRecordResult = updateStudentRecord(Username, BookName, AuthorName);
				
				//check if student already has the book reserved 
				if (_updateRecordResult.contains("Result: Failed.")){
										
					unreserveBookFromARemoteLibraryViaUDP(BookName, AuthorName, 6790);
					
					if (_updateRecordResult.contains("already reserved")){
						_result="Result: Failed. The user " + Username + " has already reserved the book " + BookName;
						
					}
					else{
						//Another failure, such as the user does not exist anymore 
						_result = _updateRecordResult;						
					}
				
				}
				else if (_updateRecordResult.contains("Result: Success.")){
					_result = "Result: Success. Able to reserve book from McGill Library";
					writeToServerLogFile("reserveInterLibrary: Successfully reserved book " + BookName + " from McGill library for the username " + Username + ".");
        			writeToStudentLogFile(Username.toLowerCase(), "reserveInterLibrary: " + m_serverName + " Successfully reserved book " + BookName + " from McGill library. Duration is 14 days");
				}
			}
			else if(_mcgillResult.contains("Result: Failed.")){
				//Check polytechnique
				String _polytechniqueResult = reserveBookFromARemoteLibraryViaUDP(BookName, AuthorName, 6791);
				if (_polytechniqueResult.contains("Result: Success.")){
					
					//update student record
					String _updateRecordResult = updateStudentRecord(Username, BookName, AuthorName);
					
					//check if student already has the book reserved 
					if (_updateRecordResult.contains("Result: Failed.")){
						
						unreserveBookFromARemoteLibraryViaUDP(BookName, AuthorName, 6791);
						
						if (_updateRecordResult.contains("already reserved")){
							_result="Result: Failed. The user " + Username + " has already reserved the book " + BookName;
							
						}
						else{
							//Another failure, such as the user does not exist anymore 
							_result = _updateRecordResult;						
						}
					}
					else if (_updateRecordResult.contains("Result: Success.")){
						_result = "Result: Success. Able to reserve book from polytechnique Library";
						writeToServerLogFile("reserveInterLibrary: Successfully reserved book " + BookName + " from polytechnique library for the username " + Username + ".");
	        			writeToStudentLogFile(Username.toLowerCase(), "reserveInterLibrary: " + m_serverName + " Successfully reserved book " + BookName + " from polytechnique library. Duration is 14 days");
					}
				}
				else if (_polytechniqueResult.contains("Result: Failed.")){
					_result = "Result: Failed. Not able to Reserve book " + BookName +" from local or other inter libraries.";					
				}
			}
		}
		else if (m_serverName.equals("McGill")){
			//Check Concordia first
			String _concordiaResult = reserveBookFromARemoteLibraryViaUDP(BookName, AuthorName, 6789);
			if (_concordiaResult.contains("Result: Success.")){
				
				//update student record
				String _updateRecordResult = updateStudentRecord(Username, BookName, AuthorName);
				
				//check if student already has the book reserved 
				if (_updateRecordResult.contains("Result: Failed.")){
										
					unreserveBookFromARemoteLibraryViaUDP(BookName, AuthorName, 6789);
					
					if (_updateRecordResult.contains("already reserved")){
						_result="Result: Failed. The user " + Username + " has already reserved the book " + BookName;
						
					}
					else{
						//Another failure, such as the user does not exist anymore 
						_result = _updateRecordResult;						
					}
				
				}
				else if (_updateRecordResult.contains("Result: Success.")){
					_result = "Result: Success. Able to reserve book from Concordia Library";
					writeToServerLogFile("reserveInterLibrary: Successfully reserved book " + BookName + " from Concordia library for the username " + Username + ".");
        			writeToStudentLogFile(Username.toLowerCase(), "reserveInterLibrary: " + m_serverName + " Successfully reserved book " + BookName + " from Concordia library. Duration is 14 days");
				}
			}
			else if(_concordiaResult.contains("Result: Failed.")){
				//Check polytechnique
				String _polytechniqueResult = reserveBookFromARemoteLibraryViaUDP(BookName, AuthorName, 6791);
				if (_polytechniqueResult.contains("Result: Success.")){
					
					//update student record
					String _updateRecordResult = updateStudentRecord(Username, BookName, AuthorName);
					
					//check if student already has the book reserved 
					if (_updateRecordResult.contains("Result: Failed.")){
						
						unreserveBookFromARemoteLibraryViaUDP(BookName, AuthorName, 6791);
						
						if (_updateRecordResult.contains("already reserved")){
							_result="Result: Failed. The user " + Username + " has already reserved the book " + BookName;
							
						}
						else{
							//Another failure, such as the user does not exist anymore 
							_result = _updateRecordResult;						
						}
					}
					else if (_updateRecordResult.contains("Result: Success.")){
						_result = "Result: Success. Able to reserve book from polytechnique Library";
						writeToServerLogFile("reserveInterLibrary: Successfully reserved book " + BookName + " from polytechnique library for the username " + Username + ".");
	        			writeToStudentLogFile(Username.toLowerCase(), "reserveInterLibrary: " + m_serverName + " Successfully reserved book " + BookName + " from polytechnique library. Duration is 14 days");
					}
				}
				else if (_polytechniqueResult.contains("Result: Failed.")){
					_result = "Result: Failed. Not able to Reserve book " + BookName +" from local or other inter libraries.";					
				}
			}
		}
		else if (m_serverName.equals("Polytechnique")){
			//Check Concordia first
			String _concordiaResult = reserveBookFromARemoteLibraryViaUDP(BookName, AuthorName, 6789);
			if (_concordiaResult.contains("Result: Success.")){
				
				//update student record
				String _updateRecordResult = updateStudentRecord(Username, BookName, AuthorName);
				
				//check if student already has the book reserved 
				if (_updateRecordResult.contains("Result: Failed.")){
										
					unreserveBookFromARemoteLibraryViaUDP(BookName, AuthorName, 6789);
					
					if (_updateRecordResult.contains("already reserved")){
						_result="Result: Failed. The user " + Username + " has already reserved the book " + BookName;
						
					}
					else{
						//Another failure, such as the user does not exist anymore 
						_result = _updateRecordResult;						
					}
				
				}
				else if (_updateRecordResult.contains("Result: Success.")){
					_result = "Result: Success. Able to reserve book from Concordia Library";
					writeToServerLogFile("reserveInterLibrary: Successfully reserved book " + BookName + " from Concordia library for the username " + Username + ".");
        			writeToStudentLogFile(Username.toLowerCase(), "reserveInterLibrary: " + m_serverName + " Successfully reserved book " + BookName + " from Concordia library. Duration is 14 days");
				}
			}
			else if(_concordiaResult.contains("Result: Failed.")){
				//Check McGill
				String _mcgillResult = reserveBookFromARemoteLibraryViaUDP(BookName, AuthorName, 6790);
				if (_mcgillResult.contains("Result: Success.")){
					
					//update student record
					String _updateRecordResult = updateStudentRecord(Username, BookName, AuthorName);
					
					//check if student already has the book reserved 
					if (_updateRecordResult.contains("Result: Failed.")){
						
						unreserveBookFromARemoteLibraryViaUDP(BookName, AuthorName, 6790);
						
						if (_updateRecordResult.contains("already reserved")){
							_result="Result: Failed. The user " + Username + " has already reserved the book " + BookName;
							
						}
						else{
							//Another failure, such as the user does not exist anymore 
							_result = _updateRecordResult;						
						}
					}
					else if (_updateRecordResult.contains("Result: Success.")){
						_result = "Result: Success. Able to reserve book from McGill Library";
						writeToServerLogFile("reserveInterLibrary: Successfully reserved book " + BookName + " from McGill library for the username " + Username + ".");
	        			writeToStudentLogFile(Username.toLowerCase(), "reserveInterLibrary: " + m_serverName + " Successfully reserved book " + BookName + " from McGill library. Duration is 14 days");
					}
				}
				else if (_mcgillResult.contains("Result: Failed.")){
					_result = "Result: Failed. Not able to Reserve book " + BookName +" from local or other inter libraries.";					
				}
			}
		}
		
		return _result;
	}
	
	/**
	Desc : This method will try to update the students record in the local library with the book name and author name of the book that was reserved from the inter
	library.
	It will first check if student exists in the local server. If the student does not exist, then the method will return with a Failed result.
	If the student exists, it will then check to see if the student has already reserved a copy of the book. If so, the method will return with a Failed result. 
	If the student has not yet reserved this book, then the method will update the students record and return with a Success result.
	@param1 : The users Username
	@param2 : The name of the Book the user wishes to reserve
	@param3 : The name of the author of the Book the user wishes to reserve
	@return : String that contains the Success or Failure description
	*/
	public String updateStudentRecord(String Username, String BookName, String AuthorName){
		String _result = "";
		boolean _userExists = false;
		
		//check for user exists
		char _firstChar = Username.toLowerCase().charAt(0);		
		if(m_allUsers.isEmpty() || m_allUsers.get(_firstChar) == null){
			_result = "Result: Failed. The username "  + Username + " does not exist in " + m_serverName + ".";
			writeToServerLogFile("updateStudentRecord: Failed to update Student Record for book " + BookName + " for The username " + Username + " . The username does not exist in " + m_serverName + ".");
			
		}
		else{
			synchronized (m_allUsers.get(_firstChar)){
				List<Student> _studentList = m_allUsers.get(_firstChar);
				
				for(Student _s : _studentList){
					
			        if(_s.m_userName != null && _s.m_userName.toLowerCase().equals(Username.toLowerCase())){
			        	_userExists = true;
			        	
			        	if (_s.m_reservedBooks.containsKey(BookName)){
			        		writeToServerLogFile("updateStudentRecord: Failed to reserve book " + BookName + " for the username " + Username + ". It is already reserved.");
		        			writeToStudentLogFile(Username.toLowerCase(), "updateStudentRecord: Book " + BookName + " is already reserved for the username " + Username + ". Duration is 14 days");
		        			_result = "Result: Failed. The book " + BookName + " has been already reserved for user " + Username;
			        		
			        	}
			        	else{
			        		_s.m_reservedBooks.put(BookName, 14);
			        		
			        		writeToServerLogFile("updateStudentRecord: Successfully reserved book " + BookName + " for the username " + Username + ".");
		        			writeToStudentLogFile(Username.toLowerCase(), "updateStudentRecord: Successfully reserved book " + BookName + " for the username " + Username + ". Duration is 14 days");
		        			_result = "Result: Success. The book " + BookName + " has been reserved for " + Username;
			        	}
			        }
				}
				
				if (!_userExists){					
					_result = "Result: Failed. The username "  + Username + " does not exist in " + m_serverName + ".";
					writeToServerLogFile("updateStudentRecord: Failed to update Student Record for book " + BookName + " for The username " + Username + " . The username does not exist in " + m_serverName + ".");
				}
			}	
		}
		
		return _result;
	}
	
	/**
	Desc : This will return a list of all the users who have not returned the a book with "number of days" past their due date. 
	It will fist get the list of non returners on the local server. Then, via a UDP request, it will ask the other two servers
	for their list on returners.
	@param1 : The Admin users Username
	@param2 : The Admin users password
	@param3 : The Education institution where the admin would like to process the request
	@param4 : The Number of days past their loan date
	@return : String that contains the list of non returners of all the servers
	*/
	public String getNonReturners(String AdminUsername, String AdminPassword, String EducationalInstitution, int NumDays){
		String _result="";
		String _eol = System.getProperty("line.separator"); 
		_result = listNonReturnersFromCurrentServerInstance() + _eol + "........................."+ _eol;
		
		if (m_serverName.equals("Concordia")){
			//McGill
			_result = _result + listNonReturnersFromAnotherServerInstanceViaUDP(6790) + _eol + "........................."+ _eol;
			//polytechnique
			_result = _result + listNonReturnersFromAnotherServerInstanceViaUDP(6791) + _eol + "........................."+ _eol;
		}
		else if (m_serverName.equals("McGill")){
			//Concordia
			_result = _result + listNonReturnersFromAnotherServerInstanceViaUDP(6789) + _eol + "........................."+ _eol;
			//polytechnique
			_result = _result + listNonReturnersFromAnotherServerInstanceViaUDP(6791) + _eol + "........................."+ _eol;
		}
		else if (m_serverName.equals("Polytechnique")){
			//Concordia
			_result = _result + listNonReturnersFromAnotherServerInstanceViaUDP(6789) + _eol + "........................."+ _eol;
			//McGill
			_result = _result + listNonReturnersFromAnotherServerInstanceViaUDP(6790) + _eol + "........................."+ _eol;
		}
		
		writeToServerLogFile("Admin operation Get Non Returners called on " + m_serverName + " with Number of Days" + NumDays + ". The result is: \n" + _result);
		writeToAdminLogFile("Admin operation Get Non Returners called on " + m_serverName + " with Number of Days" + NumDays + ". The result is: \n" + _result);

		return _result;
	}
	
	/**
	Desc : This method will set a fine (equal to a number of days) if the user has reserved a given book.
	It fist checks if the username exists in the system. If so, it will then check if the user has reserved the book.
	If so the user as reserved the book, then it will update the students "fines accumulated" field.  
	@param1 : The username of the student
	@param2 : The name of the book
	@param3 : The Number of days past their loan date
	@return : String that contains the success or failure with the description of the result
	*/
	public String setDuration(String Username, String BookName, int NumDays){
		String _result="";
		boolean _userExists = false;
		boolean _userHasReservedBook = false;
		
		//check for user exists
		char _firstChar = Username.toLowerCase().charAt(0);		
		if(m_allUsers.isEmpty() || m_allUsers.get(_firstChar) == null){
			_result = "Result: Failed. The username "  + Username + " does not exist in " + m_serverName + ".";
			writeToServerLogFile("Admin operation set Duration called on " + m_serverName + ". Result: Failed. The username "  + Username + " does not exist in " + m_serverName + ".");
			writeToAdminLogFile("Admin operation set Duration called on " + m_serverName + ". Result: Failed. The username "  + Username + " does not exist in " + m_serverName + ".");
			
			
			
		}
		else{
			synchronized (m_allUsers.get(_firstChar)){
				List<Student> _studentList = m_allUsers.get(_firstChar);
				
				for(Student _s : _studentList){
					
			        if(_s.m_userName != null && _s.m_userName.toLowerCase().equals(Username.toLowerCase())){
			        	_userExists = true;

			        	//check if user has reserved book
			        	for (String _reservedBook : _s.m_reservedBooks.keySet()){
			        		if (_reservedBook.toLowerCase().equals(BookName.toLowerCase())){
			        			_userHasReservedBook = true;
			        			_s.m_finesAccumulated = _s.m_finesAccumulated + NumDays;
			        			_result = "Result: Success. The username "  + Username + " was fined for book " + BookName + ". Fines Accumulated for this user is now: $" + _s.m_finesAccumulated;
			        			writeToServerLogFile("Admin operation set Duration called on " + m_serverName + ". Result: Success. The username "  + Username + " was fined for book " + BookName + ". Fines Accumulated for this user is now: $" + _s.m_finesAccumulated);
			        			writeToStudentLogFile(Username.toLowerCase(), "The username "  + Username + " was fined for book " + BookName + ". Fines Accumulated for this user is now: $" + _s.m_finesAccumulated);
			        			writeToAdminLogFile("Admin operation set Duration called on " + m_serverName + ". Result: Success. The username "  + Username + " was fined for book " + BookName + ". Fines Accumulated for this user is now: $" + _s.m_finesAccumulated);
			        			
			        		}
			        	}
			        	
			        	if (!_userHasReservedBook){
			        		_result = "Result: Failed. The user "  + Username + " has not reserved the book " + BookName;
			        		writeToServerLogFile("Admin operation set Duration called on " + m_serverName + ". Result: Failed. The user "  + Username + " has not reserved the book " + BookName);
			        		writeToAdminLogFile("Admin operation set Duration called on " + m_serverName + ". Result: Failed. The user "  + Username + " has not reserved the book " + BookName);
			        	}
			        }
				}			        	
			        		
				if (!_userExists){					
					_result = "Result: Failed. The username "  + Username + " does not exist in " + m_serverName + ".";
					writeToServerLogFile("Admin operation set Duration called on " + m_serverName + ". Result: Failed. The username "  + Username + " does not exist in " + m_serverName + ".");
					writeToAdminLogFile("Admin operation set Duration called on " + m_serverName + ". Result: Failed. The username "  + Username + " does not exist in " + m_serverName + ".");
				}
			}//end synchronized block
		}
		
		return _result;
	}
	
	/**
	Desc : This method will get the server to listen for UDP messages on a port. 
	When it receives a "ListOfNonReturners" request, it will reply with the list of non returners from its server.
	When it receives a "ReserveBook" request, it will call the reserveBookForInterLibrary method and send back its result.
	When it receives a "UnreserveBook" request, it will call the unreserveBookForInterLibrary method and send back its result.
	
	Concordia server will listen on port 6789, McGill will listen on port 6790, and the Polytechnique server will listen 6791   
	@return : Nothing
	*/
	public void startUDPServer(){
		DatagramSocket _aSocket = null;

		try{
			int _port = 6788;
			if (m_serverName.equals("Concordia")){
				_port = 6789;
			}
			else if (m_serverName.equals("McGill")){
				_port = 6790;
			}
			if (m_serverName.equals("Polytechnique")){
				_port = 6791;
			}
			_aSocket = new DatagramSocket(_port);
			System.out.println(m_serverName + " UDP Library Server binded to port: " + _port);
			byte [] _buffer = new byte[1000];
			
			while(true){
			
			DatagramPacket _request = new DatagramPacket(_buffer, _buffer.length);
			
			_aSocket.receive(_request);
				
			String _result ="";
			String _udpRequest = "";
						
			
			Charset _charset = Charset.forName("UTF-8");
			int _i;
			for (_i = 0; _i < _buffer.length && _buffer[_i] != 0; _i++) { }
			_udpRequest = new String(_buffer, 0, _i, _charset);
				
			if( _udpRequest.contains("ListOfNonReturners")){
				_result = listNonReturnersFromCurrentServerInstance();				
			}
			else if( _udpRequest.contains("ReserveBook")){
				_result = reserveBookForInterLibrary(_udpRequest);				
			}
			else if( _udpRequest.contains("UnreserveBook")){
				_result = unreserveBookForInterLibrary(_udpRequest);				
			}
			
			DatagramPacket _reply = new DatagramPacket(_result.getBytes(), _result.getBytes().length, _request.getAddress(), _request.getPort());
			_aSocket.send(_reply);
			}
		} catch (SocketException e){ System.out.println("Socket: " + e.getMessage());
		} catch (IOException e){ System.out.println("IO: " + e.getMessage());
		}finally {if(_aSocket != null) _aSocket.close();}
		
	}
	
	/**
	Desc : This method will get the list of non returners from the local server. Then it will create a UDP connection and
	get the list of non returners from the other two servers.   
	@return : list of non returners from the local server
	*/
	public String listNonReturnersFromCurrentServerInstance(){
		String _result="Educational Institution " + m_serverName + ": ";
		
		for (Character _c : m_allUsers.keySet()) {
		    synchronized (m_allUsers.get(_c)){
		    	List<Student> _studentList = m_allUsers.get(_c);
		    	
		    	
		    	for(Student _s : _studentList){
		    		boolean _reportStudent = false;
		    		for(int _duration: _s.m_reservedBooks.values()){
		    			if (_duration == 14){
		    				_reportStudent = true;
		    			}	
		    		}
		    		
		    		if(_reportStudent){
		    			_result = _result + _s.m_fristName + " " + _s.m_lastName + " " + _s.m_phoneNumber + "\n";
		    		}
			    }
		    }
		}
		return _result;
	}
	
	/**
	Desc : When the library server receives a udp request to reserve a book for another server, this method will be called.
	library.
	It will first extract the Book Name and Author Name from the udp request string.
	It will then check to see if the book is in its library. If the book is not in its library, the method will return with a Failed result.
	If the book is in its library, it will then check to see if there are any copies available. If not, the method will return with a Failed result.
	If there are copies available, then it will decrease the count by 1 and return with a Success result.
	@param1 : The String that contains the ReserveBook udp request with the book name and author
	 		  Format of udp request string: "ReserveBook:Book Name:Author Name:"
	@return : String that contains the Success or Failure description
	*/
	public String reserveBookForInterLibrary(String udpRequest){
		String _result="";
		String[] parametersInUdpRequest = udpRequest.split(":");
		boolean _bookExists = false;
		
		String BookName = parametersInUdpRequest[1];
		String AuthorName = parametersInUdpRequest[2];
		
		for(Book _b : m_bookList){
			if(_b.m_name != null && _b.m_name.toLowerCase().equals(BookName.toLowerCase()) && _b.m_author != null && _b.m_author.toLowerCase().equals(AuthorName.toLowerCase())){
	        	_bookExists = true;
	        	synchronized (m_bookList.get(m_bookList.indexOf(_b))){
	        		if (_b.m_availableCopies == 0){
	        			_result = "Result: Failed. Sorry, all copies are already reserved.";
	        			writeToServerLogFile("Failed to reserve book " + BookName + " for an Inter Library request. No more copies available.");
	        		}
	        		else{
	        			_b.m_availableCopies--;
	        			_result = "Result: Success. The book " + _b.m_name + " has been reserved for an Inter Library request.";
	        			writeToServerLogFile("Successfully reserved book " + BookName + " for an Inter Library request. Number of copies available now: " + _b.m_availableCopies);
	        		}
	        	}
    		}
    	}
		
		if(!_bookExists){
			_result = "Result: Failed. The book " + BookName + " with author " + AuthorName + " was not found in the library.";
			writeToServerLogFile("Failed to reserve book " + BookName + " with author " + AuthorName + " for an Inter Library request");
		}
		
		return _result;
	}
	
	/**
	Desc : When the library server receives a udp request to unreserve a book for another server, this method will be called.
	library.
	It will first extract the Book Name and Author Name from the udp request string.
	It will then check to see if the book is in its library. If the book is not in its library, the method will return with a Failed result.
	If the book is in its library, it will increase the count by 1 and return with a Success result.
	@param1 : The String that contains the UnreserveBook udp request with the book name and author
	 		  Format of udp request string: "UnreserveBook:Book Name:Author Name:"
	@return : String that contains the Success or Failure description
	*/
	public String unreserveBookForInterLibrary(String udpRequest){
		String _result="";
		String[] parametersInUdpRequest = udpRequest.split(":");
		boolean _bookExists = false;
		
		String BookName = parametersInUdpRequest[1];
		String AuthorName = parametersInUdpRequest[2];
		
		for(Book _b : m_bookList){
			if(_b.m_name != null && _b.m_name.toLowerCase().equals(BookName.toLowerCase()) && _b.m_author != null && _b.m_author.toLowerCase().equals(AuthorName.toLowerCase())){
	        	_bookExists = true;
	        	synchronized (m_bookList.get(m_bookList.indexOf(_b))){
	        		_b.m_availableCopies++;
        			_result = "Result: Success. The book " + _b.m_name + " has been unreserved for an Inter Library request.";
        			writeToServerLogFile("Successfully unreserved book " + BookName + " for an Inter Library request. Number of copies available now: " + _b.m_availableCopies);
	        		
	        	}
    		}
    	}
		
		if(!_bookExists){
			_result = "Result: Failed. The book " + BookName + " with author " + AuthorName + " was not found in the library.";
			writeToServerLogFile("Failed to unreserve book " + BookName + " with author " + AuthorName + " for an Inter Library request");
		}
		
		return _result;
	}
	
	/**
	Desc : This method will get send a UDP request to another server, and get the list of non returners from that server. 
	@param1 : The port number of the server that you wish to send the request to   
	@return : list of non returners from that server
	*/
	public String listNonReturnersFromAnotherServerInstanceViaUDP(int serverPort){
		String _result="";

		DatagramSocket _aSocket = null;

		try{
			_aSocket = new DatagramSocket();
			byte [] _m = "ListOfNonReturners".getBytes();
			InetAddress _aHost = InetAddress.getByName("localhost");
			
			DatagramPacket _request = new DatagramPacket(_m, _m.length, _aHost, serverPort);
			
			_aSocket.send(_request);
			
			byte[] _buffer = new byte[1000];
			DatagramPacket _reply = new DatagramPacket(_buffer, _buffer.length);
			
			_aSocket.receive(_reply);
			
			//Copy result from buffer into string, but remove all the null characters
			Charset _charset = Charset.forName("UTF-8");
			int _i;
			for (_i = 0; _i < _buffer.length && _buffer[_i] != 0; _i++) { }
				_result = new String(_buffer, 0, _i, _charset);
			
		} catch (SocketException e){ System.out.println("Socket: " + e.getMessage());
		} catch (IOException e){ System.out.println("IO: " + e.getMessage());
		}finally {if(_aSocket != null) _aSocket.close();}
		
		
		return _result;
	}
	
	
	/**
	Desc : When the library server needs to send a udp request to another library to reserve a book, this method will be called.
	library.
	It will first create the udp "ReserveBook" request that also contains the Book Name and Author Name.
	The format of the request string will be "ReserveBook:Book Name:Author Name:"
	It will send the udp request to the inter library at the specified port name.
	It will then get the result, remove any null characters and then return the result.
	@param1 : The String that contains book name
	@param2 : The String that contains the name of the author
	@param3 : The int that contains the port number of the inter library where the udp request will be sent 		  
	@return : String that contains the Success or Failure result description
	*/
	public String reserveBookFromARemoteLibraryViaUDP(String BookName, String AuthorName, int serverPort){
		String _result="";
		String _strRequest = "ReserveBook:" + BookName + ":" + AuthorName + ":";

		DatagramSocket _aSocket = null;

		try{
			_aSocket = new DatagramSocket();
			byte [] _m = _strRequest.getBytes();
			InetAddress _aHost = InetAddress.getByName("localhost");
			
			DatagramPacket _request = new DatagramPacket(_m, _m.length, _aHost, serverPort);
			
			_aSocket.send(_request);
			
			byte[] _buffer = new byte[1000];
			DatagramPacket _reply = new DatagramPacket(_buffer, _buffer.length);
			
			_aSocket.receive(_reply);
			
			//Copy result from buffer into string, but remove all the null characters
			Charset _charset = Charset.forName("UTF-8");
			int _i;
			for (_i = 0; _i < _buffer.length && _buffer[_i] != 0; _i++) { }
				_result = new String(_buffer, 0, _i, _charset);
			
		} catch (SocketException e){ System.out.println("Socket: " + e.getMessage());
		} catch (IOException e){ System.out.println("IO: " + e.getMessage());
		}finally {if(_aSocket != null) _aSocket.close();}
		
		
		return _result;
	}
	
	/**
	Desc : When the library server needs to send a udp request to another library to unreserve a book, this method will be called.
	library.
	It will first create the udp "UnreserveBook" request that also contains the Book Name and Author Name.
	The format of the request string will be "UnreserveBook:Book Name:Author Name:"
	It will send the udp request to the inter library at the specified port name.
	It will then get the result, remove any null characters and then return the result.
	@param1 : The String that contains book name
	@param2 : The String that contains the name of the author
	@param3 : The int that contains the port number of the inter library where the udp request will be sent 		  
	@return : String that contains the Success or Failure result description
	*/
	public String unreserveBookFromARemoteLibraryViaUDP(String BookName, String AuthorName, int serverPort){
		String _result="";
		String _strRequest = "UnreserveBook:" + BookName + ":" + AuthorName + ":";

		DatagramSocket _aSocket = null;

		try{
			_aSocket = new DatagramSocket();
			byte [] _m = _strRequest.getBytes();
			InetAddress _aHost = InetAddress.getByName("localhost");
			
			DatagramPacket _request = new DatagramPacket(_m, _m.length, _aHost, serverPort);
			
			_aSocket.send(_request);
			
			byte[] _buffer = new byte[1000];
			DatagramPacket _reply = new DatagramPacket(_buffer, _buffer.length);
			
			_aSocket.receive(_reply);
			
			//Copy result from buffer into string, but remove all the null characters
			Charset _charset = Charset.forName("UTF-8");
			int _i;
			for (_i = 0; _i < _buffer.length && _buffer[_i] != 0; _i++) { }
				_result = new String(_buffer, 0, _i, _charset);
			
		} catch (SocketException e){ System.out.println("Socket: " + e.getMessage());
		} catch (IOException e){ System.out.println("IO: " + e.getMessage());
		}finally {if(_aSocket != null) _aSocket.close();}
		
		
		return _result;
	}
	
	/**
	Desc : This method will write logs to the server log file. 
	@param1 : The string to write to the log file    
	@return : Nothing
	*/
	public void writeToServerLogFile(String output){
		try{
			File _file = new File(m_serverName + ".txt");
			if(!_file.exists()){
				_file.createNewFile();
			}
			
			FileWriter _fileWritter = new FileWriter(_file.getName(),true);
			BufferedWriter _bufferWritter = new BufferedWriter(_fileWritter);
			Calendar _cal = Calendar.getInstance();
			output = "\n" + _cal.getTime() + ": " + output;
			_bufferWritter.write(output);
		    _bufferWritter.close();
			
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	Desc : This method will write logs to the student log file. 
	@param1 : The username of the student
	@param1 : The string to write to the log file    
	@return : Nothing
	*/
	public void writeToStudentLogFile(String username, String output){
		try{
			File _file = new File(username + ".txt");
			if(!_file.exists()){
				_file.createNewFile();
			}
			
			FileWriter _fileWritter = new FileWriter(_file.getName(),true);
			BufferedWriter _bufferWritter = new BufferedWriter(_fileWritter);
			Calendar _cal = Calendar.getInstance();
			output = "\n" + _cal.getTime() + ": " + output;
			_bufferWritter.write(output);
		    _bufferWritter.close();
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	/**
	Desc : This method will write logs to the admin log file. 
	@param1 : The string to write to the log file    
	@return : Nothing
	*/
	public void writeToAdminLogFile(String output){
		try{
			File _file = new File("Admin.txt");
			if(!_file.exists()){
				_file.createNewFile();
			}
			
			FileWriter _fileWritter = new FileWriter(_file.getName(),true);
			BufferedWriter _bufferWritter = new BufferedWriter(_fileWritter);
			Calendar _cal = Calendar.getInstance();
			output = "\n" + _cal.getTime() + ": " + output;
			_bufferWritter.write(output);
		    _bufferWritter.close();			
		
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	
	/**
	Desc : This method will be called when the tread will start. It will initialize the server.
	It will call the export server, then it will initialize the server name, the books it has in its library,
	and will register 1 student in the server.     
	@return : Nothing
	*/
	public void run(){
		try {			
			
			if (m_port == 2020){
				m_serverName="Concordia";
				
				Book _absoluteCplusplus = new Book("Absolute C++","Walter", 5);
				Book _absoluteC = new Book("Absolute C","Walter", 5);
				m_bookList.add(_absoluteCplusplus);
				m_bookList.add(_absoluteC);
				
				
				/*Assignment #1 Demo
				 * Book _absoluteCplusplus = new Book("Cuda","Nicholas", 2);
				Book _absoluteC = new Book("Opencl","Munshi", 3);
				Book _absolutePerl = new Book("3DMath","Fletcher", 1);
				m_bookList.add(_absoluteCplusplus);
				m_bookList.add(_absoluteC);
				m_bookList.add(_absolutePerl);
				*/
				
				
				
				List<Student> _studentList = new ArrayList<Student>();
				char _firstChar = "paulka".charAt(0);
				Student _paulk = new Student("Paul", "Karia", "paul.karia@karia.com", 5141111111L, "paulka", "paulka", "Concordia");
				//Student _paulk = new Student("boromir", "king", "paul.karia@karia.com", 5141111111L, "boromirking", "password", "Concordia");
				_studentList.add(_paulk);
				m_allUsers.put(_firstChar, _studentList);
				
				
				 
			}
			else if (m_port == 2021){
				m_serverName="McGill";
				
				Book _absolutePython = new Book("Absolute Python","Walter", 5);
				Book _absolutePerl = new Book("Absolute Perl","Walter", 5);				
				m_bookList.add(_absolutePython);
				m_bookList.add(_absolutePerl);
				
				/* Assignment #1 Demo
				 * Book _absoluteCplusplus = new Book("Cuda","Nicholas", 2);
				Book _absoluteC = new Book("Opencl","Munshi", 3);
				Book _absolutePerl = new Book("3DMath","Fletcher", 1);
				m_bookList.add(_absoluteCplusplus);
				m_bookList.add(_absoluteC);
				m_bookList.add(_absolutePerl);*/
				
				
				List<Student> _studentList = new ArrayList<Student>();
				char _firstChar = "paulco".charAt(0);
				Student _paulco = new Student("Paul", "Coffee", "paul.coffee@coffee.com", 5142222222L, "paulco", "paulco", "McGill");
				_studentList.add(_paulco);
				m_allUsers.put(_firstChar, _studentList);
			}
			else if (m_port == 2022){
				m_serverName="Polytechnique";
				
				Book _absoluteScala = new Book("Absolute Scala","Walter", 5);
				//Book _absoluteScala = new Book("bones of the lost","kathy reichs", 4);
				Book _absoluteErlang = new Book("Absolute Erlang","Walter", 5);				
				m_bookList.add(_absoluteScala);
				m_bookList.add(_absoluteErlang);
				
				/* Assignment #1 Demo
				 * Book _absoluteCplusplus = new Book("Cuda","Nicholas", 2);
				Book _absoluteC = new Book("Opencl","Munshi", 3);
				Book _absolutePerl = new Book("3DMath","Fletcher", 1);
				m_bookList.add(_absoluteCplusplus);
				m_bookList.add(_absoluteC);
				m_bookList.add(_absolutePerl);*/
				
				
				List<Student> _studentList = new ArrayList<Student>();
				char _firstChar = "wayneg".charAt(0);
				Student _wayneg = new Student("Wayne", "Gretzky", "wayne.gretzky@gretzky.com", 5143333333L, "wayneg", "wayneg", "Polytechnique");
				_studentList.add(_wayneg);
				m_allUsers.put(_firstChar, _studentList);
			}
			
			System.out.println(m_serverName + " Library Server is up an running! starting UDP server...");
			Thread t = new Thread(new Runnable() {
		         public void run()
		         {
		        	 startUDPServer();
		         }
			});
			t.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class Student{
		private String m_fristName;
		private String m_lastName;
		private String _emailAddress;
		private long m_phoneNumber;
		private String m_userName;
		private String m_password;
		private HashMap<String, Integer> m_reservedBooks;
		private int m_finesAccumulated;
		private String m_educationalInstitution;
		
		/**
		Desc : Constructor of the Student class;
		@param1 : The student's First Name
		@param2 : The student's Last Name
		@param3 : The student's Email Address
		@param4 : The student's Phone Number
		@param5 : The student's Username
		@param6 : The student's password
		@param6 : The Educational Institution of the student
		@return : Nothing, this is a Constructor
		*/
		public Student(String stuFristName, String stuLastName, String stuEmailAddress, long stuPhoneNumber, String stuUserName, 
				String stuPassword, String stuEducationalInstitution){
			m_fristName = stuFristName;
			m_lastName = stuLastName;
			_emailAddress = stuEmailAddress;
			m_phoneNumber = stuPhoneNumber;
			m_userName = stuUserName;
			m_password = stuPassword;
			m_reservedBooks = new HashMap<String, Integer>();
			m_finesAccumulated = 0;
			m_educationalInstitution = stuEducationalInstitution;
		}
		
	}
	
	class Book{
		private String m_name;
		private String m_author;
		private int m_availableCopies;
		
		/**
		Desc : Constructor of the Book class;
		@param1 : The Name of the book
		@param2 : The Author of the book
		@param3 : The number of available copies
		@return : Nothing, this is a Constructor
		*/
		public Book(String bookName, String bookAuthor, int availCopies){
			m_name = bookName;
			m_author = bookAuthor;
			m_availableCopies = availCopies;
			
		}
		
	}
}
