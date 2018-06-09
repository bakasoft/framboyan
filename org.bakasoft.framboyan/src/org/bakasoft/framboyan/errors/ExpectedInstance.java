package org.bakasoft.framboyan.errors;

import org.bakasoft.framboyan.Debugger;

public class ExpectedInstance extends AssertionError {

	private static final long serialVersionUID = -4970655768294801968L;

	public ExpectedInstance(Object actual, Class<?> type) {
		super(String.format("%s expected to be an instance of %s.", 
				Debugger.inspect(actual),
				type.getCanonicalName()));
	}
}
