package org.bakasoft.framboyan;

import java.lang.reflect.InvocationTargetException;

import org.bakasoft.framboyan.runners.PlainTextRunner;
import org.bakasoft.framboyan.util.Reflection;

public class Framboyan extends Suite {
	
	private static final Group _root;
	private static final Console _console;
	
	private static boolean autoloadTests;
	
	static {
		autoloadTests = true;
		_console = new Console();
		_root = new Group(null, null, _console, false);
	}
	
	public Framboyan() {
		super(_console, _root);
	}
	
	private static void loadTests() {
		if (autoloadTests) {
			for (Class<? extends Framboyan> type : Reflection.findSubclasses(Framboyan.class)) {
				System.out.println("Evaluating " + type + "...");
				
				try {
					type.getConstructor().newInstance();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					throw new RuntimeException(e);
				}
			}
			
			autoloadTests = false;	
		}
	}
	
	public static boolean run() {
		return run(new PlainTextRunner());
	}
	
	public static boolean run(Runner runner) {
		loadTests();
		return runner.run(_root);
	}
	
}
