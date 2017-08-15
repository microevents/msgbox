package microevents.msgbox;

import org.junit.Assert;
import org.junit.Test;

import microevents.msgbox.MessageNumber;
import microevents.msgbox.MessageNumberType;
import microevents.msgbox.MessageFactory;
import microevents.msgbox.impl.MessageNumberImpl;
import microevents.msgbox.support.MessageNumberFormatException;

/**
 * MessageNumberImplTest
 * 
 * @author Alex Shvid
 *
 */

public class MessageNumberImplTest extends AbstractMessageTest {

	@Test(expected = IllegalArgumentException.class)
	public void testNull() {

		new MessageNumberImpl(null);

	}

	@Test(expected = MessageNumberFormatException.class)
	public void testEmpty() {

		new MessageNumberImpl("");

	}

	@Test(expected = MessageNumberFormatException.class)
	public void testNAN() {

		new MessageNumberImpl("abc");

	}
	
	@Test
	public void testZeroLong() {

		MessageNumberImpl number = new MessageNumberImpl("0");

		Assert.assertEquals(MessageNumberType.LONG, number.getType());
		Assert.assertEquals(0L, number.asLong());
		Assert.assertTrue(Math.abs(0.0 - number.asDouble()) < 0.001);
		Assert.assertEquals("0", number.asString());
		
    Assert.assertEquals("00", number.toHexString());
    Assert.assertEquals("00", toHexString(number));
    
    MessageNumberImpl actual = MessageFactory.newTypedValue(number.toByteArray());
    Assert.assertEquals(number, actual);
	}
	
	@Test
	public void testZeroDouble() {

		MessageNumberImpl number = new MessageNumberImpl("0.0");

		Assert.assertEquals(MessageNumberType.DOUBLE, number.getType());
		Assert.assertEquals(0L, number.asLong());
		Assert.assertTrue(Math.abs(0.0 - number.asDouble()) < 0.001);		
		Assert.assertEquals("0.0", number.asString());
		
    Assert.assertEquals("cb0000000000000000", number.toHexString());
    Assert.assertEquals("cb0000000000000000", toHexString(number));
    
    MessageNumberImpl actual = MessageFactory.newTypedValue(number.toByteArray());
    Assert.assertEquals(number, actual);
	}
	
	@Test
	public void testLong() {

		MessageNumberImpl number = new MessageNumberImpl("123456789");

		Assert.assertEquals(MessageNumberType.LONG, number.getType());
		Assert.assertEquals(123456789L, number.asLong());
		Assert.assertTrue(Math.abs(123456789.0 - number.asDouble()) < 0.001);
		Assert.assertEquals("123456789", number.asString());
		
    Assert.assertEquals("ce075bcd15", number.toHexString());
    Assert.assertEquals("ce075bcd15", toHexString(number));
    
    MessageNumberImpl actual = MessageFactory.newTypedValue(number.toByteArray());
    Assert.assertEquals(number, actual);
	}
	
	@Test
	public void testDouble() {

		MessageNumberImpl number = new MessageNumberImpl("123456789.0");

		Assert.assertEquals(MessageNumberType.DOUBLE, number.getType());
		Assert.assertEquals(123456789L, number.asLong());
		Assert.assertTrue(Math.abs(123456789.0 - number.asDouble()) < 0.001);
		Assert.assertEquals("1.23456789E8", number.asString());
		
    Assert.assertEquals("cb419d6f3454000000", number.toHexString());
    Assert.assertEquals("cb419d6f3454000000", toHexString(number));
    
    MessageNumberImpl actual = MessageFactory.newTypedValue(number.toByteArray());
    Assert.assertEquals(number, actual);
	}
	
	@Test
	public void testMinusLong() {

		MessageNumberImpl number = new MessageNumberImpl("-123456789");

		Assert.assertEquals(MessageNumberType.LONG, number.getType());
		Assert.assertEquals(-123456789L, number.asLong());
		Assert.assertTrue(Math.abs(-123456789.0 - number.asDouble()) < 0.001);
		Assert.assertEquals("-123456789", number.asString());
		
    Assert.assertEquals("d2f8a432eb", number.toHexString());
    Assert.assertEquals("d2f8a432eb", toHexString(number));
    
    MessageNumberImpl actual = MessageFactory.newTypedValue(number.toByteArray());
    Assert.assertEquals(number, actual);
	}
	
