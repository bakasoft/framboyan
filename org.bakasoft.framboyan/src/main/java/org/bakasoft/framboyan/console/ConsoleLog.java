package org.bakasoft.framboyan.console;

import java.time.LocalDateTime;

public class ConsoleLog extends ConsoleEvent {

	public static enum Type {
		LOG,
		ERROR,
		INFO,
		WARN
	}
	
	private final Type type;
	private final Object[] items;
	
	public ConsoleLog(LocalDateTime dateTime, Type type, Object[] items) {
		super(dateTime);
		this.type = type;
		this.items = items;
	}

	public Object[] getItems() {
		return items;
	}

	public Type getType() {
		return type;
	}
	
}
