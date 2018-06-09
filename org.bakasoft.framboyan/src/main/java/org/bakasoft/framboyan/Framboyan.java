package org.bakasoft.framboyan;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.bakasoft.framboyan.expects.PositiveExpect;
import org.bakasoft.framboyan.runners.PlainTextRunner;

public class Framboyan {
	
	private static final ArrayList<Suite> suites;
	
	private static Suite currentSuite;
	
	static {
		suites = new ArrayList<>();
	}
	
	private static void addSpec(Spec spec) {
		Suite suite = currentSuite;
		
		if (suite == null) {
			suite = new Suite(null);
		}
		
		suite.addSpec(spec);
	}
	
	public static void describe(String name) {
		describe(name, null);
	}
	
	public static void describe(String name, Runnable content) {
		Suite suite = new Suite(name);
		
		suites.add(suite);
		
		currentSuite = suite;
		
		if (content != null) {
			content.run();
		}
	}
	
	public static void it(String name) {
		addSpec(new Spec(name, null));
	}
	
	public static void it(String name, MutedAction action) {
		addSpec(new Spec(name, action));
	}
	
	public static void it(String name, Action action) {
		addSpec(new Spec(name, action));
	}
	
	public static PositiveExpect expect(MutedAction action) {
		return new PositiveExpect(action);
	}
	
	public static PositiveExpect expect(Object value) {
		return new PositiveExpect(value);
	}
	
	public static void fail() {
		throw new AssertionError();
	}

	public static void add(Class<? extends Framboyan> testType) {
		try {
			testType.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static boolean run() {
		return run(new PlainTextRunner());
	}
	
	public static boolean run(Runner runner) {
		return runner.run(suites);
	}
	
	public static String inspect(Object value) {
		return Debugger.inspect(value);
	}
	
}
