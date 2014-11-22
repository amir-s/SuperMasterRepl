package com.concordia.comp6231;

import javax.xml.ws.Endpoint;
import com.concordia.comp6231.LibraryServer;

/**
 * @author Ilyas Rashid - Student Id 4819608
 * @date 19/11/2014
 * Assignment # 3 - Option 1
 */

public class PublishConcordia {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//LibraryServer _concordiaLibraryServer = new LibraryServer(2020);
		//Thread _t1 = ;
		
		Endpoint endpoint = Endpoint.publish("http://localhost:7777/drms", new LibraryServer(2020));
		System.out.println("Concorida server is up = " + endpoint.isPublished());
		
		
		

	}

}