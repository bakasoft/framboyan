package org.bakasoft.framboyan.runners;

import java.io.PrintStream;

import org.bakasoft.framboyan.Spec;
import org.bakasoft.framboyan.Suite;
import org.bakasoft.framboyan.Result;
import org.bakasoft.framboyan.Runner;

public class PlainTextRunner extends Runner {

	private final PrintStream out;

	private String lastSuiteName;
	
	public PlainTextRunner() {
		this(System.out);
	}
	
	public PlainTextRunner(PrintStream out) {
		this.out = out;
	}

	@Override
	protected void testStarted(Suite suite, Spec spec) {
		String suiteName = suite.getName();
		
		if (suiteName == null || suiteName.isEmpty()) {
			suiteName = "Unnamed suite";
		}
		
		if (lastSuiteName == null || !suiteName.equals(lastSuiteName)) {
			out.println();
			out.println("⚙️ " + suiteName + "...");
		}
		
		lastSuiteName = suiteName;
	}

	@Override
	protected void testCompleted(Suite suite, Spec spec, Result result) {
		String specName = spec.getName();
		
		if (result.isSuccessful()) {
			out.println("  ✅ " + specName);
		} else if (result.isPending()) {
			out.println("  ⚠️ " + specName);
		} else {
			Throwable error = result.getError();
			String output = result.getOutput();
			
			out.println();
			out.println("  ❌ " + specName);
			
			if (output != null && !output.isEmpty()) {
				out.println(output);
				out.println();
			}
			
			if (error != null) {
				error.printStackTrace(out);
			}
		}
	}

	@Override
	protected void testSummary(boolean overallResult, int totalPassed, int totalPending, int totalFailed) {
		out.println();
		
		if (overallResult) {
			out.println("OK");
		} else {
			out.println("FAILED");
		}
		
		if (totalFailed == 0 && totalPending == 0) {
			out.println(totalPassed + " test(s), all passed ✨");
		} else if (totalPassed == 0 && totalPending == 0) {
			out.println(totalFailed + " test(s), all failed ☠️");
		} else {
			out.println((totalPassed + totalFailed + totalPending) + " test(s), " + totalPassed + " passed, " + totalPending + " pending, " + totalFailed + " failed ⚠️");
		}
	}

}
