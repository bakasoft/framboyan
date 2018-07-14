package org.bakasoft.framboyan.runners;

import java.io.PrintStream;

import org.bakasoft.framboyan.util.Toolbox;
import org.bakasoft.framboyan.Node;
import org.bakasoft.framboyan.Result;
import org.bakasoft.framboyan.Runner;
import org.bakasoft.framboyan.diff.DiffError;

public class PlainTextRunner implements Runner {

	private final PrintStream out;

	private String lastGroupSubject;
	
	public PlainTextRunner() {
		this(System.out);
	}
	
	public PlainTextRunner(PrintStream out) {
		this.out = out;
	}

	@Override
	public void started(Node node) {
		String groupSubject = node.hasParent() ? node.getParent().joinDescriptions() : "";
		
		if (lastGroupSubject == null || !groupSubject.equals(lastGroupSubject)) {
			out.println();
			out.println(groupSubject + " ⚙️");
		}
		
		lastGroupSubject = groupSubject;
	}

	@Override
	public void completed(Node node, Result result) {
		String specSubject = String.valueOf(node.getDescription());
		
		if (result.isSuccessful()) {
			out.println("  " + specSubject + " ✅");
		} else if (result.isPending()) {
			out.println("  " + specSubject + " ⚠️");
		} else {
			out.println("  " + specSubject + " ❌");
		
			String output = result.getOutput();
			if (output != null && !output.isEmpty()) {
				out.println();
				out.println(Toolbox.trimEnd(output));
			}

			Throwable error = result.getError();
			if (error != null) {
				if (error instanceof DiffError) {
					DiffError diff = (DiffError)error;
					
					if (diff.getDifference() != null && !diff.getDifference().isEmpty()) {
						out.println();
						out.println(Toolbox.trimEnd(diff.getDifference()));	
					}
				}
				
				String stackTrace = Toolbox.getStackTrace(error);
				
				out.println();
				out.println(Toolbox.trimEnd(stackTrace));
			}
			
			out.println();
		}
	}

	@Override
	public void summary(boolean overallResult, int totalPassed, int totalPending, int totalFailed) {
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
