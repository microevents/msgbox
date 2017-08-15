package microevents.msgbox.impl;

import java.io.IOException;

import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.buffer.ArrayBufferOutput;

import microevents.msgbox.MessageValue;
import microevents.msgbox.support.MessageException;
import microevents.msgbox.util.MessageStringifyUtil;

/**
 * AbstractMessageValueImpl
 * 
 * @author Alex Shvid
 *
 * @param <T> - Msg type
 */

public abstract class AbstractMessageValueImpl<T> implements MessageValue<T> {

	@Override
	public byte[] toByteArray() {

		ArrayBufferOutput out = new ArrayBufferOutput();
		try {
			MessagePacker packer = MessagePack.newDefaultPacker(out);
			writeTo(packer);
			packer.flush();
		} catch (IOException e) {
			throw new MessageException("IOException happened during serialization to byte array", e);
		}

		return out.toByteArray();
	}

	@Override
	public String toHexString() {
		return MessageStringifyUtil.toHex(toByteArray());
	}

	@Override
	public String toJson() {
		return toValue().toJson();
	}

}
