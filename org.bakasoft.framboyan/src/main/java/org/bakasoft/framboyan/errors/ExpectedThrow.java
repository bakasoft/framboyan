package org.bakasoft.framboyan.errors;

public class ExpectedThrow extends AssertionError {

	private static final long serialVersionUID = 4346475000051409110L;

	public ExpectedThrow(Class<? extends Throwable> errorType, Throwable error) {
		super(String.format("Expected to throw %s instead of %s.", errorType, error));
	}

	public ExpectedThrow(Class<? extends Throwable> errorType) {
		super(String.format("Expected to throw %s.", errorType));
	}

	public ExpectedThrow() {
		super("Expected to throw any error.");
	}
	
}
