package org.bakasoft.framboyan;

public class Debugger {

	public static final int DEFAULT_MAX_LENGTH = 25;
	
	public static String inspect(Object value) {
		return inspect(value, DEFAULT_MAX_LENGTH);
	}

	private static String inspect(Object value, int maxLength) {
		StringBuilder output = new StringBuilder();
		
		if (value == null) {
			output.append("null");
		}
		else if (value instanceof String) {
			output.append('"');
			output.append(value
					.toString()
					.replace("\t", "\\t")
					.replace("\r", "\\r")
					.replace("\n", "\\n")
					.replace("\"", "\\\""));
			output.append('"');
		}
		else {
			output.append(value.toString());
		}
		
		if (output.length() > maxLength) {
			output.setLength(maxLength - 1);
			output.append('…');
		}
		
		return output.toString();
	}
}
