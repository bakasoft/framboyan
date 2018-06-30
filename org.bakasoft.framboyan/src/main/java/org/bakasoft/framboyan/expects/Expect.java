package org.bakasoft.framboyan.expects;

import org.bakasoft.framboyan.Action;
import org.bakasoft.framboyan.Debugger;

public interface Expect {

	void toBe(Object expected);

	void toBeInstanceOf(Class<?> type);
	
	void toThrow(Class<? extends Throwable> errorType);
	
	default void toThrow() {
		toThrow(null);
	}
	
	public static Action toAction(Object obj) {
		if (obj instanceof Action) {
			return (Action) obj;
		} else {
			throw new AssertionError(String.format("%s expected to implement the %s", Debugger.inspect(obj), Action.class));
		}
	}
	
}
