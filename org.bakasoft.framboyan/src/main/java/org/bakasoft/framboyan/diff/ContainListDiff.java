package org.bakasoft.framboyan.diff;

import java.util.List;

import org.bakasoft.framboyan.util.JSON;
import org.bakasoft.framboyan.util.Inspector;

public class ContainListDiff implements Diff {

	private final List<?> set;
	private final List<?> subset;
	
	public ContainListDiff(List<?> set, List<?> subset) {
		this.set = set;
		this.subset = subset;
	}

	@Override
	public boolean test() {
		return set.containsAll(subset);
	}

	@Override
	public String generateExpectMessage() {
		return String.format("Expected %s to contain %s.", 
				Inspector.inspect(set), 
				Inspector.inspect(subset));
	}

	@Override
	public String generateNotExpectMessage() {
		return String.format("Expected %s to NOT contain %s.", 
				Inspector.inspect(set), 
				Inspector.inspect(subset));
	}

	@Override
	public String generateActualValue() {
		return JSON.stringify(set);
	}

	@Override
	public String generateExpectedValue() {
		return JSON.stringify(subset);
	}
	
	// TODO add detailed message

}
