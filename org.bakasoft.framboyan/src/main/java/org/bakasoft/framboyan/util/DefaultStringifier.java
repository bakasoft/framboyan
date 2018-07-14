package org.bakasoft.framboyan.util;

public class DefaultStringifier implements Stringifier {

	@Override
	public String toString(Object value) {
		return String.valueOf(value);
	}

}
