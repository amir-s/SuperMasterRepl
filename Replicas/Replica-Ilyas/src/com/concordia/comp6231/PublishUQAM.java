package com.concordia.comp6231;

import javax.xml.ws.Endpoint;
import com.concordia.comp6231.LibraryServer;

/**
 * @author Ilyas Rashid - Student Id 4819608
 * @date 19/11/2014
 * Assignment # 3 - Option 1
 */

public class PublishUQAM {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Endpoint endpoint = Endpoint.publish("http://localhost:7779/drms", new LibraryServer(2022));
		System.out.println("UQAM server is up = " + endpoint.isPublished());

	}

}