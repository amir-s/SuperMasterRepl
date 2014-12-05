


public class Reservation {
	
	/*
	 * Mark - Constructors
	 */

	public Reservation() {
		super();
	}

	public Reservation(Book book, int duration) {
		super();
		this.book = book;
		this.duration = duration;
	}
	
	/*
	 * Mark - Basic - Properties
	 */
	 
	private Book book;
	private int duration;
	
	/*
	 * Mark - Basic - Getters & Setters
	 */
	 
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
}	