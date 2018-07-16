package org.bakasoft.framboyan.diff;

import org.bakasoft.framboyan.util.Inspector;

public interface Diff {

	boolean test();
	
	String generateExpectMessage();
	
	String generateNotExpectMessage();
	
	String generateActualValue();
	
	String generateExpectedValue();
	
	default String generateDifference() {
		// TODO: same diff message when `notExpect`?
		String actual = generateActualValue();
		String expected = generateExpectedValue();
		
		return Inspector.generateDiff(actual, expected);
	}
	
	default void expect() {
		if (!test()) {
			String message = generateExpectMessage();
			String difference = generateDifference();
			
			throw new DiffError(message, difference);
		}
	}
	
	default void notExpect() {
		if (test()) {
			String message = generateNotExpectMessage();
			String difference = generateDifference();
			
			throw new DiffError(message, difference);
		}
	}
}
