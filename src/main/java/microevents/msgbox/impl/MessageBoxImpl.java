package microevents.msgbox.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.msgpack.core.MessageFormat;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.core.buffer.ArrayBufferOutput;
import org.msgpack.value.Value;
import org.msgpack.value.impl.ImmutableBinaryValueImpl;
import org.msgpack.value.impl.ImmutableMapValueImpl;
import org.msgpack.value.impl.ImmutableStringValueImpl;

import microevents.msgbox.MessageBox;
import microevents.msgbox.MessageConstants;
import microevents.msgbox.support.MessageBoxException;

/**
 * MessageBoxImpl
 * 
 * Default implementation of the MessageBox
 * 
 * @author Alex Shvid
 *
 */

public class MessageBoxImpl implements MessageBox {

	private final Map<String, String> header = new HashMap<String, String>();
	private final Map<String, byte[]> body = new HashMap<String, byte[]>();
	
	public MessageBoxImpl() {
	}

	public MessageBoxImpl(byte[] buffer) {
		
		if (buffer == null) {
			throw new IllegalArgumentException("null buffer");
		}
		
		MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(buffer);
		try {
			parse(unpacker);
		} catch (IOException e) {
			throw new MessageBoxException("unexpected IOException", e);
		}
	}
	
	public MessageBoxImpl(byte[] buffer, int offset, int length) {
		
		if (buffer == null) {
			throw new IllegalArgumentException("null buffer");
		}
		
		MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(buffer, offset, length);
		try {
			parse(unpacker);
		} catch (IOException e) {
			throw new MessageBoxException("unexpected IOException", e);
		}		
	}
	
	public MessageBoxImpl(ByteBuffer buffer) {
		
		if (buffer == null) {
			throw new IllegalArgumentException("null buffer");
		}
		
		MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(buffer);
		try {
			parse(unpacker);
		} catch (IOException e) {
			throw new MessageBoxException("unexpected IOException", e);
		}
	}
	
	private void parse(MessageUnpacker unpacker) throws IOException {
		
		if(!unpacker.hasNext()) { 
			return;
		}
		
		MessageFormat format = unpacker.getNextFormat();

		if (isNull(format)) {
			unpacker.unpackNil();
			return;
		}
		
		if (!isMap(format)) {
			throw new IOException("expected Map in message pack format");
		}
		
    int size = unpacker.unpackMapHeader();
    
    for (int i = 0; i != size; ++i) {
    	
    	String key = unpacker.unpackString();
    	
    	if (MessageConstants.HEADER_KEY.equals(key)) {
    		parseHeader(unpacker);
    	}
    	else if (MessageConstants.BODY_KEY.equals(key)) {
    		parseBody(unpacker);
    	}
    	else {
    		throw new IOException("unexpected key in the map: " + key);
    	}
    	
    }
		
	}
	
	private void parseHeader(MessageUnpacker unpacker) throws IOException {
		
		if(!unpacker.hasNext()) { 
			return;
		}
		
		MessageFormat format = unpacker.getNextFormat();

		if (isNull(format)) {
			unpacker.unpackNil();
			return;
		}
		
		if (!isMap(format)) {
			throw new MessageBoxException("expected Map in message pack format");
		}
		
    int size = unpacker.unpackMapHeader();
		
    for (int i = 0; i != size; ++i) {
    	
     	String key = unpacker.unpackString();
     	String value = unpacker.unpackString();
     	
     	header.put(key, value);
     	
    }
		
	}
	
	private void parseBody(MessageUnpacker unpacker) throws IOException {
		
		if(!unpacker.hasNext()) { 
			return;
		}
		
		MessageFormat format = unpacker.getNextFormat();

		if (isNull(format)) {
			unpacker.unpackNil();
			return;
		}
		
		if (!isMap(format)) {
			throw new MessageBoxException("expected Map in message pack format");
		}
		
    int size = unpacker.unpackMapHeader();
		
    for (int i = 0; i != size; ++i) {
    	
     	String key = unpacker.unpackString();
     	
     	int length = unpacker.unpackBinaryHeader();
     	byte[] value = new byte[length];
     	unpacker.readPayload(value);
     	
     	body.put(key, value);
     	
    }
		
	}
	
	private static boolean isNull(MessageFormat format) {
		return MessageFormat.NIL == format;
	}

	private static boolean isMap(MessageFormat format) {

		switch (format) {

		case FIXMAP:
		case MAP16:
		case MAP32:
			return true;

		default:
			return false;

		}

	}
	
	@Override
	public boolean isEmpty() {
		return header.isEmpty() && body.isEmpty();
	}

	@Override
	public MessageBox addHeader(String key, String value) {
		if (value != null) {
			header.put(key, value);
		}
		else {
			header.remove(key);
		}
		return this;
	}
	
