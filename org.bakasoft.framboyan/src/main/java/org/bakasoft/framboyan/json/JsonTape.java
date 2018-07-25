package org.bakasoft.framboyan.json;

import java.io.IOException;
import java.io.Reader;

class JsonTape {
	
	private final Reader reader;

	private boolean alive;
	private int line;
	private int column;
	private int buffer;

	public JsonTape(Reader reader) {
		this.reader = reader;
		
		reset();
	}
	
	public void reset() {
		line = 1;
		column = 0;
		buffer = -1;
		alive = true;
	}
	
	public boolean alive() {
		return alive;
	}
	
	public void flush() {
		buffer = -1;
	}
	
	public int pop() {
		int c;
		
		if (buffer != -1) {
			c = buffer;
		}
		else if (alive) {
			try {
				c = reader.read();
			} 
			catch (IOException e) {
				throw error("Interrupted stream", e);
			}
			
			if (c == -1) {
				alive = false;
			}
			else if (c == '\n') {
				line++;
				column = 0;
			}
			else {
				column++;
			}
		}
		else {
			c = -1;
		}
		
		flush();

		return c;
	}
	
	public int peek() {
		if (alive) {
			if (buffer == -1) {
				buffer = pop();	
			}
			
			return buffer;	
		}
		
		return -1;
	}

	public String pop(int length) {
		StringBuilder text = new StringBuilder();
		
		for (int i = 0; i < length; i++) {
			int c = pop();
			
			if (c == -1) {
				throw error("Unexpected end of stream");
			}
			
			text.append((char)c);
		}
		
		return text.toString();
	}
	
	public void skipWhitespace() {
		int c = peek();
		
		while(Character.isWhitespace(c)) {
			flush();
			
			c = peek();
		}
	}

	public JsonException error(String message) {
		return new JsonException(message, line, column);
	}

	public JsonException error(String message, Exception cause) {
		return new JsonException(message, line, column, cause);
	}

	
}
