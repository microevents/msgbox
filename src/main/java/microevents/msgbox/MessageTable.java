package microevents.msgbox;

import java.util.List;
import java.util.Set;

/**
 * Mutable Table interface
 * 
 * @author Alex Shvid
 *
 */

public interface MessageTable extends MessageValue<MessageTable> {

	/**
	 * Gets Msg table type
	 * 
	 * @return not null type of the table
	 */
	
	MessageTableType getType();
		
	/**
	 * Gets value by key
	 * 
	 * @param key - could be String or stringfy Integer
	 * @return message value or null
	 */
	
	MessageValue<?> get(String key);
	
	/**
	 * Gets table by key if possible
	 * 
	 * Does not do type conversion, if Msg value is not a table it will return null
	 * 
	 * @param key - could be String or stringfy Integer
	 * @return message table or null
	 */
	
	MessageTable getTable(String key);
	
	/**
	 * Gets boolean value by key
	 * 
	 * Converts any other simple value to boolean
	 * 
	 * @param key - could be String or stringfy Integer
	 * @return boolean or null
	 */
	
	Boolean getBoolean(String key);
	
	/**
	 * Gets number value by key
	 * 
	 * Converts any other simple value to number
	 * 
	 * @param key - could be String or stringfy Integer
	 * @return message number or null
	 */
	
	MessageNumber getNumber(String key);
	
	/**
	 * Gets long number value by key
	 * 
	 * Converts any other simple value to long number
	 * 
	 * @param key - could be String or stringfy Integer
	 * @return long number or null
	 */
	
	Long getLong(String key);
	
	/**
	 * Gets double number value by key
	 * 
	 * Converts any other simple value to double number
	 * 
	 * @param key - could be String or stringfy Integer
	 * @return double number or null
	 */
	
	Double getDouble(String key);
	
	/**
	 * Gets string value by key
	 * 
	 * Converts any other simple value to string
	 * 
	 * @param key - could be String or stringfy Integer
	 * @return Msg string or null
	 */
	
	MessageString getString(String key);
	
	/**
	 * Gets string value by key
	 * 
	 * Converts any other simple value to string
	 * 
	 * @param key - could be String or stringfy Integer
	 * @return string or null
	 */
	
	String getStringUtf8(String key);
	
	/**
	 * Gets bytes value by key
	 * 
	 * Converts any other simple value to bytes string
	 * 
	 * @param key - could be String or stringfy Integer
	 * @param copy - copy bytes or not
	 * @return bytes string or null
	 */
	
	byte[] getBytes(String key, boolean copy);
	
	/**
	 * Gets value by key
	 * 
	 * @param key - integer key
	 * @return message value or null
	 */
	
	MessageValue<?> get(Integer key);
	
	/**
	 * Gets table by key if possible
	 * 
	 * Does not do type conversion, if Msg value is not a table it will return null
	 * 
	 * @param key - integer key
	 * @return message table or null
	 */
	
	MessageTable getTable(Integer key);
	
	/**
	 * Gets boolean value by key
	 * 
	 * Converts any other simple value to boolean
	 * 
	 * @param key - integer key
	 * @return message boolean or null
	 */
	
	Boolean getBoolean(Integer key);
	
	/**
	 * Gets number value by key
	 * 
	 * Converts any other simple value to number
	 * 
	 * @param key - integer key
	 * @return message number or null
	 */
	
	MessageNumber getNumber(Integer key);
	
	/**
	 * Gets long number value by key
	 * 
	 * Converts any other simple value to long number
	 * 
	 * @param key - integer key
	 * @return long number or null
	 */
	
	Long getLong(Integer key);
	
	/**
	 * Gets double number value by key
	 * 
	 * Converts any other simple value to double number
	 * 
	 * @param key - integer key
	 * @return double number or null
	 */
	
	Double getDouble(Integer key);
	
	/**
	 * Gets string value by key
	 * 
	 * Converts any other simple value to string
	 * 
	 * @param key - integer key
	 * @return message string or null
	 */
	
	MessageString getString(Integer key);
	
	/**
	 * Gets string value by key
	 * 
	 * Converts any other simple value to string
	 * 
	 * @param key - integer key
	 * @return string or null
	 */
	
	String getStringUtf8(Integer key);
	
