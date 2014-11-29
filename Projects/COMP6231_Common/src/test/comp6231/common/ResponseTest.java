package test.comp6231.common;

import static org.junit.Assert.*;

import org.junit.Test;

import com.comp6231.common.Response;

public class ResponseTest {

	@Test
	public void testSetFromString() {
		Response response = new Response();
		
		response.setFromString("1@heyhey");
		
		assertTrue(response.getErrorCode().equals("1"));
		assertTrue(response.getData().equals("heyhey"));
		

		response.setFromString("1@");
		
		assertTrue(response.getErrorCode().equals("1"));
		assertTrue(response.getData().equals(""));
		
	}
}
