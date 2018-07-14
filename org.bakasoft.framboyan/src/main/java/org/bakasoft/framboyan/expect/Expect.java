package org.bakasoft.framboyan.expect;

import java.util.function.Consumer;

import org.bakasoft.framboyan.Action;
import org.bakasoft.framboyan.diff.CloseToDiff;
import org.bakasoft.framboyan.diff.CompareDiff;
import org.bakasoft.framboyan.diff.ContainDiff;
import org.bakasoft.framboyan.diff.EmptyDiff;
import org.bakasoft.framboyan.diff.EndWithDiff;
import org.bakasoft.framboyan.diff.EqualDiff;
import org.bakasoft.framboyan.diff.InstanceOfDiff;
import org.bakasoft.framboyan.diff.MatchDiff;
import org.bakasoft.framboyan.diff.SameClassDiff;
import org.bakasoft.framboyan.diff.SimilarDiff;
import org.bakasoft.framboyan.diff.StartWithDiff;
import org.bakasoft.framboyan.util.Normalizer;

public class Expect implements ExpectTemplate {

	private final Object actual;
	
	public final NotExpect not;
	
	public Expect(Object actual) {
		this.actual = actual;
		this.not = new NotExpect(actual);
	}
	
	/* similarity */
	
	@Override
	public void toEqual(Object expected) {
		new EqualDiff(actual, expected).expect();
	}
	
	@Override
	public void toBe(Object expected) {
		new SimilarDiff(actual, expected).expect();
	}
	
	@Override
	public void toBeNull() {
		new EqualDiff(actual, null).expect();
	}
	
	@Override
	public void toBeTrue() {
		new EqualDiff(actual, true).expect();
	}
	
	@Override
	public void toBeFalse() {
		new EqualDiff(actual, false).expect();
	}
	
	/* types */
	
	@Override
	public void toBeInstanceOf(Class<?> expectedType) {
		new InstanceOfDiff(actual, expectedType).expect();
	}
	
	@Override
	public void toBeOfClass(Class<?> expectedType) {
		new SameClassDiff(actual, expectedType).expect();
	}
	
	/* matching */
	
	@Override
	public void toMatch(Object pattern) {
		new MatchDiff(actual, pattern).expect();
	}
	
	@Override
	public void toBeCloseTo(Object expected, Object delta) {
		new CloseToDiff(actual, expected, delta).expect();
	}
	
	/* collections | strings | numbers */

	@Override
	public void toBeGreaterThan(Object expected) {
		new CompareDiff(actual, expected, ">").expect();
	}
	
	@Override
	public void toBeLessThan(Object expected) {
		new CompareDiff(actual, expected, "<").expect();
	}
	
	@Override
	public void toBeEmpty() {
		new EmptyDiff(actual).expect();
	}
	
	@Override
	public void toContain(Object expected) {
		new ContainDiff(actual, expected).expect();
	}
	
	@Override
	public void toContain(Object... expected) {
		new ContainDiff(actual, expected).expect();
	}

	@Override
	public void toStartWith(Object prefix) {
		new StartWithDiff(actual, prefix).expect();
	}
	
	@Override
	public void toEndWith(Object suffix) {
		new EndWithDiff(actual, suffix).expect();
	}
	
	@Override
	public <T extends Exception> void toThrow(Class<T> expectedType, Consumer<T> details) {
		// TODO: refactor this mess
		Action action = Normalizer.toAction(actual);
		Exception e;
		
		try {
			action.run();
			e = null;
		}
		catch (Exception exception) {
			e = exception;
		}
		
		if (e == null) {
			if (expectedType == null) {
				throw new AssertionError();	
			}
			else {
				throw new AssertionError();	 // TODO: expectedType
			}
		}
		else if (expectedType != null && !expectedType.isInstance(e)) {
			throw new AssertionError(); // TODO: expectedType, e
		}
		
		if (details != null && expectedType != null && e != null) {
			details.accept(expectedType.cast(e));	
		}
	}
	
}
