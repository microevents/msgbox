package microevents.msgbox;

import java.util.Set;

import org.msgpack.value.Value;

/**
 * MessageBox
 * 
 * @author Alex Shvid
 *
 */

public interface MessageBox {

	/**
	 * Checks if message is empty
	 * 
	 * @return true if both header and body are empty
	 */
	
	boolean isEmpty();
	
	/**
	 * Adds header to the message
	 * 
	 * @param key - name of the header
	 * @param value - value of the header
	 * @return this
	 */
	
	MessageBox addHeader(String key, String value);
	
	/**
	 * Gets header
	 * 
	 * @param key - header key
	 * @return null or header value
	 */
	
  String getHeader(String key);
	
  /**
   * Gets all header keys from the message
   * 
   * @return not null set
   */
  
  Set<String> getHeaderKeys();
  
	/**
	 * Adds payload to the message
	 * 
	 * @param key - name of the payload
	 * @param payload - byte array
	 * @param copy - copy if needed
	 * @return this
	 */
	
	MessageBox addPayload(String key, byte[] payload, boolean copy);

	/**
	 * Adds payload as UTF-8 string
	 * 
	 * @param key - name of the payload
	 * @param payload - byte array
	 * @return this
	 */
	
	MessageBox addPayloadUtf8(String key, String payload);
	
	/**
	 * Gets payload
	 * 
	 * @param key - payload key
	 * @param copy - copy bytes if needed
	 * @return null or payload value
	 */
	
	byte[] getPayload(String key, boolean copy);

	/**
	 * Gets payload as UTF-8 string
	 * 
	 * @param key - payload key
	 * @return null or payload value
	 */
	
	String getPayloadUtf8(String key);
	
	/**
	 * Gets payload keys from the message
	 *  
	 * @return not null set 
	 */
	
	Set<String> getPayloadKeys();
	
	/**
	 * Converts message to json format
	 * 
	 * @return not null json
	 */
	
	String toJson();
	
	/**
	 * Serialize message to MsgPack
	 * 
	 * @return not null byte array
	 */
	
	byte[] toByteArray();
	
	/**
	 * Converts STEP message to MsgPack value
	 * 
	 * @return not null value object
	 */
	
	Value toValue();
	
}
