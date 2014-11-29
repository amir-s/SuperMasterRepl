import java.util.HashMap;

// This class is mainly used as a data structure
// to keep track of each book and all the students
// that borrowed this book
public class Book {
	public String name; // the name of the book
	public String author; // the author of the book
	public int quantity; // the quantity of the book that is available in the library
	// The hash map that maps the user name of
	// users that borrowed the book to an integer
	// which indicates the duration of the reservation
	public HashMap<String, Integer> borrowers = new HashMap<String, Integer>();
	// constructor
	public Book(String name, String author, int quantity) {
		this.name = name;
		this.author = author;
		this.quantity = quantity;
	}
}
