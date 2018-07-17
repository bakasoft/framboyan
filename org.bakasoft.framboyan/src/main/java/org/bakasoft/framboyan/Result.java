package org.bakasoft.framboyan;

import org.bakasoft.framboyan.console.ConsoleEvent;

public class Result {

	private final boolean successful;
	private final boolean pending;
	private final Throwable error;
	private final ConsoleEvent[] output;
	
	public Result(boolean successful, boolean pending, Throwable error, ConsoleEvent[] output) {
		this.successful = successful;
		this.pending = pending;
		this.error = error;
		this.output = output;
	}
	
	public boolean isSuccessful() {
		return successful;
	}
	
	public boolean isPending() {
		return pending;
	}

	public Throwable getError() {
		return error;
	}
	
	public ConsoleEvent[] getOutput() {
		return output;
	}
	
}
