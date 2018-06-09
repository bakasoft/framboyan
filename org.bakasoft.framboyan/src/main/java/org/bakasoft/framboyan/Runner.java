package org.bakasoft.framboyan;

import java.util.List;

abstract public class Runner {

	abstract protected void testStarted(Suite suite, Spec spec);
	abstract protected void testCompleted(Suite suite, Spec spec, Result result);
	abstract protected void testSummary(boolean overallResult, int totalPassed, int totalPending, int totalFailed);
	
	public boolean run(List<Suite> suites) {
		int totalPassed = 0;
		int totalFailed = 0;
		int totalPending = 0;
		
		for (Suite suite : suites) {
			for (Spec spec : suite.getSpecs()) {
				testStarted(suite, spec);
				
				Result result = spec.execute();
				
				testCompleted(suite, spec, result);
				
				if (result.isSuccessful()) {
					totalPassed++;
				} else if (result.isPending()) {
					totalPending++;
				} else {
					totalFailed++;
				}
			}
		}
		
		boolean overallResult = (totalPassed > 0 && totalFailed == 0);
		
		testSummary(overallResult, totalPassed, totalPending, totalFailed);
		
		return overallResult;
	}

}
