package org.bakasoft.framboyan.util;

import java.util.List;
import java.util.regex.Pattern;

public class Inspector {

	private static final int MAX_LENGTH = 50;
	
	public static String hash(Object obj) {
		int hash = obj != null ? obj.hashCode() : 0;
		
		return String.format("%08X", hash & 0xFFFFFFFFL);
	}
	
	public static String typeFull(Object obj) {
		if (obj == null) {
			return "null";
		}
		
		Class<?> clazz = obj.getClass();
		return clazz.getName();
	}
	
	public static String type(Object obj) {
		if (obj == null) {
			return "null";
		}
		
		Class<?> clazz = obj.getClass();
		return clazz.getSimpleName();
	}
	
	public static String escape(char c) {
		switch(c) {
		case '\r': return "\\r";
		case '\n': return "\\n";
		case '\t': return "\\t";
		case '\f': return "\\f";
		case '\b': return "\\b";
		case '\\': return "\\\\";
		case '\"': return "\\\"";
		case '\'': return "\\\'";
		// TODO add more cases
		default: 
			return String.valueOf(c);
		}
	}
	
	public static String escape(String str) {
		StringBuilder builder = new StringBuilder();
		
		for (char c : str.toCharArray()) {
			builder.append(escape(c));
		}
		
		return builder.toString();
	}
	
	public static String inspect(Object value) {
		if (value == null) {
			return "null";
		}
		else if (value instanceof String) {
			String str = (String)value;

			if (str.length() > MAX_LENGTH) {
				str = str.substring(0, MAX_LENGTH).trim();
			
				return "\"" + escape(str) + "...";
			}

			return "\"" + escape(str) + "\"";
		}
		else if (value instanceof Character) {
			return "'" + escape((Character)value) + "'";
		}
		else if (value instanceof Number) {
			return value.toString();
		}
		else if (value instanceof Boolean) {
			return value.toString();
		}
		else if (value instanceof List) {
			String className = value.getClass().getSimpleName();
			int size = ((List<?>)value).size();
			
			return className + "(" + size + ")";
		}
		else if (value instanceof Pattern) {
			return ((Pattern)value).pattern();
		}

		return type(value) + "@" + hash(value);
	}

	public static String generateDiff(String actual, String expected) {
		StringBuilder out = new StringBuilder();
		String lineBreak = System.lineSeparator();
		String[] actualLines = Strings.split(actual, '\n');
		String[] expectedLines = Strings.split(expected, '\n');
		int maxLength = Math.max(actualLines.length, expectedLines.length);
		
		// TODO: add line wrapping and special characters visualization
		
		for (int i = 0; i < maxLength; i++) {
			String actualLine = (i < actualLines.length ? actualLines[i] : null);
			String expectedLine = (i < expectedLines.length ? expectedLines[i] : null);
			
			if (actualLine != null && expectedLine == null) {
				out.append("+ " + actualLine + lineBreak);
			}
			else if (actualLine == null && expectedLine != null) {
				out.append("- " + expectedLine + lineBreak);
			}
			else if (actualLine.equals(expectedLine)) {
				out.append("  " + actualLine + lineBreak);
			}
			else {
				out.append("- " + expectedLine + lineBreak);
				out.append("+ " + actualLine + lineBreak);
			}
		}
		
		return out.toString();
	}
	
}
