package org.bakasoft.framboyan.console;

import java.time.LocalDateTime;

public class ConsoleEvent {

	private final LocalDateTime dateTime;
	
	public ConsoleEvent(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
}
