package microevents.msgbox.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.msgpack.core.MessagePacker;
import org.msgpack.value.Value;
import org.msgpack.value.impl.ImmutableLongValueImpl;
import org.msgpack.value.impl.ImmutableMapValueImpl;
import org.msgpack.value.impl.ImmutableStringValueImpl;

import microevents.msgbox.MessageBoolean;
import microevents.msgbox.MessageFactory;
import microevents.msgbox.MessageNumber;
import microevents.msgbox.MessageString;
import microevents.msgbox.MessageTable;
import microevents.msgbox.MessageTableType;
import microevents.msgbox.MessageValue;
import microevents.msgbox.MessageValueExpression;
import microevents.msgbox.support.MessageException;
import microevents.msgbox.support.MessageNumberFormatException;
import microevents.msgbox.util.MessageStringifyUtil;
import microevents.msgbox.util.MessageStringifyUtil.NumberType;
import microevents.msgbox.util.MessageValueUtil;

/**
 * Implementation of the message table
 * 
 * @author Alex Shvid
 *
 */

public class MessageTableImpl extends AbstractMessageValueImpl<MessageTable> implements MessageTable {

	/**
	 * Keys could be stringify integers or any strings
	 */

	private final Map<String, MessageValue<?>> table = new HashMap<String, MessageValue<?>>();

	private MessageTableType type = MessageTableType.INT_KEY;

	/**
	 * Simple comparator that uses to make sure that Map is sorted by keys
	 * 
	 * @author Alex Shvid
	 *
	 */
	
	public final class KeyComparator implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {

			if (type == MessageTableType.INT_KEY) {

				try {
					int i1 = Integer.parseInt(o1);
					int i2 = Integer.parseInt(o2);

					return Integer.compare(i1, i2);
					
				} catch (NumberFormatException e) {
					return o1.compareTo(o2);
				}
			}