	/**
	 * Gets bytes string value by key
	 * 
	 * Converts any other simple value to bytes string
	 * 
	 * @param key - integer key
	 * @param copy - copy bytes or not
	 * @return bytes string or null
	 */
	
	byte[] getBytes(Integer key, boolean copy);
	
	/**
	 * Gets value
	 * 
	 * @param ve - value expression
	 * @return message value or null
	 */
	
	MessageValue<?> get(MessageValueExpression ve);
	
	/**
	 * Gets table by key if possible
	 * 
	 * Does not do type conversion, if Msg value is not a table it will return null
	 * 
	 * @param ve - value expression
	 * @return message table or null
	 */
	
	MessageTable getTable(MessageValueExpression ve);
	
	/**
	 * Gets boolean value
	 * 
	 * Converts any other simple value to boolean
	 * 
	 * @param ve - value expression
	 * @return message boolean or null
	 */
	
	Boolean getBoolean(MessageValueExpression ve);
	
	/**
	 * Gets number value
	 * 
	 * Converts any other simple value to number
	 * 
	 * @param ve - value expression
	 * @return message number or null
	 */
	
	MessageNumber getNumber(MessageValueExpression ve);
	
	/**
	 * Gets long number value
	 * 
	 * Converts any other simple value to long number
	 * 
	 * @param ve - value expression
	 * @return long or null
	 */
	
	Long getLong(MessageValueExpression ve);
	
	/**
	 * Gets double number value
	 * 
	 * Converts any other simple value to double number
	 * 
	 * @param ve - value expression
	 * @return double or null
	 */
	
	Double getDouble(MessageValueExpression ve);
	
	/**
	 * Gets string value
	 * 
	 * Converts any other simple value to string
	 * 
	 * @param ve - value expression
	 * @return message string or null
	 */
	
	MessageString getString(MessageValueExpression ve);
	
	/**
	 * Gets string value
	 * 
	 * Converts any other simple value to string
	 * 
	 * @param ve - value expression
	 * @return string or null
	 */
	
	String getStringUtf8(MessageValueExpression ve);
	
	/**
	 * Gets bytes string value
	 * 
	 * Converts any other simple value to string
	 * 
	 * @param ve - value expression
	 * @param copy - copy bytes or not
	 * @return bytes string or null
	 */
	
	byte[] getBytes(MessageValueExpression ve, boolean copy);
	
	/**
	 * Puts value to the table
	 * 
	 * @param key - could be String or stringfy Integer
	 * @param value - new value
	 * @return old value or null
	 */
	
	MessageValue<?> put(String key, MessageValue<?> value);
	
	/**
	 * Puts stringify value to the table with auto-detection type
	 * 
	 * @param key - could be String or stringfy Integer
	 * @param stringifyValue - new value in string format
	 * @return old value or null
	 */
	
	MessageValue<?> put(String key, String stringifyValue);
	
	/**
	 * Puts boolean value to the table
	 * 
	 * @param key - could be String or stringfy Integer
	 * @param value - new boolean value
	 * @return old value or null
	 */
	
	MessageValue<?> putBoolean(String key, boolean value);
	
	/**
	 * Puts long value to the table
	 * 
	 * @param key - could be String or stringfy Integer
	 * @param value - new long value
	 * @return old value or null
	 */
	
	MessageValue<?> putLong(String key, long value);
	
	/**
	 * Puts double value to the table
	 * 
	 * @param key - could be String or stringfy Integer
	 * @param value - new double value
	 * @return old value or null
	 */
	
	MessageValue<?> putDouble(String key, double value);
	
	/**
	 * Puts string value to the table
	 * 
	 * @param key - could be String or stringfy Integer
	 * @param value - new string value
	 * @return old value or null
	 */
	
	MessageValue<?> putString(String key, String value);
	
	/**
	 * Puts bytes value to the table
	 * 
	 * @param key - could be String or stringfy Integer
	 * @param value - new bytes value
	 * @param copy - copy bytes if needed
	 * @return old value or null
	 */
	
	MessageValue<?> putBytes(String key, byte[] value, boolean copy);
	
	/**
	 * Puts value to the table
	 * 
	 * @param key - int keys
	 * @param value - new value
	 * @return old value or null
	 */
	
	MessageValue<?> put(Integer key, MessageValue<?> value);
	
