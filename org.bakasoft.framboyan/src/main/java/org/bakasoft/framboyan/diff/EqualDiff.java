package org.bakasoft.framboyan.diff;

import org.bakasoft.framboyan.util.Toolbox;

public class EqualDiff extends AbstractDiff {

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
				Toolbox.inspect(actual), 
				Toolbox.inspect(expected));
	}

	@Override
	public String generateNotExpectMessage() {
		return String.format("Expected %s to NOT equal to %s.", 
				Toolbox.inspect(actual), 
				Toolbox.inspect(expected));
	}
	
	@Override
	public String generateActualValue() {
		return Toolbox.hash(actual) + " : " + Toolbox.typeFull(actual);
	}

	@Override
	public String generateExpectedValue() {
		return Toolbox.hash(expected) + " : " + Toolbox.typeFull(expected);
	}
	
}
