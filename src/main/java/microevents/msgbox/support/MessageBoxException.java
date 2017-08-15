package microevents.msgbox.support;

/**
 * MessageBoxException - is the exception for MessageBox
 * 
 * @author Alex Shvid
 *
 */

public class MessageBoxException extends MessageException {

	private static final long serialVersionUID = -6681262602329832399L;

	public MessageBoxException(String msg) {
    super(msg);
  }
  
  public MessageBoxException(String msg, Throwable t) {
    super(msg, t);
  } 
  
}
