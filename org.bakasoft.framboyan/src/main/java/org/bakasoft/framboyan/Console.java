package org.bakasoft.framboyan;

import java.util.Arrays;
import java.util.IllegalFormatException;

import org.bakasoft.framboyan.util.DefaultStringifier;
import org.bakasoft.framboyan.util.Stringifier;

public class Console {
	
	private final StringBuilder output;
	
	private final Stringifier stringifier;
	
	public Console() {
		this(new DefaultStringifier());
	}
	
	public Console(Stringifier stringifier) {
		this.output = new StringBuilder();
		this.stringifier = stringifier;
	}

	public void log(Object... args) {
		if (args != null && args.length > 0) {
			if (args.length >= 2 && args[0] instanceof String) {
				String format = (String)args[0];
				
				if (format.contains("%")) {
					try {
						String message = String.format(format, Arrays.copyOfRange(args, 1, args.length));	
						
						output.append(message);
						output.append(System.lineSeparator());
						return;
					}
					catch (IllegalFormatException e) {
						// ignore
					}	
				}
			}
			
			for (Object item : args) {
				output.append(item);
			}
		}
		
		output.append(System.lineSeparator());
	}
	
	public String consume() {
		String log = output.toString();
		
		output.setLength(0);
		
		return log;
	}

	public Stringifier getStringifier() {
		return stringifier;
	}
	
}