	@Test
	public void testMinusDouble() {

		MessageNumberImpl number = new MessageNumberImpl("-123456789.0");

		Assert.assertEquals(MessageNumberType.DOUBLE, number.getType());
		Assert.assertEquals(-123456789L, number.asLong());
		Assert.assertTrue(Math.abs(-123456789.0 - number.asDouble()) < 0.001);
		Assert.assertEquals("-1.23456789E8", number.asString());
		
    Assert.assertEquals("cbc19d6f3454000000", number.toHexString());
    Assert.assertEquals("cbc19d6f3454000000", toHexString(number));
    
    MessageNumberImpl actual = MessageFactory.newTypedValue(number.toByteArray());
    Assert.assertEquals(number, actual);
	}
	
	@Test
	public void testMaxLong() {

		MessageNumberImpl number = new MessageNumberImpl(Long.MAX_VALUE);

		Assert.assertEquals(MessageNumberType.LONG, number.getType());
		Assert.assertEquals(Long.MAX_VALUE, number.asLong());
		Assert.assertTrue(Math.abs((double)Long.MAX_VALUE - number.asDouble()) < 0.001);
		Assert.assertEquals(Long.toString(Long.MAX_VALUE), number.asString());
		
    Assert.assertEquals("cf7fffffffffffffff", number.toHexString());
    Assert.assertEquals("cf7fffffffffffffff", toHexString(number));
    
    MessageNumberImpl actual = MessageFactory.newTypedValue(number.toByteArray());
    Assert.assertEquals(number, actual);
	}
	
	@Test
	public void testMinLong() {

		MessageNumberImpl number = new MessageNumberImpl(Long.MIN_VALUE);

		Assert.assertEquals(MessageNumberType.LONG, number.getType());
		Assert.assertEquals(Long.MIN_VALUE, number.asLong());
		Assert.assertTrue(Math.abs((double)Long.MIN_VALUE - number.asDouble()) < 0.001);
		Assert.assertEquals(Long.toString(Long.MIN_VALUE), number.asString());
		
    Assert.assertEquals("d38000000000000000", number.toHexString());
    Assert.assertEquals("d38000000000000000", toHexString(number));
    
    MessageNumberImpl actual = MessageFactory.newTypedValue(number.toByteArray());
    Assert.assertEquals(number, actual);
	}

	@Test
	public void testAdd() {
		
		MessageNumber number = new MessageNumberImpl(123L);
		
		Assert.assertEquals(123L + 555L, number.add(555L).asLong());
		Assert.assertEquals(123L + 555L, number.add(new MessageNumberImpl(555L)).asLong());
		Assert.assertEquals(126L, number.add(3.0).asLong());
		Assert.assertEquals(126L, number.add(new MessageNumberImpl(3.0)).asLong());
		
		number = new MessageNumberImpl(123.0);
		
		Assert.assertEquals(123L + 555L, number.add(555L).asLong());
		Assert.assertEquals(123L + 555L, number.add(new MessageNumberImpl(555L)).asLong());
		Assert.assertEquals(126L, number.add(3.0).asLong());
		Assert.assertEquals(126L, number.add(new MessageNumberImpl(3.0)).asLong());
	}
	
	@Test
	public void testSubtruct() {
		
		MessageNumber number = new MessageNumberImpl(123L);
		
		Assert.assertEquals(123L - 555L, number.subtract(555L).asLong());
		Assert.assertEquals(123L - 555L, number.subtract(new MessageNumberImpl(555L)).asLong());
		Assert.assertEquals(120L, number.subtract(3.0).asLong());
		Assert.assertEquals(120L, number.subtract(new MessageNumberImpl(3.0)).asLong());
		
		number = new MessageNumberImpl(123.0);
		
		Assert.assertEquals(123L - 555L, number.subtract(555L).asLong());
		Assert.assertEquals(123L - 555L, number.subtract(new MessageNumberImpl(555L)).asLong());
		Assert.assertEquals(120L, number.subtract(3.0).asLong());
		Assert.assertEquals(120L, number.subtract(new MessageNumberImpl(3.0)).asLong());
	}
}
