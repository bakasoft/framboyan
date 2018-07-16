package org.bakasoft.framboyan.diff;

import org.bakasoft.framboyan.util.Inspector;
import org.bakasoft.framboyan.util.JSON;
import org.bakasoft.framboyan.util.Normalizer;

public class SimilarDiff implements Diff {

	private final Object actual;
	private final Object expected;
	
	public SimilarDiff(Object actual, Object expected) {
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
		Object actualN = Normalizer.normalize(actual);
		Object expectedN = Normalizer.normalize(expected);
		
		return (actualN == expectedN) || (actualN != null && actualN.equals(expectedN));
	}

	@Override
	public String generateExpectMessage() {
		return String.format("Expected %s to be %s.", 
				Inspector.inspect(actual), 
				Inspector.inspect(expected));
	}

	@Override
	public String generateNotExpectMessage() {
		return String.format("Expected %s to NOT be %s.", 
				Inspector.inspect(actual), 
				Inspector.inspect(expected));
	}
	
	@Override
	public String generateActualValue() {
		return JSON.stringify(actual);
	}

	@Override
	public String generateExpectedValue() {
		return JSON.stringify(expected);
	}
	
}
