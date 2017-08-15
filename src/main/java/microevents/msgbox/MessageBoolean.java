package microevents.msgbox;

/**
 * Immutable Boolean type 
 * 
 * @author Alex Shvid
 *
 */

public interface MessageBoolean extends MessageValue<MessageBoolean> {

	/**
	 * Gets boolean value
	 * 
	 * @return bool value
	 */
	
	boolean asBoolean();

	
}
