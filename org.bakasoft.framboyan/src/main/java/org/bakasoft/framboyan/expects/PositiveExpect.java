package org.bakasoft.framboyan.expects;

import java.util.Objects;

import org.bakasoft.framboyan.Action;
import org.bakasoft.framboyan.errors.ExpectedEqual;
import org.bakasoft.framboyan.errors.ExpectedInstance;
import org.bakasoft.framboyan.errors.ExpectedNull;
import org.bakasoft.framboyan.errors.ExpectedThrow;

public class PositiveExpect implements Expect {

	public final Expect not;
	
	private final Object actual;

	public PositiveExpect(Object actual) {
		this.actual = actual;
		this.not = new NegativeExpect(actual);
	}
	
	@Override
	public void toBe(Object expected) {
		if (expected == null && actual != null) {
			throw new ExpectedNull(actual);
		}
		else if (!Objects.equals(actual, expected)) {
			throw new ExpectedEqual(actual, expected);
		}
	}

	@Override
	public void toBeInstanceOf(Class<?> type) {
		if (!type.isInstance(actual)) {
			throw new ExpectedInstance(actual, type);
		}
	}

	@Override
	public void toThrow(Class<? extends Throwable> errorType) {
		Action action = Expect.toAction(actual);
		
		try {
			action.run();
			
			if (errorType != null) {
				throw new ExpectedThrow(errorType);
			} else {
				throw new ExpectedThrow();
			}
		} catch (Throwable e) {
			if (errorType != null && !errorType.isInstance(e)) {
				throw new ExpectedThrow(errorType, e);	
			}
		}
	}
	
}
