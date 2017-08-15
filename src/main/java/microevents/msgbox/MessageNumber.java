package microevents.msgbox;

/**
 * Immutable Number interface
 * 
 * @author Alex Shvid
 *
 */

public interface MessageNumber extends MessageValue<MessageNumber> {

	/**
	 * Gets type of the Msg number
	 * 
	 * @return not null type
	 */
	
	MessageNumberType getType();
	
	/**
	 * Gets long, even if value is double, it will be converted to long
	 * 
	 * @return long value
	 */
	
	long asLong();
	
	/**
	 * Gets double, even if value is long, it will be converted to double
	 * 
	 * @return double value
	 */
	
	double asDouble();
	
	/**
	 * Add number
	 * 
	 * @param number - number to add
	 * @return new immutable Msg number
	 */
	
	MessageNumber add(MessageNumber otherNumber);
	MessageNumber add(long otherLongValue);
	MessageNumber add(double otherDoubleValue);

	/**
	 * Subtract number
	 * 
	 * @param number - number to add
	 * @return new immutable Msg number
	 */
	
	MessageNumber subtract(MessageNumber otherNumber);
	MessageNumber subtract(long otherLongValue);
	MessageNumber subtract(double otherDoubleValue);

}
