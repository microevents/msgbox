package microevents.msgbox.support;

/**
 * MessageNumberFormatException - wrapped exception for wrong numbers
 * 
 * @author Alex Shvid
 *
 */

public class MessageNumberFormatException extends MessageException {

	private static final long serialVersionUID = -6636837527625149364L;

	public MessageNumberFormatException(String msg) {
    super(msg);
  }
  
  public MessageNumberFormatException(String msg, Throwable t) {
    super(msg, t);
  } 
  
}
