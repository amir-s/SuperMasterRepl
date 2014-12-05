

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
	
	/*
	 * Mark - Constructors
	 */

	public Database(){
		accounts = new ArrayList<Account>();
		accountsByNameLetter = new HashMap<String, Map<String,Account>>();
		books = new ArrayList<Book>();
	}
	
	/*
	 * Mark - Account - Properties
	 */

	private List<Account> accounts;
	private Map<String, Map<String, Account>> accountsByNameLetter;	
	
	/*
	 * Mark - Account - Methods
	 */
	
	public Account getAccount(String username){
		String firstLetter = username.substring(0, 1);
		Map<String, Account> listByFirstLetter = accountsByNameLetter.get(firstLetter);
		
		return listByFirstLetter != null ? listByFirstLetter.get(username) : null;
	}
	
	public void addAccount(Account account){
		String firstLetter = account.getUsername().substring(0, 1);
		Map<String, Account> listByFirstLetter = accountsByNameLetter.get(firstLetter);
		
		if (listByFirstLetter == null) {
			listByFirstLetter = new HashMap<String, Account>();
			accountsByNameLetter.put(firstLetter, listByFirstLetter);
		}
		
		listByFirstLetter.put(account.getUsername(), account);
		accounts.add(account);
	}
	
	public List<Account> getAccounts(){
		return accounts;
	}
	
	/*
	 * Mark - Book - Properties
	 */
	 
	private List<Book> books;
	
	/*
	 * Mark - Book - Methods
	 */
	
	public Book getBook(String name){
		Book bookForSearch = new Book();
		bookForSearch.setName(name);
//		bookForSearch.setAuthorName(authorName);
		
		int index = books.indexOf(bookForSearch);
		if (index > -1) {
			return books.get(index);
		} else {
			return null;
		}
	}
	
	public void addBook(Book book){
		books.add(book);
	}
	
	public List<Book> getBooks(){
		return books;
	}
	
}
