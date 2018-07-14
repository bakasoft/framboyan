package org.bakasoft.framboyan.diff;

import org.bakasoft.framboyan.util.Inspector;

abstract public class AbstractDiff {

	abstract public boolean test();
	
	abstract public String generateExpectMessage();
	
	abstract public String generateNotExpectMessage();
	
	abstract public String generateActualValue();
	
	abstract public String generateExpectedValue();
	
	public String generateDifference() {
		// TODO: same diff message when `notExpect`?
		String actual = generateActualValue();
		String expected = generateExpectedValue();
		
		return Inspector.generateDiff(actual, expected);
	}
	
	public void expect() {
		if (!test()) {
			String message = generateExpectMessage();
			String difference = generateDifference();
			
			throw new DiffError(message, difference);
		}
	}
	
	public void notExpect() {
		if (test()) {
			String message = generateNotExpectMessage();
			String difference = generateDifference();
			
			throw new DiffError(message, difference);
		}
	}
}
