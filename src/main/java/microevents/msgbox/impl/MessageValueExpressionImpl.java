package microevents.msgbox.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import microevents.msgbox.MessageValueExpression;
import microevents.msgbox.util.MessageStringifyUtil;
import microevents.msgbox.util.MessageStringifyUtil.NumberType;

/**
 * MsgValueExpressionImpl
 * 
 * @author Alex Shvid
 *
 */

public final class MessageValueExpressionImpl implements MessageValueExpression {

	private final List<String> path = new ArrayList<String>();

	public MessageValueExpressionImpl(String valueExpression) {
		
		if (valueExpression == null) {
			throw new IllegalArgumentException("empty valueExpression");
		}

		StringTokenizer tokenizer = new StringTokenizer(valueExpression, ".[]");
		
		while(tokenizer.hasMoreTokens()) {
			
			String token = tokenizer.nextToken().trim();
			
			if (!token.isEmpty()) {
				path.add(token);
			}
			
		}
		
	}

	public boolean isEmpty() {
		return path.isEmpty();
	}
	
	public int size() {
		return path.size();
	}
	
	public String get(int i) {
		return path.get(i);
	}
	
	public List<String> getPath() {
		return Collections.unmodifiableList(path);
	}

	public String asString() {
		StringBuilder str = new StringBuilder();
		for (String element : path) {
			NumberType numberType = MessageStringifyUtil.detectNumber(element);
			if (numberType == NumberType.LONG) {
				str.append("[").append(element).append("]");
			}
			else {
				if (str.length() > 0) {
					str.append(".");
				}
				str.append(element);
			}
		}
		return str.toString();
	}
	
	@Override
	public String toString() {
		return asString();
	}
	
}
