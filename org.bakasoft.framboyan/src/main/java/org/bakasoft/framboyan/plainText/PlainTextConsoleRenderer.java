package org.bakasoft.framboyan.plainText;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.IllegalFormatException;

import org.bakasoft.framboyan.console.ConsoleEvent;
import org.bakasoft.framboyan.console.ConsoleLog;
import org.bakasoft.framboyan.console.ConsoleRenderer;

public class PlainTextConsoleRenderer implements ConsoleRenderer {

	public void renderLog(ConsoleLog log, PrintStream out) {
		// TODO implement JS console rules
		Object[] items = log.getItems();
		if (items != null && items.length > 0) {
			if (items.length >= 2 && items[0] instanceof String) {
				String format = (String)items[0];
				
				if (format.contains("%")) {
					try {
						String message = String.format(format, Arrays.copyOfRange(items, 1, items.length));	
						
						out.println(message);
						return;
					}
					catch (IllegalFormatException e) {
						// ignore
					}	
				}
			}
			
			for (Object item : items) {
				out.print(item);
			}
		}
		
		out.println();
	}

	@Override
	public void render(ConsoleEvent event, PrintStream out) {
		if (event instanceof ConsoleLog) {
			renderLog((ConsoleLog)event, out);
		}
		else {
			out.println(event.toString());
		}
	}
	
}
