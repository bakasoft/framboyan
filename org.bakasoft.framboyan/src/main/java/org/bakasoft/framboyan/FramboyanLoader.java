package org.bakasoft.framboyan;

import java.util.ArrayList;
import java.util.List;

import org.bakasoft.framboyan.plainText.PlainTextRunner;
import org.bakasoft.framboyan.util.Reflection;

public class FramboyanLoader {
	
	public static List<Target> findTargets() {
		ArrayList<Target> targets = new ArrayList<>();
		
		for (Class<? extends Target> type : Reflection.findAssignableTo(Target.class)) {
			if (Reflection.hasEmptyConstructor(type)) {
				Target target = Reflection.createInstance(type);
				
				targets.add(target);
			}
		}
		
		return targets;
	}
	
	public static boolean run() {
		return run(new PlainTextRunner());
	}
	
	public static boolean run(Runner runner) {
		List<Target> targets = findTargets();
	
		return runner.run(targets);
	}
	
}
