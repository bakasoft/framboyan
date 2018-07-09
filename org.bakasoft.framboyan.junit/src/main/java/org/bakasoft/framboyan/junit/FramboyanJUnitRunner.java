package org.bakasoft.framboyan.junit;

import java.util.HashMap;

import org.bakasoft.framboyan.Group;
import org.bakasoft.framboyan.Result;
import org.bakasoft.framboyan.Spec;
import org.bakasoft.framboyan.Target;
import org.bakasoft.framboyan.util.Reflection;
import org.bakasoft.framboyan.util.Toolbox;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class FramboyanJUnitRunner extends org.junit.runner.Runner {

	private final HashMap<Spec, Description> specs;
	private final Class<?> testClass;
	private final FramboyanJUnit suite;
	private final Description root;
	
	public FramboyanJUnitRunner(Class<?> testClass) {
		this.testClass = testClass;
		
		suite = Reflection.createDefaultInstance(testClass, FramboyanJUnit.class);
		
		specs = new HashMap<>();
		
		root = createSuite(suite.getRootGroup());
	}
	
	private Description createSuite(Group group) {
		String groupName = null;
		
		if (group.getParent() != null) {
			if (group.getSubject() != null) {
				groupName = group.getSubject().toString();	
			}
		}
		else {
			groupName = testClass.getName();
		}
		
		if (groupName == null || groupName.isEmpty()) {
			groupName = "(unamed group)";
		}
		
		Description suite = Description.createSuiteDescription(groupName);
		
		for (Target item : group.getItems()) {
			if (item instanceof Spec) {
				Spec spec = (Spec)item;
				String specName = Toolbox.joinSubjects(spec);
				Description test = Description.createTestDescription(testClass, specName);
				
				suite.addChild(test);
				
				specs.put(spec, test);
			}
			else if(item instanceof Group) {
				Description subsuite = createSuite((Group)item);
				
				suite.addChild(subsuite);
			}
			else {
				throw new RuntimeException(); // TODO not supported type
			}
		}
		
		return suite;
	}
	
	private Description of(Spec spec) {
		return specs.get(spec);
	}

	@Override
	public Description getDescription() {		
		return root;
	}

	@Override
	public void run(RunNotifier notifier) {
		new org.bakasoft.framboyan.Runner() {

			@Override
			protected void testStarted(Spec spec) {
				Description description = of(spec);
				
				notifier.fireTestStarted(description);
			}

			@Override
			protected void testCompleted(Spec spec, Result result) {
				Description description = of(spec);
				
				if (result.isPending()) {
					notifier.fireTestIgnored(description);
				} 
				else if (!result.isSuccessful()) {
					notifier.fireTestFailure(new Failure(description, result.getError()));
				}
				
				notifier.fireTestFinished(description);
			}

			@Override
			protected void testSummary(boolean overallResult, int totalPassed, int totalPending, int totalFailed) {
				// do nothing
			}
			
		}.run(suite.getRootGroup());
	}
	
}
