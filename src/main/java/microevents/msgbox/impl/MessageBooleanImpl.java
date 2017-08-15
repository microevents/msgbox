package microevents.msgbox.impl;

import java.io.IOException;

import org.msgpack.core.MessagePacker;
import org.msgpack.value.Value;
import org.msgpack.value.impl.ImmutableBooleanValueImpl;

import microevents.msgbox.MessageBoolean;

/**
 * MsgBooleanImpl immutable implementation
 * 
 * @author Alex Shvid
 *
 */

public final class MessageBooleanImpl extends AbstractMessageValueImpl<MessageBoolean> implements MessageBoolean {

	private final boolean booleanValue;
	
	public MessageBooleanImpl(boolean value) {
		this.booleanValue = value;
	}
	
	public MessageBooleanImpl(String value) {
		this.booleanValue = Boolean.parseBoolean(value);
	}
	
	@Override
	public String asString() {
		return Boolean.toString(booleanValue);
	}

	@Override
	public boolean asBoolean() {
		return booleanValue;
	}

	@Override
  public Value toValue() {
    return booleanValue ? ImmutableBooleanValueImpl.TRUE : ImmutableBooleanValueImpl.FALSE;
  }
	
  @Override
	public void writeTo(MessagePacker packer) throws IOException {
  	packer.packBoolean(booleanValue);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (booleanValue ? 1231 : 1237);
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
		MessageBooleanImpl other = (MessageBooleanImpl) obj;
		if (booleanValue != other.booleanValue)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MsgBooleanImpl [booleanValue=" + booleanValue + "]";
	}


}
