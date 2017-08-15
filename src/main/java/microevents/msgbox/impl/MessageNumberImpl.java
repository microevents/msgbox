package microevents.msgbox.impl;

import java.io.IOException;

import org.msgpack.core.MessagePacker;
import org.msgpack.value.Value;
import org.msgpack.value.impl.ImmutableDoubleValueImpl;
import org.msgpack.value.impl.ImmutableLongValueImpl;

import microevents.msgbox.MessageNumber;
import microevents.msgbox.MessageNumberType;
import microevents.msgbox.support.MessageException;
import microevents.msgbox.support.MessageNumberFormatException;
import microevents.msgbox.util.MessageStringifyUtil;
import microevents.msgbox.util.MessageStringifyUtil.NumberType;

/**
 * 
 * MessageNumber immutable implementation
 * 
 * @author Alex Shvid
 *
 */

public final class MessageNumberImpl extends AbstractMessageValueImpl<MessageNumber> implements MessageNumber {

	private final long longValue;
	private final double doubleValue;
	private final MessageNumberType type;

	public MessageNumberImpl(long value) {
		this.type = MessageNumberType.LONG;
		this.longValue = value;
		this.doubleValue = 0.0;
	}
	
	public MessageNumberImpl(double value) {
		this.type = MessageNumberType.DOUBLE;
		this.longValue = 0L;
		this.doubleValue = value;
	}

	public MessageNumberImpl(String stringValue) {
		
		if (stringValue == null) {
			throw new IllegalArgumentException("empty value");
		}
		
		NumberType numberType = MessageStringifyUtil.detectNumber(stringValue);
		
		switch(numberType) {
		case LONG:
			try {
				this.type = MessageNumberType.LONG;
				this.longValue = Long.parseLong(stringValue);
				this.doubleValue = 0.0;
			}
			catch(NumberFormatException e) {
				throw new MessageNumberFormatException(stringValue, e);
			}				
			break;
		case DOUBLE:
			try {
				this.type = MessageNumberType.DOUBLE;
				this.longValue = 0L;
				this.doubleValue = Double.parseDouble(stringValue);
			}
			catch(NumberFormatException e) {
				throw new MessageNumberFormatException(stringValue, e);
			}			
			break;
		case NAN:
		default:
			throw new MessageNumberFormatException(stringValue);
		}
		
	}
	
	@Override
	public MessageNumberType getType() {
		return type;
	}

	@Override
	public MessageNumber add(MessageNumber otherNumber) {
		switch(otherNumber.getType()) {
		case LONG:
			return add(otherNumber.asLong());
		case DOUBLE:
			return add(otherNumber.asDouble());
		}
		throw new MessageException("unexpected type: " + otherNumber.getType());
	}
	
	@Override
	public MessageNumber add(long otherLongValue) {
		switch(type) {
		case LONG:
			return new MessageNumberImpl(longValue + otherLongValue);
		case DOUBLE:
			return new MessageNumberImpl(doubleValue + (double) otherLongValue);
		}
		throw new MessageException("unexpected type: " + type);
	}
	
	@Override
	public MessageNumber add(double otherDoubleValue) {
		switch(type) {
		case LONG:
			return new MessageNumberImpl((double) longValue + otherDoubleValue);
		case DOUBLE:
			return new MessageNumberImpl(doubleValue + otherDoubleValue);
		}
		throw new MessageException("unexpected type: " + type);
	}
	
	@Override
	public MessageNumber subtract(MessageNumber otherNumber) {
		switch(otherNumber.getType()) {
		case LONG:
			return subtract(otherNumber.asLong());
		case DOUBLE:
			return subtract(otherNumber.asDouble());
		}
		throw new MessageException("unexpected type: " + otherNumber.getType());
	}	

	@Override
	public MessageNumber subtract(long otherLongValue) {
		switch(type) {
		case LONG:
			return new MessageNumberImpl(longValue - otherLongValue);
		case DOUBLE:
			return new MessageNumberImpl(doubleValue - (double) otherLongValue);
		}
		throw new MessageException("unexpected type: " + type);
	}
	
	@Override
	public MessageNumber subtract(double otherDoubleValue) {
		switch(type) {
		case LONG:
			return new MessageNumberImpl((double) longValue - otherDoubleValue);
		case DOUBLE:
			return new MessageNumberImpl(doubleValue - otherDoubleValue);
		}
		throw new MessageException("unexpected type: " + type);
	}

	@Override
	public long asLong() {
		switch(type) {
		case LONG:
			return longValue;
		case DOUBLE:
			return (long) doubleValue;
		}
		throw new MessageException("unexpected type: " + type);
	}

	@Override
	public double asDouble() {
		switch(type) {
		case LONG:
			return longValue;
		case DOUBLE:
			return doubleValue;
		}
		throw new MessageException("unexpected type: " + type);
	}

	@Override
	public String asString() {
		switch(type) {
		case LONG:
			return Long.toString(longValue);
		case DOUBLE:
			return Double.toString(doubleValue);
		}
		throw new MessageException("unexpected type: " + type);
	}
	
  @Override
  public Value toValue() {
		switch(type) {
		case LONG:
			return new ImmutableLongValueImpl(longValue);  
		case DOUBLE:
	    return new ImmutableDoubleValueImpl(doubleValue);  
		}	
		throw new MessageException("unexpected type: " + type);
  }
  
  @Override
	public void writeTo(MessagePacker packer) throws IOException {
		switch(type) {
		case LONG:
			packer.packLong(longValue);
			break;
		case DOUBLE:
			packer.packDouble(doubleValue);
			break;
		default:
		  throw new IOException("unexpected type: " + type);		
		}	
	}
  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (type == MessageNumberType.DOUBLE) {
			long temp;
			temp = Double.doubleToLongBits(doubleValue);
			result = prime * result + (int) (temp ^ (temp >>> 32));
		}
		else if (type == MessageNumberType.LONG) {
			result = prime * result + (int) (longValue ^ (longValue >>> 32));
		}
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageNumberImpl other = (MessageNumberImpl) obj;
		if (type != other.type)
			return false;
		if (type == MessageNumberType.DOUBLE && Double.doubleToLongBits(doubleValue) != Double.doubleToLongBits(other.doubleValue))
			return false;
		if (type == MessageNumberType.LONG && longValue != other.longValue)
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder();
		str.append("MsgNumberImpl [type=").append(type);
		if (type == MessageNumberType.LONG) {
			str.append(", longValue=").append(longValue);
		}
		else if (type == MessageNumberType.DOUBLE) {
			str.append(", doubleValue=").append(doubleValue);
		}
		str.append("]");
		return str.toString();
		
	}
	
}
