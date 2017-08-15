package microevents.msgbox.support;

/**
 * MessageParseException - wrapped exception for strings
 * 
 * @author Alex Shvid
 *
 */

public class MessageParseException extends MessageException {

	private static final long serialVersionUID = -3552348772637149118L;

	public MessageParseException(String msg) {
    super(msg);
  }
  
  public MessageParseException(String msg, Throwable t) {
    super(msg, t);
  } 
  
}
