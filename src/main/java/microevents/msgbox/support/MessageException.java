package microevents.msgbox.support;

/**
 * MessageException - is the root exception of the library
 * 
 * @author Alex Shvid
 *
 */

public class MessageException extends RuntimeException {

	private static final long serialVersionUID = -387649865196967964L;

	public MessageException(String msg) {
    super(msg);
  }
  
  public MessageException(String msg, Throwable t) {
    super(msg, t);
  } 
  
}
