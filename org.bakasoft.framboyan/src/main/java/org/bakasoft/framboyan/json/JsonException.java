package org.bakasoft.framboyan.json;

public class JsonException extends RuntimeException {

	private static final long serialVersionUID = 9057332739674022533L;

	public JsonException(String message, int line, int column) {
		this(message, column, line, null);
	}

	public JsonException(String message, int column, int line, Exception cause) {
		super(String.format("%s (line %s, column %s)", 
				message, 
				line, 
				column)
			, cause);
	}
	
}
