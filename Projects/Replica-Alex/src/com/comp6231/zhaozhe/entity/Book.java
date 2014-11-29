package com.comp6231.zhaozhe.entity;

public class Book {
	
	/*
	 * Mark - Constructors
	 */

	public Book() {
		super();
		
	}
	
	public Book(String name, String authorName, int quantity) {
		super();
		this.name = name;
		this.authorName = authorName;
		this.quantity = quantity;
	}
	
	/*
	 * Mark - Basic - Properties
	 */
	 
	private String name;
	private String authorName;
	private int quantity;
	
	
	/*
	 * Mark - Basic - Getters & Setters
	 */
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/*
	 * Mark - Object
	 */
	 
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj instanceof Book) {
			Book book = (Book)obj;
			boolean sameName = book.name.equalsIgnoreCase(this.name);
//			boolean sameAuthor = book.authorName.equalsIgnoreCase(this.authorName);
			return sameName ; //&& sameAuthor;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return name + " " + authorName + " " + quantity;
	}
}
