package org.bakasoft.framboyan;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.bakasoft.framboyan.expects.PositiveExpect;
import org.bakasoft.framboyan.runners.PlainTextRunner;
import org.bakasoft.framboyan.templates.Template1;
import org.bakasoft.framboyan.templates.Template1Action;
import org.bakasoft.framboyan.templates.Template2;
import org.bakasoft.framboyan.templates.Template2Action;
import org.bakasoft.framboyan.templates.Template3;
import org.bakasoft.framboyan.templates.Template3Action;
import org.bakasoft.framboyan.templates.Template4;
import org.bakasoft.framboyan.templates.Template4Action;
import org.bakasoft.framboyan.templates.Template5;
import org.bakasoft.framboyan.templates.Template5Action;

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
	
	public static void it(String name, MutedAction action) {
		addSpec(new Spec(name, action));
	}
	
	public static void it(String name, Action action) {
		addSpec(new Spec(name, action));
	}
	
	public static void xit(String name) {
		addSpec(new Spec(name, null));
	}
	
	public static void xit(String name, MutedAction action) {
		addSpec(new Spec(name, null));
	}
	
	public static void xit(String name, Action action) {
		addSpec(new Spec(name, null));
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
	
	// Templates 1, 2, 3, 4 and 5
	
	public static <T1> Template1<T1> template(Template1Action<T1> action) {
		return new Template1<>(action);
	}
	
	public static <T1, T2> Template2<T1, T2> template(Template2Action<T1, T2> action) {
		return new Template2<>(action);
	}
	
	public static <T1, T2, T3> Template3<T1, T2, T3> template(Template3Action<T1, T2, T3> action) {
		return new Template3<>(action);
	}
	
	public static <T1, T2, T3, T4> Template4<T1, T2, T3, T4> template(Template4Action<T1, T2, T3, T4> action) {
		return new Template4<>(action);
	}
	
	public static <T1, T2, T3, T4, T5> Template5<T1, T2, T3, T4, T5> template(Template5Action<T1, T2, T3, T4, T5> action) {
		return new Template5<>(action);
	}
	
}
