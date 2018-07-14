package org.bakasoft.framboyan.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import org.bakasoft.framboyan.Action;

public class Toolbox {


	public static String string(Object actual) {
		throw new RuntimeException(); // TODO implement move to normalizer
	}
	
	public static BigDecimal number(Object expected) {
		throw new RuntimeException(); // TODO implement move to normalizer
	}

	
	public static Exception getException(Object actual) {
		Action action = Toolbox.toAction(actual);
		
		if (action != null) {
			try {
				action.run();
			}
			catch (Exception t) {
				return t;
			}	
		}
		
		return null;
	}
	
	public static Pattern pattern(Object value) {
		if (value instanceof Pattern) {
			return ((Pattern)value);
		}
		else if (value instanceof String) {
			return (Pattern.compile((String)value));
		}
		
		throw new RuntimeException("invalid pattern");
	}

	public static Action toAction(Object value) {
		if (value instanceof Runnable) {
			return (((Runnable)value)::run);
		}
		else if (value instanceof Supplier) {
			return (() -> ((Supplier<?>)value).get());
		}
		else if (value instanceof Consumer) {
			return (() -> ((Consumer<?>)value).accept(null));
		}
		else if (value instanceof Function) {
			return (() -> ((Function<?, ?>)value).apply(null));
		}
		else if (value instanceof Predicate) {
			return (() -> ((Predicate<?>)value).test(null));
		}
		else if (value instanceof Action) {
			return ((Action) value);
		}

		return null;
	}
	
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

			if (str.length() > 10) {
				str = str.substring(0, 9).trim();
			
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
	
	
	public static String trimEnd(String text) {
		if (text == null) {
			return "";
		}
		
		int lastIndex = text.length() - 1;
		int i = lastIndex;
		
		while (i >= 0 && Character.isWhitespace(text.charAt(i))) {
			i--;
		}
		
		if (i <= 0) {
			return "";
		}
		else if (i != lastIndex) {
			return text.substring(0, i + 1);
		}
		
		return text;
	}
	
	public static String bufferedPrinting(Consumer<PrintWriter> consumer) {
		try(
			StringWriter stringWriter = new StringWriter();
	        PrintWriter writer = new PrintWriter(stringWriter);
		) {
			consumer.accept(writer);
			
	        return stringWriter.toString();
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getStackTrace(Throwable error) {
		return bufferedPrinting(writer -> {
			error.printStackTrace(writer);
		});
	}

	public static String[] split(String input, char c) {
		if (input == null) {
			return new String[0];
		}
		
		return input.split(Pattern.quote(String.valueOf(c)));
	}

	public static String lastPart(String text, int length) {
		if (text.length() > length) {
			return text.substring(text.length() - length, text.length());
		}
		
		return text;
	}
	
	public static String repeat(char c, int times) {
		StringBuilder output = new StringBuilder();
		
		for (int i = 0; i < times; i++) {
			output.append(c);
		}
		
		return output.toString();
	}

}
