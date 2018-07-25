package org.bakasoft.framboyan.json;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bakasoft.framboyan.util.JSON;

public class JsonReader {
	
	private static final String TRUE = "true";
	private static final String FALSE = "false";
	private static final String NULL = "null";
	
	private final JsonTape tape;
	
	public JsonReader(String json) {
		this(new StringReader(json));
	}
	
	public JsonReader(Reader reader) {
		this.tape = new JsonTape(reader);
	}
	
	public void reset() {
		tape.reset();
	}
	
	public Map<String, Object> readMap() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		
		int c = tape.pop();
		
		if (c == '{') {
			while(tape.alive()) {
				tape.skipWhitespace();
				
				if (tape.peek() == '}') {
					break;
				}
				
				String name = readString();
				
				tape.skipWhitespace();
				
				c = tape.pop();
				
				if (c != ':') {
					throw tape.error(msg(':', c));
				}
				
				Object value = readValue();
				
				map.put(name, value);
				
				tape.skipWhitespace();
				
				c = tape.peek();
				
				if (c == ',') {
					tape.pop();
				}
				else {
					break;
				}
			}
			
			c = tape.pop();
			
			if (c != '}') {
				throw tape.error(msg(',', '}', c));
			}
		}
		else {
			throw tape.error(msg('{', c));
		}
		
		return map;
	}
	
	public List<Object> readList() {
		ArrayList<Object> list = new ArrayList<>();
		
		int c = tape.pop();
		
		if (c == '[') {
			while(tape.alive()) {
				tape.skipWhitespace();
				
				if (tape.peek() == ']') {
					break;
				}
				
				Object item = readValue();
				
				list.add(item);
				
				tape.skipWhitespace();
				
				if (tape.peek() == ',') {
					tape.pop();
				}
				else {
					break;
				}
			}
			
			c = tape.pop();
			
			if (c != ']') {
				throw tape.error(msg(',', ']', c));
			}
		}
		else {
			throw tape.error(msg('[', c));
		}
		
		return list;
	}
	
	public String readString() {
		StringBuilder string = new StringBuilder();
		
		int c = tape.pop();
		
		if (c == '"') {
			while((c = tape.pop()) != '"') {
				if (Character.isISOControl(c)) {
					break;
				}
				else if (c == '\\') {
					c = tape.pop();
					
					switch(c) {
					case '"':
					case '\\':
					case '/':
						string.append((char)c);
						break;
					case 'b':
						string.append('\b');
						break;
					case 'f':
						string.append('\f');
						break;
					case 'n':
						string.append('\n');
						break;
					case 'r':
						string.append('\r');
						break;
					case 't':
						string.append('\t');
						break;
					case 'u':
						String hex = tape.pop(4);
						try {
							string.append((char)Integer.parseInt(hex, 16));	
						}
						catch (NumberFormatException e) {
							throw tape.error(msg("a valid hexadecimal string", hex), e);
						}
						
						break;
					default:
						throw tape.error(msg("a valid char to be escaped", c));
					}
				}
				else {
					string.append((char)c);
				}
			}	
		}
		else {
			throw tape.error(msg('"', c));
		}
		
		return string.toString();
	}
	
	public boolean readBoolean() {
		int c = tape.peek();
		
		if (c == 't') {
			return readTrue();
		}
		else if (c == 'f') {
			return readFalse();
		}
		else {
			throw tape.error(msg('t', 'f', c));
		}
	}

	public boolean readTrue() {
		String literal = tape.pop(4);
		
		if (!TRUE.equals(literal)) {
			throw tape.error(msg(TRUE, literal));
		}
		
		return true;
	}
	
	public boolean readFalse() {
		String literal = tape.pop(5);
		
		if (!FALSE.equals(literal)) {
			throw tape.error(msg(FALSE, literal));
		}
		
		return false;
	}
	
	public Object readNull() {
		String literal = tape.pop(4);
		
		if (!NULL.equals(literal)) {
			throw tape.error(msg(NULL, literal));
		}
		
		return null;
	}
	
	public BigDecimal readNumber() {
		StringBuilder number = new StringBuilder();
		
		int c = tape.pop();
		
		if (c == '-') {
			number.append((char)c);
			
			c = tape.pop();
		}
		
		if (c == '0') {
			number.append((char)c);
			
			c = tape.peek();
		}
		else if(c >= '1' && c <= '9') {
			number.append((char)c);
			
			c = tape.peek();
			
			while(c >= '0' && c <= '9') {
				c = tape.pop();
				
				number.append((char)c);	
				
				c = tape.peek();
			}
		}
		else {
			throw tape.error(msg("a digit", c));
		}
		
		if (c == '.') {
			c = tape.pop();
			
			number.append((char)c);
			
			c = tape.pop();
			
			if(c >= '0' && c <= '9') {
				number.append((char)c);
				
				c = tape.peek();
				
				while(c >= '0' && c <= '9') {
					c = tape.pop();
					
					number.append((char)c);	
					
					c = tape.peek();
				}
			}
			else {
				throw tape.error(msg("a digit", c));
			}
		}
		
		if (c == 'e' || c == 'E') {
			c = tape.pop();
			
			number.append((char)c);

			c = tape.pop();
			
			if (c == '+' || c == '-') {
				number.append((char)c);
				
				c = tape.pop();
			}
			
			if(c >= '0' && c <= '9') {
				number.append((char)c);
				
				c = tape.peek();
				
				while(c >= '0' && c <= '9') {
					c = tape.pop();
					
					number.append((char)c);	
					
					c = tape.peek();
				}
			}
			else {
				throw tape.error(msg("a digit or sign", c));
			}
		}
		
		return new BigDecimal(number.toString());
	}
	
	public Object readValue() {
		tape.skipWhitespace();
		
		int c = tape.peek();
		
		if (c == '{') {
			return readMap();
		}
		else if (c == '[') {
			return readList();
		}
		else if (c == '"') {
			return readString();
		}
		else if (c == 't') {
			return readTrue();
		}
		else if (c == 'f') {
			return readFalse();
		}
		else if (c == 'n') {
			return readNull();
		}
		else if (c >= '0' && c <= '9' || c == '-') {
			return readNumber();
		}
		else {
			throw tape.error(msg("JSON value", c));
		}
	}

	private static String msg(Object expected1, Object expected2, Object actual) {
		return String.format("Expected %s or %s instead of %s", 
				str(expected1), 
				str(expected2), 
				str(actual));
	}
	
	private static String msg(Object expected, Object actual) {		
		return String.format("Expected %s instead of %s", 
				str(expected),
				str(actual));
	}

	private static Object str(Object actual) {
		if (actual instanceof String) {
			return (String)actual;
		}
		else if (actual instanceof Integer) {
			int actualInt = ((Integer)actual).intValue();
			
			if (actualInt == -1) {
				return "end of stream";
			}
			else {
				return JSON.stringify((char)actualInt);
			}
		}

		return JSON.stringify(actual);
	}
	
}
