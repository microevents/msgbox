package microevents.msgbox;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.msgpack.core.MessageFormat;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import microevents.msgbox.impl.MessageBooleanImpl;
import microevents.msgbox.impl.MessageNumberImpl;
import microevents.msgbox.impl.MessageStringImpl;
import microevents.msgbox.impl.MessageTableImpl;
import microevents.msgbox.impl.MessageBoxImpl;
import microevents.msgbox.support.MessageException;
import microevents.msgbox.support.MessageNumberFormatException;
import microevents.msgbox.support.MessageParseException;
import microevents.msgbox.util.MessageStringifyUtil;
import microevents.msgbox.util.MessageStringifyUtil.NumberType;

/**
 * MessageFactory
 * 
 * @author Alex Shvid
 *
 */

public final class MessageFactory {

	private MessageFactory() {
	}
	
	/**
	 * Creates a new empty message box
	 * 
	 * @return not null instance
	 */
	
	public static final MessageBox newBox() {
		return new MessageBoxImpl();
	}
	
	/**
	 * Creates a message from serialized MsgPack blob
	 * 
	 * @param blob - input buffer
	 * 
	 * @return not null instance
	 */
	
	public static final MessageBox parseBox(byte[] blob) {
		return new MessageBoxImpl(blob);
	}
	
	/**
	 * 
	 * Creates a message from serialized MsgPack blob
	 * 
	 * @param buffer - input buffer
	 * @param offset - position in the buffer
	 * @param length - length of the byte array
	 * @return not null instance
	 */
	
	public static final MessageBox parseBox(byte[] buffer, int offset, int length) {
		return new MessageBoxImpl(buffer, offset, length);
	}
	
	/**
	 * Creates a message from serialized MsgPack blob
	 * 
	 * @param buffer - input buffer
	 * 
	 * @return not null instance
	 */
	
	public static final MessageBox parseBox(ByteBuffer buffer) {
		return new MessageBoxImpl(buffer);
	}
	
	/**
	 * Parse value from buffer
	 * 
	 * @param buffer - not null byte array
	 * @return message value
	 * @throws IOException
	 */

	@SuppressWarnings("unchecked")
	public static <T extends MessageValue<?>> T newTypedValue(byte[] buffer) {
		if (buffer == null) {
			throw new IllegalArgumentException("null buffer");
		}
		return (T) newValue(buffer, 0, buffer.length);
	}

	/**
	 * Parse value from buffer
	 * 
	 * @param buffer - not null byte array
	 * @return message value
	 * @throws IOException
	 */

	public static MessageValue<?> newValue(byte[] buffer) {
		if (buffer == null) {
			throw new IllegalArgumentException("null buffer");
		}
		return newValue(buffer, 0, buffer.length);
	}

	/**
	 * Parse value from buffer
	 * 
	 * @param buffer - not null byte array
	 * @param offset - offset in the array
	 * @param length - length of the payload
	 * @return message value
	 * @throws IOException
	 */

	public static MessageValue<?> newValue(byte[] buffer, int offset, int length) {
		if (buffer == null) {
			throw new IllegalArgumentException("null buffer");
		}
		MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(buffer, offset, length);
		try {
			return newValue(unpacker);
		} catch (IOException e) {
			throw new MessageException("unexpected IOException", e);
		}
	}

	/**
	 * Parse value from ByteBuffer
	 * 
	 * @param buffer - not null byte buffer
	 * @return message value
	 * @throws IOException
	 */

	public static MessageValue<?> newValue(ByteBuffer buffer) {
		if (buffer == null) {
			throw new IllegalArgumentException("null buffer");
		}
		MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(buffer);
		try {
			return newValue(unpacker);
		} catch (IOException e) {
			throw new MessageException("unexpected IOException", e);
		}
	}

	/**
	 * Parse value from message unpacker
	 * 
	 * @param unpacker - message unpacker
	 * @return Msg value or null
	 * @throws IOException
	 */

	public static MessageValue<?> newValue(MessageUnpacker unpacker) throws IOException {

		if (unpacker == null) {
			throw new IllegalArgumentException("null unpacker");
		}
		
		if(!unpacker.hasNext()) { 
			return null;
		}
		
		MessageFormat format = unpacker.getNextFormat();

		if (isNull(format)) {
			unpacker.unpackNil();
			return null;
		}

		else if (isArray(format)) {
			return newArray(unpacker);
		}

		else if (isMap(format)) {
			return newMap(unpacker);
		}

		else {
			return newSimpleValue(format, unpacker);
		}

	}