	@Override
	public String getHeader(String key) {
		return header.get(key);
	}

	@Override
	public Set<String> getHeaderKeys() {
		return header.keySet();
	}

	@Override
	public MessageBox addPayload(String key, byte[] payload, boolean copy) {
		if (payload != null) {
			body.put(key, copy ? Arrays.copyOf(payload, payload.length) : payload);
		}
		else {
			body.remove(key);
		}
		return this;
	}
	
	@Override
	public MessageBox addPayloadUtf8(String key, String payload) {
		if (payload != null) {
			body.put(key, payload.getBytes(StandardCharsets.UTF_8));
		}
		else {
			body.remove(key);
		}
		return this;
	}

	@Override
	public byte[] getPayload(String key, boolean copy) {
		byte[] payload =  body.get(key);
		return copy && payload != null ? Arrays.copyOf(payload, payload.length) : payload;
	}

	@Override
	public String getPayloadUtf8(String key) {
		byte[] payload = body.get(key);
		if (payload != null) {
			return new String(payload, StandardCharsets.UTF_8);
		}
		return null;
	}
	
	@Override
	public Set<String> getPayloadKeys() {
		return body.keySet();
	}

	@Override
	public String toJson() {
		return toValue().toJson();
	}

	@Override
	public byte[] toByteArray() {
		
		ArrayBufferOutput out = new ArrayBufferOutput();
		try {
			MessagePacker packer = MessagePack.newDefaultPacker(out);
			writeTo(packer);
			packer.flush();
		} catch (IOException e) {
			throw new MessageBoxException("IOException happened during serialization to byte array", e);
		}

		return out.toByteArray();
	}
	
	@Override
	public Value toValue() {
		
		int size = 0;
		if (!header.isEmpty()) {
			size += 2;
		}
		if (!body.isEmpty()) {
			size += 2;
		}
		
		int index = 0;
    Value[] array = new Value[size];
		
    if (!header.isEmpty()) {
    	array[index++] = new ImmutableStringValueImpl(MessageConstants.HEADER_KEY);
    	array[index++] = toHeaderValue();
    }
    
    if (!body.isEmpty()) {
    	array[index++] = new ImmutableStringValueImpl(MessageConstants.BODY_KEY);
    	array[index++] = toBodyValue();
    }
    
    return new ImmutableMapValueImpl(array);
	}
	
	private Value toHeaderValue() {
		
		int index = 0;
    Value[] array = new Value[header.size()  * 2];
    
    for (Map.Entry<String, String> entry : header.entrySet()) {
    	
    	String key = entry.getKey();
    	String value = entry.getValue();

      array[index++] = new ImmutableStringValueImpl(key);
      array[index++] = new ImmutableStringValueImpl(value);
    	
    }
    
    return new ImmutableMapValueImpl(array);
	}
	
	private Value toBodyValue() {
		
		int index = 0;
    Value[] array = new Value[body.size()  * 2];
    
    for (Map.Entry<String, byte[]> entry : body.entrySet()) {
    	
    	String key = entry.getKey();
    	byte[] value = entry.getValue();
    	
      array[index++] = new ImmutableStringValueImpl(key);
      array[index++] = new ImmutableBinaryValueImpl(value);
    }
    
    return new ImmutableMapValueImpl(array);
	}
	
  private void writeTo(MessagePacker packer) throws IOException {
  	
  	int size = 0;
  	if (!header.isEmpty()) {
  		size++;
  	}
  	if (!body.isEmpty()) {
  		size++;
  	}
  	
    packer.packMapHeader(size);
    
    if (!header.isEmpty()) {
    	packer.packString(MessageConstants.HEADER_KEY);
    	writeHeaderTo(packer);
    }

    if (!body.isEmpty()) {
    	packer.packString(MessageConstants.BODY_KEY);
    	writeBodyTo(packer);
    }

  } 
  
  private void writeHeaderTo(MessagePacker packer) throws IOException {
  	
    int size = header.size();
    
    packer.packMapHeader(size);
    
    for (Map.Entry<String, String> entry : header.entrySet()) {
    	
    	String key = entry.getKey();
    	String value = entry.getValue();
    	
    	packer.packString(key);
    	packer.packString(value);
    	
    }
    
  } 
  
  private void writeBodyTo(MessagePacker packer) throws IOException {
  	
    int size = body.size();
    
    packer.packMapHeader(size);
    
    for (Map.Entry<String, byte[]> entry : body.entrySet()) {
    	
    	String key = entry.getKey();
    	byte[] value = entry.getValue();
    	
    	packer.packString(key);
    	packer.packBinaryHeader(value.length);
    	packer.writePayload(value);
    	
    }
    
  } 
	
}
