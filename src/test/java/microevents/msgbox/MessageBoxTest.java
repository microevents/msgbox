package microevents.msgbox;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;


/**
 * MessageBoxTest
 * 
 * Unit test for MessageBox
 * 
 * @author Alex Shvid
 *
 */

public class MessageBoxTest {

	@Test
	public void testEmpty() {
		
		MessageBox message = MessageFactory.newMessage();
		
		Assert.assertTrue(message.isEmpty());

		byte[] msgpack = message.toByteArray();
		Assert.assertTrue(Arrays.equals(new byte[] { -128 }, msgpack));
		
		String json = message.toJson();
		Assert.assertEquals("{}", json);
		
		MessageBox actual = MessageFactory.parseMessage(msgpack);
		Assert.assertTrue(actual.isEmpty());
		
	}
	
	@Test
	public void testNotEmpty() {
		
		String payload = "{}";
		
		MessageBox message = MessageFactory.newMessage();
		
		message
		.addHeader("id", "123")
		.addHeader("url", "www")
		.addPayloadUtf8("json", payload);
		
		Assert.assertFalse(message.isEmpty());
		
		byte[] msgpack = message.toByteArray();
		
		String json = message.toJson();
		//System.out.println("json = " + json);
		
		MessageBox actual = MessageFactory.parseMessage(msgpack);
		Assert.assertFalse(actual.isEmpty());
		
		Assert.assertEquals(message.getHeader("id"), actual.getHeader("id"));
		Assert.assertEquals(message.getHeader("url"), actual.getHeader("url"));
		
		Assert.assertEquals(payload, message.getPayloadUtf8("json"));
		Assert.assertEquals(payload, actual.getPayloadUtf8("json"));
		
	}
	
	@Test
	public void testNotEmptyBlob() {
		
		byte[] payload = "{}".getBytes(StandardCharsets.UTF_8);
		
		MessageBox message = MessageFactory.newMessage();
		
		message
		.addHeader("id", "123")
		.addHeader("url", "www")
		.addPayload("json", payload, false);
		
		Assert.assertFalse(message.isEmpty());
		
		byte[] msgpack = message.toByteArray();
		//System.out.println("msgpack = " + Arrays.toString(msgpack));
		
		String json = message.toJson();
		//System.out.println("json = " + json);
		
		MessageBox actual = MessageFactory.parseMessage(msgpack);
		Assert.assertFalse(actual.isEmpty());
		
		Assert.assertEquals(message.getHeader("id"), actual.getHeader("id"));
		Assert.assertEquals(message.getHeader("url"), actual.getHeader("url"));
		
		Assert.assertTrue(Arrays.equals(payload, message.getPayload("json", false)));
		Assert.assertTrue(Arrays.equals(payload, actual.getPayload("json", false)));
		
	}
	
	
	
}
