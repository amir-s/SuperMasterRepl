package test.zhaozhe.server;

import static org.junit.Assert.*;

import org.junit.Test;

import com.zhaozhe.server.InternalMessage;

public class InternalMessageTest {

	@Test
	public void testCoding() {
		InternalMessage message = new InternalMessage();
		message.setType("TEST");
		message.addParameter("a", "1");
		byte[] bytes = message.encode();
		System.out.println(new String(bytes));
		assertEquals(new String(bytes), "TEST|a:1\n");
		
		byte[] b2 = new byte[30];
		for (int i = 0 ; i < bytes.length; i++){
			b2[i] = bytes[i];
		}
		
		InternalMessage m2 = new InternalMessage();
		m2.decode(b2);
		assertEquals(m2.getType(), "TEST");
		assertEquals(m2.getParameters().get("a"), "1");
	}

}
