package microevents.msgbox;

import org.junit.Assert;
import org.junit.Test;

import microevents.msgbox.MessageNumber;
import microevents.msgbox.MessageTable;
import microevents.msgbox.MessageValueExpression;
import microevents.msgbox.impl.MessageNumberImpl;
import microevents.msgbox.impl.MessageStringImpl;
import microevents.msgbox.impl.MessageTableImpl;
import microevents.msgbox.impl.MessageValueExpressionImpl;



/**
 * MessageValueExpressionTest
 * 
 * @author Alex Shvid
 *
 */

public class MessageValueExpressionTest {

	@Test(expected=IllegalArgumentException.class)
	public void testNull() {
		
		new MessageValueExpressionImpl(null);
		
	}
	
	@Test
	public void testEmpty() {
		
		MessageValueExpression ve = new MessageValueExpressionImpl("");
		Assert.assertTrue(ve.isEmpty());
		
	}
	
	@Test
	public void testSingle() {
		
		MessageValueExpression ve = new MessageValueExpressionImpl("logins");
		Assert.assertFalse(ve.isEmpty());
		Assert.assertEquals(1, ve.size());
		Assert.assertEquals("logins", ve.get(0));
		
		//System.out.println(ve);
		
	}
	
	@Test
	public void testSingleIndex() {
		
		MessageValueExpression ve = new MessageValueExpressionImpl("[4]");
		Assert.assertFalse(ve.isEmpty());
		Assert.assertEquals(1, ve.size());
		Assert.assertEquals("4", ve.get(0));
		
		//System.out.println(ve);
		
	}
	
	@Test
	public void testTwo() {
		
		MessageValueExpression ve = new MessageValueExpressionImpl("name.first");
		Assert.assertFalse(ve.isEmpty());
		Assert.assertEquals(2, ve.size());
		Assert.assertEquals("name", ve.get(0));
		Assert.assertEquals("first", ve.get(1));
		
		//System.out.println(ve);
		
	}
	
	@Test
	public void testTwoWithIndex() {
		
		MessageValueExpression ve = new MessageValueExpressionImpl("name[1]");
		Assert.assertFalse(ve.isEmpty());
		Assert.assertEquals(2, ve.size());
		Assert.assertEquals("name", ve.get(0));
		Assert.assertEquals("1", ve.get(1));
		
		//System.out.println(ve);
		
	}
	
	@Test
	public void testComplex() {
		
		MessageValueExpression ve = new MessageValueExpressionImpl("educations[2].name");
		Assert.assertFalse(ve.isEmpty());
		Assert.assertEquals(3, ve.size());
		Assert.assertEquals("educations", ve.get(0));
		Assert.assertEquals("2", ve.get(1));
		Assert.assertEquals("name", ve.get(2));
		
		//System.out.println(ve);
		
	}
	
	@Test
	public void testSimple() {
		
		MessageTable table = new MessageTableImpl();
		table.put("name", "John");
		
		MessageValueExpression ve = new MessageValueExpressionImpl("name");
		
		Assert.assertEquals(new MessageStringImpl("John"), table.get(ve));
		Assert.assertEquals(new MessageStringImpl("John"), table.getString(ve));
		
	}
	
	@Test
	public void testInner() {
		
		MessageTable innerTable = new MessageTableImpl();
		innerTable.put("first", "John");
		innerTable.put("last", "Dow");
		
		MessageTable table = new MessageTableImpl();
		table.put("name", innerTable);
		
		MessageValueExpression ve = new MessageValueExpressionImpl("name.first");
		
		Assert.assertEquals(new MessageStringImpl("John"), table.get(ve));
		Assert.assertEquals(new MessageStringImpl("John"), table.getString(ve));
		
	}
	
	@Test
	public void testInnerWithIndex() {
		
		MessageTable innerTable = new MessageTableImpl();
		innerTable.put(1, "John");
		innerTable.put(2, "Dow");
		
		MessageTable table = new MessageTableImpl();
		table.put("name", innerTable);
		
		MessageValueExpression ve = new MessageValueExpressionImpl("name[1]");
		
		Assert.assertEquals(new MessageStringImpl("John"), table.get(ve));
		Assert.assertEquals(new MessageStringImpl("John"), table.getString(ve));
		
	}
	
	@Test
	public void testEmptyPut() {
		
		MessageTable table = new MessageTableImpl();
		MessageValueExpression ve = new MessageValueExpressionImpl("name");

		table.put(ve, "John");
		
		Assert.assertEquals(new MessageStringImpl("John"), table.get(ve));
		Assert.assertEquals(new MessageStringImpl("John"), table.getString(ve));
	}
	
	@Test
	public void testEmptyInnerPut() {
		
		MessageTable table = new MessageTableImpl();
		MessageValueExpression ve = new MessageValueExpressionImpl("name.first");

		table.put(ve, "John");
		
		Assert.assertEquals(new MessageStringImpl("John"), table.get(ve));
		Assert.assertEquals(new MessageStringImpl("John"), table.getString(ve));
		
	}
	
	@Test
	public void testIncrement() {
		
		MessageTable table = new MessageTableImpl();
		MessageValueExpression ve = new MessageValueExpressionImpl("logins");

		MessageNumber number = table.getNumber(ve);
		if (number == null) {
			number = new MessageNumberImpl(0l);
		}
		number = number.add(new MessageNumberImpl(1));
		
		table.put(ve, number);
		
		Assert.assertEquals(1, table.getNumber(ve).asLong());
		
	}
	
	@Test
	public void testDecrement() {
		
		MessageTable table = new MessageTableImpl();
		MessageValueExpression ve = new MessageValueExpressionImpl("logins");

		MessageNumber number = table.getNumber(ve);
		if (number == null) {
			number = new MessageNumberImpl(0l);
		}
		number = number.subtract(new MessageNumberImpl(1.0));
		
		table.put(ve, number);
		
		Assert.assertEquals(-1, table.getNumber(ve).asLong());
		
	}

	
}
