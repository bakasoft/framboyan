package org.bakasoft.framboyan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RunnerTest extends Framboyan {{

	describe(Runner.class, () -> {
		it("should run the tests and notify the progress", () -> {
			template((Boolean result, Integer passed, Integer pending, Integer failed, String input) -> {
				Group rootGroup = new Group("Root", Mode.NORMAL);
				
				TestTool.generateTargets(input, rootGroup);
				
				Node rootNode = new Node();
				
				rootNode.addNode(rootGroup.buildInto(rootNode));
				
				// filter node with actions
				List<Node> nodes = rootNode.findNodes(node -> node.getAction() != null);
				
				ArrayList<Result> results = new ArrayList<>();
				ArrayList<Node> started = new ArrayList<Node>();
				ArrayList<Node> completed = new ArrayList<Node>();
				AtomicInteger actualOverallResult = new AtomicInteger(-1);
				AtomicInteger actualTotalPasseed = new AtomicInteger(-1);
				AtomicInteger actualTotalPending = new AtomicInteger(-1);
				AtomicInteger actualTotalFailed = new AtomicInteger(-1);
				
				boolean actualResult = new Runner() {

					@Override
					public void started(Node node) {
						started.add(node);
					}

					@Override
					public void completed(Node node, Result result) {
						completed.add(node);
						results.add(result);
					}

					@Override
					public void summary(boolean overallResult, int totalPassed, int totalPending, int totalFailed) {
						actualOverallResult.set(overallResult ? 1 : 0);
						actualTotalPasseed.set(totalPassed);
						actualTotalPending.set(totalPending);
						actualTotalFailed.set(totalFailed);
					}
				}.run(rootNode);
				
				expect(actualOverallResult.get()).toBe(result ? 1 : 0);
				expect(actualTotalPasseed.get()).toBe(passed);
				expect(actualTotalPending.get()).toBe(pending);
				expect(actualTotalFailed.get()).toBe(failed);

				expect(started).toContain(nodes);
				expect(completed).toContain(nodes);
				expect(actualResult).toBe(result);
				
				expect(results.stream().filter(item -> item.isSuccessful()).count()).toBe(passed);
				expect(results.stream().filter(item -> item.isPending()).count()).toBe(pending);
				expect(results.stream().filter(item -> !item.isSuccessful() && !item.isPending()).count()).toBe(failed);
			})
			//    Result | Pass | Pend | Fail | Input 
			.test(false  , 0    , 0    , 0    , "")
			.test(true   , 1    , 0    , 0    , "ns")
			.test(true   , 1    , 2    , 0    , "ns xs xs")
			.test(false  , 1    , 2    , 3    , "ns xs xs ne ne ne")
			.test(true   , 4    , 6    , 0    , "ns xs xs ne ne ne fs fs fs fs")
			.test(false  , 3    , 2    , 1    , "x( ns ns ) n( ns ns ns ne )")
			.test(true   , 1    , 2    , 0    , "x( ns ) n( ns ) f( ns )")
			;
		});
	});
	
}}
