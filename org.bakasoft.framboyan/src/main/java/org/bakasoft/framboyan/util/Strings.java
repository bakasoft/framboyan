package org.bakasoft.framboyan.util;

import java.util.function.Function;
import java.util.regex.Pattern;

public class Strings {

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

    public static <T> String join(String separator, T[] items, Function<T, String> mapper) {
	    return join(separator, separator, items, mapper);
    }

    public static <T> String join(String separator, String lastSeparator, T[] items, Function<T, String> mapper) {
		if (items == null) {
		    return "";
		}

	    StringBuilder output = new StringBuilder();

		for (int i = 0; i < items.length; i++) {
		    if (i > 0) {
		        if (i == items.length - 1) {
		            output.append(lastSeparator);
                }
                else {
		            output.append(separator);
                }
            }

            output.append(mapper.apply(items[i]));
        }

		return output.toString();
	}
}
