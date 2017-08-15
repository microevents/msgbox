package microevents.msgbox;

import org.junit.Assert;
import org.junit.Test;

import microevents.msgbox.MessageFactory;
import microevents.msgbox.impl.MessageBooleanImpl;



/**
 * MessageBooleanImplTest
 * 
 * @author Alex Shvid
 *
 */

public class MessageBooleanImplTest extends AbstractMessageTest {

	@Test
	public void testNull() {

		MessageBooleanImpl bool = new MessageBooleanImpl(null);

		Assert.assertEquals(false, bool.asBoolean());
    Assert.assertEquals("c2", bool.toHexString());
    Assert.assertEquals("c2", toHexString(bool));
    
    MessageBooleanImpl actual = MessageFactory.newTypedValue(bool.toByteArray());
    Assert.assertEquals(bool, actual);
	}
	
	@Test
	public void testEmpty() {

		MessageBooleanImpl bool = new MessageBooleanImpl("");

		Assert.assertEquals(false, bool.asBoolean());
    Assert.assertEquals("c2", bool.toHexString());
    Assert.assertEquals("c2", toHexString(bool));
    
    MessageBooleanImpl actual = MessageFactory.newTypedValue(bool.toByteArray());
    Assert.assertEquals(bool, actual);
	}
	
	@Test
	public void testFalse() {

		MessageBooleanImpl bool = new MessageBooleanImpl("false");

		Assert.assertEquals(false, bool.asBoolean());
    Assert.assertEquals("c2", bool.toHexString());
    Assert.assertEquals("c2", toHexString(bool));
    
    MessageBooleanImpl actual = MessageFactory.newTypedValue(bool.toByteArray());
    Assert.assertEquals(bool, actual);
	}
	
	@Test
	public void testUnknown() {

		MessageBooleanImpl bool = new MessageBooleanImpl("unknown");

		Assert.assertEquals(false, bool.asBoolean());
    Assert.assertEquals("c2", bool.toHexString());
    Assert.assertEquals("c2", toHexString(bool));
    
    MessageBooleanImpl actual = MessageFactory.newTypedValue(bool.toByteArray());
    Assert.assertEquals(bool, actual);
	}
		
	@Test
	public void testTrue() {

		MessageBooleanImpl bool = new MessageBooleanImpl("true");

		Assert.assertEquals(true, bool.asBoolean());
    Assert.assertEquals("c3", bool.toHexString());
    Assert.assertEquals("c3", toHexString(bool));
		
    MessageBooleanImpl actual = MessageFactory.newTypedValue(bool.toByteArray());
    Assert.assertEquals(bool, actual);
	}
	
	
}
