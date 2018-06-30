package org.bakasoft.framboyan.expects;

import java.util.Objects;

import org.bakasoft.framboyan.Action;
import org.bakasoft.framboyan.errors.NotExpectedEqual;
import org.bakasoft.framboyan.errors.NotExpectedInstance;
import org.bakasoft.framboyan.errors.NotExpectedNull;
import org.bakasoft.framboyan.errors.NotExpectedThrow;

public class NegativeExpect implements Expect {

	private final Object actual;

	public NegativeExpect(Object actual) {
		this.actual = actual;
	}
	
	@Override
	public void toBe(Object expected) {
		if (expected == null && actual == null) {
			throw new NotExpectedNull(actual);
		}
		else if (Objects.equals(actual, expected)) {
			throw new NotExpectedEqual(actual, expected);
		}
	}

	@Override
	public void toBeInstanceOf(Class<?> type) {
		if (type.isInstance(actual)) {
			throw new NotExpectedInstance(actual, type);
		}
	}

	@Override
	public void toThrow(Class<? extends Throwable> errorType) {
		Action action = Expect.toAction(actual);
		
		try {
			action.run();
		} catch (Throwable e) {
			if (errorType == null) {
				throw new NotExpectedThrow(e);	
			} 
			else if (errorType.isInstance(e)) {
				throw new NotExpectedThrow(e, errorType);	
			}
		}
	}
	
}
