package org.bakasoft.framboyan.util;

@FunctionalInterface
public interface Stringifier {

	String toString(Object value);
	
	default String format(String format, Object... args) {
		Object[] strs = new Object[args != null ? args.length : 0];
		
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				strs[i] = toString(args[i]);
			}
		}
		
		return String.format(format, strs);
	}
	
	default String formatExpectation(Object subject, String message, Object value) {
		return "Expect: " + toString(subject) + System.lineSeparator() + message + ": " + toString(value);
	}
	
	default String formatExpectation(Object subject, String message) {
		return "Expect: " + toString(subject) + System.lineSeparator() + message;
	}
	
}
