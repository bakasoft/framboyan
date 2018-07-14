package org.bakasoft.framboyan;

import java.util.ArrayList;
import java.util.List;

import org.bakasoft.framboyan.util.Reflection;

public interface Runner {

	void started(Node spec);
	void completed(Node spec, Result result);
	void summary(boolean overallResult, int totalPassed, int totalPending, int totalFailed);
	
	default boolean run(Class<?>... types) {
		ArrayList<Target> targets = new ArrayList<>();
		
		for (Class<?> type : types) {
			if (Target.class.isAssignableFrom(type)) {
				if (Reflection.hasEmptyConstructor(type)) {
					Target target = Reflection.createInstance(type, Target.class);
					
					targets.add(target);
				}
				else {
					throw new RuntimeException();	
				}
			}
			else {
				throw new RuntimeException();
			}
		}
		
		return run(targets);
	}
	
	default boolean run(Target target) {
		Node node = target.buildInto(null);
		
		return run(node);
	}
	
	default boolean run(List<Target> targets) {
		Node root = new Node();
		
		for (Target target : targets) {
			Node node = target.buildInto(root);
			
			root.addNode(node);
		}
		
		return run(root);
	}
	
	default boolean run(Node root) {
		List<Node> specs = root.findNodes(node -> node.getAction() != null);
		boolean onlyFocused = root.containFocus();
		int totalPassed = 0;
		int totalFailed = 0;
		int totalPending = 0;
		
		for (Node spec : specs) {
			started(spec);
			
			Result result = spec.run(onlyFocused);
			
			completed(spec, result);
			
			if (result.isSuccessful()) {
				totalPassed++;
			} else if (result.isPending()) {
				totalPending++;
			} else {
				totalFailed++;
			}
		}
		
		boolean overallResult = (totalPassed > 0 && totalFailed == 0);
		
		summary(overallResult, totalPassed, totalPending, totalFailed);
		
		return overallResult;
	}

}
