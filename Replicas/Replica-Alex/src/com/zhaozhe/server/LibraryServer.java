package com.zhaozhe.server;

import java.io.PrintWriter;
import java.util.List;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import com.zhaozhe.entity.Account;
import com.zhaozhe.entity.Book;
import com.zhaozhe.entity.Reservation;

import corba.CORBAResponse;
import corba.LibraryServerPOA;


public class LibraryServer extends LibraryServerPOA{
	
	/*
	 * Mark - Driver
	 */
	
	public static void main(String[] args) {
		try {
			
			ORB orb = ORB.init(args, null);
			POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			

			ServerInfoManager serverInfoManager = ServerInfoManager.defaultManager();
			for (ServerInfo serverInfo: serverInfoManager.getServers()){

				LibraryServer libraryServer = new LibraryServer(serverInfo);
				
				byte[] id = rootPOA.activate_object(libraryServer);
				org.omg.CORBA.Object ref = rootPOA.id_to_reference(id);
				String ior = orb.object_to_string(ref);
				
				PrintWriter fw = new PrintWriter("corba/ior_"+ serverInfo.getIdentity() + ".txt");
				fw.println(ior);
				fw.close();
			}
			
			rootPOA.the_POAManager().activate();
			orb.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Mark - Constructors
	 */

	public LibraryServer(ServerInfo serverInfo){
		this.serverInfo = serverInfo;

		database = new Database();
		
		loger = new Logger();
		loger.setLibraryServer(this);

		buildFakeData();
		initInternalComunication();
	}

	/*
	 * Mark - Components - Properties
	 */
	 
	private ServerInfo serverInfo;
	
	private Database database;
	
	private Logger loger;
	
	/*
	 * Mark - Components - Getters & Setters
	 */
	 
	public ServerInfo getServerInfo() {
		return serverInfo;
	}


	/*
	 * Mark - Services - Locks
	 */
	
	private static Object accountLock = new Object();
	private static Object bookLock = new Object();
	private static Object interCommunicationLock = new Object();
	
	/*
	 * Mark - Services - Student - Methods
	 */
	
	@Override
	public CORBAResponse createAccount(String firstName, String lastName, String emailAddress, String phoneNumber, 
						      String username, String password, String educationalInstitution) {
		
		// Assuming that every parameter in this function is not null
		
		Response response = new Response();
		response.setErrorCode(ServerError.SUCCESS);
		
		synchronized (accountLock) {

			// To check if the account is exist
			Account account = database.getAccount(username);
			if (account != null){
				response.setErrorCode(ServerError.ACCOUNT_EXISTED);
				System.out.println(response.getErrorCode());
				return response.toCorba();
			}
			
			// To execute
			account = new Account(username, password, firstName, lastName, emailAddress, phoneNumber);
			database.addAccount(account);
			
			loger.record(new Log(account, "Account Created"));
		}

		System.out.println(response.getErrorCode());
		return response.toCorba();
	}

	
	private static final int _DEFAULT_RESERVATION_DURATION = 14;
	
	@Override
	public CORBAResponse reserveBook(String username, String password, String bookName, String authorName) {

		// Assuming that every parameter in this function is not null

		Response response = new Response();
		response.setErrorCode(ServerError.SUCCESS);

		// To check if the account is exist
		Account account = database.getAccount(username);
		if (account == null) {
			response.setErrorCode(ServerError.ACCOUNT_NOT_EXISTED);
			System.out.println(response.getErrorCode());
			return response.toCorba();
		}

		// To check if the password is correct
		if (!account.getPassword().equals(password)) {
			response.setErrorCode(ServerError.ACCOUNT_WRONG_PASSWORD);
			System.out.println(response.getErrorCode());
			return response.toCorba();
		}

		// To check if the book is exist
		Book book = database.getBook(bookName);
		if (book == null){
			response.setErrorCode(ServerError.BOOK_NOT_EXISTED);
			System.out.println(response.getErrorCode());
			return response.toCorba();
		}
		
		// Try to reserve a book locally
		boolean localBookReserved = false;
		
		synchronized (bookLock) {
			if (book.getQuantity() > 0) {
				
				Reservation reservation = new Reservation(book, _DEFAULT_RESERVATION_DURATION);
				account.addReservation(reservation);
				
				book.setQuantity(book.getQuantity() - 1);
				
				loger.record(new Log(account, "Did Reservate a Book:" + book.getName()));

				localBookReserved = true;
				
			}
		}

		// To check if the book is not enough
		if (localBookReserved == false) {
			response.setErrorCode(ServerError.BOOK_NOT_ENOUGH);
			System.out.println(response.getErrorCode());
			return response.toCorba();
		}
		
		return response.toCorba();
	}
	
	public CORBAResponse reserveInterLibrary (String username, String password, String bookName, String authorName){

		// Assuming that every parameter in this function is not null

		final Response response = new Response();
		response.setErrorCode(ServerError.SUCCESS);
		
		// To check if the account is exist
		Account account = database.getAccount(username);
		if (account == null) {
			response.setErrorCode(ServerError.ACCOUNT_NOT_EXISTED);
			System.out.println(response.getErrorCode());
			return response.toCorba();
		}
		
		// To check if the password is correct
		if (!account.getPassword().equals(password)) {
			response.setErrorCode(ServerError.ACCOUNT_WRONG_PASSWORD);
			System.out.println(response.getErrorCode());
			return response.toCorba();
		}

//		boolean localBookExisted = true;
		boolean localBookReserved = false;
		
		// To check if the book is exist
		Book book = database.getBook(bookName);
		if (book == null){
			book = new Book(bookName, authorName, 1);
		} else {
			// Try to resever book locally
			synchronized (bookLock) {
				if (book.getQuantity() > 0) {
					
					Reservation reservation = new Reservation(book, _DEFAULT_RESERVATION_DURATION);
					account.addReservation(reservation);
					
					book.setQuantity(book.getQuantity() - 1);
					
					loger.record(new Log(account, "Did Reservate a Book:" + book.getName()));

					localBookReserved = true;
				}
			}
		}
		
		

		// Try to reserve book in other libraries
		if (localBookReserved == false){
			final BoolValue remoteBookReserved = new BoolValue();
			
			synchronized (interCommunicationLock) {
				InternalMessage sendMessage = new InternalMessage();
				sendMessage.setType(InternalMessage.TYPE_RESERVE_BOOK);
				sendMessage.addParameter("bookName", bookName);
				sendMessage.addParameter("authorName", authorName);
				

				internalSender.setDelegate(new InternalSenderDelegate() {
					@Override
					public void internalSenderDidReceiveMessage(ServerInfo serverInfo, ServerInfo fromServerInfo, InternalMessage receiveMessage) {
						String successString = receiveMessage.getParameter("value");
						boolean success = Boolean.valueOf(successString);
						
						if (success) {
							remoteBookReserved.setValue(true);
							
							response.setData("Reserved the book in " + fromServerInfo.getName());
							
							internalSender.stop();
						}
					}
					
					@Override
					public void internalSenderDidReceiveAllMessages(ServerInfo serverInfo) {
						
					}
				});
				internalSender.sendMessage(sendMessage);
			}
			
			// To check the result of reserving book in other libraries
			if (remoteBookReserved.getValue() == false){
				response.setErrorCode(ServerError.BOOK_NOT_ENOUGH_EVEN_REMOTE);
				System.out.println(response.getErrorCode());
				return response.toCorba();
			} 
			

			// logic
			Reservation reservation = new Reservation(book, _DEFAULT_RESERVATION_DURATION);
			account.addReservation(reservation);
			
			loger.record(new Log(account, "Did Reservate a Book:" + book.getName()));
			
		}
		
		return response.toCorba();
	}
	
	/*
	 * Mark - Services - Admin - Methods
	 */

	@Override
	public CORBAResponse getNonRetuners(String adminUsername, String adminPassword,
			                   String educationalInstitution, String numDays) {

		// Assuming that every parameter in this function is not null

		Response response = new Response();
		response.setErrorCode(ServerError.SUCCESS);
		
		// To check if the account is an admin account
		if (!adminUsername.equals(Account.ADMIN_USERNAME)){
			response.setErrorCode(ServerError.ACCOUNT_IS_NOT_ADMIN);
			System.out.println(response.getErrorCode());
			return response.toCorba();
		}
		
		// To check the password
		Account account = database.getAccount(adminUsername);
		if (!account.getPassword().equals(adminPassword)) {
			response.setErrorCode(ServerError.ACCOUNT_WRONG_PASSWORD);
			System.out.println(response.getErrorCode());
			return response.toCorba();
		}

		// To combine the local result
		final StringBuilder allMessages = new StringBuilder();
		allMessages.append(internalGetNonReturns(numDays));

		// To combine the remote results
		synchronized (interCommunicationLock) {

			InternalMessage sendMessage = new InternalMessage();
			sendMessage.setType(InternalMessage.TYPE_GET_NON_RETURNS);
			sendMessage.addParameter("numDays", numDays);
			
			internalSender.setDelegate(new InternalSenderDelegate() {
				@Override
				public void internalSenderDidReceiveMessage(ServerInfo serverInfo, ServerInfo fromServerInfo, InternalMessage receiveMessage) {
					allMessages.append(receiveMessage.getParameter("value"));
				}
				
				@Override
				public void internalSenderDidReceiveAllMessages(ServerInfo serverInfo) {
					
				}
			});

			response.setData(allMessages.toString());
		}
		
		return response.toCorba();
	}

	@Override
	public CORBAResponse setDuration(String username, String bookName, String numDays) {

		// Assuming that every parameter in this function is not null
		
		Response response = new Response();
		response.setErrorCode(ServerError.SUCCESS);
		
		// To check if the account is exist
		Account account = database.getAccount(username);
		if (account == null) {
			response.setErrorCode(ServerError.ACCOUNT_NOT_EXISTED);
			System.out.println(response.getErrorCode());
			return response.toCorba();
		}
		
		// To execute
		List<Reservation> reservations = account.getReservations();
		for (Reservation reservation : reservations) {
			Book book = reservation.getBook();
			if (book.getName().equals(bookName)) {
				reservation.setDuration(Integer.valueOf(numDays));
				break;
			}
		}
		
		return response.toCorba();
	}
	
	/*
	 * Mark - Services - Debug - Methods
	 */
	 
	@Override
	public CORBAResponse show() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Name:" + "\n");
		stringBuilder.append("\t" + serverInfo.getName() + "\n");
		
		stringBuilder.append("Book:" + "\n");
		List<Book> books = database.getBooks();
		for (Book book : books) {
			stringBuilder.append("\t" + book.getName() + " " + book.getQuantity() + "\n");
		}
		
		stringBuilder.append("Account:" + "\n");
		List<Account> accounts = database.getAccounts();
		for (Account account : accounts) {
			stringBuilder.append("\t" + account.getUsername() + "\n");
			
			List<Reservation> reservations = account.getReservations();
			for (Reservation reservation : reservations) {
				stringBuilder.append("\t\t" + reservation.getBook().getName() + "\n");
			}
		}
		
		Response response = new Response();
		response.setErrorCode(ServerError.SUCCESS);
		response.setData(stringBuilder.toString());
		
		return response.toCorba();
	}
	

	/*
	 * Mark - Interal Communication - Properties
	 */
	 
	private InternalSender internalSender;
	private InternalReceiver internalReceiver;

	/*
	 * Mark - Interal Communication - Methods
	 */
	
	private void initInternalComunication() {

		internalSender = new InternalSender();
		internalSender.setServerInfo(serverInfo);
		
		internalReceiver = new InternalReceiver();
		internalReceiver.setServerInfo(serverInfo);
		internalReceiver.addHandler(new InternalGetNonReturnsHandler(), InternalMessage.TYPE_GET_NON_RETURNS);
		internalReceiver.addHandler(new InternalReserveBookHandler(), InternalMessage.TYPE_RESERVE_BOOK);
		internalReceiver.start();
	}
	
	// to convert messgae params to function params.
	private class InternalGetNonReturnsHandler implements InternalReceiverHandler {
		@Override
		public void handle(InternalMessage receiveMessage, InternalMessage sendMessage) {
			String numDays = receiveMessage.getParameter("numDays");
			
			String value = LibraryServer.this.internalGetNonReturns(numDays);
			
			sendMessage.addParameter("value", value);
		}
	}
	
	// internal get non returns function
	public String internalGetNonReturns(String numDays){
		
		int numDaysInt = Integer.valueOf(numDays);
		
		StringBuilder sb = new StringBuilder();
		sb.append(serverInfo.getName() + ":\n");
		List<Account> accounts = database.getAccounts();
		
		for (Account account : accounts) {
			for (Reservation reservation : account.getReservations()){
				if (reservation.getDuration() <= numDaysInt) {
					sb.append(account.getFirstName() + " ");
					sb.append(account.getLastName() + " ");
					sb.append(account.getPhoneNumber() + " ");
					sb.append(reservation.getBook().getName() + "\n");
				}
			}
		}
		return sb.toString();
	}
	

	// to convert messgae params to function params.
	private class InternalReserveBookHandler implements InternalReceiverHandler {

		@Override
		public void handle(InternalMessage receiveMessage, InternalMessage sendMessage) {
			String bookName = receiveMessage.getParameter("bookName") ;
			String authorName = receiveMessage.getParameter("authorName");
			
			boolean success = LibraryServer.this.internalReserveBook(bookName, authorName);
			
			sendMessage.addParameter("value", Boolean.toString(success));
		}
	}
	
	// internal reserve book function
	private boolean internalReserveBook(String bookName, String authorName){
		Book book = database.getBook(bookName);
		
		if (book == null) {
			return false;
		}
		
		synchronized (bookLock) {
			if (book.getQuantity() <= 0) {
				return false;
			}
			book.setQuantity(book.getQuantity() - 1);
		}
		return true;
	}
	

	/*
	 * Mark - Demo
	 */
	 
	
	private void buildFakeData()
	{
		Account account;
//		
//		account = new Account("Admin", "Admin", "Ad", "Min", "xxxxxxx", "911");
//		database.addAccount(account);
//
		account = new Account("zhaozhe", "zhaozhe", "Zhe", "Zhao", "xxxxxxx", "514");
		database.addAccount(account);
//		
//		account = new Account("xiaoming", "xiaoming", "Xiao", "Ming", "xxxxxxx", "123");
//		database.addAccount(account);
//		
		Book book;
		
//		book = new Book("BookA", "A", 3);
//		database.addBook(book);	
//		
		book = new Book("Book", "B", 1);
		database.addBook(book);		
		
//		this.reserveBook("zhaozhe", "zhaozhe", "BookA", "A");
//		this.reserveBook("zhaozhe", "zhaozhe", "BookB", "B");
//		
//		this.reserveBook("xiaoming", "xiaoming", "BookB", "B");
		
		
		account = new Account("Admin", "Admin", "Ad", "Min", "xxxxxxx", "911");
		database.addAccount(account);
	
//		account = new Account("FrankenStein", "frate", "Stein", "Franken", "xxxxxxx", "514");
//		database.addAccount(account);
//		
//		account = new Account("drwho900", "drw900", "900", "drwho900", "xxxxxxx", "123");
//		database.addAccount(account);
//		
//		account = new Account("patrickStar", "patSta", "Star", "patrick", "xxxxxxx", "123");
//		database.addAccount(account);
		
//		Book book;
		
//		book = new Book("Cuda", "Nicholas", 2);
//		database.addBook(book);	
//		
//		book = new Book("Opencl", "Munshi", 3);
//		database.addBook(book);	
//		
//		book = new Book("3DMath", "Pletchen", 1);
//		database.addBook(book);	
		
//		this.reserveBook("patrickStar", "patSta", "Opencl", "Munshi");
//		
//		this.reserveBook("drwho900", "drw900", "3DMath", "Pletchen");
//		this.reserveBook("drwho900", "drw900", "3DMath", "Pletchen");
//		
//		this.reserveBook("FrankenStein", "fraSte", "Cuda", "Nicholas");

//		account = new Account("boromirking", "bmk", "boromir", "king", "xxxxxxx", "911");
//		database.addAccount(account);
//		
//		if (serverInfo.getIdentity() == 3){
//			book = new Book("Bones", "Kathy", 4);
//			database.addBook(book);
//		}
		
		
		//Stein Franken xxxxxxx 514 FrankenStein fraSte
		//900 drwho900 xxxxxxx 123 drwho900 drw900
		//Star patrick xxxxxxx 123 patrickStar patSta
		
		
		//patrickStar patSta Opencl Munshi
		
		//drwho900 drw900 3DMath Pletchen
		//drwho900 drw900 3DMath Pletchen
		
		//FrankenStein fraSte Cuda Nicholas
		//boromirking bmk Bones Kathy
		
		//patrickStar Opencl -2
		//drwho900 3DMath -2
		//FrankenStein Cuda -2
		
		
		//Admin Admin 0
		
		
		
	}
}
