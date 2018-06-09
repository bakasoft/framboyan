package org.bakasoft.framboyan.errors;

import org.bakasoft.framboyan.Debugger;

public class NotExpectedInstance extends AssertionError {

	private static final long serialVersionUID = -7745469632988312283L;

	public NotExpectedInstance(Object actual, Class<?> type) {
		super(String.format("%s not expected to be an instance of %s.", 
				Debugger.inspect(actual),
				type.getCanonicalName()));
	}
}