  private static MessageValue<?> newArray(MessageUnpacker unpacker) throws IOException {

  	MessageTableImpl table = new MessageTableImpl();
  	
    int arraySize = unpacker.unpackArrayHeader();
    if (arraySize == 0) {
      return table;
    }

    for (int i = 0; i != arraySize; ++i) {
    	
      MessageValue<?> value = newValue(unpacker);

      if (value != null) {
      	table.put(i, value);
      }
      
    }

    return table;
  }

  private static MessageValue<?> newMap(MessageUnpacker unpacker) throws IOException {

  	MessageTableImpl table = new MessageTableImpl();
  	
    int mapSize = unpacker.unpackMapHeader();
    if (mapSize == 0) {
      return table;
    }

    for (int i = 0; i != mapSize; ++i) {
    	
    	MessageValue<?> key = newValue(unpacker);
      MessageValue<?> value = newValue(unpacker);

      if (key != null && value != null) {
      	
      	if (key instanceof MessageNumber && table.getType() == MessageTableType.INT_KEY) {
      		MessageNumber number = (MessageNumber) key;
      		table.put((int) number.asLong(), value);
      	}
      	else {
      		table.put(key.asString(), value);
      	}
      }
      
    }

    return table;
  }

	private static MessageValue<?> newSimpleValue(MessageFormat format, MessageUnpacker unpacker) throws IOException {

		switch (format) {

		case BOOLEAN:
			return new MessageBooleanImpl(unpacker.unpackBoolean());

		case INT8:
		case INT16:
		case INT32:
		case INT64:
		case UINT8:
		case UINT16:
		case UINT32:
		case UINT64:
		case POSFIXINT:
		case NEGFIXINT:
			return new MessageNumberImpl(unpacker.unpackLong());

		case FLOAT32:
		case FLOAT64:
			return new MessageNumberImpl(unpacker.unpackDouble());

		case STR8:
		case STR16:
		case STR32:
		case FIXSTR:
			return new MessageStringImpl(unpacker.unpackString());

		case BIN8:
		case BIN16:
		case BIN32:
			return new MessageStringImpl(unpacker.readPayload(unpacker.unpackBinaryHeader()), false);

		default:
			unpacker.skipValue();
			return null;
		}

	}
	
	/**
	 * Parse stringify value primitive value
	 * 
	 * @param stringifyValue - value in string formats
	 * @return Msg value or null
	 */

	public static MessageValue<?> newStringifyValue(String stringifyValue) {

		if (stringifyValue == null) {
			return null;
		}

		if (stringifyValue.equalsIgnoreCase("true")) {
			return new MessageBooleanImpl(true);
		}

		if (stringifyValue.equalsIgnoreCase("false")) {
			return new MessageBooleanImpl(false);
		}

		NumberType type = MessageStringifyUtil.detectNumber(stringifyValue);

		switch (type) {

		case LONG:
			try {
				return new MessageNumberImpl(Long.parseLong(stringifyValue));
			} catch (NumberFormatException e) {
				throw new MessageNumberFormatException(stringifyValue, e);
			}

		case DOUBLE:
			try {
				return new MessageNumberImpl(Double.parseDouble(stringifyValue));
			} catch (NumberFormatException e) {
				throw new MessageNumberFormatException(stringifyValue, e);
			}

		case NAN:
			return new MessageStringImpl(stringifyValue);

		default:
			throw new MessageParseException("invalid type: " + type + ", for stringfy value: " + stringifyValue);
		}

	}

	public static boolean isArray(MessageFormat format) {

		switch (format) {

		case FIXARRAY:
		case ARRAY16:
		case ARRAY32:
			return true;

		default:
			return false;

		}

	}

	public static boolean isMap(MessageFormat format) {

		switch (format) {

		case FIXMAP:
		case MAP16:
		case MAP32:
			return true;

		default:
			return false;

		}

	}

	public static boolean isNull(MessageFormat format) {
		return MessageFormat.NIL == format;
	}

}
