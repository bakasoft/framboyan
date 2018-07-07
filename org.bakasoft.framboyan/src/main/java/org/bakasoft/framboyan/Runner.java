package org.bakasoft.framboyan;

import java.util.List;

import org.bakasoft.framboyan.util.Toolbox;

abstract public class Runner {

	abstract protected void testStarted(Spec spec);
	abstract protected void testCompleted(Spec spec, Result result);
	abstract protected void testSummary(boolean overallResult, int totalPassed, int totalPending, int totalFailed);
	
	public boolean run(Target target) {
		int totalPassed = 0;
		int totalFailed = 0;
		int totalPending = 0;
		
		List<Spec> specs = Toolbox.listSpecs(target);
		
		for (Spec spec : specs) {
			testStarted(spec);
			
			Result result = spec.execute();
			
			testCompleted(spec, result);
			
			if (result.isSuccessful()) {
				totalPassed++;
			} else if (result.isPending()) {
				totalPending++;
			} else {
				totalFailed++;
			}
		}
		
		boolean overallResult = (totalPassed > 0 && totalFailed == 0);
		
		testSummary(overallResult, totalPassed, totalPending, totalFailed);
		
		return overallResult;
	}

}
