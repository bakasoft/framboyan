package org.bakasoft.framboyan.errors;

public class NotExpectedThrow extends AssertionError {

	private static final long serialVersionUID = 6027638028583750486L;

	public NotExpectedThrow(Throwable error, Class<? extends Throwable> errorType) {
		super(String.format("Not expected error %s to be an instance of %s.", error, errorType));
	}

	public NotExpectedThrow(Throwable error) {
		super(String.format("Not Expected error %s.", error));
	}

}
