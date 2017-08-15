package microevents.msgbox;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import microevents.msgbox.MessageString;
import microevents.msgbox.MessageFactory;
import microevents.msgbox.impl.MessageStringImpl;



/**
 * MessageStringImplTest
 * 
 * @author Alex Shvid
 *
 */

public class MessageStringImplTest extends AbstractMessageTest {

	@Test(expected=IllegalArgumentException.class)
	public void testNullString() {
		
		new MessageStringImpl((String)null);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullBytes() {
		
		new MessageStringImpl((byte[])null, false);
		
	}
	
	@Test
	public void testEmptyString() {
		
		MessageString string = new MessageStringImpl("");
		
		Assert.assertEquals("", string.asString());
		Assert.assertEquals("", string.toUtf8());
		Assert.assertTrue(Arrays.equals(new byte[0], string.getBytes(true)));
		
    Assert.assertEquals("a0", string.toHexString());
    Assert.assertEquals("a0", toHexString(string));
    
    MessageStringImpl actual = MessageFactory.newTypedValue(string.toByteArray());
    Assert.assertEquals(string, actual);
	}
	
	@Test
	public void testString() {
		
		MessageString string = new MessageStringImpl("hello");
		
		Assert.assertEquals("hello", string.asString());
		Assert.assertEquals("hello", string.toUtf8());
		Assert.assertTrue(Arrays.equals("hello".getBytes(), string.getBytes(true)));
		
    Assert.assertEquals("a568656c6c6f", string.toHexString());
    Assert.assertEquals("a568656c6c6f", toHexString(string));
    
    MessageStringImpl actual = MessageFactory.newTypedValue(string.toByteArray());
    Assert.assertEquals(string, actual);
		
	}
	
}
