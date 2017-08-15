package microevents.msgbox;

import java.io.IOException;

import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.buffer.ArrayBufferOutput;
import org.msgpack.value.Value;

import microevents.msgbox.support.MessageException;
import microevents.msgbox.util.MessageStringifyUtil;

/**
 * AbstractMessageTest
 * 
 * @author Alex Shvid
 *
 */

public class AbstractMessageTest {

	public byte[] toByteArray(Value value) {

		ArrayBufferOutput out = new ArrayBufferOutput();
		try {
			MessagePacker packer = MessagePack.newDefaultPacker(out);
			value.writeTo(packer);
			packer.flush();
		} catch (IOException e) {
			throw new MessageException("IOException happened during serialization to byte array", e);
		}

		return out.toByteArray();

	}

	public String toHexString(MessageValue<?> value) {
		return MessageStringifyUtil.toHex(toByteArray(value.toValue()));
	}
	
}
