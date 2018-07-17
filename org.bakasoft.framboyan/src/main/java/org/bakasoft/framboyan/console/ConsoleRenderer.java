package org.bakasoft.framboyan.console;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public interface ConsoleRenderer {

	void render(ConsoleEvent event, PrintStream out);
	
	default void render(ConsoleEvent[] events, PrintStream out) {
		for (ConsoleEvent event : events) {
			render(event, out);
		}
	}
	
	default String render(ConsoleEvent... events) {
		String encoding = "UTF-8";
		try (
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			PrintStream out = new PrintStream(buffer, true, encoding);
		) {
			for (ConsoleEvent event : events) {
				render(event, out);
			}	
			
			return buffer.toString(encoding);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
