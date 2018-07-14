package org.bakasoft.framboyan.expect;

import java.util.function.Consumer;

public interface ExpectTemplate {

	/* similarity */
	
	void toEqual(Object expected);
	
	void toBe(Object expected);
	
	void toBeNull();
	
	void toBeTrue();
	
	void toBeFalse();
	
	/* types */
	
	void toBeInstanceOf(Class<?> expectedType);
	
	void toBeOfClass(Class<?> expectedType);
	
	/* matching */
	
	void toMatch(Object pattern);
	
	void toBeCloseTo(Object expected, Object delta);
	
	/* collections | strings | numbers */

	void toBeGreaterThan(Object expected);
	
	void toBeLessThan(Object expected);
	
	void toBeEmpty();
	
	void toContain(Object expected);
	
	void toContain(Object... expected);
	
	void toStartWith(Object prefix);
	
	void toEndWith(Object suffix);
	
	/* error handling */

	default void toThrowException() {
		toThrow(null);
	}
	
	default <T extends Exception> void toThrow(Class<T> expectedType) {
		toThrow(expectedType, null);
	}

	<T extends Exception> void toThrow(Class<T> expectedType, Consumer<T> details);

}
