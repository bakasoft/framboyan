package org.bakasoft.framboyan.diff;

import org.bakasoft.framboyan.util.Inspector;

public class EqualDiff implements Diff {

	private final Object actual;
	private final Object expected;
	
	public EqualDiff(Object actual, Object expected) {
		this.actual = actual;
		this.expected = expected;
	}

	public Object getActual() {
		return actual;
	}

	public Object getExpected() {
		return expected;
	}
	
	public boolean test() {
		return (actual == expected) || (actual != null && actual.equals(expected));
	}

	@Override
	public String generateExpectMessage() {
		return String.format("Expected %s to equal to %s.", 
				Inspector.inspect(actual), 
				Inspector.inspect(expected));
	}

	@Override
	public String generateNotExpectMessage() {
		return String.format("Expected %s to NOT equal to %s.", 
				Inspector.inspect(actual), 
				Inspector.inspect(expected));
	}
	
	@Override
	public String generateActualValue() {
		return Inspector.hash(actual) + " : " + Inspector.typeFull(actual);
	}

	@Override
	public String generateExpectedValue() {
		return Inspector.hash(expected) + " : " + Inspector.typeFull(expected);
	}
	
}
