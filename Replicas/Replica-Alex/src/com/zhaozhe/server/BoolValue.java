package com.zhaozhe.server;

import org.omg.CORBA.Any;

public class BoolValue {
	
	/*
	 * Mark - Constructors
	 */
	
	public BoolValue(){
		value = false;
	}
	
	/*
	 * Mark - Basic - Properties
	 */

	private boolean value;
	
	/*
	 * Mark - Basic - Methods
	 */
	
	public void beTrue(){
		this.setValue(true);
	}
	
	public void beFalse(){
		this.setValue(false);
		
	
	}
	 
	/*
	 * Mark - Basic - Getters & Setters
	 */
	 
	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
}
