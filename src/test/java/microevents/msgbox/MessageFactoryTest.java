package microevents.msgbox;

import org.junit.Assert;
import org.junit.Test;

import microevents.msgbox.MessageNumber;
import microevents.msgbox.MessageNumberType;
import microevents.msgbox.MessageString;
import microevents.msgbox.MessageTable;
import microevents.msgbox.MessageValue;
import microevents.msgbox.MessageFactory;
import microevents.msgbox.impl.MessageNumberImpl;
import microevents.msgbox.impl.MessageStringImpl;

/**
 * MessageFactoryTest
 * 
 * @author Alex Shvid
 *
 */

public class MessageFactoryTest {

	@Test
	public void testStringifyNull() {
		
		MessageFactory.newStringifyValue((String) null);
		
	}
	
	@Test
	public void testStringifyEmpty() {
		
		MessageValue<?> value = MessageFactory.newStringifyValue("");
		
		Assert.assertTrue(value instanceof MessageString);
		
		MessageString string = (MessageString) value;
		
		Assert.assertEquals("", string.asString());
	}
	
	@Test
	public void testStringifyZeroLong() {

		MessageValue<?> value = MessageFactory.newStringifyValue("0");
		
		Assert.assertTrue(value instanceof MessageNumber);
		
		MessageNumber val = (MessageNumber) value;
		
		Assert.assertEquals(MessageNumberType.LONG, val.getType());
		Assert.assertEquals(0, val.asLong());

	}
	
	@Test
	public void testStringifyZeroDouble() {

		MessageValue<?> value = MessageFactory.newStringifyValue("0.0");
		
		Assert.assertTrue(value instanceof MessageNumber);
		
		MessageNumber val = (MessageNumber) value;
		
		Assert.assertEquals(MessageNumberType.DOUBLE, val.getType());
		Assert.assertTrue(Math.abs(0.0 - val.asDouble()) < 0.00001);

	}
	
	@Test
	public void testStringifyLong() {

		MessageValue<?> value = MessageFactory.newStringifyValue("123456789");
		
		Assert.assertTrue(value instanceof MessageNumber);
		
		MessageNumber val = (MessageNumber) value;
		
		Assert.assertEquals(MessageNumberType.LONG, val.getType());
		Assert.assertEquals(123456789L, val.asLong());

	}
	
	
	@Test
	public void testStringifyString() {

		MessageValue<?> value = MessageFactory.newStringifyValue("abc");
		
		Assert.assertTrue(value instanceof MessageString);
		
		MessageString string = (MessageString) value;
		
		Assert.assertEquals("abc", string.asString());

	}

	@Test(expected=IllegalArgumentException.class)
	public void testNullExample() {
		MessageFactory.newValue((byte[]) null);
	}
	
	@Test
	public void testEmptyExample() {
		
		final byte[] example =  new byte[] {};
		
		MessageValue<?> value = MessageFactory.newValue(example);
		
		Assert.assertNull(value);
		
	}
	
	@Test
	public void testExample() {
		
		final byte[] example =  new byte[] {-125, -93, 97, 99, 99, -93, 49, 50, 51, -90, 108, 111, 103, 105, 110, 115, -9, -92, 110, 97, 109, 101, -92, 65, 108, 101, 120};
	
		MessageValue<?> value = MessageFactory.newValue(example);
		
		Assert.assertNotNull(value);
		Assert.assertTrue(value instanceof MessageTable);
		
		MessageTable table = (MessageTable) value;
		
		Assert.assertEquals("123", table.get("acc").asString());
		Assert.assertEquals(new MessageStringImpl("123"), table.getString("acc"));
		Assert.assertEquals(new MessageNumberImpl(123), table.getNumber("acc"));
		
		Assert.assertEquals("Alex", table.get("name").asString());
		Assert.assertEquals(new MessageStringImpl("Alex"), table.getString("name"));
		
		Assert.assertEquals("-9", table.get("logins").asString());
		Assert.assertEquals(new MessageNumberImpl(-9), table.getNumber("logins"));
		
		//System.out.println(table);
		
	}
	
	
}
