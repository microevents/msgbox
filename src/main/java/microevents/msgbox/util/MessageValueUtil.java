package microevents.msgbox.util;

import microevents.msgbox.MessageBoolean;
import microevents.msgbox.MessageNumber;
import microevents.msgbox.MessageString;
import microevents.msgbox.MessageTable;
import microevents.msgbox.MessageValue;
import microevents.msgbox.impl.MessageBooleanImpl;
import microevents.msgbox.impl.MessageNumberImpl;
import microevents.msgbox.impl.MessageStringImpl;

/**
 * Base util class for explicit conversion from one simple type to another
 * 
 * This is the central point of all explicit conversions
 * 
 * @author Alex Shvid
 *
 */

public final class MessageValueUtil {

	private MessageValueUtil() {
	}
	
	/**
	 * Casts Msg value to Msg table if possible
	 * 
	 * Does not do type conversion, if Msg value is not a table it will return null
	 * 
	 * @param value - Msg value with unknown type or null
	 * @return Msg boolean or null
	 */
	
	public static MessageTable toTable(MessageValue<?> value) {
		if (value != null && value instanceof MessageTable) {
			return (MessageTable) value;
		}
		return null;
	}

	/**
	 * Converts Msg value to Msg boolean if possible and if needed
	 * 
	 * @param value - Msg value with unknown type or null
	 * @return Msg boolean or null
	 */
	
	public static MessageBoolean toBoolean(MessageValue<?> value) {
		if (value != null) {
			if (value instanceof MessageBoolean) {
				return (MessageBoolean) value;
			}
			else {
				return new MessageBooleanImpl(value.asString());
			}
		}
		return null;
	}
	
	/**
	 * Converts Msg value to Msg number if possible and if needed
	 * 
	 * @param value - Msg value with unknown type or null
	 * @return Msg number or null
	 */
	
	public static MessageNumber toNumber(MessageValue<?> value) {
		if (value != null) {
			if (value instanceof MessageNumber) {
				return (MessageNumber) value;
			}
			else {
				return new MessageNumberImpl(value.asString());
			}
		}
		return null;
	}
	
	/**
	 * Converts Msg value to Msg string if possible and if needed
	 * 
	 * @param value - Msg value with unknown type or null
	 * @return Msg string or null
	 */
	
	public static MessageString toString(MessageValue<?> value) {
		if (value != null) {
			if (value instanceof MessageString) {
				return (MessageString) value;
			}
			else {
				return new MessageStringImpl(value.asString());
			}
		}
		return null;
	}
	
}
