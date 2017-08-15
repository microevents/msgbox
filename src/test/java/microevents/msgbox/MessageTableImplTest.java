package microevents.msgbox;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import microevents.msgbox.impl.MessageNumberImpl;
import microevents.msgbox.impl.MessageStringImpl;
import microevents.msgbox.impl.MessageTableImpl;



/**
 * MessageTableImplTest
 * 
 * @author Alex Shvid
 *
 */

public class MessageTableImplTest extends AbstractMessageTest {

	@Test
	public void testNull() {

		MessageTable table = new MessageTableImpl();

		Assert.assertEquals(0, table.size());
		
		table.put(1, (MessageValue<?>) null);
		Assert.assertEquals(0, table.size());

		table.put(1, (String) null);
		Assert.assertEquals(0, table.size());

		table.put("1", (MessageValue<?>) null);
		Assert.assertEquals(0, table.size());

		table.put("1", (String) null);
		Assert.assertEquals(0, table.size());
		
	}
	
	@Test
	public void testEmpty() {
		
		MessageTable table = new MessageTableImpl();
		
		Assert.assertEquals(MessageTableType.INT_KEY, table.getType());
		Assert.assertEquals(0, table.size());
		
		MessageTable actual = MessageFactory.newTypedValue(table.toByteArray());
		Assert.assertEquals(MessageTableType.INT_KEY, actual.getType());
		Assert.assertEquals(0, actual.size());
		
	}
	
	@Test
	public void testSingleInt() {
		
		MessageTable table = new MessageTableImpl();
		
		table.put(123, new MessageNumberImpl(123));
		
		Assert.assertEquals(MessageTableType.INT_KEY, table.getType());
		Assert.assertEquals(1, table.size());
		Assert.assertEquals("123", table.keySet().iterator().next());
		Assert.assertEquals(Integer.valueOf(123), table.intKeys().get(0));
		
    Assert.assertEquals("817b7b", table.toHexString());
    Assert.assertEquals("817b7b", toHexString(table));
		
		MessageTable actual = MessageFactory.newTypedValue(table.toByteArray());
		
		Assert.assertEquals(MessageTableType.INT_KEY, actual.getType());
		Assert.assertEquals(1, actual.size());
		Assert.assertEquals("123", actual.keySet().iterator().next());
		Assert.assertEquals(Integer.valueOf(123), actual.intKeys().get(0));
		
	}
	
	@Test
	public void testIntKey() {
		
		MessageTable table = new MessageTableImpl();
		table.put(123, "stringValue");
		Assert.assertEquals(MessageTableType.INT_KEY, table.getType());
		table.put("123", "newStringValue");
		Assert.assertEquals(MessageTableType.INT_KEY, table.getType());
		Assert.assertEquals(1, table.size());
		Assert.assertEquals("newStringValue", table.get(123).asString());
	}
	
	@Test
	public void testIntToStringKey() {
		
		MessageTable table = new MessageTableImpl();
		table.put(123, "stringValue");
		Assert.assertEquals(MessageTableType.INT_KEY, table.getType());
		table.put("abc", "newStringValue");
		Assert.assertEquals(MessageTableType.STRING_KEY, table.getType());
		Assert.assertEquals(2, table.size());
		Assert.assertEquals("stringValue", table.get("123").asString());
		Assert.assertEquals("newStringValue", table.get("abc").asString());
		
	}
	
	@Test
	public void testIntArray() {
		
		MessageTable table = new MessageTableImpl();
		Assert.assertNull(table.maxIntKey());
		Assert.assertTrue(table.keySet().isEmpty());
		Assert.assertTrue(table.intKeys().isEmpty());
		
		for (int i = 1; i != 11; ++i) {
			table.put(i, "value" + i);
		}
		
		Assert.assertEquals(MessageTableType.INT_KEY, table.getType());
		Assert.assertEquals(10, table.size());
		
		List<Integer> keys = table.intKeys();
		for (int i = 1, j = 0; i != 11; ++i, ++j) {
			Assert.assertEquals(Integer.valueOf(i), keys.get(j));
		}
		
		Assert.assertEquals(Integer.valueOf(10), table.maxIntKey());
		
	}
	
