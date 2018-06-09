package org.bakasoft.framboyan.expects;

import org.bakasoft.framboyan.MutedAction;
import org.bakasoft.framboyan.Debugger;

public interface Expect {

	void toBe(Object expected);

	void toBeInstanceOf(Class<?> type);
	
	void toThrow(Class<? extends Throwable> errorType);
	
	default void toThrow() {
		toThrow(null);
	}
	
	public static MutedAction toMutedAction(Object obj) {
		if (obj instanceof MutedAction) {
			return (MutedAction) obj;
		} else {
			throw new AssertionError(String.format("%s expected to implement the %s", Debugger.inspect(obj), MutedAction.class));
		}
	}
	
}
