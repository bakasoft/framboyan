package org.bakasoft.framboyan;

public class Result {

	private final boolean successful;
	private final boolean pending;
	private final Throwable error;
	private final String output;
	
	public Result(boolean successful, boolean pending, Throwable error, String output) {
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
	
	public String getOutput() {
		return output;
	}
	
}