	@Test
	public void testIntToStringTable() {
		
		MessageTable table = new MessageTableImpl();
		table.put("1", "one");
		table.put(2, "two");
		table.put("3", "three");
		
		Assert.assertEquals(MessageTableType.INT_KEY, table.getType());
		Assert.assertEquals(3, table.size());
		Assert.assertEquals(3, table.maxIntKey().intValue());
		
		byte[] msgpack = table.toByteArray();
		MessageTable actual = MessageFactory.newTypedValue(msgpack);

		Assert.assertEquals(MessageTableType.INT_KEY, actual.getType());
		Assert.assertEquals(3, actual.size());
		Assert.assertEquals(3, actual.maxIntKey().intValue());

		table.put("abc", "abc");

		Assert.assertEquals(MessageTableType.STRING_KEY, table.getType());
		Assert.assertEquals(4, table.size());
		Assert.assertEquals(3, table.maxIntKey().intValue());
		
		msgpack = table.toByteArray();
		actual = MessageFactory.newTypedValue(msgpack);
		
		Assert.assertEquals(MessageTableType.STRING_KEY, actual.getType());
		Assert.assertEquals(4, actual.size());
		Assert.assertEquals(3, actual.maxIntKey().intValue());

	}
	
	@Test
	public void testStringGetters() {
		
		MessageTable table = new MessageTableImpl();
		table.put("abc", "123.0");
		
		Assert.assertEquals(new MessageNumberImpl(123.0), table.get("abc"));
		
		Assert.assertEquals(false, table.getBoolean("abc"));
		
		Assert.assertEquals(new MessageNumberImpl(123.0), table.getNumber("abc"));
		Assert.assertEquals(Long.valueOf(123L), table.getLong("abc"));
		Assert.assertEquals(Double.valueOf(123.0), table.getDouble("abc"));
		
		Assert.assertEquals(new MessageStringImpl("123.0"), table.getString("abc"));
		Assert.assertEquals("123.0", table.getStringUtf8("abc"));
		Assert.assertTrue(Arrays.equals("123.0".getBytes(), table.getBytes("abc", false)));
		
	}
	
	@Test
	public void testIntGetters() {
		
		MessageTable table = new MessageTableImpl();
		table.put(5, "123.0");
		
		Assert.assertEquals(new MessageNumberImpl(123.0), table.get(5));
		
		Assert.assertEquals(false, table.getBoolean(5));
		
		Assert.assertEquals(new MessageNumberImpl(123.0), table.getNumber(5));
		Assert.assertEquals(Long.valueOf(123L), table.getLong(5));
		Assert.assertEquals(Double.valueOf(123.0), table.getDouble(5));
		
		Assert.assertEquals(new MessageStringImpl("123.0"), table.getString(5));
		Assert.assertEquals("123.0", table.getStringUtf8(5));
		Assert.assertTrue(Arrays.equals("123.0".getBytes(), table.getBytes(5, false)));
		

		
	}
	
	@Test
	public void testInnerTable() {

		MessageTable innerTable = new MessageTableImpl();
		innerTable.put("first", "Alex");
		
		MessageTable table = new MessageTableImpl();
		table.put("name", innerTable);
		
		//System.out.println(table.toJson());
		
		byte[] msgpack = table.toByteArray();
		MessageTable actual = MessageFactory.newTypedValue(msgpack);
		
		// one entry in table guarantee the order
		Assert.assertEquals(table.toJson(), actual.toJson());
		
		MessageTable name = actual.getTable("name");
		Assert.assertNotNull(name);
		Assert.assertEquals("Alex", name.getString("first").asString());
		
	}
	
}