	/**
	 * Puts stringify value to the table with auto-detection type
	 * 
	 * @param key - int keys
	 * @param stringifyValue - new value in string formats
	 * @return old value or null
	 */
	
	MessageValue<?> put(Integer key, String stringifyValue);
	
	/**
	 * Puts boolean value to the table
	 * 
	 * @param key - int keys
	 * @param value - new boolean value
	 * @return old value or null
	 */
	
	MessageValue<?> putBoolean(Integer key, boolean value);
	
	/**
	 * Puts long value to the table
	 * 
	 * @param key - int keys
	 * @param value - new long value
	 * @return old value or null
	 */
	
	MessageValue<?> putLong(Integer key, long value);
	
	/**
	 * Puts double value to the table
	 * 
	 * @param key - int keys
	 * @param value - new double value
	 * @return old value or null
	 */
	
	MessageValue<?> putDouble(Integer key, double value);
	
	/**
	 * Puts string value to the table
	 * 
	 * @param key - int keys
	 * @param value - new string value
	 * @return old value or null
	 */
	
	MessageValue<?> putString(Integer key, String value);
	
	/**
	 * Puts bytes value to the table
	 * 
	 * @param key - int keys
	 * @param value - new bytes value
	 * @param copy - copy if needed
	 * @return old value or null
	 */
	
	MessageValue<?> putBytes(Integer key, byte[] value, boolean copy);
	
	/**
	 * Puts value to the table
	 * 
	 * @param ve - value expression
	 * @param value - new value
	 * @return old value or null
	 */
	
	MessageValue<?> put(MessageValueExpression ve, MessageValue<?> value);
	
	/**
	 * Puts stringfy value to the table with auto-detection type
	 * 
	 * @param ve - value expression
	 * @param stringfyValue - new value in string format
	 * @return old value or null
	 */
	
	MessageValue<?> put(MessageValueExpression ve, String stringfyValue);	
	
	/**
	 * Puts boolean value to the table
	 * 
	 * @param ve - value expression
	 * @param value - new boolean value
	 * @return old value or null
	 */
	
	MessageValue<?> putBoolean(MessageValueExpression ve, boolean value);
	
	/**
	 * Puts long value to the table
	 * 
	 * @param ve - value expression
	 * @param value - new long value
	 * @return old value or null
	 */
	
	MessageValue<?> putLong(MessageValueExpression ve, long value);
	
	/**
	 * Puts double value to the table
	 * 
	 * @param ve - value expression
	 * @param value - new double value
	 * @return old value or null
	 */
	
	MessageValue<?> putDouble(MessageValueExpression ve, double value);
	
	/**
	 * Puts string value to the table
	 * 
	 * @param ve - value expression
	 * @param value - new string value
	 * @return old value or null
	 */
	
	MessageValue<?> putString(MessageValueExpression ve, String value);
	
	/**
	 * Puts bytes value to the table
	 * 
	 * @param ve - value expression
	 * @param value - new bytes value
	 * @param copy - copy if needed
	 * @return old value or null
	 */
	
	MessageValue<?> putBytes(MessageValueExpression ve, byte[] value, boolean copy);
	
	/**
	 * Removes value from the table
	 * 
	 * @param key - could be String or stringfy Integer
	 * @return old value or null
	 */
	
	MessageValue<?> remove(String key);
	
	/**
	 * Removes value from the table
	 * 
	 * @param key - int keys
	 * @return old value or null
	 */
	
	MessageValue<?> remove(Integer key);
	
	/**
	 * Removes value from the table
	 * 
	 * @param ve - value expression
	 * @return old value or null
	 */
	
	MessageValue<?> remove(MessageValueExpression ve);
	
	/**
	 * Gets all keys in the table
	 * 
	 * @return set of keys
	 */
	
	Set<String> keySet();
	
	/**
	 * Gets all keys sorted by ascending order
	 * 
	 * @return not null sorted integer keys
	 */
	
	List<Integer> intKeys();

	/**
	 * Gets minimum integer key in table
	 * 
	 * @return min int key or null of not found
	 */
	
	Integer minIntKey();
	
	/**
	 * Gets maximum integer key in table
	 * 
	 * @return max int key or null of not found
	 */
	
	Integer maxIntKey();
	
	/**
	 * Gets size of the table
	 * 
	 * @return size of the table
	 */
	
	int size();
	
	/**
	 * Clear table
	 */
	
	void clear();
	
}