			return o1.compareTo(o2);
		}

	}

	@Override
	public MessageTableType getType() {
		return type;
	}

	@Override
	public MessageValue<?> get(String key) {
		
		if (key == null) {
			throw new IllegalArgumentException("empty key");
		}
		
		return table.get(key);
	}
	
	@Override
	public MessageTable getTable(String key) {
		return MessageValueUtil.toTable(get(key));
	}

	@Override
	public Boolean getBoolean(String key) {
		MessageBoolean val = MessageValueUtil.toBoolean(get(key));
		return val != null ? val.asBoolean() : null;
	}

	@Override
	public MessageNumber getNumber(String key) {
		return MessageValueUtil.toNumber(get(key));
	}
	
	@Override
	public Long getLong(String key) {
		MessageNumber number = getNumber(key);
		return number != null ? number.asLong() : null;
	}
	
	@Override
	public Double getDouble(String key) {
		MessageNumber number = getNumber(key);
		return number != null ? number.asDouble() : null;
	}

	@Override
	public MessageString getString(String key) {
		return MessageValueUtil.toString(get(key));
	}

	@Override
	public String getStringUtf8(String key) {
		MessageString str = getString(key);
		return str != null ? str.toUtf8() : null;
	}

	@Override
	public byte[] getBytes(String key, boolean copy) {
		MessageString str = getString(key);
		return str != null ? str.getBytes(copy) : null;
	}

	@Override
	public MessageValue<?> get(Integer key) {
		
		if (key == null) {
			throw new IllegalArgumentException("empty key");
		}
		
		return table.get(key.toString());
	}
	
	@Override
	public MessageTable getTable(Integer key) {
		return MessageValueUtil.toTable(get(key));
	}
	
	@Override
	public Boolean getBoolean(Integer key) {
		MessageBoolean val = MessageValueUtil.toBoolean(get(key));
		return val != null ? val.asBoolean() : null;
	}

	@Override
	public MessageNumber getNumber(Integer key) {
		return MessageValueUtil.toNumber(get(key));
	}
	
	@Override
	public Long getLong(Integer key) {
		MessageNumber number = getNumber(key);
		return number != null ? number.asLong() : null;
	}
	
	@Override
	public Double getDouble(Integer key) {
		MessageNumber number = getNumber(key);
		return number != null ? number.asDouble() : null;
	}

	@Override
	public MessageString getString(Integer key) {
		return MessageValueUtil.toString(get(key));
	}
	
	@Override
	public String getStringUtf8(Integer key) {
		MessageString str = getString(key);
		return str != null ? str.toUtf8() : null;
	}

	@Override
	public byte[] getBytes(Integer key, boolean copy) {
		MessageString str = getString(key);
		return str != null ? str.getBytes(copy) : null;
	}
	
	@Override
	public MessageValue<?> get(MessageValueExpression ve) {
	
		if (ve == null) {
			throw new IllegalArgumentException("null ve");
		}
		
		if (ve.isEmpty()) {
			return this;
		}
		
		int lastIndex = ve.size() - 1;
		MessageTable currentTable = this;
		for (int i = 0; i != lastIndex; ++i) {
			
			String key = ve.get(i);
			MessageValue<?> existingValue = currentTable.get(key);
			
			if (existingValue == null || !(existingValue instanceof MessageTable)) {
				return null;
			}
			else {
				currentTable = (MessageTable) existingValue;
			}
			
		}
		
		String key = ve.get(lastIndex);
		return currentTable.get(key);
		
	}
	
	@Override
	public MessageTable getTable(MessageValueExpression ve) {
		return MessageValueUtil.toTable(get(ve));
	}

	@Override
	public Boolean getBoolean(MessageValueExpression ve) {
		MessageBoolean val = MessageValueUtil.toBoolean(get(ve));
		return val != null ? val.asBoolean() : null;
	}
	
	@Override
	public MessageNumber getNumber(MessageValueExpression ve) {
		return MessageValueUtil.toNumber(get(ve));
	}
	
	@Override
	public Long getLong(MessageValueExpression ve) {
		MessageNumber number = getNumber(ve);
		return number != null ? number.asLong() : null;
	}
	
	@Override
	public Double getDouble(MessageValueExpression ve) {
		MessageNumber number = getNumber(ve);
		return number != null ? number.asDouble() : null;
	}
	
	@Override
	public MessageString getString(MessageValueExpression ve) {
		return MessageValueUtil.toString(get(ve));
	}
	
	@Override
	public String getStringUtf8(MessageValueExpression ve) {
		MessageString str = getString(ve);
		return str != null ? str.toUtf8() : null;
	}

	@Override
	public byte[] getBytes(MessageValueExpression ve, boolean copy) {
		MessageString str = getString(ve);
		return str != null ? str.getBytes(copy) : null;
	}
	
	@Override
	public MessageValue<?> put(String key, MessageValue<?> value) {

		if (type == MessageTableType.INT_KEY) {

			NumberType numberType = MessageStringifyUtil.detectNumber(key);
			if (numberType == NumberType.NAN) {
				type = MessageTableType.STRING_KEY;
			}

		}

		if (value != null) {
			return table.put(key, value);
		}
		else {
			return table.remove(key);
		}
	}
	
	@Override
	public MessageValue<?> put(String key, String stringfyValue) {
		return put(key, MessageFactory.newStringifyValue(stringfyValue));
	}
	
	@Override
	public MessageValue<?> putBoolean(String key, boolean value) {
		return put(key, new MessageBooleanImpl(value));
	}

	@Override
	public MessageValue<?> putLong(String key, long value) {
		return put(key, new MessageNumberImpl(value));
	}

	@Override
	public MessageValue<?> putDouble(String key, double value) {
		return put(key, new MessageNumberImpl(value));
	}

	@Override
	public MessageValue<?> putString(String key, String value) {
		return put(key, new MessageStringImpl(value));
	}

	@Override
	public MessageValue<?> putBytes(String key, byte[] value, boolean copy) {
		return put(key, new MessageStringImpl(value, copy));
	}

	@Override
	public MessageValue<?> put(Integer key, MessageValue<?> value) {
		
		if (value != null) {
			return table.put(key.toString(), value);
		}
		else {
			return table.remove(key.toString());
		}
		
	}
	
	@Override
	public MessageValue<?> put(Integer key, String stringfyValue) {
		return put(key, MessageFactory.newStringifyValue(stringfyValue));
	}

	@Override
	public MessageValue<?> putBoolean(Integer key, boolean value) {
		return put(key, new MessageBooleanImpl(value));
	}
	
	@Override
	public MessageValue<?> putLong(Integer key, long value) {
		return put(key, new MessageNumberImpl(value));
	}

	@Override
	public MessageValue<?> putDouble(Integer key, double value) {
		return put(key, new MessageNumberImpl(value));
	}
	
	@Override
	public MessageValue<?> putString(Integer key, String value) {
		return put(key, new MessageStringImpl(value));
	}

	@Override
	public MessageValue<?> putBytes(Integer key, byte[] value, boolean copy) {
		return put(key, new MessageStringImpl(value, copy));
	}
	
	@Override
	public MessageValue<?> put(MessageValueExpression ve, MessageValue<?> value) {

		if (ve == null) {
			throw new IllegalArgumentException("null ve");
		}
		
		if (ve.isEmpty()) {
			return value;
		}
		
		int lastIndex = ve.size() - 1;
		MessageTable currentTable = this;
		for (int i = 0; i != lastIndex; ++i) {
			
			String key = ve.get(i);
			MessageValue<?> existingValue = currentTable.get(key);
			
			if (existingValue == null || !(existingValue instanceof MessageTable)) {
			  MessageTable newTable = new MessageTableImpl();
			  currentTable.put(key, newTable);
			  currentTable = newTable;
			}
			else {
				currentTable = (MessageTable) existingValue;
			}
			
		}
		
		String key = ve.get(lastIndex);
		return currentTable.put(key, value);
	}
		
	@Override
	public MessageValue<?> put(MessageValueExpression ve, String stringfyValue) {
		return put(ve, MessageFactory.newStringifyValue(stringfyValue));
	}
	
	@Override
	public MessageValue<?> putBoolean(MessageValueExpression ve, boolean value) {
		return put(ve, new MessageBooleanImpl(value));
	}
	
	@Override
	public MessageValue<?> putLong(MessageValueExpression ve, long value) {
		return put(ve, new MessageNumberImpl(value));
	}

	@Override
	public MessageValue<?> putDouble(MessageValueExpression ve, double value) {
		return put(ve, new MessageNumberImpl(value));
	}
	
	@Override
	public MessageValue<?> putString(MessageValueExpression ve, String value) {
		return put(ve, new MessageStringImpl(value));
	}

	@Override
	public MessageValue<?> putBytes(MessageValueExpression ve, byte[] value, boolean copy) {
		return put(ve, new MessageStringImpl(value, copy));
	}
	
	@Override
	public MessageValue<?> remove(String key) {
		return put(key, (MessageValue<?>) null);
	}
	
	@Override
	public MessageValue<?> remove(Integer key) {
		return put(key, (MessageValue<?>) null);
	}
	
	@Override
	public MessageValue<?> remove(MessageValueExpression ve) {
		return put(ve, (MessageValue<?>) null);
	}
	
	@Override
	public Set<String> keySet() {
		return table.keySet();
	}
	
	@Override
	public List<Integer> intKeys() {
		
		List<Integer> list = new ArrayList<Integer>(table.size());
		
		for (String key : table.keySet()) {

			try {
				list.add(Integer.parseInt(key));
			}
			catch(NumberFormatException e) {
				// ignore
			}
		}
		
		Collections.sort(list);
		
		return list;
	}
	
	@Override
	public Integer minIntKey() {
		
		Integer minKey = null;
		
		for (String key : table.keySet()) {

			try {
				int value = Integer.parseInt(key);
				if (minKey == null || value < minKey) {
					minKey = value;
				}
			}
			catch(NumberFormatException e) {
				// ignore
			}
		}
		
		return minKey;
	}
	
	@Override
	public Integer maxIntKey() {
		
		Integer maxKey = null;
		
		for (String key : table.keySet()) {

			try {
				int value = Integer.parseInt(key);
				if (maxKey == null || value > maxKey) {
					maxKey = value;
				}
			}
			catch(NumberFormatException e) {
				// ignore
			}
		}
		
		return maxKey;
	}

	@Override
	public int size() {
		return table.size();
	}

	@Override
	public void clear() {
		table.clear();
	}

	@Override
	public String asString() {
		StringBuilder str = new StringBuilder();
		str.append("{");
		boolean first = true;
		for (Map.Entry<String, MessageValue<?>> entry : table.entrySet()) {
			if (!first) {
				str.append(", ");
			}
			str.append(entry.getKey()).append("=").append(entry.getValue().asString());
			first = false;
		}
		str.append("}");
		return str.toString();
	}

	@Override
	public Value toValue() {

		switch(type) {
		
		case INT_KEY:
			return toIntValue();
			
		case STRING_KEY:
			return toStringValue();
			
		}
		
		throw new MessageException("unexpected type: " + type);
	}

	private Value toIntValue() {
		
    int size = size();
    
    int capacity = size << 1;
    Value[] array = new Value[capacity];
    
    int index = 0;
    for (Map.Entry<String, MessageValue<?>> entry : table.entrySet()) {
      
    	int integerKey;
    	try {
    		integerKey = Integer.parseInt(entry.getKey());
    	}
    	catch(NumberFormatException e) {
    		throw new MessageNumberFormatException(entry.getKey(), e);
    	}
    	
    	MessageValue<?> val = entry.getValue();
      
      array[index++] = new ImmutableLongValueImpl(integerKey);
      array[index++] = val.toValue();
      
    }
    
    return new ImmutableMapValueImpl(array);
		
	}

	private Value toStringValue() {
		
    int size = size();

    int capacity = size << 1;
    Value[] array = new Value[capacity];
    
    int index = 0;
    for (Map.Entry<String, MessageValue<?>> entry : table.entrySet()) {
      
    	MessageValue<?> val = entry.getValue();
      
      array[index++] = new ImmutableStringValueImpl(entry.getKey());
      array[index++] = val.toValue();
      
    }
    
    return new ImmutableMapValueImpl(array);
    
	}
	
  @Override
	public void writeTo(MessagePacker packer) throws IOException {
		switch(type) {
		
		case INT_KEY:
			writeIntMapTo(packer);
			break;
			
		case STRING_KEY:
			writeStringMapTo(packer);
			break;
			
		default:
			throw new IOException("unexpected type: " + type);
		}
		
	}	
  
  private void writeIntMapTo(MessagePacker packer) throws IOException {
  	
    int size = size();
    
    packer.packMapHeader(size);
    
    for (Map.Entry<String, MessageValue<?>> entry : table.entrySet()) {
    	
    	String key = entry.getKey();
    	int intKey;
    	try {
    		intKey = Integer.parseInt(key);
    	}
    	catch(NumberFormatException e) {
    		throw new IOException(key, e);
    	}
    	
    	MessageValue<?> value = entry.getValue();
    	
			packer.packInt(intKey);
    	value.writeTo(packer);
    }
    
  }
  
  private void writeStringMapTo(MessagePacker packer) throws IOException {
  	
    int size = size();
    
    packer.packMapHeader(size);
    
    for (Map.Entry<String, MessageValue<?>> entry : table.entrySet()) {
    	
    	String key = entry.getKey();
    	MessageValue<?> value = entry.getValue();
    	
			byte[] data = key.getBytes(StandardCharsets.UTF_8);
			packer.packRawStringHeader(data.length);
			packer.writePayload(data);
			
    	value.writeTo(packer);
    }
    
  }  

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("MsgTableImpl [type=" + type + ", size=" + table.size() + "] {\n");
		boolean first = true;
		for (Map.Entry<String, MessageValue<?>> entry : table.entrySet()) {
			if (!first) {
				str.append(",\n");
			}
			str.append(entry.getKey()).append("=").append(entry.getValue().toString());
			first = false;
		}
		str.append("\n}");
		return str.toString();
	}

}
