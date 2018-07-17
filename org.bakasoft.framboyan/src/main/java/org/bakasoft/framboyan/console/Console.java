package org.bakasoft.framboyan.console;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.bakasoft.framboyan.console.ConsoleLog.Type;

public class Console {
	
	private final ArrayList<ConsoleEvent> events;
	
	public Console() {
		events = new ArrayList<>();
	}

	synchronized public void log(Object... args) {
		events.add(new ConsoleLog(LocalDateTime.now(), Type.LOG, args));
	}

	synchronized public void error(Object... args) {
		events.add(new ConsoleLog(LocalDateTime.now(), Type.ERROR, args));
	}

	synchronized public void info(Object... args) {
		events.add(new ConsoleLog(LocalDateTime.now(), Type.INFO, args));
	}

	synchronized public void warn(Object... args) {
		events.add(new ConsoleLog(LocalDateTime.now(), Type.WARN, args));
	}
	
	synchronized public ConsoleEvent[] getEvents() {
		return events.toArray(new ConsoleEvent[events.size()]);	
	}
	
	synchronized public void clear() {
		events.clear();
	}
	
	synchronized public ConsoleEvent[] popEvents() {
		ConsoleEvent[] array = getEvents();	

		clear();
		
		return array;
	}

}
